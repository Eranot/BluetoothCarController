package com.example.minsulin.carcontroller

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by minsulin on 21/09/17.
 */
class PairedDevicesListAdapter(val context : Context, val devices: MutableList<BluetoothDevice>) : RecyclerView.Adapter<DeviceHolder>() {


    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.holder_device, parent, false)

        return DeviceHolder(itemView)
    }

    override fun onBindViewHolder(holder: DeviceHolder?, position: Int) {
        val device = devices.get(position)
        holder!!.deviceName.text = "${device.name} : ${device.address}"

        holder.itemView.setOnClickListener {
            if(BluetoothHelper.connectTo(device.address)){
                (context as Activity).finish()
            } else {
                Log.e("Error", "ERRO AO CONECTAR")
            }


        }
    }

}