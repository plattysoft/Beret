package com.plattysoft.blocklyrainbowhat

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*

class NearbyIotWrapper (context: Context, listener: RemoteEditorListener) {

    interface RemoteEditorListener {
        fun onProgramReceived(program: String)
    }

    companion object {
        private val TAG = NearbyIotWrapper::class.java.simpleName
        private const val NICKNAME = "IoT Host"
    }

    private val nearbyConnections = Nearby.getConnectionsClient(context)

    private val payloadCallback = object: PayloadCallback() {
        override fun onPayloadReceived(p0: String, p1: Payload) {
            // I have received a program
            val program = String(p1.asBytes()!!)
            listener.onProgramReceived(program)
        }

        override fun onPayloadTransferUpdate(p0: String, p1: PayloadTransferUpdate) {
        }
    }

    private val connectionLifecycleCallback= object : ConnectionLifecycleCallback(){
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
            nearbyConnections.acceptConnection(endpointId, payloadCallback)
        }
    }

    private var connectedEndpoint: String? = null

    init {
        startAdvertising()
    }

    private fun startAdvertising() {
        // Cleanup state
        if (connectedEndpoint != null) {
            nearbyConnections.disconnectFromEndpoint(connectedEndpoint!!)
            connectedEndpoint = null
        }
        nearbyConnections.stopAdvertising()

        nearbyConnections.startAdvertising(
                NICKNAME,
                RainbowHatBlocklyBaseActivity.SERVICE_ID,
                connectionLifecycleCallback,
                AdvertisingOptions.Builder().setStrategy(RainbowHatBlocklyBaseActivity.STRATEGY).build())
                .addOnSuccessListener {
                    Log.d(TAG, "Advertising...")
                }
                .addOnFailureListener {
                    Log.d(TAG, "onFailure: "+it.localizedMessage)
                }
    }

    fun stop() {
        nearbyConnections.stopAdvertising()
    }
}
