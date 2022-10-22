<p align="center">
    <img src="https://user-images.githubusercontent.com/11387981/121815650-281b4000-cc95-11eb-96ca-c190ded683d3.png" alt="Hiper">
</p>

<p align="center">
    <a href="https://jitpack.io/#gumify/hiper"><img src="https://img.shields.io/jitpack/v/github/gumify/hiper?style=for-the-badge" alt="Release"></a>
    <a href="https://travis-ci.com/gumify/hiper"><img src="https://img.shields.io/travis/com/gumify/hiper/master?style=for-the-badge" alt="Build Status"></a>
    <a href="https://github.com/gumify/hiper/blob/master/LICENSE"><img src="https://img.shields.io/github/license/gumify/hiper.svg?style=for-the-badge" alt="License"></a>
<!--     <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/gumify/hiper?logo=GitHub&style=for-the-badge"> -->
    <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/gumify/hiper?logo=GitHub&style=for-the-badge">
    <a href="https://github.com/gumify/hiper/issues"><img alt="GitHub open issues" src="https://img.shields.io/github/issues/gumify/hiper?style=for-the-badge"></a>
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
- [Using The Utils Library](#using-the-utils-library)
    - [Context.toast(o: Any?, isLong: Boolean = false)](#contexttoasto-any-islong-boolean--false)
    - [debug(vararg args: Any?)](#debugvararg-args-any-errorvararg-args-any-warnvararg-args-any-infovararg-args-any) also `error(vararg args: Any?)`, `warn(vararg args: Any?)`, `info(vararg args: Any?)`
    - [onUiThread(callback: () -> Unit)](#onuithreadcallback----unit)
    - [async(block: suspend CoroutineScope.() -> Unit)](#asyncblock-suspend-coroutinescope---unit)
    - [sleep(millis: Long)](#sleepmillis-long)
    - [CoroutineScope.run(block: () -> Unit)](#coroutinescoperunblock----unit)
    - :warning: @Deprecated [ignore(callback: Exception?.() -> Unit)](#contextisdarkthemeon-boolean)
    - [Context.isDarkThemeOn(): Boolean](#contextisdarkthemeon-boolean)
    - [Context.readFile(dir: String, fileName: String): ByteArray](#contextreadfiledir-string-filename-string-bytearray)
    - [Context.readFromRawFolder(path: String): ByteArray](#contextreadfromrawfolderpath-string-bytearray)
    - [fetch(url: String, callback: BufferedReader.() -> Unit)](#fetchurl-string-callback-bufferedreader---unit)
    - [Context.newDialog(): Dialog](#contextnewdialog-dialog)
    - [View.visible()](#viewvisible)
    - [View.invisible()](#viewinvisible)
    - [View.gone()](#viewgone)
    - :warning: @Deprecated [TinyDB(appContext: Context)](#warning-deprecated-tinydbappcontext-context)
    - [WeeDB(appContext: Context)](#weedbappcontext-context)

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
implementation "com.github.gumify.hiper:http:$hiper_version"
```

For utils

```css
implementation "com.github.gumify.hiper:util:$hiper_version"
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
val caller = hiper.get("http://httpbin.org/get", args = mix("name" to "Hiper", "age" to 1)) {
    debug(this)
}
```

Or you can use inline args, headers, cookies or form using the `mix` method

```kotlin
hiper.get("http://httpbin.org/get", args = mix("name" to "Hiper"), headers = mix("user-agent" to "Hiper/1.0")
```

### Using custom headers

```kotlin
val caller = hiper.get("http://httpbin.org/get", headers = mix("User-Agent" to "Hiper/1.1")) {
    debug(this)
}
```

### Downloading a file using Hiper.

```kotlin
val caller = hiper.get("http://httpbin.org/get", isStream = true) {
    // do things with the stream
    ...
    stream.close()
}
```



## Using The Utils Library

The utils library contains few useful method that will makes your android development little bit easier.

### `Context.toast(o: Any?, isLong: Boolean = false)`

Showing a toast message is easier with this extension method.

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ...

        toast("hello, world")
    }
}
```

### `debug(vararg args: Any?)`, `error(vararg args: Any?)`, `warn(vararg args: Any?)`, `info(vararg args: Any?)`

When we debug our application we use the `Log.d` a lot, it requires a TAG and a `String` as value. With the `debug` method we can pass any value and it will use the class name wherever it called from as TAG (with .kt extension).


```kotlin
val person = Person()
debug(person)
```

### `onUiThread(callback: () -> Unit)`

Switch to UI thread even without an `Activity`. It uses coroutines to run your code in the UI thread.

```kotlin
class MyService: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            ...

            onUiThread {
                toast("Done.")
            }
        }
    }
}
```

### `async(block: suspend CoroutineScope.() -> Unit)`

Launch a coroutine using the `Dispatchers.IO`

```kotlin
async {
    // do your background tasks
}
```


### `sleep(millis: Long)`

Suspend `async` for specified milliseconds. Only valid inside a coroutine scope.

```kotlin
async {
    ...
    sleep(1000) // 1 second
}
```

### `CoroutineScope.run(block: () -> Unit)`

Run blocking code inside `async`

```kotlin
async {
    ...
    run {
        // blocking code
    }
}
```

### :warning: `@Deprecated ignore(callback: Exception?.() -> Unit)`

If you are tired of writing `try {} catch(e: Exception) {}` to ignore exception.

> **WARNING**: It does not works in some cases

```kotlin
ignore {
    ...
    debug(this)
}
```


### `Context.isDarkThemeOn(): Boolean`

Check if the system dark mode is enabled

```kotlin
if (isDarkThemeOn()) {
    // switch sh to dark mode
}
```

### `Context.readFile(dir: String, fileName: String): ByteArray`

Read a file from `getExternalFilesDir`

```kotlin
val data: ByteArray = readFile(Environment.DIRECTORY_DOWNLOADS, "test.txt")
```


### `Context.readFromRawFolder(path: String): ByteArray`

Read a file from `raw` folder

```kotlin
// R.raw.test
val data: ByteArray = readFromRawFolder("test")
```

### `fetch(url: String, callback: BufferedReader.() -> Unit)`

Send a simple GET http request

```kotlin
fetch("https://httpbin.org/ip") { reader ->

}
```

### `Context.newDialog(): Dialog`

Creating a `MaterialAlertDialog` is easier with it.

```kotlin
newDialog().withTitle("Hello, World")
    .withMessage("Hi")
    .withCancelable(false)
    .withPositiveButton("OK") { dialog ->
        dialog.dismiss()
    }
    .create() // returns AlertDialog
    .show()
```

### `View.visible()`

Set a `View` visibility to `View.VISIBLE`

```kotlin
val button: Button = findViewById(R.id.button)
button.visible()
```

### `View.invisible()`

Set a `View` visibility to `View.INVISIBLE`

```kotlin
val button: Button = findViewById(R.id.button)
button.invisible()
```

### `View.gone()`

Set a `View` visibility to `View.GONE`

```kotlin
val button: Button = findViewById(R.id.button)
button.gone()
```


### :warning: `@Deprecated TinyDB(appContext: Context)`

The utils library also contains `TinyDB` that allows you to persist some simple data.

> **WARNING**: TinyDB is deprecated, there is a better version of TinyDB available as WeeDB

```kotlin
val tinyDB = TinyDB(applicationContext)
tinyDB.putString("KEY", "hello")
debug(tinyDB.getString("KEY"))
```

### `WeeDB(appContext: Context)`

Creating a `WeeDB` instance.

```kotlin
val wee = WeeDB(applicationContext)
```

Storing and retrieving primitive data type.

```kotlin
wee.put("age", 26)
debug(wee.getInt("age"))

wee.put("name", "Jeeva")
debug(wee.getString("name"))
```

Storing a `List` that contains `Any` datatype. In this case `wee.getList` returns `WeeList`. Which is a dynamic list that can store any type data. but when you directly access the data you will get back the `String` version of the data. Because `WeeDB` stores everything as a `String`. In order to get a data with a specific datatype, you need to call an appropriate get method.

```kotlin
wee.put("names", listOf("Jeeva", "Fearless", 5, true))
val names = wee.getList("names")
debug(names[2]) // "5"
debug(names.getInt(2)) // 5
```


Storing a specific type `List`.

```kotlin
val scores = wee.newList("scores", Int::class.java)
scores.add(40)
scores.add(72)
scores.add(35)
scores.add(98)
debug(scores[0]+1) // 41
```

Storing a `Parcelable` data. Add the `kotlin-parcelize` plugin.

```gradle
// app build.gradle
plugins {
    ..
    id 'kotlin-parcelize'
}
```

```kotlin
@Parcelize
data class Person(val name: String, val age: String): Parcelable

wee.put("jeeva", Person("Jeeva", 26))
debug(wee.get("jeeva", Person::class.java).name)
```

Working with `Parcelable` list.

```kotlin
val jeeva = Person(name = "Jeeva", age = 26)
val senkathir = Person(name = "Senkathir", age = 15)

val persons = wee.newList("persons", Person::class.java)
persons.add(jeeva)
persons.add(senkathir)

debug(persons[0].name) // Jeeva
```

Looping through `WeeDB` list.

```kotlin
val persons = wee.newList("persons", Person::class.java)
persons.add(jeeva, senkathir)

for (person in persons) {
    debug(person.name)
}
```

Check if a key exists in `WeeDB`.

```kotlin
wee.put("person", Person(name = "Jeeva", age = 26))
if ("person" in wee) {
    // ...
}
```

Check if a value exists in `WeeDB` list.

> **NOTE**: Only works in a `WeeDB` list

```kotlin
val persons = wee.newList("persons", Person::class.java)
persons.add(jeeva, senkathir)

if (Person(name = "Jeeva", age = 26) in persons) {
    // ...
}
```
