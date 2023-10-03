package com.homework.mobile.component

import android.content.Context
import android.content.Intent
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
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Purple80
import com.homework.mobile.ui.theme.PurpleGrey80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserList() {
	val context = LocalContext.current
	val resolver = context.contentResolver
	val dataList = remember { mutableStateListOf<UserData>() }
	var reloadTrigger by remember { mutableStateOf(true) }

	LaunchedEffect(reloadTrigger) {
		dataList.clear()
		withContext(Dispatchers.IO) {
			resolver
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
	}

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "User Info",
						fontSize = 24.sp
					)
				},
				actions = {
					IconButton(onClick = { reloadTrigger = !reloadTrigger }) {
						Icon(
							imageVector = Icons.Rounded.Refresh,
							contentDescription = null
						)
					}
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = PurpleGrey80
				)
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					val sharedPreferences =
						context.getSharedPreferences("Provider", Context.MODE_PRIVATE)

					val isServiceStarted = sharedPreferences.getBoolean("isServiceStarted", false)

					Intent(context, NotifierService::class.java).apply {
						if (isServiceStarted) context.stopService(this)
						else context.startService(this)

						with(sharedPreferences.edit()) {
							putBoolean("isServiceStarted", !isServiceStarted)
							apply()
						}
					}
				}
			) {
				Icon(
					imageVector = Icons.Rounded.Notifications,
					contentDescription = null
				)
			}
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth()
				.padding(innerPadding)
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
							color = Purple80,
							shape = RoundedCornerShape(16.dp)
						)
						.padding(16.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Detail(
						icon = Icons.Rounded.Person,
						text = it.name,
						fontSize = 24.sp
					)

					Detail(
						icon = Icons.Rounded.Phone,
						text = it.phone,
						fontSize = 16.sp
					)

					Detail(
						icon = Icons.Rounded.Email,
						text = it.email.ifEmpty { "**NULL**" },
						fontSize = 16.sp
					)
				}
			}
		}
	}
}

@Composable
fun Detail(
	icon: ImageVector,
	text: String,
	fontSize: TextUnit
) {
	Row(
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			imageVector = icon,
			contentDescription = null
		)
		Text(
			text = text,
			fontSize = fontSize
		)
	}
}

data class UserData(
	val id: Long,
	val name: String,
	val phone: String,
	val email: String
)