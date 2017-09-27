package com.example.minsulin.carcontroller.Helper

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/**
 * Created by minsulin on 21/09/17.
 */
object BluetoothHelper {

    val adapter = BluetoothAdapter.getDefaultAdapter()
    private var socket : BluetoothSocket? = null
    private var device : BluetoothDevice? = null
    private var devices = adapter.bondedDevices
    private var input : InputStream? = null
    private var output : OutputStream? = null

    fun enableBluetooth(){
        if(!adapter.isEnabled) {
            adapter.enable()
        }
    }

    fun getIsConnected() : Boolean {
        return socket?.isConnected ?: false
    }

    fun getDevicesList() : MutableList<BluetoothDevice> {
        enableBluetooth()
        val list = mutableListOf<BluetoothDevice>()
        list.addAll(devices)
        return list
    }

    fun connectTo(address: String) : Boolean{
        device = adapter.getRemoteDevice(address)
        socket = device!!.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))

        try{
            socket!!.connect()
            input = socket!!.inputStream
            output = socket!!.outputStream
            output!!.write("teste".toByteArray())
        } catch (ex : IOException){
            Log.d("erro", "Não foi possível conectar")
        }

        return socket!!.isConnected
    }

    fun write(message: String) {
        socket?.let {
            if (it.isConnected){
                output!!.write(message.toByteArray())
            }
        }

    }

    fun disconnect(){

        socket?.let{
            it.close()
        }
    }

}