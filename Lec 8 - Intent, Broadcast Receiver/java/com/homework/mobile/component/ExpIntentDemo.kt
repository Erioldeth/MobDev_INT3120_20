package com.homework.mobile.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.homework.mobile.ClockActivity
import com.homework.mobile.broadcastReceiver.ExpReceiver

@Composable
fun ExpIntentDemo() {
	val context = LocalContext.current
	val time = context.findActivity()?.intent?.extras?.getString("Time") ?: "None"

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		Row {
			IntentButton(
				onClick = {
					Intent(context, ClockActivity::class.java)
						.let { context.startActivity(it) }
				},
				text = "Activity"
			)
			Spacer(modifier = Modifier.width(16.dp))
			Button(
				onClick = {
					Toast.makeText(context, time, Toast.LENGTH_SHORT).show()
				}
			) {
				Icon(
					imageVector = Icons.Rounded.Info,
					contentDescription = null
				)
			}
		}

		IntentButton(
			onClick = {
				Intent(context, ExpReceiver::class.java)
					.apply { putExtra("message", "Explicit receiver called!") }
					.let { context.sendBroadcast(it) }
			},
			text = "Broadcast",
			iconImage = Icons.Rounded.Share
		)
	}
}

fun Context.findActivity(): Activity? = when (this) {
	is Activity -> this
	is ContextWrapper -> baseContext.findActivity()
	else -> null
}