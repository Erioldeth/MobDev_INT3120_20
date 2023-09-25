package com.homework.mobile.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntentButton(
	onClick: () -> Unit,
	text: String,
	iconImage: ImageVector = Icons.Rounded.Star
) {
	Button(
		onClick = onClick,
		shape = RoundedCornerShape(corner = CornerSize(16.dp))
	) {
		Icon(
			imageVector = iconImage,
			contentDescription = null
		)

		Spacer(modifier = Modifier.width(16.dp))

		Text(
			text = text,
			fontSize = 20.sp,
			fontWeight = FontWeight.SemiBold
		)
	}
}