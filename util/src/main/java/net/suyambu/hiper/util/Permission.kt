package net.suyambu.hiper.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat


@Composable
fun rememberActivityResult(callback: (Boolean) -> Unit): ManagedActivityResultLauncher<String, Boolean> {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        callback(isGranted)
    }
    return launcher
}

fun ManagedActivityResultLauncher<String, Boolean>.requestPermission(permission: String) {
    launch(permission)
}

//fun requestPermission(
//    permission: String,
//    launcher: ManagedActivityResultLauncher<String, Boolean>
//) {
////    val permissionCheckResult = ContextCompat.checkSelfPermission(context, permission)
////    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
////        // Open camera because permission is already granted
////    } else {
////        // Request a permission
////
////    }
//    launcher.launch(permission)
//}
