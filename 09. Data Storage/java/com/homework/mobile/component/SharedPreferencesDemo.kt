package com.homework.mobile.component

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun SharedPreferencesDemo() {
	val sharedPref = LocalContext.current.getSharedPreferences("TEXT", Context.MODE_PRIVATE)
	var text by remember { mutableStateOf(sharedPref.getString("text", "<-!Unknown!->")) }

	DemoTemplate(
		text = text!!,
		onTextChange = { text = it },
		read = {},
		write = {
			sharedPref
				.edit()
				.apply {
					putString("text", text)
					apply()
				}
		},
		delete = {
			sharedPref
				.edit()
				.apply {
					remove("text")
					apply()
				}
		}
	)
}
