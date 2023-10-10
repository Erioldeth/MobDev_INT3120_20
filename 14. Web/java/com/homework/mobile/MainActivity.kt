package com.homework.mobile

import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.homework.mobile.ui.theme.MobileTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent { MainContent() }
	}
}

@Preview(showBackground = true)
@Composable
fun MainContent() {
	MobileTheme {
		AndroidView(
			factory = {
				WebView(it).apply {
					loadUrl("https://www.cornhub.website")
				}
			}
		)
	}
}