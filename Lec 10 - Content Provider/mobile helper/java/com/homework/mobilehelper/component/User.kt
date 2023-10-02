package com.homework.mobilehelper.component

import android.content.ContentUris
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun User(
	data: UserData,
	onBack: () -> Unit
) {
	val resolver = LocalContext.current.contentResolver

	var name by remember { mutableStateOf(data.name) }
	var phone by remember { mutableStateOf(data.phone) }
	var email by remember { mutableStateOf(data.email) }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 24.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		TextField(
			value = name,
			onValueChange = { name = it },
			leadingIcon = {
				Icon(
					imageVector = Icons.Rounded.Person,
					contentDescription = null
				)
			},
			isError = name.isEmpty(),
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Done
			)
		)

		Spacer(modifier = Modifier.height(8.dp))

		TextField(
			value = phone,
			onValueChange = { phone = it },
			leadingIcon = {
				Icon(
					imageVector = Icons.Rounded.Phone,
					contentDescription = null
				)
			},
			isError = phone.isEmpty(),
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Phone,
				imeAction = ImeAction.Done
			)
		)

		Spacer(modifier = Modifier.height(8.dp))

		TextField(
			value = email,
			onValueChange = { email = it },
			leadingIcon = {
				Icon(
					imageVector = Icons.Rounded.Email,
					contentDescription = null
				)
			},
			keyboardOptions = KeyboardOptions(
				imeAction = ImeAction.Done
			)
		)

		Spacer(modifier = Modifier.height(24.dp))

		Button(
			onClick = {
				resolver.delete(
					ContentUris.withAppendedId(Info.CONTENT_URI, data.id),
					null, null
				)
				onBack()
			},
			enabled = (data.id > -1)
		) {
			Text(
				text = "Delete",
				fontSize = 20.sp
			)
		}

		Spacer(modifier = Modifier.height(8.dp))

		Button(
			onClick = {
				val contentValues = ContentValues().apply {
					put(Info.User.NAME, name)
					put(Info.User.PHONE, phone)
					put(Info.User.EMAIL, email)
				}
				try {
					if (data.id > -1)
						resolver.update(
							ContentUris.withAppendedId(Info.CONTENT_URI, data.id), contentValues,
							null, null
						)
					else
						resolver.insert(Info.CONTENT_URI, contentValues)
				}
				catch (e: Exception) {
					Log.e("Saving user", e.message.toString())
				}
				onBack()
			},
			enabled = (name.isNotEmpty() && phone.isNotEmpty())
		) {
			Text(
				text = "Save",
				fontSize = 20.sp
			)
		}

		Spacer(modifier = Modifier.height(8.dp))

		Button(onClick = onBack) {
			Text(
				text = "Back",
				fontSize = 20.sp
			)
		}
	}
}