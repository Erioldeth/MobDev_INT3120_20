package com.homework.mobile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class Broadcaster : BroadcastReceiver() {

	override fun onReceive(context: Context, intent: Intent) {
		val message = intent.extras?.getString("message") ?: "None"
		Toast.makeText(context, "Message sent: $message", Toast.LENGTH_SHORT).show()
	}
}