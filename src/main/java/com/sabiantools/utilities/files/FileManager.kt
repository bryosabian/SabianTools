package com.sabiantools.utilities.files

import android.content.Context
import android.net.Uri
import java.io.*

open class FileManager(private val context: Context) {

    @Throws
    fun writeToFile(fileName: String, data: String): File {
        val fos = context.openFileOutput(
                fileName,
                Context.MODE_PRIVATE
        )
        var error: Throwable? = null
        var os: OutputStreamWriter? = null
        try {
            os = OutputStreamWriter(fos)
            os.write(data)
        } catch (e: Exception) {
            error = e
        } finally {
            try {
                os?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (error != null)
            throw error

        return context.getFileStreamPath(fileName)
    }

    @Throws
    fun copyFromPrivateToPublic(sourcePath: String, destinationPath: String): File? {
        val sourceFile = context.applicationContext.getFileStreamPath(sourcePath)
        val destinationFile = File(destinationPath)
        clearFile(destinationFile)
        copy(sourceFile, destinationFile)
        return destinationFile
    }

    @Throws(FileNotFoundException::class)
    fun clearFile(file: File) {
        if (file.exists()) {
            val writer = PrintWriter(file)
            writer.print("")
            writer.close()
        }
    }

    @Throws(IOException::class)
    fun copy(src: File, dst: File) {
        FileInputStream(src).use { `in` ->
            FileOutputStream(dst).use { out ->
                // Transfer bytes from in to out
                val buf = ByteArray(1024)
                var len: Int
                while (`in`.read(buf).also { len = it } > 0) {
                    out.write(buf, 0, len)
                }
            }
        }
    }

    @Throws(Exception::class)
    fun getFileFromUri(uri: Uri): File? {
        return uri.path?.let { File(it) }
    }

    fun getFileFromPath(filePath: String, checkExists: Boolean = true): File? {
        try {
            val file = File(filePath)
            if (checkExists && !file.exists()) {
                return null
            }
            return file
        } catch (e: Throwable) {
            e.printStackTrace()
            return null
        }
    }
}