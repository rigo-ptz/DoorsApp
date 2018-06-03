package com.jollypanda.petrsudoors.ui.main

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.data.remote.response.ACTION
import com.jollypanda.petrsudoors.data.remote.response.KeyResponse
import com.jollypanda.petrsudoors.databinding.ActivityMainBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity
import com.jollypanda.petrsudoors.ui.result.FailActivity
import com.jollypanda.petrsudoors.ui.result.SuccessActivity
import com.jollypanda.petrsudoors.utils.extension.viewModel
import com.jollypanda.petrsudoors.utils.formatting.formatToRequest
import java.util.*


/**
 * @author Yamushev Igor
 * @since  26.03.18
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    
    override val layout: Int = R.layout.activity_main
    
    private val vm by viewModel(MainViewModel::class)
    
    private var startDiscoveryMills = 0L
    private var successStartDiscovery = 0L
    private var endPointFoundStartDiscovery = 0L
    private var connectionOK = 0L
    private var startGetKey = 0L
    private var endGetKey = 0L
    private var startReturnKey = 0L
    private var endReturnKey = 0L
    
    
    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            Log.e("NEARBY", "endpointDiscoveryCallback onEndpointFound $endpointId ${discoveredEndpointInfo.endpointName} ${discoveredEndpointInfo.serviceId}")
            if (discoveredEndpointInfo.serviceId != "com.jollypanda.doorsthingsappID1") return
            endPointFoundStartDiscovery = System.currentTimeMillis()
            Log.e("NEARBY", "endpointDiscoveryCallback onEndpointFound TIME = ${(endPointFoundStartDiscovery - successStartDiscovery) / 1000}}")
            connectToEndPoint(endpointId)
        }
        
        override fun onEndpointLost(endpointId: String) {
            Log.e("NEARBY", "endpointDiscoveryCallback onEndpointLost")
            // A previously discovered endpoint has gone away.
            vm.endPointId = null
            binding.showProgress = true
            binding.isLookoutFound = false
        }
    }
    
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            Log.e("NEARBY", "connectionLifecycleCallback onConnectionInitiated from ${connectionInfo.endpointName} isIncoming=${connectionInfo.isIncomingConnection}\"")
            // Automatically accept the connection on both sides.
            Nearby.getConnectionsClient(this@MainActivity).acceptConnection(endpointId, payloadCallback)
        }
        
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    vm.endPointId = endpointId
                    connectionOK = System.currentTimeMillis()
                    Log.e("NEARBY", "connectionLifecycleCallback onConnectionResult OK TIME = ${(connectionOK - endPointFoundStartDiscovery) / 1000}")
                    Log.e("NEARBY", "ALL TIME = ${(successStartDiscovery - startDiscoveryMills + endPointFoundStartDiscovery - successStartDiscovery + connectionOK - endPointFoundStartDiscovery) / 1000} s")
                    Nearby.getConnectionsClient(this@MainActivity).stopDiscovery()
                    binding.showProgress = false
                    binding.isLookoutFound = true
                }
                
                ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                    Log.e("NEARBY", "connectionLifecycleCallback onConnectionResult REJECTED")
                }
                
                ConnectionsStatusCodes.STATUS_ERROR -> {
                    Log.e("NEARBY", "connectionLifecycleCallback onConnectionResult ERROR")
                }
            }
        }
        
        override fun onDisconnected(endpointId: String) {
            // We've been disconnected from this endpoint. No more data can be
            // sent or received.
            vm.endPointId = null
            binding.showProgress = true
            binding.isLookoutFound = false
            vm.endPointId?.apply {
                Nearby.getConnectionsClient(this@MainActivity).disconnectFromEndpoint(this)
            }
            Nearby.getConnectionsClient(this@MainActivity).stopDiscovery()
            startNearbyDiscovery()
            Log.e("NEARBY", "connectionLifecycleCallback onDisconnected")
        }
    }
    
    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            Log.e("NEARBY", "payloadCallback onPayloadReceived")
            vm.handlePayload(payload, this@MainActivity::goToSuccess, this@MainActivity::goToFail)
        }
    
        override fun onPayloadTransferUpdate(payloadId: String, update: PayloadTransferUpdate) {
            val staus: String
            when(update.status) {
                PayloadTransferUpdate.Status.CANCELED -> {
                    staus = "CANCELED"
                    binding.showProgress = false
                }
                PayloadTransferUpdate.Status.FAILURE -> {
                    staus = "FAILURE"
                    binding.showProgress = false
                }
                PayloadTransferUpdate.Status.IN_PROGRESS -> staus = "IN_PROGRESS"
                PayloadTransferUpdate.Status.SUCCESS -> staus = "SUCCESS"
                else -> staus = "not defined"
            }
            Log.e("NEARBY", "payloadCallback onPayloadTransferUpdate status::$staus")
        }
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        wifiManager.isWifiEnabled = true
        bluetoothManager.adapter.enable()
        
        binding.view = this
        if (vm.endPointId != null) {
            binding.showProgress = false
            binding.isLookoutFound = true
        } else {
            startNearbyDiscovery()
        }
    }
    
    override fun onResume() {
        super.onResume()
        binding.hasNotReturnedKey = vm.hasNotReturnedKey
    }
    
   /* override fun onDestroy() {
        vm.endPointId?.apply {
            Nearby.getConnectionsClient(this@MainActivity).disconnectFromEndpoint(this)
        }
        Nearby.getConnectionsClient(this).stopDiscovery()
        vm.endPointId = null
        super.onDestroy()
    }*/
    
    fun getKeyBySchedule() {
        binding.showProgress = true
        startGetKey = System.currentTimeMillis()
        Nearby.getConnectionsClient(this)
            .sendPayload(
                vm.endPointId!!,
                vm.getPayloadForSchedule(Date().formatToRequest(), ACTION.GET_KEY_BY_SCHEDULE)
            )
    }
    
    fun returnKey() {
        binding.showProgress = true
        startReturnKey = System.currentTimeMillis()
        Nearby.getConnectionsClient(this)
            .sendPayload(
                vm.endPointId!!,
                vm.getPayload(vm.savedRoomNumber!!, ACTION.RETURN_KEY)
            )
    }
    
    private fun startNearbyDiscovery() {
        Log.e("NEARBY", "startNearbyDiscovery")
        startDiscoveryMills = System.currentTimeMillis()
//        Nearby.getConnectionsClient(this).stopDiscovery()
        binding.showProgress = true
        binding.isLookoutFound = false
        Nearby.getConnectionsClient(this)
            .startDiscovery(
                "com.jollypanda.doorsthingsappID1",
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder()
                    .setStrategy(Strategy.P2P_CLUSTER)
                    .build()
            )
            .addOnSuccessListener {
                // we are discovering now
                successStartDiscovery = System.currentTimeMillis()
                Log.e("NEARBY", "startNearbyDiscovery SUCCESS. TIME = ${(successStartDiscovery - startDiscoveryMills) / 1000}")
            }
            .addOnFailureListener {
                Log.e("NEARBY", "startNearbyDiscovery FAIL")
                it.printStackTrace()
            }
    }
    
    private fun connectToEndPoint(endpointId: String) {
        Nearby.getConnectionsClient(this@MainActivity)
            .requestConnection(
                "com.jollypanda.petrsudoors",
                endpointId,
                connectionLifecycleCallback
            )
            .addOnSuccessListener {
                // We successfully requested a connection. Now both sides
                // must accept before the connection is established.
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
    
    private fun goToSuccess(keyResponse: KeyResponse) {
        when(keyResponse.action) {
            ACTION.GET_KEY_BY_SCHEDULE -> {
                endGetKey = System.currentTimeMillis()
                Log.e("NEARBY", "getKey TIME = ${(endGetKey - startGetKey)} ms")
            }
            ACTION.RETURN_KEY -> {
                endReturnKey = System.currentTimeMillis()
                Log.e("NEARBY", "returnKey TIME = ${(endReturnKey - startReturnKey)} ms")
            }
            else -> {}
        }
        binding.showProgress = false
        startActivity(SuccessActivity.getStartIntent(this, keyResponse))
    }
    
    private fun goToFail(keyResponse: KeyResponse) {
        when(keyResponse.action) {
            ACTION.GET_KEY_BY_SCHEDULE -> {
                endGetKey = System.currentTimeMillis()
                Log.e("NEARBY", "getKey TIME = ${(endGetKey - startGetKey)} ms")
            }
            ACTION.RETURN_KEY -> {
                endReturnKey = System.currentTimeMillis()
                Log.e("NEARBY", "returnKey TIME = ${(endReturnKey - startReturnKey)} ms")
            }
            else -> {}
        }
        binding.showProgress = false
        startActivity(FailActivity.getStartIntent(this, keyResponse.failReason!!))
    }
    
    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}