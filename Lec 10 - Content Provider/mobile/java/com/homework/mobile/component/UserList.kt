package com.homework.mobile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Purple40

@Composable
fun UserList() {
	val resolver = LocalContext.current.contentResolver
	val dataList = remember { mutableStateListOf<UserData>() }

	LaunchedEffect(Unit) {
		resolver
			.query(
				UserProvider.CONTENT_URI, null,
				null, null,
				null
			)
			?.use {
				while (it.moveToNext()) {
					with(it) {
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
	}

	LazyColumn(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 32.dp, vertical = 16.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		items(
			items = dataList,
			key = { it.id }
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.background(
						color = Purple40,
						shape = RoundedCornerShape(16.dp)
					)
					.padding(16.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Row(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Rounded.Person,
						contentDescription = null
					)
					Text(
						text = it.name,
						fontSize = 24.sp
					)
				}
				Row(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Rounded.Phone,
						contentDescription = null
					)
					Text(
						text = it.phone,
						fontSize = 16.sp
					)
				}
				Row(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Rounded.Email,
						contentDescription = null
					)
					Text(
						text = it.email.ifEmpty { "NULL" },
						fontSize = 16.sp
					)
				}
			}
		}
	}
}

data class UserData(
	val id: Long,
	val name: String,
	val phone: String,
	val email: String
)