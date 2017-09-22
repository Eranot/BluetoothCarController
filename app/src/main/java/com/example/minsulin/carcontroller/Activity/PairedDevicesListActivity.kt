package com.example.minsulin.carcontroller.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.minsulin.carcontroller.Helper.BluetoothHelper
import com.example.minsulin.carcontroller.Adapter.PairedDevicesListAdapter
import com.example.minsulin.carcontroller.R
import kotlinx.android.synthetic.main.activity_paired_devices_list.*

/**
 * Created by minsulin on 21/09/17.
 */
class PairedDevicesListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_paired_devices_list)

        val devicesList = BluetoothHelper.getDevicesList()

        Log.d("tag", devicesList.size.toString())

        recyclerView.adapter = PairedDevicesListAdapter(this, devicesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}