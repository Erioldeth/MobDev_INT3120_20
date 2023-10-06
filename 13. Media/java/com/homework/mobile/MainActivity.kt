package com.homework.mobile

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
		var isPlaying by remember { mutableStateOf(false) }

		val player = MediaPlayer().apply {
			setAudioAttributes(
				AudioAttributes
					.Builder()
					.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
					.build()
			)
			setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
			prepare()
		}

		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			Button(
				onClick = {
					if (isPlaying) player.pause() else player.start()
					isPlaying = !isPlaying
				}
			) {
				Text(
					text = if (isPlaying) "Pause" else "Play",
					color = if (isPlaying) Color.Red else Color.Green,
					fontWeight = FontWeight.Bold,
					fontSize = 28.sp
				)
			}
		}
	}
}