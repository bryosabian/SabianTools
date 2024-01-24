package com.sabiantools.utilities.files

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import java.io.*

open class FileManager(private val context: Context) {

    @Throws
    fun createInternalDirectory(directoryName: String, asPrivate: Boolean = true, externalDirectoryType: String? = null, createIfNotFound: Boolean = true): File {
        val directory = if (asPrivate) {
            context.filesDir
        } else {
            context.getExternalFilesDir(externalDirectoryType
                    ?: if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Environment.DIRECTORY_DOCUMENTS
                    } else {
                        Environment.DIRECTORY_PICTURES
                    })
        } ?: throw java.lang.Exception("Could not find app directory")

        val fileName: String = "%s/%s/".format(directory, directoryName)

        val file = File(fileName)

        if (!file.exists() && createIfNotFound) {
            file.mkdir()
        }

        return file
    }


    @Throws
    fun createInternalFile(fileName: String, directoryName: String, asPrivate: Boolean = true, externalDirectoryType: String? = null): File {
        val directory = createInternalDirectory(directoryName, asPrivate, externalDirectoryType)
        return File(directory, fileName)
    }

    fun readFromFile(fileName: String): String? {
        val file = File(fileName)
        return file.readFrom()
    }

    fun readFromFile(file: File): String? {
        return file.readFrom()
    }

    @Throws
    fun writeToFile(file: File, data: String): File {
        file.writeTo(data)
        return file
    }


    @Throws
    fun writeToInternalFile(fileName: String, data: String): File {
        val fos = context.openFileOutput(
                fileName,
                Context.MODE_PRIVATE
        )
        fos.use {
            it.writeText(data)
        }
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
        file.clear()
    }

    @Throws(IOException::class)
    fun copy(src: File, dst: File) {
        src.copyTo(dst)
    }

    @Throws(Exception::class)
    fun getFileFromUri(uri: Uri): File? {
        return uri.toFile()
    }

    fun getFileFromPath(filePath: String, checkExists: Boolean = true): File? {
        return filePath.pathToFile(checkExists)
    }

    private fun String.pathToFile(checkExists: Boolean = true): File? {
        try {
            val file = File(this)
            if (checkExists && !file.exists()) {
                return null
            }
            return file
        } catch (e: Throwable) {
            e.printStackTrace()
            return null
        }
    }


    @Throws(Exception::class)
    private fun Uri.toFile(): File? {
        return path?.let { File(it) }
    }

    private fun File.clear() {
        if (exists()) {
            val writer = PrintWriter(this)
            writer.print("")
            writer.close()
        }
    }

    private fun File.copyTo(dst: File) {
        FileInputStream(this).use { `in` ->
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


    /**
     * Size limit of 2GB
     */
    private fun File.writeTo(data: String) {
        writeText(data)
    }


    /**
     * Size limit of 2GB
     */
    private fun File.readFrom(): String? {
        if (!exists()) {
            return null
        }
        val content = readText()
        return content
    }

    /**
     * Writes text to file
     */
    private fun FileOutputStream.writeText(data: String) {
        val os = OutputStreamWriter(this)
        os.use {
            it.write(data)
        }
    }

}