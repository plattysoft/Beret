package com.plattysoft.blocklyrainbowhat

import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.PayloadTransferUpdate

interface ConnectionListener {
    fun onEndpointFound(endpointId: String)
    fun onPayloadTransferUpdate(p1: PayloadTransferUpdate)
    fun onConnectionResult(endpointId: String, result: ConnectionResolution)
    fun onStartDiscoverFailure(e: Exception)
    fun onStartDiscoverSuccess()
}
