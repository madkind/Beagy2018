#include <TimerOne.h>
#include <Servo.h>

int maxDistance = 50;

int EchoPin = 4;
int TriggerPin = 5;
int MaximumRange = 200;
int MinimumRange = 0;

int servoPin = 9;
Servo myservo;

int A_MotorEnable = 13;
int A_MotorDir2 = 12;
int A_MotorDir1 = 11;

int B_MotorDir1 = 8;
int B_MotorDir2 = 7;
int B_MotorEnable = 6;

int MotorSpeed = 140;

enum gestureType {
  START = 48,
  DOWN = 49,
  RIGHT = 50,
  LEFT = 51,
  STOP = 52,
  FELADAT1START = 53,
  FELADAT1END = 54,
  FELADAT2START = 55,
  FELADAT2END = 56,
  WAITING = 57
};

gestureType actGesture;

struct detectedObject {
  float angle;
  float distance;
};

void setup() {
  // put your setup code here, to run once:
  //pinMode(LED_BUILTIN, OUTPUT);
  Serial.begin(9600);
  myservo.attach(servoPin);

    pinMode(A_MotorDir1, OUTPUT);
  pinMode(A_MotorDir2, OUTPUT);
  pinMode(A_MotorEnable, OUTPUT);
  pinMode(B_MotorDir1, OUTPUT);
  pinMode(B_MotorDir2, OUTPUT);
  pinMode(B_MotorEnable, OUTPUT);

}

void loop() {
  // put your main code here, to run repeatedly:
  switch (actGesture) {
    case FELADAT1START:
      doSonarMeasurement();
      break;
  }

  watchForIncomingMessages();
}

void watchForIncomingMessages() {
  if (Serial.available() > 0) {
    // read the incoming byte:
    int incomingByte = Serial.read();
    switch ( incomingByte) {
      case START:
        moveCarForward();
        break;
      case RIGHT:
        moveCarRight();
        break;
      case DOWN:
        moveCarBackward();
        break;
      case LEFT:
        moveCarLeft();
        break;
      case STOP:
        stopCar();
        break;
      case FELADAT1START:
        actGesture = FELADAT1START;
        break;
      case FELADAT1END:
        actGesture = FELADAT1END;
        break;
      case FELADAT2END:
        stopCar();
        break;
    }
  }
}

void moveCarLeft() {
  A_motorF();
  B_motorStop();
  analogWrite(A_MotorEnable, MotorSpeed);
  analogWrite(B_MotorEnable, MotorSpeed);
}

void moveCarForward() {
  A_motorF();
  B_motorF();
  analogWrite(A_MotorEnable, MotorSpeed);
  analogWrite(B_MotorEnable, MotorSpeed);
}

void moveCarRight() {
  B_motorF();
  A_motorStop();
  analogWrite(A_MotorEnable, MotorSpeed);
  analogWrite(B_MotorEnable, MotorSpeed);
}

void moveCarBackward() {
  A_motorR();
  B_motorR();
  analogWrite(A_MotorEnable, MotorSpeed);
  analogWrite(B_MotorEnable, MotorSpeed);
}

void stopCar() {
  A_motorStop();
  B_motorStop();
}

void doSonarMeasurement() {
  for (int i = 0; i < 181; i += 10) {
    myservo.write(i);
    delay(200);
    long distance = getSonarValue();
    sendMessage(distance, i);
    watchForIncomingMessages();
    if (actGesture == FELADAT1END) {
      break;
    }
  }
}

void sendMessage(long distance, float angle) {
  int distancePercent;
  if (distance > maxDistance) {
    distancePercent = 100;
  }
  else if (distance < 0) {
    distancePercent = 0;
  }
  else {
    distancePercent = (distance * 100) / maxDistance;
  }
  Serial.print(distancePercent);
  Serial.print('|');
  Serial.println(angle);
}

long getSonarValue() {
  digitalWrite(TriggerPin, LOW);
  delayMicroseconds(2);
  digitalWrite(TriggerPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(TriggerPin, LOW);
  long duration = pulseIn(EchoPin, HIGH);
  long distance = duration / 52.2;

  if (distance >= MaximumRange || distance <= MinimumRange) {
    distance = -1;
  }
  return distance;
}

void A_motorStop() {
  digitalWrite(A_MotorDir1, LOW);
  digitalWrite(A_MotorDir2, LOW);
  digitalWrite(A_MotorEnable, LOW);
}
void A_motorR() {
  digitalWrite(A_MotorDir2, HIGH);
  digitalWrite(A_MotorDir1, LOW);
}

void A_motorF() {
  digitalWrite(A_MotorDir2, LOW);
  digitalWrite(A_MotorDir1, HIGH);
}
void B_motorStop() {
  digitalWrite(B_MotorDir1, LOW);
  digitalWrite(B_MotorDir2, LOW);
  digitalWrite(B_MotorEnable, LOW);
}
void B_motorR() {
  digitalWrite(B_MotorDir2, LOW);
  digitalWrite(B_MotorDir1, HIGH);
}
void B_motorF() {
  digitalWrite(B_MotorDir2, HIGH);
  digitalWrite(B_MotorDir1, LOW);
}
