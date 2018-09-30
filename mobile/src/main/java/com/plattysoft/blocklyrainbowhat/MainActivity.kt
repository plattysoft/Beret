package com.plattysoft.blocklyrainbowhat

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.blockly.android.codegen.CodeGenerationRequest
import android.app.ProgressDialog



class MainActivity : RainbowHatBlocklyBaseActivity(), ConnectionListener {

    companion object {
        private const val PERMISSION_REQUEST = 12
    }

    lateinit private var nearbyWrapper: NearbyWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nearbyWrapper = NearbyWrapper(this)
        nearbyWrapper.listener = this
    }

    private fun loadProgram(program: String) {
        if (checkAndRequestPermissionsIfNeeded()) {
            Log.d("Program", program)
            nearbyWrapper.sendProgram(program)
        }
    }

    private fun checkAndRequestPermissionsIfNeeded(): Boolean {
        if (isGranted(Manifest.permission.ACCESS_FINE_LOCATION) &&
                isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return true
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showPermissionsExplanationDialog()
        } else {
            requestPermissions()
        }
        return false
    }

    private fun isGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPermissionsExplanationDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.permissions_needed_title)
                .setMessage(R.string.permissions_needed)
                .setPositiveButton(R.string.ok, { dialogInterface: DialogInterface, i: Int ->
                    requestPermissions()
                })
                .create()
                .show()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
                arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()) {
                    for (result in grantResults) {
                       if (result != PackageManager.PERMISSION_GRANTED) {
                           Toast.makeText(this, R.string.permissions_needed_error, Toast.LENGTH_LONG).show()
                           return
                       }
                    }
                    // permissions were granted, retry loading the program
                    onRunCode()
                }
                else {
                    Toast.makeText(this, R.string.permissions_needed_error, Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    override fun getCodeGenerationCallback(): CodeGenerationRequest.CodeGeneratorCallback {
        return CodeGenerationRequest.CodeGeneratorCallback {
            loadProgram(it)
        }
    }

    override fun onStartDiscoverFailure(e: Exception) {
       Toast.makeText(this, "onStartDiscoverFailure: "+e.localizedMessage, Toast.LENGTH_LONG).show()
    }

    override fun onStartDiscoverSuccess() {
        Toast.makeText(this, "onStartDiscoverSuccess", Toast.LENGTH_LONG).show()
    }

    override fun onEndpointFound(endpointId: String) {
        Toast.makeText(this, "onEndpointFound: "+endpointId, Toast.LENGTH_LONG).show()
    }

    override fun onPayloadTransferUpdate(p1: PayloadTransferUpdate) {
        Toast.makeText(this, "onPayloadTransferUpdate: "+p1.status, Toast.LENGTH_LONG).show()
    }

    override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
        Toast.makeText(this, "onConnectionResult: "+result.status, Toast.LENGTH_LONG).show()
    }

}