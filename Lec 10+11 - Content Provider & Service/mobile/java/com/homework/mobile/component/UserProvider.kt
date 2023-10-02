package com.homework.mobile.component

import android.content.ContentProvider
import android.content.ContentResolver.NOTIFY_DELETE
import android.content.ContentResolver.NOTIFY_INSERT
import android.content.ContentResolver.NOTIFY_UPDATE
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.net.Uri

class UserProvider : ContentProvider() {
	companion object {
		private const val AUTHORITY = "demo.info.provider"
		private const val TABLE_CODE = 1
		private const val ITEM_CODE = 2

		val CONTENT_URI = Uri.parse("content://$AUTHORITY/${InfoDBHelper.TABLE_NAME}")
		val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
			addURI(AUTHORITY, "user", TABLE_CODE)
			addURI(AUTHORITY, "user/#", ITEM_CODE)
		}
	}

	private lateinit var dbHelper: InfoDBHelper

	override fun onCreate(): Boolean {
		dbHelper = InfoDBHelper(context!!)
		return true
	}

	override fun insert(uri: Uri, values: ContentValues?): Uri {
		when (uriMatcher.match(uri)) {
			TABLE_CODE -> {
				val dbWriter = dbHelper.writableDatabase
				val id = dbWriter.insert(InfoDBHelper.TABLE_NAME, null, values)
				if (id == -1L) throw SQLException("Failed to insert a row into $uri")
				context!!.contentResolver.notifyChange(uri, null, NOTIFY_INSERT)
				return ContentUris.withAppendedId(CONTENT_URI, id)
			}

			else       -> throw IllegalArgumentException("Insert is not supported for $uri")
		}
	}

	override fun query(
		uri: Uri,
		projection: Array<out String>?,
		selection: String?,
		selectionArgs: Array<out String>?,
		sortOrder: String?
	): Cursor? {
		val dbReader = dbHelper.readableDatabase

		return when (uriMatcher.match(uri)) {
			TABLE_CODE -> dbReader.query(
				InfoDBHelper.TABLE_NAME, projection,
				selection, selectionArgs,
				null, null, sortOrder
			)

			ITEM_CODE  -> dbReader.query(
				InfoDBHelper.TABLE_NAME, projection,
				"${InfoDBHelper.User.ID}=?", arrayOf(uri.lastPathSegment),
				null, null, sortOrder
			)

			else       -> throw IllegalArgumentException("Query is not supported for $uri")
		}.apply { setNotificationUri(context!!.contentResolver, uri) }
	}

	override fun update(
		uri: Uri,
		values: ContentValues?,
		selection: String?,
		selectionArgs: Array<out String>?
	): Int {
		val dbWriter = dbHelper.writableDatabase

		return when (uriMatcher.match(uri)) {
			TABLE_CODE -> dbWriter.update(
				InfoDBHelper.TABLE_NAME, values,
				selection, selectionArgs
			)

			ITEM_CODE  -> dbWriter.update(
				InfoDBHelper.TABLE_NAME, values,
				"${InfoDBHelper.User.ID}=?", arrayOf(uri.lastPathSegment)
			)

			else       -> throw IllegalArgumentException("Update is not supported for $uri")
		}.also { context!!.contentResolver.notifyChange(uri, null, NOTIFY_UPDATE) }
	}

	override fun delete(
		uri: Uri,
		selection: String?,
		selectionArgs: Array<out String>?
	): Int {
		val dbWriter = dbHelper.writableDatabase

		return when (uriMatcher.match(uri)) {
			TABLE_CODE -> dbWriter.delete(
				InfoDBHelper.TABLE_NAME,
				selection, selectionArgs
			)

			ITEM_CODE  -> dbWriter.delete(
				InfoDBHelper.TABLE_NAME,
				"${InfoDBHelper.User.ID}=?", arrayOf(uri.lastPathSegment)
			)

			else       -> throw IllegalArgumentException("Delete is not supported for $uri")
		}.also { context!!.contentResolver.notifyChange(uri, null, NOTIFY_DELETE) }
	}

	override fun getType(uri: Uri): String {
		return when (uriMatcher.match(uri)) {
			TABLE_CODE -> "vnd.android.cursor.dir/user"
			ITEM_CODE  -> "vnd.android.cursor.item/user"
			else       -> throw IllegalArgumentException("Unsupported uri: $uri")
		}
	}
}
