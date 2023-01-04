package com.sabiantools.utilities.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.sabiantools.utilities.SabianUtilities
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*
import kotlin.coroutines.CoroutineContext

class GeoCoder(
    context: Context,
    listener: OnGeoDescriptionListener
) : CoroutineScope {

    companion object {
        @JvmStatic
        fun getQualifiedDescription(addresses: List<Address>?): String {
            return getQualifiedDescription(addresses, "Waiting for location")
        }

        @JvmStatic
        fun getQualifiedDescription(
            addresses: List<Address>?,
            default: String = "Waiting for Location"
        ): String {
            try {
                if (addresses == null) {
                    return default
                }
                return if (addresses.isEmpty()) {
                    default
                } else {
                    val address = addresses.first()
                    "%s,%s,%s".format(
                        address.featureName ?: "",
                        address.locality ?: "",
                        address.countryName ?: ""
                    )
                }
            } catch (e: Exception) {
                return default
            }
        }
    }


    private var geoCoder: Geocoder? = null

    private val debounceRate: Long = 500
    var allowDebounce: Boolean = true

    private var defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var mainDispatcher: CoroutineDispatcher = Dispatchers.Main


    private var contextReference: WeakReference<Context> = WeakReference(context)

    private val context: Context
        get() {
            return contextReference.get()!!
        }

    private var listenerReference: WeakReference<OnGeoDescriptionListener> = WeakReference(listener)
    private val listener: OnGeoDescriptionListener?
        get() {
            return listenerReference.get()
        }

    private var decodeJob: Job? = null
    private var exception: Exception? = null
    private var addresses: List<Address>? = null

    /**
     * The maximum addresses to return
     */
    var maxAddresses = 1

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0


    private var isComplete = false

    fun get(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        get()
    }

    private fun get() {
        beforeDecode()

        decodeJob?.cancel()

        decodeJob = launch(defaultDispatcher) {
            if (allowDebounce)
                delay(debounceRate)
            decode()
            isComplete = true
        }

        launch(mainDispatcher) {
            decodeJob?.join()
            afterDecode()
        }
    }

    /**
     * Runs the search operation
     */
    @Synchronized
    private fun decode() {
        try {

            if (!SabianUtilities.IsNetworkOn(context))
                throw Exception("Could not decode location : No internet connection")

            geoCoder = Geocoder(context, Locale.ENGLISH)

            SabianUtilities.WriteLog(
                "Decoder",
                "Getting geo coordinates description from $latitude : $longitude"
            )


            val addresses = geoCoder!!.getFromLocation(latitude, longitude, maxAddresses)
            if (addresses == null || addresses.isEmpty()) {
                SabianUtilities.WriteLog(
                    "Decoder",
                    "No geo coordinates gotten cause its null or empty"
                )
                return
            }

            this.addresses = addresses

        } catch (e: IOException) {
            exception = e
            SabianUtilities.WriteLog(
                "Decoder",
                "No geo coordinates gotten cause of IOException ${e.message}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            SabianUtilities.WriteLog(
                "Decoder",
                "No geo coordinates gotten cause of Exception ${e.message}"
            )
            exception = e
        }
    }

    /**
     * Called before search
     */
    private fun beforeDecode() {
        exception = null
        addresses = null
        geoCoder = null
        isComplete = false
        listener?.onDecoding()
    }

    private fun afterDecode() {
        exception?.let {
            listener?.onDecodeFailed(it)
        } ?: run {
            if (!isComplete) {
                listener?.onDecodeCancel()
                SabianUtilities.WriteLog(
                    "Decoder",
                    "Decoder has been cancelled"
                )
            } else {
                listener?.onDecoded(addresses)
                SabianUtilities.WriteLog(
                    "Decoder",
                    "Decoder has completed successfully"
                )
            }
        }
        geoCoder = null
        isComplete = false
    }


    /**
     * Cancels the search operation
     */
    fun cancel() {
        try {
            decodeJob?.let {
                if (it.isActive) {
                    it.cancel()
                }
            }
            listenerReference.clear()
            geoCoder = null
        } catch (e: Exception) {
            SabianUtilities.WriteLog("Decoder", "Could not cancel search job ${e.message}")
            e.printStackTrace()
        }
    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    interface OnGeoDescriptionListener {
        fun onDecoding() {
            //Do nothing
        }

        fun onDecoded(addresses: List<Address>?)
        fun onDecodeFailed(e: Exception) {
            e.printStackTrace()
        }

        fun onDecodeCancel() {
//Do nothing
        }
    }
}
