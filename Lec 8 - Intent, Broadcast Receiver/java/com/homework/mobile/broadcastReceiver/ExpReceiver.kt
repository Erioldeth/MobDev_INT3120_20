package com.homework.mobile.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ExpReceiver : BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		Log.i("Explicit Broadcast", intent.extras?.getString("message") ?: "None")
	}
}