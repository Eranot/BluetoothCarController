package com.example.minsulin.carcontroller.Helper

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/**
 * Created by minsulin on 21/09/17.
 */
object BluetoothHelper {

    private val adapter = BluetoothAdapter.getDefaultAdapter()
    private var devices = adapter.bondedDevices
    private var input : InputStream? = null
    private var output : OutputStream? = null
    private var bluetoothIsEnabled = false

    fun enableBluetooth(){
        if(!bluetoothIsEnabled) {
            adapter.enable()
        }
    }

    fun getDevicesList() : MutableList<BluetoothDevice> {
        enableBluetooth()
        val list = mutableListOf<BluetoothDevice>()
        list.addAll(devices)
        return list
    }

    fun connectTo(address: String) : Boolean{
        val device = adapter.getRemoteDevice(address)
        val socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID())

        try{
            socket.connect()
            input = socket.inputStream
            output = socket.outputStream
        } catch (ex : IOException){
            Log.d("erro", "Não foi possível conectar")
        }



        return socket.isConnected
    }

    fun write(message: String){
        output!!.write(message.toByteArray())
    }

}