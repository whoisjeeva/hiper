<p align="center">
    <img src="https://user-images.githubusercontent.com/11387981/121815650-281b4000-cc95-11eb-96ca-c190ded683d3.png" alt="Hiper">
</p>

<p align="center">
    <a href="https://jitpack.io/#piplib/hiper"><img src="https://img.shields.io/jitpack/v/github/piplib/hiper?style=for-the-badge" alt="Release"></a>
    <a href="https://travis-ci.com/piplib/hiper"><img src="https://img.shields.io/travis/com/piplib/hiper/master?style=for-the-badge" alt="Build Status"></a>
    <a href="https://github.com/piplib/hiper/blob/master/LICENSE"><img src="https://img.shields.io/github/license/piplib/hiper.svg?style=for-the-badge" alt="License"></a>
<!--     <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/piplib/hiper?logo=GitHub&style=for-the-badge"> -->
    <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/piplib/hiper?logo=GitHub&style=for-the-badge">
    <a href="https://github.com/piplib/hiper/issues"><img alt="GitHub open issues" src="https://img.shields.io/github/issues/piplib/hiper?style=for-the-badge"></a>
</p>

# Hiper - A Human Friendly HTTP Library for Android

> Hiper is for human by human.

## Table of Contents

- [Getting Started](#getting-started)
- [Using The HTTP Library](#using-the-http-library)
    - [Creating an instance](#create-a-hiper-instance-you-can-use-the-hiper-in-two-different-ways)
    - [GET request](#sending-a-simple-get-request)
    - [POST request](#sending-a-simple-post-request)
    - [GET request with parameters](#sending-get-parameters-with-your-request)
    - [Sending custom headers](#using-custom-headers)
    - [Downloading files](#downloading-a-file-using-hiper)

## Getting Started

Add it in your root build.gradle at the end of repositories

```css
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency

```css
implementation "com.github.piplib:hiper:$hiper_version"
```


## Using The HTTP Library

### Create a hiper instance. You can use the Hiper in two different ways.

1. Synchronous: This will wait for the response, this will block whatever thread it is running on.
2. Asynchronous: This won't wait or block the running thread, instead you pass callbacks and it will execute those callbacks when it succeed or fail.

```kotlin
val hiper = Hiper.getInstance() // for synchronous requests
// or
val hiper = Hiper.getInstance().async() // for asynchronous requests
```

> Now you are ready to see the power of hiper.

### Sending a simple GET request

```kotlin
val caller = hiper.get("http://httpbin.org/get") { response ->
    debug(response.text)
}
```

### Sending a simple POST request

```kotlin
val caller = hiper.post("http://httpbin.org/post") { response ->
    debug(response.this)
}
```

### Sending GET parameters with your request

```kotlin
val caller = hiper.get("http://httpbin.org/get", args = mix("name" to "Hiper", "age" to 1)) { response ->
    debug(response)
}
```

Or you can use inline args, headers, cookies or form using the `mix` method

```kotlin
hiper.get("http://httpbin.org/get", args = mix("name" to "Hiper"), headers = mix("user-agent" to "Hiper/1.0")
```

### Using custom headers

```kotlin
val caller = hiper.get("http://httpbin.org/get", headers = mix("User-Agent" to "Hiper/1.1")) { response ->
    debug(response)
}
```

### Downloading a file using Hiper.

```kotlin
val caller = hiper.get("http://httpbin.org/get", isStream = true) { response ->
    // do things with the stream
    ...
    response.stream?.close()
}
```
