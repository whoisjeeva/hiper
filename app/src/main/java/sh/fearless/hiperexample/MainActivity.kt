package sh.fearless.hiperexample

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import sh.fearless.hiper.Hiper
import sh.fearless.kookaburra.controllers.PermissionHandler
import sh.fearless.util.WeeDB
import sh.fearless.util.debug


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hiper = Hiper.getInstance().async()
        val dollar = '$'
        val json = """
            {"query":"\n    query browse(            ${dollar}input            : BrowseAsUgcInput!) {\n      browseAsUgc(input:             ${dollar}input            ) {\n        ...browseContentItemsResource\n      }\n    }\n    \n  fragment browseContentItemsResource on BrowseContentItems {\n    page\n    total\n    items {\n      ... on BrowseWallpaper {\n        id\n        contentType\n        title\n        tags\n        imageUrl\n        placeholderUrl\n        licensed\n      }\n\n      ... on BrowseRingtone {\n        id\n        contentType\n        title\n        tags\n        imageUrl\n        placeholderUrl\n        licensed\n        meta {\n          durationMs\n          previewUrl\n          gradientStart\n          gradientEnd\n        }\n      }\n    }\n  }\n\n  ","variables":{"input":{"contentType":"WALLPAPER","page":2,"size":24}}}
        """.trimIndent()
        hiper.post("https://api-gateway.zedge.net/", json = JSONObject(json)).resolve {
            debug(it.text)
        }.reject {
            debug("Rejected:", it.text)
        }.catch {
            debug("Exception:", it)
        }.execute()
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
