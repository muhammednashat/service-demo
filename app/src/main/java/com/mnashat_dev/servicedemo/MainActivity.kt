package com.mnashat_dev.servicedemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn_foreground_service)?.setOnClickListener {
            startActivity(
                intentNewActivity(this, ForegroundServiceActivity())
            )
        }


    }
}