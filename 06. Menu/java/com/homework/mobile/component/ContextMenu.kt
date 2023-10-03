package com.homework.mobile.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.mobile.ui.theme.Cyan
import com.homework.mobile.ui.theme.HotPink

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContextMenu() {
	var selectedItem by remember { mutableStateOf("None") }

	Spacer(modifier = Modifier.height(20.dp))

	Text(
		text = buildAnnotatedString {
			withStyle(style = SpanStyle(fontSize = 16.sp)) {
				append("Selected item: ")
			}
			withStyle(
				style = SpanStyle(
					fontSize = 20.sp,
					textDecoration = TextDecoration.Underline
				)
			) {
				append(selectedItem)
			}
		}
	)

	Spacer(modifier = Modifier.height(20.dp))

	Surface(
		shape = RoundedCornerShape(corner = CornerSize(20.dp)),
		color = HotPink,
		contentColor = Cyan,
		shadowElevation = 10.dp
	) {
		Menu(
			anchor = {
				Box(
					modifier = Modifier
						.heightIn(min = 48.dp)
						.padding(horizontal = 24.dp)
						.combinedClickable(
							onClick = {},
							onLongClick = it
						),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "Context Menu",
						fontSize = 24.sp
					)
				}
			},
			items = listOf(
				MenuItem(
					name = "Setting",
					subItems = listOf(
						MenuItem(
							name = "Appearance",
							subItems = listOf(
								MenuItem(name = "Color"),
								MenuItem(name = "Layout")
							)
						),
						MenuItem(name = "Experimental")
					)
				),
				MenuItem(name = "About")
			),
			onItemSelected = { selectedItem = it }
		)
	}
}