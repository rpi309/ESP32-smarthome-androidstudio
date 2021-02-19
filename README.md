# esp32-smarthome-androidstudio

ESP32 Smart Home with Android Studio Integration
What do you do when something you want doesn't exist? You make your own!
This repository is for the ESP32/ESP8266 lines of wifi-capable microcontrollers and utilizes the "simpleWifiSketch" and uses the HTTP-GET from android studio to send a request to the ESP, thus, triggering a relay,led,etc.
Arduino Requirements
	•	Arduino IDE download here
	•	A ESP Board
	•	USB -> Serial Converter (FTDI) ONLY NEEDED IF USING ESP 01S and CAM)
	•	Any device you wanted to turn on (relay, led, etc)


Android Studio Requirements
	•	Computer with an Intel CPU or Any Computer with Physical Device (afaik Android Studio doesn't support emulation on ARM based processors)
	•	API level >26
	•	greater than 8GB ram (pretty ram intensive, during testing, android studio was using 6GB ram) 

System tested on
	•	Late 2021 ARM M1 Mac Mini 16GB
	•	Physical Pixel 3 Device
	•	Doit ESP32 DEVKIT v1
	•	Optional Relay (300watt, 10amps@110v)


Installation Directions (Android)
	•	Open Android Studio
	•	Start new Basic Project
	•	What I have changed in Android Studio:
	⁃	activity_main
	⁃	main_activity
	⁃	project&app level of build.gradle
	⁃	manifest
	•	Find those files in the Git Repo, then C&P then into their respective places
	•	Upload the Code to the Arduino First, so that you retrieve the IP address
	•	Then, change the ip address in lines:
	•	line 34: val statusURl = URL("http://urIP/S")
	•	line 57: val highURL = URL("http://urIP/H")
	•	line 58: val lowURL = URL("http://urIP/L")
	•	line 118 val url = URL("http://urIP/".plus(path))
	•	For example, if the IP of the arduino is 192.168.86.35, then for line 34, it would be: "val statusURl = URL("http://192.168.86.35/S")

Installation Directions (Arduino)
	•	Download the "arduinoBuild_v1.0.zip"
	•	Unzip the file using your preferred method
	•	Open the arduinoBuild_v1.0 directory
	•	Open the arduinoBuild_V1.0.ino
	•	Open arduino preferences (cmd+, ctrl+,)
	•	C&P this link into "addition board managers URL"
	•	Open Boards Manager (tools -> board -> board manager)
	•	Enter "ESP32" in the search bar
	•	Download the latest version
	•	Select your board in Tools -> board -> ESP32 arduion -> [whateverboardyouareusing] (if you dont see your board, select ESP32 Dev Module)
	•	Set the baud rate to 115200 (i found that the code will not upload at higher baud rates)
	•	Connect the ESP to the computer (via ftdi or usb)
	•	Select the port via Tools -> Port -> windows is COM(watever) or mac is /dev/cu.devmodem(watever)
	•	Change the SSID, PSK, and the OUTPUT PIN to suite your needs
	•	Upload Code (you may or may not need to hold the "boot" button on esp or connect GPIO 0 to Ground(esp-01s) when uploading) 
Happy ESPing!
