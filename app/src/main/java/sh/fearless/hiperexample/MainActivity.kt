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
        val jeeva = Person(name = "Jeeva", age = 26)
        val siva = Person(name = "siva", age = 12)
        val anyms = Person(name = "anyms", age = 26)

        wee.remove("persons")
        val persons = wee.newList("persons", Person::class.java)
        persons.add(jeeva, siva)



        debug(persons[0])
        debug("Is contains Jeeva?", jeeva in persons)
        debug("Is contains Anyms?", anyms in persons)

        debug(persons[0])
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
