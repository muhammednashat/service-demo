package com.mnashat_dev.servicedemo

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView


class ForegroundServiceActivity : AppCompatActivity() {

    companion object{
        const val  ACTION_STOP = "STOP SERVICE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forground_service)
        findViewById<View>(R.id.btn_start)?.setOnClickListener {

             if (isMyServiceRunning(SampleForegroundService::class.java)){
                 toast(this,"Foreground Service is Running")
             }else{
                 startService(Intent(this, SampleForegroundService::class.java))
                 updateTextStatus()
             }

        }


        findViewById<View>(R.id.btn_stop)?.setOnClickListener {
            log("TAG","btn_stop")
            val intentStop = Intent(this, SampleForegroundService::class.java)
            intentStop.action = ACTION_STOP
            startService(intentStop)
            Handler().postDelayed({
                updateTextStatus()
            },100)
        }
        updateTextStatus()
    }

    private fun updateTextStatus() {
        if(isMyServiceRunning(SampleForegroundService::class.java)){
            findViewById<TextView>(R.id.txt_service_status)?.text = "Service is Running"
        }else{
            findViewById<TextView>(R.id.txt_service_status)?.text = "Service is NOT Running"
        }
    }


    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager =
                getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(
                Int.MAX_VALUE
            )) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }


}