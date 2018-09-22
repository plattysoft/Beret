package com.plattysoft.blocklyrainbowhat

import android.os.Bundle
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.blockly.android.codegen.CodeGenerationRequest

class MainActivity : RainbowHatBlocklyBaseActivity() {

    companion object {
        private const val NICKNAME = "Remote Editor" // This could be the device ID
        private val TAG = MainActivity::class.simpleName
    }

    lateinit var nearbyConnection : ConnectionsClient
    private var connectedEndpoint: String? = null

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            connectToEndpoint(endpointId)
        }
        override fun onEndpointLost(endpointId: String) {
            // A previously discovered endpoint has gone away.
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback(){
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.statusCode == ConnectionsStatusCodes.STATUS_OK) {
                connectedEndpoint = endpointId
            }
        }
        override fun onDisconnected(endpointId: String) {
            if (endpointId.equals(connectedEndpoint)) {
                connectedEndpoint = null
            }
        }
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            nearbyConnection.acceptConnection(endpointId, payloadCallback)
        }
    }

    private val payloadCallback = object: PayloadCallback() {
        override fun onPayloadReceived(p0: String, payload: Payload) {
            // Not expecting to receive anything yet
        }

        override fun onPayloadTransferUpdate(p0: String, p1: PayloadTransferUpdate) {
            // Nothing to do here
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nearbyConnection = Nearby.getConnectionsClient(this)
        startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        nearbyConnection.stopDiscovery()
    }

    private fun connectToEndpoint(endpointId: String) {
        nearbyConnection.requestConnection(
                NICKNAME,
                endpointId,
                connectionLifecycleCallback)
    }

    private fun startDiscovery() {
        nearbyConnection.startDiscovery(
                SERVICE_ID,
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder().setStrategy(STRATEGY).build())
                .addOnFailureListener {
                    Log.e(TAG, "onFailure: "+it.localizedMessage)
                }
                .addOnSuccessListener {
                    Log.e(TAG, "Discovering...")
                }
    }

    private fun loadProgram(program: String) {
        Log.d("Program", program)
    }

    override fun getCodeGenerationCallback(): CodeGenerationRequest.CodeGeneratorCallback {
        return CodeGenerationRequest.CodeGeneratorCallback {
            loadProgram(it)
            nearbyConnection.sendPayload(connectedEndpoint!!, Payload.fromBytes(it.toByteArray()))
        }
    }
}