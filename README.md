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
<img width="346" alt="Screenshot1" src="https://github.com/user-attachments/assets/dce71a3e-281e-461c-8959-c4e3380ddf86">
<img width="346" alt="Screenshot2" src="https://github.com/user-attachments/assets/ce8ac2f5-c1c6-4ac3-a244-2121cd9b4ec2">
<img width="346" alt="Screenshot3" src="https://github.com/user-attachments/assets/2839aa94-fa62-46dd-a75a-90a496c69dad">
<img width="346" alt="Screenshot4" src="https://github.com/user-attachments/assets/f862edc8-9bd5-420d-937b-73d4ee6465ab">

