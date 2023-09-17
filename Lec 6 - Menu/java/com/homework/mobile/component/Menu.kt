package com.homework.mobile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Lavender
import com.homework.mobile.ui.theme.Teal

@Composable
fun Menu(
	anchor: @Composable (() -> Unit) -> Unit,
	items: List<MenuItem>,
	onItemSelected: (String) -> Unit,
	modifier: Modifier = Modifier
) {
	var isVisible by remember { mutableStateOf(false) }
	var itemList by remember { mutableStateOf(items) }
	var selectedPath = ""

	LaunchedEffect(isVisible) {
		if (!isVisible) {
			itemList = items
			selectedPath = ""
		}
	}

	Box {
		anchor { isVisible = !isVisible }

		DropdownMenu(
			expanded = isVisible,
			onDismissRequest = { isVisible = false },
			modifier = modifier.background(color = Lavender),
		) {
			itemList.forEach {
				DropdownMenuItem(
					text = {
						Text(
							text = it.name,
							fontSize = 16.sp
						)
					},
					trailingIcon = {
						if (it.hasSubItem)
							Icon(
								imageVector = Icons.Rounded.PlayArrow,
								contentDescription = null,
								modifier = Modifier.size(16.dp)
							)
					},
					onClick = {
						if (it.hasSubItem) {
							itemList = it.subItems
							selectedPath = "$selectedPath${it.name}."
						} else {
							isVisible = false
							onItemSelected("$selectedPath${it.name}")
						}
					},
					colors = MenuDefaults.itemColors(
						textColor = Teal,
						trailingIconColor = Teal
					)
				)
			}
		}
	}
}

data class MenuItem(
	val name: String,
	val subItems: List<MenuItem> = emptyList(),
	val hasSubItem: Boolean = subItems.isNotEmpty()
)