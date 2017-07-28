#include "FastLED.h"
#define COLOR_ORDER GRB
#define MAX_BRIGHTNESS 255

#define NUM_LEDS 39

CRGB leds[NUM_LEDS];

#define LED_PIN 6

#define GEAR 2
#define TELEOP 3
#define HANG 4
#define WAIT -1

void setup() {
 pinMode(GEAR, INPUT);
 pinMode(TELEOP, INPUT);
 pinMode(HANG, INPUT);
 
 delay( 1000 ); // power-up safety delay
 FastLED.addLeds<WS2811, LED_PIN, COLOR_ORDER>(leds, NUM_LEDS);
 FastLED.clear();
 FastLED.show();
 delay(250);
 
}

void loop() {  
  //Serial.println(decoded());
  int key = decoded();
  if(key == GEAR) {
    fill_solid(leds, NUM_LEDS, CRGB::Green);
    FastLED.show();
  } else if (key == TELEOP) {
    bounce(CRGB::White, 20, 10, TELEOP);
  } else if (key == HANG) {
    middle_out(CRGB::Green, 25,true, HANG);
  } else {
    solid_fade(CRGB::White, 5, WAIT);
  }
  
  
  
}

void solid_fade(uint32_t color, uint8_t wait, int pin) {
  fill_solid(leds, 10, color);
  for(int bright = 0; bright <= MAX_BRIGHTNESS; bright++) {
    
    if(bright < 3) {
      fill_solid(leds, NUM_LEDS, CRGB::Black);
    } else {
      fill_solid(leds, NUM_LEDS, color);
      FastLED.setBrightness(bright);
    }
     
    FastLED.show();
    delay(wait);
    if(decoded() != pin) {
      FastLED.clear();
      FastLED.show();
      return;
    }
  }
  for(int bright = MAX_BRIGHTNESS; bright >= 0; bright--) {
   
    if(bright < 3) {
      fill_solid(leds, NUM_LEDS, CRGB::Black);
    }  else {
      fill_solid(leds, NUM_LEDS, color);
      FastLED.setBrightness(bright);
    }
    FastLED.show();
    delay(wait);
     if(decoded() != pin) {
      FastLED.clear();
      FastLED.show();
      return;
    }
  }

  FastLED.setBrightness(MAX_BRIGHTNESS);
}

void middle_out(uint32_t color,uint8_t wait, boolean back, int pin) {
  FastLED.clear();
  FastLED.show();
  FastLED.setBrightness(MAX_BRIGHTNESS);
  int middle[2] = {middle[0] = NUM_LEDS/2,middle[0] = NUM_LEDS/2};
  if(NUM_LEDS % 2 == 0){
    middle[1] = NUM_LEDS/2 + 1;
  }
  for(int len = 0; len < middle[0]; len ++) {
    if(decoded() != pin) {
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
    for(int led = middle[0]; led >= 0; led --) {
      if(decoded() != pin) {
        FastLED.clear();
        FastLED.show();
        return;
      }
      
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
    if(decoded() != pin){
      FastLED.clear();
      FastLED.show();
      return;
    }
    for(int led = 0; led < blockSize; led ++) {
      if(blockHead - led > 0 && blockHead - led < NUM_LEDS)
        leds[blockHead - led] = color;
    }
    if(blockHead - blockSize - 1 >=0)
      leds[blockHead - blockSize - 1] = CRGB::Black;
    FastLED.show();
    delay(wait);
  }
  // GO BACK
  for(int blockHead = NUM_LEDS-2; blockHead+blockSize > 0; blockHead --) {
    if(decoded() != pin){
      FastLED.clear();
      FastLED.show();
      return;
    }
    for(int led = 0; led < blockSize; led ++) {
      if(blockHead + led >=0 && blockHead + led < NUM_LEDS-1)
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
  while(decoded() == pin) {
    unsigned long endTime = millis() + wait;
    fill_solid(leds,NUM_LEDS, color);
    FastLED.show();
    while(millis() < endTime) {
      if(decoded() != pin){
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
      if(decoded() != pin){
        FastLED.clear();
        FastLED.show();
        return;
      }
      delay(10);
    }
  }
}

int decoded() {
  int gear = digitalRead(GEAR);
  int teleop = digitalRead(TELEOP);
  int hang = digitalRead(HANG);
  
  if (teleop == 1)
    return TELEOP;
  else if (gear == 1)
    return GEAR;
  else if (hang == 1)
    return HANG;
  else return WAIT;
}

