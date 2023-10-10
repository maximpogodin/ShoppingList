# ShoppingList
Example shopping list application in Kotlin
## Contents
1. [Description](#description)
2. [Plugins, library and stuff](#plugins-library-and-stuff)
3. [SDK specification](#sdk-specification)
4. [Setting up the project](#setting-up-the-project)
5. [APK](#apk)
6. [App in action](#app-in-action)
## Description
The test project was developed in Android Studio using Kotlin and Jetpack Compose. The build is based on the Gradle build system.
Pre-interview assignment UniSafe LLC.
An example of a shopping list application in Kotlin. 
Using selected queries, you must be able to create, edit and upload shopping lists. 
Application can work on multiple devices. The application have the ability to “cross out”
items from the shopping list.
## Plugins, library and stuff:
- Kotlin - ver. 1.4.3
- [GSON](https://github.com/google/gson) (Google open source library) for serialization-deserialization JSON - ver. 2.5.0
- [Retrofit](https://github.com/square/retrofit) (a type-safe HTTP client for Android and Java) - ver. 2.9.0
- [Jetpack Compose](https://developer.android.com/jetpack/compose) (recommended modern toolkit for building native UI) - ver. 2.7.0-alpha01
- The application was launched on the Pixel XL emulator API 29 (Android 10.0)
## SDK specification:
- Target sdk version 33
- Min sdk version 23
## Setting up the project
1. Start Android Studio
2. Select "Get from Version Control"
![](https://github.com/maximpogodin/swapi-android/blob/master/screenshots/step1.png)
3. Configure properties:
    * Version control: Git
    * URL: https://github.com/maximpogodin/ShoppingList
    * Directory: your_path</br>
4. Click on Clone button
5. Open project
___
Since build.gradle already exists, Android Studio will set everything else up automatically.
## APK
The build result is [here](https://github.com/maximpogodin/ShoppingList/blob/master/app/app.apk).
## App in action
- Screen **Main Screen**</br>
![](https://github.com/maximpogodin/ShoppingList/blob/master/screens/1.%20Main%20Screen.png)
- Screen **Key Generation Screen**</br>
![](https://github.com/maximpogodin/ShoppingList/blob/master/screens/2.%20Key%20Generation%20Screen.png)
- Screen **Shop Lists Screen**</br>
![](https://github.com/maximpogodin/ShoppingList/blob/master/screens/3.%20Shop%20Lists%20Screen.png)
- Screen **Create Shop List Screen**</br>
![](https://github.com/maximpogodin/ShoppingList/blob/master/screens/4.%20Create%20Shop%20List%20Screen.png)
- Screen **Shop List Screen (cross it off and dismiss demo)**</br>
![](https://github.com/maximpogodin/ShoppingList/blob/master/screens/5.%20Shop%20List%20Screen%20(cross%20it%20off%20and%20dismiss%20demo).png)
- Screen **Add Item To Shop List Screen**</br>
![](https://github.com/maximpogodin/ShoppingList/blob/master/screens/6.%20Add%20Item%20To%20Shop%20List%20Screen.png)
- Screen **Validation Example**</br>
![](https://github.com/maximpogodin/ShoppingList/blob/master/screens/7.%20Validation%20Example.png)
