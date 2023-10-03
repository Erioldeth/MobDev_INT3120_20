package com.homework.mobile.component

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
fun ImpIntentDemo() {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceEvenly
	) {
		val context = LocalContext.current

//		val launcher =
//			rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {}

		IntentButton(
			onClick = {
				Intent()
					.apply {
						action = Intent.ACTION_WEB_SEARCH
//						putExtra(SearchManager.QUERY, "Jetpack Compose")
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
						data = Uri.parse("tel:0375870007")
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
					.apply { action = Settings.ACTION_SETTINGS }
					.let { context.startActivity(it) }
			},
			text = "Settings",
			iconImage = Icons.Rounded.Settings
		)
	}
}