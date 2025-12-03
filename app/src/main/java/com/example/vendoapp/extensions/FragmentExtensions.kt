package com.example.vendoapp.extensions

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.FileOutputStream

fun Fragment.navigateFromParent(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
) {
    parentFragment?.parentFragment?.findNavController()?.navigate(resId, args, navOptions)
}

fun Context.createFileFromUri(uri: Uri): File {
    val inputStream = contentResolver.openInputStream(uri)
    val file = File(cacheDir, "upload_${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(file)

    inputStream?.copyTo(outputStream)

    inputStream?.close()
    outputStream.close()

    return file
}
