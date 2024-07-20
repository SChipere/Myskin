# MySkin

MySkin is an Android application that allows users to capture an image using their device's camera, send it to a server for processing, and display the processed image. The server uses a YOLO (You Only Look Once) model to process the image.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Features

- Capture an image using the device's camera
- Send the captured image to a server for YOLO processing
- Display the processed image from the server

## Installation

### Prerequisites

- Android Studio
- Android device or emulator with camera support
- Flask (for server-side code)
- Python 3.x (for running the server)

### Android Application

1. Clone the repository:

   ```bash
   git clone https://github.com/SChipere/MySkin.git

cd myskin/server

python app.py

Usage
Open the MySkin app on your Android device.
Click the "Capture Image" button to take a photo using your device's camera.
After capturing the image, click the "Send Image for Processing" button.
Wait for the server to process the image. The processing status will be displayed.
Once processing is complete, the processed image will be displayed in a new activity.
Project Structure
css
Copy code
myskin/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/myskin/
│   │   │   │   ├── MainActivity3.java
│   │   │   │   └── MainActivity5.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main3.xml
│   │   │   │   │   └── activity_main5.xml
│   │   │   │   └── values/
│   │   │   │       └── strings.xml
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── server/
│   ├── app.py
│   ├── app2.py
│   ├── requirements.txt
│   └── Unprocessed_image/
└── README.md
API Endpoints
POST /start_yolo_execution
Description: Receives an image from the Android app, saves it, and starts the YOLO processing script.
Request Body: Raw image data
Response: "Processing done" if the processing is successful, or an error message otherwise.
POST /process_image
Description: Receives an image from the Android app and saves it.
Request Body: Raw image data
Response: "Image processed successfully" if the image is saved, or an error message otherwise.
Contributing
Contributions are welcome! Please follow these steps to contribute:

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Make your changes.
Commit your changes (git commit -m 'Add some feature').
Push to the branch (git push origin feature-branch).
Create a new Pull Request.
License
This project is licensed under the MIT License. See the LICENSE file for details.

css
Copy code

This `README.md` file provides a comprehensive guide to your project, covering features, installation instructions, usage guidelines, project structure, API endpoints, contributing guidelines, and license information. Make sure to adjust any specific details, such as the repository URL and any other project-specific information.
