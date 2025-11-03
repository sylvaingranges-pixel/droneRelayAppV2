package com.pixel.dronerelayapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pixel.dronerelayapp.databinding.ActivityMainBinding
import dji.v5.manager.KeyManager
import dji.v5.manager.SDKManager
import dji.v5.manager.aircraft.perception.data.ObstacleData
import dji.v5.manager.interfaces.SDKManagerCallback
import dji.v5.common.error.IDJIError
import dji.v5.common.register.DJISDKInitEvent

/**
 * Main Activity for DJI MSDK Sample Application
 * Supports DJI Mini 4 Pro with MSDK v5.16.0
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_PERMISSION_CODE = 12345
        
        private val REQUIRED_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }

    private lateinit var binding: ActivityMainBinding
    private var isProductConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        Log.d(TAG, "MainActivity onCreate")
        
        // Check and request permissions
        checkAndRequestPermissions()
        
        // Set up SDK registration callback
        DJIApplication.setRegistrationCallback { success, error ->
            runOnUiThread {
                if (success) {
                    binding.textViewSdkStatus.text = getString(R.string.sdk_registration_success)
                    binding.textViewSdkStatus.setTextColor(
                        ContextCompat.getColor(this, R.color.status_green)
                    )
                    Toast.makeText(this, R.string.sdk_registration_success, Toast.LENGTH_SHORT).show()
                    
                    // Set up product connection listener
                    setupProductConnectionListener()
                } else {
                    val errorMsg = getString(R.string.sdk_registration_failed, error ?: "Unknown")
                    binding.textViewSdkStatus.text = errorMsg
                    binding.textViewSdkStatus.setTextColor(
                        ContextCompat.getColor(this, R.color.status_red)
                    )
                    Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                }
            }
        }
        
        // Check if SDK is already registered
        if (DJIApplication.isSDKRegistered()) {
            binding.textViewSdkStatus.text = getString(R.string.sdk_registration_success)
            binding.textViewSdkStatus.setTextColor(
                ContextCompat.getColor(this, R.color.status_green)
            )
            setupProductConnectionListener()
        }
    }

    private fun checkAndRequestPermissions() {
        val missingPermissions = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                missingPermissions.toTypedArray(),
                REQUEST_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == REQUEST_PERMISSION_CODE) {
            val deniedPermissions = permissions.filterIndexed { index, _ ->
                grantResults[index] != PackageManager.PERMISSION_GRANTED
            }
            
            if (deniedPermissions.isNotEmpty()) {
                Log.w(TAG, "Some permissions were denied: $deniedPermissions")
                Toast.makeText(
                    this,
                    "Some permissions were denied. App functionality may be limited.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupProductConnectionListener() {
        // Check current product connection status
        val keyManager = KeyManager.getInstance()
        if (keyManager != null) {
            // If KeyManager is available, we can query product info
            updateConnectionStatus()
        }
    }

    private fun updateConnectionStatus() {
        try {
            val keyManager = KeyManager.getInstance()
            if (keyManager != null) {
                // Try to get product information
                val productType = SDKManager.getInstance().product?.productType
                
                if (productType != null) {
                    isProductConnected = true
                    val productName = productType.name
                    
                    binding.textViewConnectionStatus.text = 
                        getString(R.string.product_connected, productName)
                    binding.textViewConnectionStatus.setTextColor(
                        ContextCompat.getColor(this, R.color.status_green)
                    )
                    
                    // Show drone info card
                    binding.cardViewDroneInfo.visibility = View.VISIBLE
                    updateDroneInfo(productName)
                    
                    Toast.makeText(
                        this,
                        getString(R.string.product_connected, productName),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showDisconnected()
                }
            } else {
                showDisconnected()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating connection status", e)
            showDisconnected()
        }
    }

    private fun showDisconnected() {
        isProductConnected = false
        binding.textViewConnectionStatus.text = getString(R.string.no_product_connected)
        binding.textViewConnectionStatus.setTextColor(
            ContextCompat.getColor(this, R.color.status_red)
        )
        binding.cardViewDroneInfo.visibility = View.GONE
    }

    private fun updateDroneInfo(productName: String) {
        try {
            binding.textViewModelName.text = getString(R.string.model_name, productName)
            
            // Try to get firmware version
            val firmwareVersion = "Unknown" // Placeholder - requires specific key access
            binding.textViewFirmware.text = getString(R.string.firmware_version, firmwareVersion)
            
            // Try to get serial number
            val serialNumber = "Unknown" // Placeholder - requires specific key access
            binding.textViewSerial.text = getString(R.string.serial_number, serialNumber)
            
            // Try to get battery level
            val batteryLevel = 0 // Placeholder - requires specific key access
            binding.textViewBattery.text = getString(R.string.battery_level, batteryLevel)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error updating drone info", e)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume")
        
        // Update connection status when app resumes
        if (DJIApplication.isSDKRegistered()) {
            updateConnectionStatus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity onDestroy")
    }
}
