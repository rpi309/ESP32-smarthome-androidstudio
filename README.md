# esp32-smarthome-androidstudio

What do you do when something you want doesn't exist? You make your own! <br>
 <br>
This repository is for the ESP32/ESP8266 lines of wifi-capable microcontrollers and utilizes the "simpleWifiSketch" and uses the HTTP-GET from android studio to send a request to the ESP, thus, triggering a relay,led,etc.
 <br>
Arduino Requirements <br>
	•	Arduino IDE download here <br>
	•	A ESP Board <br> 
	•	USB -> Serial Converter (FTDI) ONLY NEEDED IF USING ESP 01S and CAM) <br>
	•	Any device you wanted to turn on (relay, led, etc) <br>

 <br>
Android Studio Requirements <br>
	•	Computer with an Intel CPU or Any Computer with Physical Device (afaik Android Studio doesn't support emulation on ARM based processors) <br>
	•	API level >26 <br>
	•	greater than 8GB ram (pretty ram intensive, during testing, android studio was using 6GB ram) <br>
 <br>
System tested on <br>
	•	Late 2021 ARM M1 Mac Mini 16GB <br>
	•	Physical Pixel 3 Device <br>
	•	Doit ESP32 DEVKIT v1 <br>
	•	Optional Relay (300watt, 10amps@110v) <br>
 <br>
Installation Directions (Android) <br>
	•	Open Android Studio <br>
	•	Start new Basic Project <br>
	•	What I have changed in Android Studio: <br>
	⁃	activity_main <br>
	⁃	main_activity <br>
	⁃	project&app level of build.gradle <br>
	⁃	manifest <br>
	•	Find those files in the Git Repo, then C&P then into their respective places <br>
	•	Upload the Code to the Arduino First, so that you retrieve the IP address <br>
	•	Then, change the ip address in lines: <br>
	•	line 34: val statusURl = URL("http://urIP/S") <br>
	•	line 57: val highURL = URL("http://urIP/H")  <br>
	•	line 58: val lowURL = URL("http://urIP/L") <br>
	•	line 118 val url = URL("http://urIP/".plus(path)) <br>
	•	For example, if the IP of the arduino is 192.168.86.35, then for line 34, it would be: "val statusURl = URL("http://192.168.86.35/S") <br>
 <br>
Installation Directions (Arduino) <br>
	•	Download the "arduinoBuild_v1.0.zip" <br> 
	•	Unzip the file using your preferred method <br>
	•	Open the arduinoBuild_v1.0 directory <br>
	•	Open the arduinoBuild_V1.0.ino <br>
	•	Open arduino preferences (cmd+, ctrl+,) <br>
	•	C&P this link into "addition board managers URL" <br>
	•	Open Boards Manager (tools -> board -> board manager) <br>
	•	Enter "ESP32" in the search bar <br>
	•	Download the latest version <br>
	•	Select your board in Tools -> board -> ESP32 arduino -> [whateverboardyouareusing] (if you dont see your board, select ESP32 Dev Module) <br>
	•	Set the baud rate to 115200 (i found that the code will not upload at higher baud rates) <br>
	•	Connect the ESP to the computer (via ftdi or usb) <br>
	•	Select the port via Tools -> Port -> windows is COM(watever) or mac is /dev/cu.devmodem(watever)v
	•	Change the SSID, PSK, and the OUTPUT PIN to suite your needs <br>
	•	Upload Code (you may or may not need to hold the "boot" button on esp or connect GPIO 0 to Ground(esp-01s) when uploading) <br>
 <br>
Happy ESPing!  <br>
