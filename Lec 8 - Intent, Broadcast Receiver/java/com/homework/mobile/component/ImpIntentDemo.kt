package com.homework.mobile.component

import android.app.SearchManager
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.homework.mobile.broadcastReceiver.ImpReceiver

@Composable
fun ImpIntentDemo() {
	val context = LocalContext.current

	DisposableEffect(Unit) {
		val receiver = ImpReceiver()

		context.registerReceiver(
			receiver,
			IntentFilter("example.ImpBroadcast"),
			RECEIVER_EXPORTED
		)

		Log.i("Receiver", "Implicit receiver registered")

		onDispose {
			context.unregisterReceiver(receiver)
			Log.i("Receiver", "Implicit receiver disposed")
		}
	}

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		IntentButton(
			onClick = {
				Intent()
					.apply {
						action = Intent.ACTION_WEB_SEARCH
						putExtra(SearchManager.QUERY, "Jetpack Compose")
					}
					.let { context.startActivity(it) }
			},
			text = "Search",
			iconImage = Icons.Rounded.Search
		)

		IntentButton(
			onClick = {
				Intent()
					.apply {
						action = Intent.ACTION_SENDTO
						data = Uri.parse("mailto:")
						putExtra(Intent.EXTRA_EMAIL, arrayOf("example@domain.com"))
						putExtra(Intent.EXTRA_SUBJECT, "Email subject")
						putExtra(Intent.EXTRA_TEXT, "Email body")
					}
					.let { context.startActivity(it) }
			},
			text = "Email",
			iconImage = Icons.Rounded.Email
		)

		IntentButton(
			onClick = {
				Intent()
					.apply {
						action = Intent.ACTION_DIAL
						data = Uri.parse("tel:911")
					}
					.let { context.startActivity(it) }
			},
			text = "Dial",
			iconImage = Icons.Rounded.Phone
		)

		IntentButton(
			onClick = {
				Intent()
					.apply {
						action = Intent.ACTION_VIEW
						data = Uri.parse("geo:0,0?q=Eiffel+Tower")
					}
					.let { context.startActivity(it) }
			},
			text = "Map",
			iconImage = Icons.Rounded.LocationOn
		)

		IntentButton(
			onClick = {
				Intent()
					.apply {
						action = "example.ImpBroadcast"
						putExtra("message", "Implicit receiver called!")
					}
					.let { context.sendOrderedBroadcast(it, null) }
			},
			text = "Broadcast",
			iconImage = Icons.Rounded.Share
		)
	}
}