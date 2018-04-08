package com.jollypanda.petrsudoors.ui.main

import android.content.Context
import android.content.Intent
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
    
    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            Log.e("NEARBY", "endpointDiscoveryCallback onEndpointFound")
            // An endpoint was found!
            connectToEndPoint(endpointId, discoveredEndpointInfo)
        }
        
        override fun onEndpointLost(endpointId: String) {
            Log.e("NEARBY", "endpointDiscoveryCallback onEndpointLost")
            // A previously discovered endpoint has gone away.
        }
    }
    
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            Log.e("NEARBY", "connectionLifecycleCallback onConnectionInitiated")
            Nearby.getConnectionsClient(this@MainActivity).stopDiscovery()
            // Automatically accept the connection on both sides.
            Nearby.getConnectionsClient(this@MainActivity).acceptConnection(endpointId, payloadCallback)
        }
        
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    vm.endPointId = endpointId
                    Log.e("NEARBY", "connectionLifecycleCallback onConnectionResult OK")
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
        binding.view = this
        initObservers()
    }
    
    override fun onResume() {
        super.onResume()
        binding.hasNotReturnedKey = vm.hasNotReturnedKey
        if (vm.endPointId != null) {
            binding.showProgress = false
            binding.isLookoutFound = true
        } else {
            startNearbyDiscovery()
        }
    }
    
    override fun onDestroy() {
        vm.endPointId?.apply {
            Nearby.getConnectionsClient(this@MainActivity).disconnectFromEndpoint(this)
        }
        Nearby.getConnectionsClient(this).stopDiscovery()
        vm.endPointId = null
        super.onDestroy()
    }
    
    private fun initObservers() { }
    
    fun getKeyBySchedule() {
        binding.showProgress = true
        Nearby.getConnectionsClient(this)
            .sendPayload(
                vm.endPointId!!,
                vm.getPayloadForSchedule(Date().formatToRequest(), ACTION.GET_KEY_BY_SCHEDULE)
            )
    }
    
    fun returnKey() {
        binding.showProgress = true
        Nearby.getConnectionsClient(this)
            .sendPayload(
                vm.endPointId!!,
                vm.getPayload(vm.savedRoomNumber!!, ACTION.RETURN_KEY)
            )
    }
    
    fun startNearbyDiscovery() {
        binding.showProgress = true
        binding.isLookoutFound = false
        Nearby.getConnectionsClient(this)
            .startDiscovery(
                "com.jollypanda.doorsthingsappID1",
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder()
                    .setStrategy(Strategy.P2P_STAR)
                    .build()
            )
            .addOnSuccessListener {
                // we are discovering now
                Log.e("NEARBY", "startNearbyDiscovery SUCCESS")
            }
            .addOnFailureListener {
                Log.e("NEARBY", "startNearbyDiscovery FAIL")
                it.printStackTrace()
            }
    }
    
    private fun connectToEndPoint(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
        Nearby.getConnectionsClient(this@MainActivity)
            .requestConnection(
                "user",
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
        binding.showProgress = false
        startActivity(SuccessActivity.getStartIntent(this, keyResponse))
    }
    
    private fun goToFail(keyResponse: KeyResponse) {
        binding.showProgress = false
        startActivity(FailActivity.getStartIntent(this, keyResponse.failReason!!))
    }
    
    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}