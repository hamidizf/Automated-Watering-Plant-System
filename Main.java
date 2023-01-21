package sample;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.io.IOException; //importing libraries


public class Main extends Application {
    private final static int MAX_MOISTURE_VALUE = 1 << 10;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        var sp = SerialPortService.getSerialPort("/dev/cu.usbserial-0001");//define the port
        var outputStream = sp.getOutputStream();
        var controller = new DataController(); // create the controller
         sp.addDataListener(controller);

        var pane = new BorderPane();//create pane

        var now = System.currentTimeMillis();
        var button = new Button("Pump");//create a new button
        button.setOnMousePressed(value ->{
            try{
                button.setText("On");
                outputStream.write(255);//send 255 byte value if the button pressed

            }catch(IOException e){
                e.printStackTrace();
            }
        });
        button.setOnMouseReleased(value ->{
            try{
                button.setText("Off");
                outputStream.write(0);//send 0 byte value if the button released
            }catch(IOException e){
                e.printStackTrace();
            }
        });
        var slider = new Slider();//create a new slider
        slider.setMin(0.0);//minimum value
        slider.setMax(10.0);//maximum value on slider

        slider.valueProperty().addListener((observableValue, oldValue, newValue)->{

            try{
                outputStream.write(newValue.byteValue());
                if (newValue.byteValue()>5)
                {
                    outputStream.write(128);//if the value on slider is over 5,send 128 byte value(to turn the led on and off)
                }

            }catch(IOException e){
                e.printStackTrace();
            }
        });
        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> {

        });
        var xAxis = new NumberAxis("Time ", now, now + 50000, 10000); // creates the x-axis (which automatically updates)
        var yAxis = new NumberAxis("Moisture Data", 0, MAX_MOISTURE_VALUE, 10); // creates the y-axis
        var series = new XYChart.Series<>(controller.getDataPoints()); // creates the series (all the data)
        var lineChart = new LineChart<>(xAxis, yAxis, FXCollections.singletonObservableList(series)); // creates the chart
        lineChart.setTitle("Moisture Value Vs Time");
        pane.setTop(button);//put the button on top of the pane
        pane.setCenter(slider);//slider in the center
        pane.setBottom(lineChart);//line chart at the bottom
        pane.setPadding(new Insets(0, 20, 0, 20));
        var scene = new Scene(pane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}