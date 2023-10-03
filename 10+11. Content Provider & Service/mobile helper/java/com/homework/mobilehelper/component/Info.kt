package com.homework.mobilehelper.component

import android.net.Uri

class Info {
	companion object {
		private const val AUTHORITY = "demo.info.provider"
		private const val TABLE_NAME = "user"

		val CONTENT_URI = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
	}

	object User {
		const val ID = "id"
		const val NAME = "name"
		const val PHONE = "phone"
		const val EMAIL = "email"
	}
}