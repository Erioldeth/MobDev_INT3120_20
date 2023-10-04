package com.homework.mobile.component

import android.app.NotificationManager
import android.app.Service
import android.content.ContentResolver.NOTIFY_DELETE
import android.content.ContentResolver.NOTIFY_INSERT
import android.content.ContentResolver.NOTIFY_UPDATE
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.homework.mobile.R

class NotifierService : Service() {
	private val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
		override fun onChange(selfChange: Boolean, uri: Uri?, flags: Int) {
			super.onChange(selfChange, uri)

			val notification = NotificationCompat
				.Builder(
					this@NotifierService,
					"database_notifier"
				)
				.setSmallIcon(R.drawable.ic_launcher_foreground)
				.setContentTitle("Database Notification")
				.setContentText(
					when (flags) {
						NOTIFY_INSERT -> "A new user has been added!"
						NOTIFY_UPDATE -> "A user has been modified!"
						NOTIFY_DELETE -> "A user has been deleted!"
						else          -> ""
					}
				)
				.build()

			val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.notify(
				System
					.currentTimeMillis()
					.toInt(),
				notification
			)
		}
	}

	override fun onBind(intent: Intent?): IBinder? = null

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		val notification = NotificationCompat
			.Builder(
				this,
				"database_notifier"
			)
			.setSmallIcon(R.drawable.ic_launcher_foreground)
			.setContentTitle("Content Provider")
			.setContentText("Observing changes...")
			.build()

		startForeground(1, notification)

		return START_STICKY
	}

	override fun onCreate() {
		super.onCreate()

		getSharedPreferences("Provider", MODE_PRIVATE)
			.edit()
			.putBoolean("isServiceStarted", true)
			.apply()

		contentResolver.registerContentObserver(UserProvider.CONTENT_URI, true, observer)

		Toast
			.makeText(baseContext, "Starting notifier...", Toast.LENGTH_SHORT)
			.show()
	}

	override fun onDestroy() {
		super.onDestroy()

		getSharedPreferences("Provider", MODE_PRIVATE)
			.edit()
			.putBoolean("isServiceStarted", false)
			.apply()

		contentResolver.unregisterContentObserver(observer)

		Toast
			.makeText(baseContext, "Stopping notifier...", Toast.LENGTH_SHORT)
			.show()
	}
}