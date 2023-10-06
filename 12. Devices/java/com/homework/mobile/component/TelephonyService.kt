package com.homework.mobile.component

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Telephony
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelephonyService() {
	val context = LocalContext.current

	var sender by remember { mutableStateOf<String?>("") }
	var message by remember { mutableStateOf("") }

	val receiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context, intent: Intent) {
			Telephony.Sms.Intents
				.getMessagesFromIntent(intent)
				.forEach {
					sender = it.originatingAddress
					message = it.messageBody
				}
		}
	}

	DisposableEffect(Unit) {
		context.registerReceiver(receiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))

		onDispose {
			context.unregisterReceiver(receiver)
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		TextField(
			value = sender ?: "Unknown",
			onValueChange = {},
			enabled = false
		)

		Spacer(modifier = Modifier.height(24.dp))
		
		TextField(
			value = message,
			onValueChange = {},
			enabled = false
		)
	}
}