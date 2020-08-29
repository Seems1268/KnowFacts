# KnowFacts
### Function Design Sepcification - Android


## Overview
Sample app to demonstrate various Android Jetpack Library features such as coding in Kotlin programming language, using MVVM design patter, Data Binding, Live Data, etc.

It also uses Retrofit Network libraries to consume REST API and parse the JSON response using Gson library and RxJava for effective background tasks handling.

## About KnowFacts

This app ingests an API (.json end point) and displays the parsed data into the scrollable list. Data includes a title and number of rows. Each row is a fact about a Country or could be anything.

The list is refreshed using "Pull to Refresh" functionality.

The app is compiled against the minSDKVersion 25 and targetSDKVersion 30 (Requirement of Google Playstore).
