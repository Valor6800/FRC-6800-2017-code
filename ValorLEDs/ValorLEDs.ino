#include "FastLED.h"
#define COLOR_ORDER GRB
#define MAX_BRIGHTNESS 255

#define NUM_LEDS 150

CRGB leds[NUM_LEDS];

#define LED_PIN 6

#define WAIT 0
#define GEAR_GRAB 1
#define TURBO 2
#define DEPLOY 3
#define SHOOT 4

#define LEFT 10
#define MIDDLE 9
#define RIGHT 8

void setup() {
 pinMode(LEFT, INPUT_PULLUP);
 pinMode(MIDDLE, INPUT_PULLUP);
 pinMode(RIGHT, INPUT_PULLUP);
 
 delay( 3000 ); // power-up safety delay
 FastLED.addLeds<WS2811, LED_PIN, COLOR_ORDER>(leds, NUM_LEDS);
 FastLED.clear();
 FastLED.show();
 delay(250);

 // start serial port 
 Serial.begin(9600);
}

void loop() {  
  FastLED.clear();
  if(decoded() == WAIT){
    fill_solid(leds,10, CRGB::Purple);
  } else if (decoded() == GEAR_GRAB) {
    fill_solid(leds,NUM_LEDS, CRGB::Green);
  } else if(decoded() == TURBO) {
    bounce(CRGB::White, 10, 10, TURBO);
  } else if(decoded() == DEPLOY){
    flash(CRGB::Blue, 500, DEPLOY);
  } else if (decoded() == SHOOT) {
    middle_out(CRGB::Green, 1,true, SHOOT);
  } else {
    fill_solid(leds,NUM_LEDS, CRGB::Black);
  }
  FastLED.show();
  
  
}

void middle_out(uint32_t color,uint8_t wait, boolean back, int pin) {
  FastLED.clear();
  FastLED.show();
  int middle[2] = {middle[0] = NUM_LEDS/2,middle[0] = NUM_LEDS/2};
  if(NUM_LEDS % 2 == 0){
    middle[1] = NUM_LEDS/2 + 1;
  }
  for(int len = 0; len < middle[0]; len ++) {
    if(decoded() == pin) {
      FastLED.clear();
      FastLED.show();
      return;
    }
    for(int led = 0; led < len; led ++) {
      leds[middle[0] - led] = color;
      leds[middle[1] + led] = color;
    }
    FastLED.show();
    delay(wait);
  }

  if(back) {
    if(decoded() == pin) {
      FastLED.clear();
      FastLED.show();
      return;
    }

    for(int led = middle[0]; led >= 0; led --) {
      leds[led] = CRGB::Black;
      leds[middle[1] + (middle[0]- led)] = CRGB::Black;
      FastLED.show();
      delay(wait);
    }
  }
}


void bounce(uint32_t color, uint8_t wait, uint8_t blockSize, int pin) {
  FastLED.clear();
  FastLED.show();
  FastLED.setBrightness(MAX_BRIGHTNESS);
  // GO TO END
  for(int blockHead = 0; blockHead - blockSize < NUM_LEDS; blockHead ++) {
    if(decoded() == pin){
      FastLED.clear();
      FastLED.show();
      return;
    }
    for(int led = 0; led < blockSize; led ++) {
      if(blockHead - led >= 0 && blockHead - led < NUM_LEDS)
        leds[blockHead - led] = color;
    }
    if(blockHead - blockSize - 1 >=0)
      leds[blockHead - blockSize - 1] = CRGB::Black;

    FastLED.show();
    delay(wait);
  }
  // GO BACK
  for(int blockHead = NUM_LEDS-1; blockHead+blockSize >= 0; blockHead --) {
    if(decoded() == pin){
      FastLED.clear();
      FastLED.show();
      return;
    }
    for(int led = 0; led < blockSize; led ++) {
      if(blockHead + led >=0 && blockHead + led < NUM_LEDS)
        leds[blockHead + led] = color;
    }
    if(blockHead + blockSize + 1 < NUM_LEDS)
      leds[blockHead + blockSize + 1] = CRGB::Black;

    FastLED.show();
    delay(wait);
  }
  
  return; 
}

void flash(uint32_t color, int wait, int pin) {
  while(digitalRead(pin) == 0) {
    unsigned long endTime = millis() + wait;
    fill_solid(leds,NUM_LEDS, color);
    FastLED.show();
    while(millis() < endTime) {
      if(decoded() == pin){
        FastLED.clear();
        FastLED.show();
        return;
      }
      delay(10);
    }
    endTime = millis() + wait;
    FastLED.clear();
    FastLED.show();
    while(millis() < endTime) {
      if(decoded() == pin){
        FastLED.clear();
        FastLED.show();
        return;
      }
      delay(10);
    }
  }
}

int decoded() {
  int i = digitalRead(LEFT) == 0 ? 4 : 0;
  int j = digitalRead(MIDDLE) == 0 ? 2 : 0;
  int k = digitalRead(RIGHT) == 0 ? 1 : 0;
  return i + j + k;
}

