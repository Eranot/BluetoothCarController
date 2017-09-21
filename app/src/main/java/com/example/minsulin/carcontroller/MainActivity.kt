package com.example.minsulin.carcontroller

import android.content.Intent
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView




class MainActivity : AppCompatActivity() {

    var touchedButton : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnConnect.setOnClickListener {
            val intent = Intent(this, PairedDevicesListActivity::class.java)
            startActivity(intent)
        }

        setupButton(btnUp, "W")
        setupButton(btnDown, "S")
        setupButton(btnRight, "D")
        setupButton(btnLeft, "A")

        sendMessage()
    }

    var buttons = mutableMapOf<ImageView, String>()

    fun checkIfClickingOnButton(event : MotionEvent) : Pair<ImageView, String>?{
        val eventX = event.x.toInt()
        val eventY = event.y.toInt()
        for (pair in buttons) {
            val bt = pair.key
            val parentLeft = (bt.getParent() as ViewGroup).left
            val parentTop = (bt.getParent() as ViewGroup).top
            val rect = Rect(bt.getLeft() + parentLeft, bt.getTop() + parentTop,
                    bt.getRight() + parentLeft, bt.getBottom() + parentTop)


            if (rect.contains(eventX, eventY)) {
                val p = Pair(pair.key, pair.value)
                return p
            }
        }
        return null
    }

    fun setupButton(imageView: ImageView, message: String){

        buttons[imageView] = message

        imageView.setOnTouchListener { view, motionEvent ->

            when(motionEvent.action){

                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE ->{

                    val parentLeft = (imageView.getParent() as ViewGroup).left
                    val parentTop = (imageView.getParent() as ViewGroup).top

                    val e = motionEvent
                    e.setLocation(e.getX() + imageView.getLeft() + parentLeft, e.getY() + imageView.getTop() + parentTop) //Essa linha é necessária porque o X e Y dos evento é relativo à própria View

                    val p = checkIfClickingOnButton(e)
                    p?.let{
                        touchedButton = p.first
                    }
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
        Handler().postDelayed(Runnable {
            touchedButton?.let{
                Log.d("mandou", "mandou" + buttons[touchedButton!!])

            }
            sendMessage()
        }, 100)
    }
}
