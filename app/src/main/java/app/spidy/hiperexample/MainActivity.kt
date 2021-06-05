package app.spidy.hiperexample

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.spidy.hiper.Hiper
import app.spidy.hiper.data.Headers
import app.spidy.hiper.utils.mix
import app.spidy.kookaburra.controllers.PermissionHandler
import app.spidy.kotlinutils.debug
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val hiper = Hiper.getInstance()
        thread {
            val response = hiper.post(
                url = "https://httpbin.org/post",
                headers = mix("user-agent" to "hello/1.0"),
                cookies = mix("name" to "jeeva"),
                form = mix("age" to 26)
            )
            debug("response:", response.text)
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PermissionHandler.STORAGE_PERMISSION_CODE ||
            requestCode == PermissionHandler.LOCATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PermissionHandler.execute()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
