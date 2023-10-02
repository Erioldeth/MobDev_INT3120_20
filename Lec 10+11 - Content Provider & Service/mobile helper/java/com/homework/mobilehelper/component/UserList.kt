package com.homework.mobilehelper.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobilehelper.ui.theme.Purple80
import com.homework.mobilehelper.ui.theme.PurpleGrey80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserList(onUserSelected: (UserData) -> Unit) {
	val resolver = LocalContext.current.contentResolver
	val dataList = remember { mutableStateListOf<UserData>() }

	LaunchedEffect(Unit) {
		withContext(Dispatchers.IO) {
			resolver
				.query(
					Info.CONTENT_URI, null,
					null, null,
					null
				)
				?.use {
					while (it.moveToNext()) with(it) {
						dataList.add(
							UserData(
								id = getLong(getColumnIndexOrThrow(Info.User.ID)),
								name = getString(getColumnIndexOrThrow(Info.User.NAME)),
								phone = getString(getColumnIndexOrThrow(Info.User.PHONE)),
								email = getString(getColumnIndexOrThrow(Info.User.EMAIL)),
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
						text = "User List",
						fontSize = 24.sp
					)
				},
				actions = {
					IconButton(onClick = {
						onUserSelected(
							UserData(
								id = -1,
								name = "",
								phone = "",
								email = ""
							)
						)
					}) {
						Icon(
							imageVector = Icons.Rounded.Add,
							contentDescription = null
						)
					}
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = PurpleGrey80
				)
			)
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
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.background(
							color = Purple80,
							shape = RoundedCornerShape(16.dp)
						)
						.padding(16.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						imageVector = Icons.Rounded.Person,
						contentDescription = null
					)

					Spacer(modifier = Modifier.width(8.dp))

					Text(
						text = it.name,
						fontSize = 24.sp
					)

					Spacer(modifier = Modifier.weight(1f))

					IconButton(onClick = { onUserSelected(it) }) {
						Icon(
							imageVector = Icons.Rounded.Edit,
							contentDescription = null
						)
					}
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