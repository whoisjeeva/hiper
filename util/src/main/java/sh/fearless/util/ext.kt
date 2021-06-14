package sh.fearless.util

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL


fun Context.toast(o: Any?, isLong: Boolean = false) {
    val duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    Toast.makeText(this, o.toString(), duration).show()
}

fun debug(vararg o: Any?) {
    Throwable().stackTrace[1].also {
        var s = ""
        for (i in o) {
            s += "$i "
        }
        Log.d("[${it.lineNumber}]:${it.fileName}", s.dropLast(1))
    }
}

fun error(vararg o: Any?) {
    Throwable().stackTrace[1].also {
        var s = ""
        for (i in o) {
            s += "$i "
        }
        Log.e("[${it.lineNumber}]:${it.fileName}", s.dropLast(1))
    }
}

fun warn(vararg o: Any?) {
    Throwable().stackTrace[1].also {
        var s = ""
        for (i in o) {
            s += "$i "
        }
        Log.w("[${it.lineNumber}]:${it.fileName}", s.dropLast(1))
    }
}

fun info(vararg o: Any?) {
    Throwable().stackTrace[1].also {
        var s = ""
        for (i in o) {
            s += "$i "
        }
        Log.i("[${it.lineNumber}]:${it.fileName}", s.dropLast(1))
    }
}


fun onUiThread(callback: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        callback()
    }
}

suspend fun sleep(millis: Long) {
    delay(millis)
}

fun async(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        block.invoke(this)
    }
}

fun CoroutineScope.run(block: () -> Unit) {
    block()
}

@Deprecated(message = "It does not works in some cases", replaceWith = ReplaceWith(""), level = DeprecationLevel.WARNING)
fun ignore(callback: Exception?.() -> Unit) {
    try {
        callback(null)
    } catch (e: Exception) {
        callback(e)
    }
}


fun Context.isDarkThemeOn(): Boolean {
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}


fun Context.readFile(dir: String, fileName: String): ByteArray {
    val fileDir = getExternalFilesDir(dir)
    val file = File(fileDir, fileName)
    val fos = FileInputStream(file)
    val data = fos.readBytes()
    fos.close()
    return data
}

fun Context.readFromRawFolder(path: String): ByteArray {
    val outputStream = ByteArrayOutputStream()
    val inputStream = resources.openRawResource(
        resources.getIdentifier(
            path,
            "raw", packageName
        )
    )

    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } != -1) {
        outputStream.write(buf, 0, len)
    }
    outputStream.close()
    inputStream.close()
    return outputStream.toByteArray()
}


fun fetch(url: String, callback: BufferedReader.() -> Unit) {
    async {
        run {
            val uri = URL(url)

            with(uri.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                val reader = inputStream.bufferedReader()
                callback(reader)
                reader.close()
            }
        }
    }
}

fun Context.newDialog(): Dialog {
    return Dialog(this)
}


fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
