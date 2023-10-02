package com.homework.mobilehelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.homework.mobilehelper.component.User
import com.homework.mobilehelper.component.UserData
import com.homework.mobilehelper.component.UserList
import com.homework.mobilehelper.ui.theme.MobileHelperTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { MainContent() }
	}
}

@Preview(showBackground = true)
@Composable
fun MainContent() {
	var selectedUser by remember { mutableStateOf<UserData?>(null) }
	MobileHelperTheme {
		if (selectedUser != null)
			User(
				data = selectedUser!!,
				onBack = { selectedUser = null }
			)
		else
			UserList(onUserSelected = { selectedUser = it })
	}
}