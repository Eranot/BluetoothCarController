package com.example.minsulin.carcontroller.Activity

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.minsulin.carcontroller.Helper.BluetoothHelper
import com.example.minsulin.carcontroller.R
import kotlinx.android.synthetic.main.activity_control.*
import kotlinx.android.synthetic.main.control_buttons_group.view.*


/**
 * Created by minsulin on 21/09/17.
 */
class ControlActivity : AppCompatActivity() {

    private var touchedButtons = ArrayList<View>()
    private var buttons = mutableMapOf<View, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        setupButton(btnUp, "W")
        setupButton(btnDown, "S")
        setupButton(btnRight, "D")
        setupButton(btnLeft, "A")

        Log.d("or", this.resources.configuration.orientation.toString())

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){

            setupButton(groupArt1.findViewById(R.id.btnGroupUp), "R")
            setupButton(groupArt1.findViewById(R.id.btnGroupDown), "F")

            setupButton(groupArt2.btnGroupUp, "T")
            setupButton(groupArt2.findViewById(R.id.btnGroupDown), "G")

            setupButton(groupGarra.findViewById(R.id.btnGroupUp), "Y")
            setupButton(groupGarra.findViewById(R.id.btnGroupDown), "H")

            setupButton(groupHorizontal.findViewById(R.id.btnGroupUp), "U")
            setupButton(groupHorizontal.findViewById(R.id.btnGroupDown), "J")

            //Log.d("diff", groupArt1.btnGroupUp.toString() + " - " + groupArt2.btnGroupUp.toString())
        }


        setupButton(root, "")

        sendMessage()

    }

    fun checkIfClickingOnButton(event : MotionEvent) : View?{
        val eventX = event.x.toInt()
        val eventY = event.y.toInt()
        for (pair in buttons) {

            val bt = pair.key
            var parentLeft = (bt.parent as ViewGroup).left
            var parentTop = (bt.parent as ViewGroup).top

            var parent = bt.parent
            while(parent.parent != root){
                parentLeft += (parent as ViewGroup).left
                parentTop += (parent as ViewGroup).top
                parent = parent.parent
            }

            val rect = Rect(bt.left + parentLeft, bt.top + parentTop,
                    bt.right + parentLeft, bt.bottom + parentTop)

            if(pair.key == root){
                continue
            }

            if (rect.contains(eventX, eventY)) {
                //Log.d("mandou", pair.value)
                return pair.key
            }
        }
        return null
    }

    fun setupButton(view2: View, message: String){
        if(view2 != root){
            buttons[view2] = message
        }

        var touch : View? = null
        view2.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action){

                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE ->{
                    var parentLeft = (view.parent as ViewGroup).left
                    var parentTop = (view.parent as ViewGroup).top

                    if(view == root){
                        parentLeft = 0
                        parentTop = 0
                    } else {

                        var parent = view.parent
                        while (parent.parent != root) {
                            parentLeft += (parent as ViewGroup).left
                            parentTop += (parent as ViewGroup).top
                            parent = parent.parent
                        }
                    }



                    motionEvent.setLocation(motionEvent.x + view.left+ parentLeft, motionEvent.y + view.top + parentTop) //Essa linha é necessária porque o X e Y dos evento é relativo à própria View

                    val touch2 = checkIfClickingOnButton(motionEvent)

                    if(touch2 != touch){
                        //Log.d("asd", "removeu o antigo" + touch.toString())
                        touchedButtons.remove(touch)
                    }

                    touch = touch2

                    touch?.let {
                        if(!touchedButtons.contains(it)){
                            touchedButtons.add(it)
                        }

                        if(it != view2){
                            touchedButtons.remove(view2)
                        }
                    }

                }

                else -> {
                    touchedButtons.remove(touch)
                }
            }

            true
        }
    }

    fun sendMessage(){
        Handler().postDelayed({
            for(v in touchedButtons){
                Log.d("mandou", buttons[v])
                BluetoothHelper.write(buttons[v].toString())
            }

            sendMessage()
        }, 100)
    }
}
