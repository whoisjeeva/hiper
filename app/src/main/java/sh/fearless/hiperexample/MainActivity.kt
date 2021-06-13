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
        val weeDB = WeeDB(applicationContext)
        val jeeva = Person(name = "Jeeva", age = 26)
        val siva = Person(name = "siva", age = 12)

        weeDB.remove("persons")
        val persons = if ("persons" !in weeDB) {
            val persons = weeDB.newList("persons")
            persons.add(jeeva, siva)
            persons
        } else {
            weeDB.getList("persons")
        }

        if (Person("siva", 12) in persons!!) {
            debug("HAS! HAS!")
        } else {
            debug("NO! NO!")
        }
        val s1 = weeDB.turnValue(Person("siva", 43))
        val s2 = persons[0]
        debug(s1.length)
        debug(s2.length)
        debug(persons.get(0, Person::class.java))
        debug(persons.size)
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
