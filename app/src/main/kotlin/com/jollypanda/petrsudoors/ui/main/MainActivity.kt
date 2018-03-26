package com.jollypanda.petrsudoors.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.UnderlineSpan
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.jollypanda.petrsudoors.R
import com.jollypanda.petrsudoors.databinding.ActivityMainBinding
import com.jollypanda.petrsudoors.ui.common.BaseActivity
import com.jollypanda.petrsudoors.utils.extension.viewModel


/**
 * @author Yamushev Igor
 * @since  26.03.18
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {
    
    override val layout: Int = R.layout.activity_main
    
    private val vm by viewModel(MainViewModel::class)
    
    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, discoveredEndpointInfo: DiscoveredEndpointInfo) {
            // An endpoint was found!
            connectToEndPoint(endpointId, discoveredEndpointInfo)
        }
        
        override fun onEndpointLost(endpointId: String) {
            // A previously discovered endpoint has gone away.
        }
    }
    
    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            // Automatically accept the connection on both sides.
            Nearby.getConnectionsClient(this@MainActivity).acceptConnection(endpointId, payloadCallback)
        }
        
        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    binding.showProgress = false
                }
                
                ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                    Log.e("NEARBY", "REJECTED")
                }
                
                ConnectionsStatusCodes.STATUS_ERROR -> {
                    Log.e("NEARBY", "ERROR")
                }
            }
        }
        
        override fun onDisconnected(endpointId: String) {
            // We've been disconnected from this endpoint. No more data can be
            // sent or received.
        }
    }
    
    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
        
        }
    
        override fun onPayloadTransferUpdate(payloadId: String, update: PayloadTransferUpdate) { }
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.view = this
        binding.showProgress = true
        initObservers()
        initViews()
    }
    
    override fun onStop() {
        Nearby.getConnectionsClient(this).stopDiscovery()
        super.onStop()
    }
    
    private fun initObservers() {
    
    }
    
    private fun initViews() {
        val ssb = SpannableStringBuilder(binding.tvGetKeyBySchedule.text)
        ssb.setSpan(UnderlineSpan(), 0, ssb.length, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvGetKeyBySchedule.text = ssb
    }
    
    fun returnKey() {
    
    }
    
    fun getKeyByNum() {
    
    }
    
    fun getKeyBySchedule() {
    
    }
    
    fun startNearbyDiscovery() {
        Nearby.getConnectionsClient(this)
            .startDiscovery(
                "com.jollypanda.petrsudoors",
                endpointDiscoveryCallback,
                DiscoveryOptions.Builder()
                    .setStrategy(Strategy.P2P_POINT_TO_POINT)
                    .build()
            )
            .addOnSuccessListener {
                // we are discovering now
            }
            .addOnFailureListener {
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
    
    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}