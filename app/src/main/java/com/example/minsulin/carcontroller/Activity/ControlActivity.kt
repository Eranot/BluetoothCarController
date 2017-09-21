package com.example.minsulin.carcontroller.Activity

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.minsulin.carcontroller.R
import kotlinx.android.synthetic.main.activity_control.*


/**
 * Created by minsulin on 21/09/17.
 */
class ControlActivity : AppCompatActivity() {

    var touchedButton : View? = null
    var buttons = mutableMapOf<View, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)

        setupButton(btnUp, "W")
        setupButton(btnDown, "S")
        setupButton(btnRight, "D")
        setupButton(btnLeft, "A")

        setupButton(root, "")

        sendMessage()
    }


    fun checkIfClickingOnButton(event : MotionEvent) : View?{
        val eventX = event.x.toInt()
        val eventY = event.y.toInt()
        for (pair in buttons) {

            val bt = pair.key
            val parentLeft = (bt.getParent() as ViewGroup).left
            val parentTop = (bt.getParent() as ViewGroup).top
            val rect = Rect(bt.getLeft() + parentLeft, bt.getTop() + parentTop,
                    bt.getRight() + parentLeft, bt.getBottom() + parentTop)

            if(pair.key == root){
                continue
            }

            if (rect.contains(eventX, eventY)) {
                return pair.key
            }
        }
        return null
    }

    fun setupButton(view: View, message: String){
        if(view != root){
            buttons[view] = message
        }


        view.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action){

                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE ->{

                    val parentLeft = (view.getParent() as ViewGroup).left
                    val parentTop = (view.getParent() as ViewGroup).top

                    val e = motionEvent
                    e.setLocation(e.getX() + view.getLeft() + parentLeft, e.getY() + view.getTop() + parentTop) //Essa linha é necessária porque o X e Y dos evento é relativo à própria View

                    touchedButton = checkIfClickingOnButton(e)

                }

                else -> {
                    //Log.d("else", "else")
                    touchedButton = null
                }
            }

            true
        }
    }

    fun sendMessage(){
        Handler().postDelayed({
            touchedButton?.let{
                if(it != root){
                    Log.d("mandou", buttons[touchedButton!!])
                }
            }
            sendMessage()
        }, 200)
    }
}
