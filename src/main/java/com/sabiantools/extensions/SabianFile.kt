package com.sabiantools.extensions

import java.io.File
import java.net.URI

val String.fileNameFromUrl: String
    get() {
        val uri = URI(this)
        return File(uri.path).name
    }