package com.homework.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.component.ContextMenu
import com.homework.mobile.component.Menu
import com.homework.mobile.component.MenuItem
import com.homework.mobile.component.PopupMenu
import com.homework.mobile.ui.theme.BurntOrange
import com.homework.mobile.ui.theme.Peach

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { MainContent() }
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainContent() {
	var title by remember { mutableStateOf("Context Menu") }

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = title,
						fontSize = 24.sp,
						fontWeight = FontWeight.Bold
					)
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = Peach,
					titleContentColor = BurntOrange,
					actionIconContentColor = BurntOrange
				),
				actions = {
					Menu(
						anchor = {
							IconButton(
								onClick = it,
								content = {
									Icon(
										imageVector = Icons.Rounded.Menu,
										contentDescription = null,
										modifier = Modifier.size(32.dp)
									)
								}
							)
						},
						items = listOf(
							MenuItem("Context Menu"),
							MenuItem("Popup Menu")
						),
						onItemSelected = { title = it }
					)
				}
			)
		}
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(it),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			when (title) {
				"Context Menu" -> ContextMenu()
				"Popup Menu" -> PopupMenu()
			}
		}
	}
}