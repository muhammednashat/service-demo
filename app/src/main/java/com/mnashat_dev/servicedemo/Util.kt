package com.mnashat_dev.servicedemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


fun log(tag: String?, massage:String) = Log.e("TAG", massage)

fun toast(context:Context, massage: String) = Toast.makeText(context,massage,Toast.LENGTH_SHORT).show()

fun intentNewActivity(context:Context,activity: Activity) = Intent(context,activity::class.java)
