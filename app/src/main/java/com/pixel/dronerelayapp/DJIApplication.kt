package com.pixel.dronerelayapp

import android.app.Application
import android.util.Log
import dji.v5.common.error.IDJIError
import dji.v5.common.register.DJISDKInitEvent
import dji.v5.manager.SDKManager
import dji.v5.manager.interfaces.SDKManagerCallback

/**
 * DJI Application class for initializing MSDK v5
 * This handles SDK registration and initialization
 */
class DJIApplication : Application() {

    companion object {
        private const val TAG = "DJIApplication"
        
        @Volatile
        private var isSDKRegistered = false
        
        @Volatile
        private var registrationCallback: ((Boolean, String?) -> Unit)? = null
        
        fun isSDKRegistered(): Boolean = isSDKRegistered
        
        fun setRegistrationCallback(callback: (Boolean, String?) -> Unit) {
            registrationCallback = callback
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "DJIApplication onCreate")
        
        // Initialize DJI SDK
        initSDK()
    }

    private fun initSDK() {
        Log.d(TAG, "Initializing DJI SDK...")
        
        SDKManager.getInstance().init(this, object : SDKManagerCallback {
            override fun onRegisterSuccess() {
                Log.i(TAG, "SDK registration successful!")
                isSDKRegistered = true
                registrationCallback?.invoke(true, null)
            }

            override fun onRegisterFailure(error: IDJIError?) {
                val errorMsg = error?.toString() ?: "Unknown error"
                Log.e(TAG, "SDK registration failed: $errorMsg")
                isSDKRegistered = false
                registrationCallback?.invoke(false, errorMsg)
            }

            override fun onProductDisconnect(productId: Int) {
                Log.d(TAG, "Product disconnected: $productId")
            }

            override fun onProductConnect(productId: Int) {
                Log.d(TAG, "Product connected: $productId")
            }

            override fun onProductChanged(productId: Int) {
                Log.d(TAG, "Product changed: $productId")
            }

            override fun onInitProcess(event: DJISDKInitEvent?, totalProcess: Int) {
                Log.d(TAG, "SDK init process: ${event?.name}, progress: $totalProcess")
            }

            override fun onDatabaseDownloadProgress(current: Long, total: Long) {
                Log.d(TAG, "Database download progress: $current / $total")
            }
        })
    }
}
