
#include <Wire.h>
#include "MAX30100_PulseOximeter.h"
 
#define REPORTING_PERIOD_MS     1000
 
PulseOximeter pox;
uint32_t tsLastReport = 0;


int val;
int tempPin = 0;


/*
void onBeatDetected()
{
    Serial.println("Beat!");

}
*/
 
void setup()
{

    Serial.begin(115200);
    //Serial.print("Initializing pulse oximeter..");

 
    // Initialize the PulseOximeter instance
    // Failures are generally due to an improper I2C wiring, missing power supply
    // or wrong target chip
    if (!pox.begin()) {
        Serial.println("0");
        for(;;);
    } else {
        Serial.println("1");
    }
     pox.setIRLedCurrent(MAX30100_LED_CURR_7_6MA);
 
    // Register a callback for the beat detection
    
    //pox.setOnBeatDetectedCallback(onBeatDetected);
   
}
 
void loop()
{
  

    // Make sure to call update as fast as possible
    pox.update();
    if (millis() - tsLastReport > REPORTING_PERIOD_MS) {
        //Serial.print("Heart rate:");
        Serial.print("ARDUINO1;");
        Serial.print(pox.getHeartRate());
        Serial.print(";");
        //Serial.print("bpm / SpO2:");
        Serial.print(pox.getSpO2());
        Serial.print(";");
        //Serial.println("%");
 
        tsLastReport = millis();

        val = analogRead(tempPin);
        float mv = ( val/1024.0)*5000;
        float cel = mv/10;
        float farh = (cel*9)/5 + 32;
        //Serial.print("TEMPRATURE = ");
        Serial.print(cel);
        //Serial.print(";");
        //Serial.print("*C");
        Serial.println();
        //delay(1000);
    }

}
