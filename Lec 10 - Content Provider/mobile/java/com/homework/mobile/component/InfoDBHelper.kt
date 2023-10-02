package com.homework.mobile.component

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class InfoDBHelper(context: Context) :
	SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
	companion object {
		private const val DB_NAME = "InfoDB"
		private const val DB_VERSION = 1
		const val TABLE_NAME = "user"
	}

	object User {
		const val ID = "id"
		const val NAME = "name"
		const val PHONE = "phone"
		const val EMAIL = "email"
	}

	override fun onCreate(db: SQLiteDatabase) {
		db.execSQL(
			"""
			CREATE TABLE $TABLE_NAME (
				${User.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
				${User.NAME} TEXT NOT NULL UNIQUE,
				${User.PHONE} TEXT NOT NULL,  
				${User.EMAIL} TEXT
			)
			""".trimIndent()
		)
	}

	override fun onUpgrade(
		db: SQLiteDatabase,
		oldVersion: Int,
		newVersion: Int
	) {
		db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
		onCreate(db)
	}
}