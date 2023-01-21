#include <Arduino.h>
#include <U8x8lib.h>
#define MOSFET 2 // The MOSFET driver for the water pump on digital I/O 2
#define REDLED 4 // Big red LED on digital I/O 4
#define MOISTURE A1
#define WET_THRESH 600
auto display = U8X8_SSD1306_128X64_NONAME_HW_I2C(U8X8_PIN_NONE);
const unsigned long number = 1000;
const unsigned long number2 = 8000;//8 second manual system
unsigned long previousTime = 0;
unsigned long previousTime_2 = 0;
int moistureValue = 0;
int Integer=0;
void setup() {
 Serial.begin(9600);
 display.begin();
 display.setFlipMode(1);
 display.clearDisplay();
 pinMode(MOSFET, OUTPUT); // Sets the D2 pin (MOSFET + Pump) to output
 pinMode(REDLED, OUTPUT); // Sets the D4 pin (LED) to output
}
void loop()
{  
  unsigned long currentTime = millis(); 
 if (currentTime - previousTime >= number){
     sendMoistureData();//send moisture value to java
     previousTime = currentTime;
 }
 if (Serial.available()){
  unsigned long current= millis();
  Integer=1;
 if (!Serial.available()){//java manual system
    return;
  }
  const auto receivedData = Serial.read();
  display.setFont(u8x8_font_profont29_2x3_r);
 display.setCursor(0, 0);
 char buf[16];
 sprintf(buf, "%03d", receivedData);
  display.print(buf);
      if (receivedData == 255)
      {
        digitalWrite(MOSFET,HIGH);//turn the pump on
      }
      else if (receivedData == 128)
      {
        digitalWrite(REDLED,HIGH); //turn the RED LED on
      }
      else {
       digitalWrite(MOSFET,LOW);//turn the pump off
       digitalWrite(REDLED,LOW);//turn the RED LED off
      }

  if (current-previousTime_2>=number2){
    Integer=0;
    previousTime_2=current;
  }
 }
 if(Integer==0){//automatic system arduino
 const auto MoistureData2 = analogRead(MOISTURE);
  if(MoistureData2>WET_THRESH) 
  {
      digitalWrite(MOSFET, HIGH);    //turn the pump on

   } 
   else    
   {
      digitalWrite(MOSFET, LOW);    //turn the pump off

   };
 }

}


void sendMoistureData() {
    const auto MoistureData = analogRead(MOISTURE);
    const byte data[] = {0, 0, highByte(MoistureData), lowByte(MoistureData)};

    Serial.write(data, 4);
    Serial.println();  
}
