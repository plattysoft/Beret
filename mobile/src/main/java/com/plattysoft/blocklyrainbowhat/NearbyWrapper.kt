package com.plattysoft.blocklyrainbowhat

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.blockly.android.AbstractBlocklyActivity

class NearbyWrapper (context: Context){

    companion object {
        private const val NICKNAME = "Remote Editor" // This could be the device ID
        private val TAG = NearbyWrapper::class.java.simpleName
    }

    private val nearbyConnection = Nearby.getConnectionsClient(context)

    private var currentProgram: String = ""

    private var connectedEndpoint: String? = null

    var listener: ConnectionListener? = null

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            Log.e(TAG, "onEndpointFound: start: "+endpointId)
            listener?.onEndpointFound(endpointId)
            connectToEndpoint(endpointId)
            nearbyConnection.stopDiscovery()
        }
        override fun onEndpointLost(endpointId: String) {
            // A previously discovered endpoint has gone away.
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback(){
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            listener?.onConnectionResult(endpointId, result)

            if (result.status.statusCode == ConnectionsStatusCodes.STATUS_OK) {
                Log.e(TAG, "onConnectionResult: STATUS_OK")
                connectedEndpoint = endpointId
                Log.e(TAG, "sendPayload: start")
                nearbyConnection.sendPayload(connectedEndpoint!!, Payload.fromBytes(currentProgram.toByteArray()))
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
            listener?.onPayloadTransferUpdate(p1)

            // When we are done sending the payload, we disconnect and stop discovering
            if (p1.status == PayloadTransferUpdate.Status.SUCCESS) {
                Log.e(TAG, "onPayloadTransferUpdate: SUCCESS")

                if (connectedEndpoint != null) {
                    nearbyConnection.disconnectFromEndpoint(connectedEndpoint!!)
                }
                connectedEndpoint = null
            }
        }
    }

    private fun connectToEndpoint(endpointId: String) {
        Log.e(TAG, "connectToEndpoint: start")

        nearbyConnection.requestConnection(
                NICKNAME,
                endpointId,
                connectionLifecycleCallback)
    }

    private fun startDiscovery() {
        Log.e(TAG, "startDiscovery: start")

        nearbyConnection.startDiscovery(
                RainbowHatBlocklyBaseActivity.SERVICE_ID,
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder().setStrategy(RainbowHatBlocklyBaseActivity.STRATEGY).build())
                .addOnFailureListener {
                    listener?.onStartDiscoverFailure(it)
                    Log.e(TAG, "startDiscovery: onFailure: "+it.localizedMessage)
                    // Just in case if the error is about already discovering
                    nearbyConnection.stopDiscovery()
                }
                .addOnSuccessListener {
                    listener?.onStartDiscoverSuccess()
                    Log.e(TAG, "startDiscovery: Discovering...")
                }
    }

    fun sendProgram(program: String) {
        Log.e(TAG, "sendProgram: start")

        currentProgram = program
        // We only send them if we are in a good state
        if (connectedEndpoint != null) {
            nearbyConnection.disconnectFromEndpoint(connectedEndpoint!!)
        }
        nearbyConnection.stopDiscovery()
        startDiscovery()
    }
}
