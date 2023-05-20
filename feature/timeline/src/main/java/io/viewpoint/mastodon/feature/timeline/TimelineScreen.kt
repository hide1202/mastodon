package io.viewpoint.mastodon.feature.timeline

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.viewpoint.mastodon.core.compose.theme.MastodonTheme

@Composable
fun TimelineScreen(contents: List<String>) {
    LazyColumn {
        items(contents) { content ->
            Text(content)
        }
    }
}

@Preview
@Composable
internal fun TimelineScreenPreview() {
    MastodonTheme {
        TimelineScreen(
            listOf(
                "1",
                "2",
            )
        )
    }
}