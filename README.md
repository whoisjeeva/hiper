[![](https://jitpack.io/v/anyms/hiper.svg)](https://jitpack.io/#anyms/hiper)
[![Build Status](https://travis-ci.com/anyms/hiper.svg?branch=master)](https://travis-ci.com/anyms/hiper)
[![License](https://img.shields.io/github/license/anyms/hiper.svg)](https://github.com/anyms/hiper/blob/master/LICENSE)

# Hiper - A Human Friendly HTTP Library for Android

> Hiper is for human by human.

# Getting Started

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
implementation "com.github.okbash.hiper:http:$hiper_version"
```

For utils

```css
implementation "com.github.okbash.hiper:util:$hiper_version"
```


# Using utils

The utils library contains few useful method that makes your android development little bit easier.

**Context.toast(o: Any?, isLong: Boolean = false)**

Showing a toast message is easier with this extension method.

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ...

        toast("hello, world")
    }
}
```

**debug(o: Any?)**, **error(o: Any?)**, **warn(o: Any?)**, **info(o: Any?)**

When you debug your application you use the `Log.d` a lot, it require a TAG and a string as value. With the `debug` method you can pass any value and it will use the class name wherever it called from as TAG (with the .kt extension).


```kotlin
val person = Person()
debug(person)
```

**onUiThread(callback: () -> Unit)**

Switch to UI thread even without the `Activity`

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

**async(block: suspend CoroutineScope.() -> Unit)**

Launch a coroutine using `Dispatchers.IO`

```kotlin
async {
    // do your background tasks
}
```


**sleep(millis: Long)**

Suspend `async` for specified milliseconds.

```kotlin
async {
    ...
    sleep(1000) // 1 second
}
```

**CoroutineScope.run(block: () -> Unit)**

Run blocking code inside `async`

```kotlin
async {
    ...
    run {
        // blocking code
    }
}
```

**ignore(callback: Exception?.() -> Unit)**

If you are tired of writing `try {} catch(e: Exception) {}` to ignore exception.

**Note**: Use it with caution can trip you off.

```kotlin
ignore {
    ...
    debug(this)
}
```


**Context.isDarkThemeOn(): Boolean**

Check if dark mode is enabled

```kotlin
if (isDarkThemeOn()) {
    // switch app to dark mode
}
```

**Context.readFile(dir: String, fileName: String): ByteArray**

Read a file from `getExternalFilesDir`

```kotlin
val data: ByteArray = readFile(Environment.DIRECTORY_DOWNLOADS, "test.txt")
```


**Context.readFromRawFolder(path: String): ByteArray**

Read a file from `raw` folder

```kotlin
// R.raw.test
val data: ByteArray = readFromRawFolder("test")
```

**fetch(url: String, callback: BufferedReader.() -> Unit)**

Send a GET http request

```kotlin
fetch("https://httpbin.org/ip") { reader ->

}
```

**Context.newDialog(): Dialog**

Creating an `AlertDialog` is easier with it.

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


**TinyDB**

The utils library also contains `TinyDB` that allows you to persist some simple data.

```kotlin
val tinyDB = TinyDB(applicationContext)
tinyDB.putString("KEY", "hello")
debug(tinyDB.getString("KEY"))
```


# Using HTTP

Create a hiper instance.

```kotlin
val hiper = Hiper.getInstance() // for synchronous requests
// or
val hiper = Hiper.getInstance().async() // for asynchronous requests
```

Now you are ready to see the power of hiper.

```kotlin
// simple GET request
hiper.get("http://httpbin.org/get")
    .resolve { response -> }
    .reject { response -> }
    .catch { exception -> }

// simple POST request
hiper.post("http://httpbin.org/post")
    .resolve { response -> }
    .reject { response -> }
    .catch { exception -> }

// sending parameters with your request
val args = Headers(
    "name" to "Hiper",
    "age" to 1
)
hiper.get("http://httpbin.org/get", args = args)
    .resolve { response -> }
    .reject { response -> }
    .catch { exception -> }

// or you can use inline args, headers, cookies or form using the mix method

hiper.get("http://httpbin.org/get", args = mix("name" to "Hiper"), headers = mix("user-agent" to "Hiper/1.0")

// custom headers
val headers = Headers(
    "User-Agent" to "Hiper/1.1"
)
hiper.get("http://httpbin.org/get", headers = headers)
    .resolve { response -> }
    .reject { response -> }
    .catch { exception -> }
```

Download binary files

```kotlin
hiper.get("http://httpbin.org/get", isStream = true)
    .reject { response -> }
    .resolve { response ->
        // use the stream
        val stream = response.stream
        ...

        // remember to close the stream
        stream.close()
    }
    .catch { exception -> }
```


**Note**: Do not forget to call the `.catch {}` at the end of every request. The `.catch {}` block is the setup of your HTTP request, and actually that's what trigger the HTTP request.
