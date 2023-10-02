package com.homework.mobile

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.homework.mobile.component.UserList
import com.homework.mobile.ui.theme.MobileTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { MainContent() }

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			ActivityCompat.requestPermissions(
				this,
				arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
				0
			)
		}

		val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
		notificationManager.createNotificationChannel(
			NotificationChannel(
				"database_notifier",
				"Database Notification",
				NotificationManager.IMPORTANCE_DEFAULT
			)
		)
	}
}

@Preview(showBackground = true)
@Composable
fun MainContent() {
	MobileTheme {
		UserList()
	}
}