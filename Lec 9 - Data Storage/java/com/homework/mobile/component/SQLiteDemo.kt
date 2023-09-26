package com.homework.mobile.component

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun SQLiteDemo() {
	val dbHelper = DbHelper(LocalContext.current)
	val rdb = dbHelper.readableDatabase
	val wdb = dbHelper.writableDatabase

	var id by remember { mutableStateOf(0L) }
	val name = "text"
	var text by remember { mutableStateOf("<-!Unknown!->") }

	DemoTemplate(
		text = text,
		onTextChange = { text = it },
		read = {
			rdb
				.query(
					"texts",
					arrayOf("id", "content"),
					"name=?",
					arrayOf(name),
					null,
					null,
					null
				)
				.use {
					with(it) {
						while (moveToNext()) {
							id = getLong(getColumnIndexOrThrow("id"))
							text = getString(getColumnIndexOrThrow("content"))
						}
					}
				}
		},
		write = {
			ContentValues()
				.apply {
					if (id == 0L) put("name", name)
					put("content", text)
				}
				.let {
					if (id == 0L) id = wdb.insert("texts", null, it)
					else wdb.update("texts", it, "id=?", arrayOf("$id"))
				}
		},
		delete = {
			wdb.delete(
				"texts",
				"id=?",
				arrayOf("$id")
			)
			id = 0
		}
	)
}

class DbHelper(context: Context) :
	SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	companion object {
		private const val DATABASE_NAME = "TextStore.db"
		private const val DATABASE_VERSION = 1
	}

	override fun onCreate(db: SQLiteDatabase) {
		db.execSQL(
			"""
			CREATE TABLE texts (
				id INTEGER PRIMARY KEY AUTOINCREMENT,
				name TEXT,
				content TEXT
			)
			""".trimIndent()
		)
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.execSQL("DROP TABLE IF EXISTS texts")
		onCreate(db)
	}
}