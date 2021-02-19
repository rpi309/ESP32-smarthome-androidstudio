
#include <WiFi.h>

const char* ssid     = "urssid";
const char* password = "urpsk";
WiFiServer server(80);

//status of LED 1=on 0=off
int stat1 = 0;
int value = 0;
//pin to alter high or low 
int outputPin = 15;

void setup()
{
  Serial.begin(115200);
  pinMode(outputPin, OUTPUT);
  delay(10);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  server.begin();
}

void loop() {
  WiFiClient client = server.available();
  if (client) {
    Serial.println("New Client.");
    String currentLine = "";
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
        Serial.write(c);
        if (c == '\n') {
          if (currentLine.length() == 0) {
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();
            client.println();
            break;
          } else {
            currentLine = "";
          }
        } else if (c != '\r') {
          currentLine += c;
        }
        if (currentLine.endsWith("GET /S")) {
          if (stat1 == 1) {
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();
            client.print("HIGH<br>");
            client.println();
          }
          else if (stat1 == 0) {
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();
            client.print("LOW<br>");
            client.println();
          }
        }
        if (currentLine.endsWith("GET /H")) {
          digitalWrite(15, HIGH);
          stat1 = 1;
        }
        if (currentLine.endsWith("GET /L")) {
          digitalWrite(15, LOW);
          stat1 = 0;
        }
      }
    }
    client.stop();
    Serial.println("Client Disconnected.");
  }
}
