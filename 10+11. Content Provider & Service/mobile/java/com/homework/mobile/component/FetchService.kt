package com.homework.mobile.component

import android.app.Service
import android.content.Intent
import android.database.ContentObserver
import android.net.Uri
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.compose.runtime.snapshots.SnapshotStateList

class FetchService : Service() {
	private lateinit var observer: ContentObserver

	fun fetch(dataList: SnapshotStateList<UserData>) {
		dataList.clear()
		contentResolver
			.query(
				UserProvider.CONTENT_URI, null,
				null, null,
				null
			)
			?.use {
				while (it.moveToNext()) with(it) {
					dataList.add(
						UserData(
							id = getLong(getColumnIndexOrThrow(InfoDBHelper.User.ID)),
							name = getString(getColumnIndexOrThrow(InfoDBHelper.User.NAME)),
							phone = getString(getColumnIndexOrThrow(InfoDBHelper.User.PHONE)),
							email = getString(getColumnIndexOrThrow(InfoDBHelper.User.EMAIL)),
						)
					)
				}
			}
	}

	fun sync(dataList: SnapshotStateList<UserData>) {
		observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
			override fun onChange(selfChange: Boolean, uri: Uri?, flags: Int) {
				super.onChange(selfChange, uri)
				fetch(dataList)
			}
		}

		contentResolver.registerContentObserver(UserProvider.CONTENT_URI, true, observer)
	}

	override fun onDestroy() {
		super.onDestroy()
		contentResolver.unregisterContentObserver(observer)
	}

	private val binder = LocalBinder()

	inner class LocalBinder : Binder() {
		fun getService() = this@FetchService
	}

	override fun onBind(intent: Intent): IBinder = binder
}