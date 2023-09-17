package com.homework.mobile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Fuchsia
import com.homework.mobile.ui.theme.NeonGreen

@Composable
fun PopupMenu() {
	val items = remember { mutableStateListOf(*(1..20).map { "Option $it" }.toTypedArray()) }

	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(12.dp),
		contentPadding = PaddingValues(20.dp)
	) {
		itemsIndexed(
			items = items,
			key = { index, _ -> index }
		) { index, item ->
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.background(
						color = Fuchsia,
						shape = RoundedCornerShape(corner = CornerSize(16.dp))
					)
					.padding(10.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = "${index + 1}) $item",
					fontSize = 24.sp,
					fontWeight = FontWeight.SemiBold,
					color = NeonGreen
				)
				Menu(
					anchor = {
						IconButton(onClick = it) {
							Icon(
								imageVector = Icons.Rounded.MoreVert,
								contentDescription = null,
								tint = NeonGreen
							)
						}
					},
					items = listOf(
						MenuItem("Move to top"),
						MenuItem("Delete")
					),
					onItemSelected = {
						when (it) {
							"Move to top" -> {
								items.remove(item)
								items.add(0, item)
							}

							"Delete" -> {
								items.remove(item)
							}
						}
					}
				)
			}
		}
	}
}