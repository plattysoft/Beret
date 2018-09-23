package com.plattysoft.blocklyrainbowhat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.blockly.android.codegen.CodeGenerationRequest

class MainActivity : RainbowHatBlocklyBaseActivity(), ConnectionListener {

    lateinit private var nearbyWrapper: NearbyWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nearbyWrapper = NearbyWrapper(this)
        nearbyWrapper.listener = this
    }

    private fun loadProgram(program: String) {
        nearbyWrapper.sendProgram(program)
        Log.d("Program", program)
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