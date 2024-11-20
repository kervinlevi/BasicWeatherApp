#include <jni.h>

extern "C" jstring
Java_dev_kervinlevi_basicweatherapp_data_weather_WeatherApiAppId_appId(JNIEnv *env, jobject thiz) {
    return env -> NewStringUTF(""); // Insert OpenWeather API app id here
}
