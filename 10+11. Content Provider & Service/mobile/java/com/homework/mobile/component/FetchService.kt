package com.homework.mobile.component

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.compose.runtime.snapshots.SnapshotStateList

class FetchService : Service() {
	fun fetchData(dataList: SnapshotStateList<UserData>) {
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

	private val binder = LocalBinder()

	inner class LocalBinder : Binder() {
		fun getService() = this@FetchService
	}

	override fun onBind(intent: Intent): IBinder = binder
}