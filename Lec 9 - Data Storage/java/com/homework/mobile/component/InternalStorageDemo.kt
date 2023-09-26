package com.homework.mobile.component

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.io.FileNotFoundException

@Composable
fun InternalStorageDemo() {
	val context = LocalContext.current
	var text by remember { mutableStateOf("<-!Unknown!->") }

	DemoTemplate(
		text = text,
		onTextChange = { text = it },
		read = {
			try {
				text = context
					.openFileInput("IntText.txt")
					.bufferedReader()
					.use { it.readText() }
			}
			catch (_: FileNotFoundException) {
				Log.e("Internal Storage", "File Not Found!")
			}
		},
		write = {
			context
				.openFileOutput("IntText.txt", Context.MODE_PRIVATE)
				.use { it.write(text.toByteArray()) }
		},
		delete = {
			context.deleteFile("IntText.txt")
		}
	)
}