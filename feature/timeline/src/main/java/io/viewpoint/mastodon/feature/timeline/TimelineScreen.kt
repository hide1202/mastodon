package io.viewpoint.mastodon.feature.timeline

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.viewpoint.mastodon.core.compose.theme.MastodonTheme

@Composable
fun TimelineScreen() {
    Text("Hello, World!")
}

@Preview
@Composable
fun TimelineScreenPreview() {
    MastodonTheme {
        TimelineScreen()
    }
}