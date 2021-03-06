package com.example.minsulin.carcontroller.Adapter

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.minsulin.carcontroller.Holder.DeviceHolder
import com.example.minsulin.carcontroller.Helper.BluetoothHelper
import com.example.minsulin.carcontroller.R

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
                val toast = Toast.makeText(context, "ERRO AO CONECTAR", Toast.LENGTH_LONG)
                toast.show()
            }


        }
    }

}