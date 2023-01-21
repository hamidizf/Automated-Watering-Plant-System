Automated-Watering-Plant-System

INTRODUCTION:

This project is an automatic and manual watering system of a plant. The goal is to water a plant
in response to moisture sensor values (through Arduino IDE), as well as controlling the pump,
LED and OLED display through java programming (using IntelliJ IDEA CE software). Moisture
values will be displayed on a live graph while IntelliJ is connected to the port, additionally. In
this project, we worked on sending data from Arduino to java and from java to Arduino.

TECHNICAL REQUIREMENTS / SPECIFICATIONS:

The system is supposed to water a plant automatically using Arduino IDE, and manually when it
is connected to IntelliJ software. In terms of the automatic system, the moisture sensor detects
dryness of the soil and the pump will be turned on in case of dryness. As for the manual system,
IntelliJ takes the control as the very first byte is sent. Two inputs are being used in the manual
system. A button and a slider. As the button is pressed, the pump starts watering the plant, and
by releasing the button the pump stops working. Also, by moving the slider, the RED LED will
be turned on and off. The byte values are being displayed on OLED display while using the
automatic system. 

COMPONENTS LIST:

• Arduino uno

• Moisture sensor(from seeed studio)

• Water pump(6V)

• Led(on the board)

• OLED display(on the board)

• MOSFET switch

• Battery holder and batteries 

PROCEDURE:

First the moisture sensor and the pump were connected to the board (Arduino uno, pin
A1 and pinD2 respectively). However, the pump was connected through a MOSFET
switch. Then the components (the board, pump, moisture sensor, LED, and OLED display
were tested to check if they work properly.

The code is consisted of two separate parts: 1. The code on Arduino IDE using C++
programming. 2. The code on IntelliJ using Java programming.

Arduino IDE is responsible for the automatic system as well as the connection between
IntelliJ and the board. The code on the software starts by importing some libraries,
defining the pins, and some constant values such as our initial moisture value and time.
Then in part “set up void”, we define our MOSFET as our output, moisture sensor as
input, and the serial baud rate as 9600. (Some other items such as display flip mode are
defined in this part as well.) Then there exist a void loop (an infinite loop) containing
the main structure of our code. There is where we control the base of the system as
desired. What I did on this part was defining a “millis” function to control my manual
system as well as sending moisture data to IntelliJ. Arduino sends moisture data to
IntelliJ every one second, and as soon as the very first byte is received from IntelliJ, it
runs the manual part of the code, whose main controller is Java, for 8 seconds. If no
serial is available, the automatic system will be run.

The code on IntelliJ starts by importing some libraries. (Noting that fazecast was
downloaded initially from maven and SDK library was imported since I decided to use
java FX.) After defining the port, the code for the button, slider and the live graph was 
written. Datacontroller implementation was responsible for controlling the moisture
data to be displayed on the live graph. (The package for Datacontroller was added
containing the instructore). The button and the slider are in charge of controlling the
pump and the LED by sending bytes to Arduino. We put that part of the code in a try
and catch to detect in case any difficulty appears in connection. 
