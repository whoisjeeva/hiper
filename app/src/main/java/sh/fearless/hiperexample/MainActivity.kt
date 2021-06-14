package sh.fearless.hiperexample

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import sh.fearless.kookaburra.controllers.PermissionHandler
import sh.fearless.util.WeeDB
import sh.fearless.util.debug


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgView: ImageView = findViewById(R.id.imgView)
        val wee = WeeDB(applicationContext)
        val persons = listOf(
            Person("Jeeva", 26),
            Person("Senkathir", 15),
            Person("Theepan", 31),
            Person("Jeevitha", 28),
        )
        val data = wee.newList("data", Person::class.java)
        data += persons

        data.updateAt(1, Person(name = "Kumar", age = 75))

        for (d in data) {
            debug(d)
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
