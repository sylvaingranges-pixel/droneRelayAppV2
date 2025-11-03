package com.pixel.dronerelayapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * USB Attach Activity
 * This activity handles USB connection events when the DJI remote controller is connected
 */
class UsbAttachActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "UsbAttachActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "USB device attached")
        
        // When USB is attached, launch the main activity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        
        // Finish this transparent activity
        finish()
    }
}
