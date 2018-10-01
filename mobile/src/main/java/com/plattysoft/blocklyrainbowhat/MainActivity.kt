package com.plattysoft.blocklyrainbowhat

import android.Manifest
import android.animation.Animator
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.blockly.android.codegen.CodeGenerationRequest
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import java.util.*


class MainActivity : RainbowHatBlocklyBaseActivity(), ConnectionListener {

    companion object {
        private val TAG = MainActivity.javaClass.simpleName.toString()
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
            if (!isDeploying()) {
                Log.d("Program", program)
                showProgress(R.string.preparing_deploy);
                nearbyWrapper.sendProgram(program)
            }
        }
    }

    private fun isDeploying(): Boolean {
        return findViewById<LinearLayout>(R.id.deploy_progress) != null
    }

    private fun showProgress(message: Int) {
        runOnUiThread {
            // Make the layout visible
            if (findViewById<LinearLayout>(R.id.deploy_progress) == null) {
                View.inflate(this, R.layout.deploy_progress, findViewById(android.R.id.content))
                findViewById<Button>(R.id.progress_cancel).setOnClickListener {
                    cancelDeploy()
                    dismissProgress()
                }
            }
            // Set the text tot he parameter passed
            findViewById<TextView>(R.id.progress_message).setText(message)
        }
    }

    private fun cancelDeploy() {
       nearbyWrapper.cancelDeploy()
    }

    private fun dismissProgress(message: String? = null) {
        runOnUiThread {
            if (message != null) {
                findViewById<ProgressBar>(R.id.progress_circular).visibility = View.INVISIBLE
                findViewById<Button>(R.id.progress_cancel).visibility = View.GONE

                findViewById<TextView>(R.id.progress_message).setText(message)
                Timer().schedule(object : TimerTask() {
                    override fun run() {
                            dismissProgress()
                    }
                }, 1000)
            }
            else {
                // Hide the progress layout
                hideLayoutWithAnimation()
            }
        }
    }

    private fun hideLayoutWithAnimation() {
        val progressView = findViewById<LinearLayout>(R.id.deploy_progress)
        if (progressView != null) {
            progressView.animate()
                    .translationYBy(progressView.height.toFloat())
                    .setDuration(200)
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {
                        }
                        override fun onAnimationEnd(p0: Animator?) {
                            findViewById<ViewGroup>(android.R.id.content).removeView(progressView)
                        }
                        override fun onAnimationCancel(p0: Animator?) {
                            findViewById<ViewGroup>(android.R.id.content).removeView(progressView)
                        }
                        override fun onAnimationStart(p0: Animator?) {
                        }
                    })
                    .start()
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
        Log.d(TAG, "onStartDiscoverFailure: "+e.localizedMessage)
        dismissProgress(e.localizedMessage)
    }

    override fun onStartDiscoverSuccess() {
        showProgress(R.string.device_searching)
        Log.d(TAG, "onStartDiscoverSuccess")
    }

    override fun onEndpointFound(endpointId: String) {
        showProgress(R.string.device_found_connecting)
    }

    override fun onPayloadTransferUpdate(p1: PayloadTransferUpdate) {
        if (p1.status == PayloadTransferUpdate.Status.SUCCESS) {
            dismissProgress(getString(R.string.deploy_completed))
        }
        if (p1.status == PayloadTransferUpdate.Status.FAILURE) {
            dismissProgress(getString(R.string.transfer_failed))
            Log.d(TAG, "onPayloadTransferUpdate: "+p1.status)
        }
    }

    override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
        if (result.status.statusCode == ConnectionsStatusCodes.STATUS_OK) {
            showProgress(R.string.sending_program)
        }
        if (result.status.statusCode == ConnectionsStatusCodes.STATUS_ERROR) {
            Log.d(TAG, "onConnectionResult: "+result.status)
            dismissProgress(result.status.statusMessage)
        }
    }

}