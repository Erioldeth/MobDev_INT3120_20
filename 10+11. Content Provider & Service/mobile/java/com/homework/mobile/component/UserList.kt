package com.homework.mobile.component

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserList() {
	val context = LocalContext.current
	val dataList = remember { mutableStateListOf<UserData>() }

	var fetchService by remember { mutableStateOf<FetchService?>(null) }
	val connection = object : ServiceConnection {
		override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
			val binder = service as FetchService.LocalBinder
			fetchService = binder.getService()
			fetchService?.fetch(dataList)
			fetchService?.sync(dataList)
		}

		override fun onServiceDisconnected(name: ComponentName?) {}
	}

	DisposableEffect(Unit) {
		Intent(context, FetchService::class.java).let {
			context.bindService(it, connection, Context.BIND_AUTO_CREATE)
		}

		onDispose {
			context.unbindService(connection)
		}
	}

	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					context
						.getSharedPreferences("Provider", Context.MODE_PRIVATE)
						.getBoolean("isServiceStarted", false)
						.let { isStarted ->
							Intent(context, NotifierService::class.java).let {
								if (isStarted) context.stopService(it)
								else context.startService(it)
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