package io.viewpoint.mastodon.feature.timeline

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.mastodon.core.compose.HtmlText
import io.viewpoint.mastodon.core.compose.theme.MastodonTheme

@Composable
fun TimelineScreen(
    modifier: Modifier = Modifier,
    contents: List<String>,
    onLinkClicked: (String) -> Unit = {},
) {
    val colorScheme = MaterialTheme.colorScheme
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(contents) { content ->
            HtmlText(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .fillMaxWidth()
                    .border(2.dp, colorScheme.primary)
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                htmlText = content,
                onLinkClicked = onLinkClicked,
            )
        }
    }
}

@Preview
@Composable
internal fun TimelineScreenPreview() {
    MastodonTheme {
        TimelineScreen(
            contents = listOf(
                "1",
                "2",
            )
        )
    }
}