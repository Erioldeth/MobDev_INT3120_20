package com.homework.mobile.component

import android.content.ContentUris
import android.content.ContentValues
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.FileNotFoundException

/*
Starting from Android 11, apps targeting Android 11 (API level 30) and higher
are given scoped access into external storage, or scoped storage, by default.
This means that an app only has access to its own app-specific directory on external storage,
and does not have access to other app-specific directories or shared directories
* */
@Composable
fun ExternalStorageDemo() {
	val resolver = LocalContext.current.contentResolver
	val uri = resolver
		.query(
			MediaStore.Files.getContentUri("external"),
			arrayOf(MediaStore.Files.FileColumns._ID),
			"${MediaStore.Files.FileColumns.DISPLAY_NAME}=? " +
				"AND ${MediaStore.Files.FileColumns.MIME_TYPE}=?",
			arrayOf("ExtText.txt", "text/plain"),
			null
		)
		?.use {
			if (it.moveToFirst()) {
				ContentUris.withAppendedId(
					MediaStore.Files.getContentUri("external"),
					it.getLong(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
				)
			}
			else null
		}
		?: resolver.insert(
			MediaStore.Files.getContentUri("external"),
			ContentValues().apply {
				put(MediaStore.Files.FileColumns.DISPLAY_NAME, "ExtText.txt")
				put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain")
				put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
			}
		)
	var text by remember { mutableStateOf("<-!Unknown!->") }

	DemoTemplate(
		text = text,
		onTextChange = { text = it },
		read = {
			try {
				text = resolver
					.openInputStream(uri!!)
					?.use { stream ->
						stream
							.bufferedReader()
							.use { it.readText() }
					}
					?: "Null!"
			}
			catch (_: FileNotFoundException) {
				Log.e("External Storage", "File Not Found!")
			}
		},
		write = {
			resolver
				.openOutputStream(uri!!)
				.use { it?.write(text.toByteArray()) }
		},
		delete = {
			uri?.let { resolver.delete(it, null, null) }
		}
	)
}