# BasicWeatherApp for Android

BasicWeatherApp is a an Android app to display weather forecasts from [OpenWeather API](https://openweathermap.org/current). The app uses the location service of the device to retrieve the user's coordinates. The latitude and longitude are then used to retrieve the weather forecast from the API.

## Features

- Weather forecast based on the device's location, which includes temperature, sunrise, and sunset
- Recording previously retrieved weather forecast and displaying it in a tab
- Simulated authentication flow which consists of sign up, log in, and log out.

## Setup

- To build the project in Android Studio, the OpenWeather API key needs to be inserted to ```src/main/cpp/keyholder.cpp```.
- Make sure to not commit the key to the remote repository, use the git command ```git update-index --assume-unchanged app/src/main/cpp/keyholder.cpp``` after inserting the key so that the changes in the file will be ignored.

## Android

The architecture of the code is MVVM. The libraries and tools used include:
  + Hilt (for dependency injection)
  + Jetpack Compose (for building the UI)
  + Jetpack Navigation (for the routing of Composable screens)
  + Room (for storing the previously fetched weather forecast)
  + Retrofit and Moshi (to connect to the OpenWeather web service)
  + FusedLocationProviderClient (to get the device's location)
  + Lottie (to display animated vectors, special thanks to user [jochang](https://lottiefiles.com/vdr0uy2wwsoljqtc) from LottieFiles.com for the weather animation assets)
  + EncryptedSharedPreferences (to simulate hiding of tokens)
  + NDK (to increase security of the API key)
  + MockK (for unit testing)
  + [icon.kitchen](https://icon.kitchen/) (to create the app icon)

The app has unit tests. The code coverage for each ViewModel is at maximum.
<img width="800" alt="Code Coverage" src="https://github.com/user-attachments/assets/5ffb9967-cc1a-4945-8c77-6ebfde1c6ee0">


## Demo

https://github.com/user-attachments/assets/2d1e6a51-ccc1-4f6a-bb20-085c3578dc0f

## Screenshots
<img width="346" alt="Network error" src="https://github.com/user-attachments/assets/dce71a3e-281e-461c-8959-c4e3380ddf86">
<img width="346" alt="Weather report history tab" src="https://github.com/user-attachments/assets/f862edc8-9bd5-420d-937b-73d4ee6465ab">
<img width="346" alt="Day rain weather at 11:29PM" src="https://github.com/user-attachments/assets/a775f5ba-3d3b-46e4-a430-6bdaac92bfd5">
<img width="346" alt="Night rain weather at 6:30PM" src="https://github.com/user-attachments/assets/dc0cf313-4a7a-418a-b000-d5e31e2c3cb1">

