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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.viewpoint.mastodon.core.compose.HtmlText
import io.viewpoint.mastodon.core.compose.theme.MastodonTheme

@Composable
fun TimelineScreen(
    modifier: Modifier = Modifier,
    contents: List<TimelineContent>,
    onLinkClicked: (String) -> Unit = {},
    onClicked: (TimelineContent) -> Unit = {}
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
                htmlText = content.content.trim(),
                maxLines = if (content.isExpand) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                onLinkClicked = onLinkClicked,
                onClicked = {
                    onClicked(content)
                }
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
                TimelineContent(
                    id = "0",
                    content = "<p>Whenever someone implies \"free speech\" is a required feature of social media more of my hair goes grey.</p><p>Nobody thought that until Jack and his tech libertarian friends pushed it on us. Before then social networks mostly had aggressive ToS and CoC rules. And even Twitter was eventually forced to back down from its lassiez faire moderation policy.</p><p>There might be a place for free-for-all social media, but it shouldn't be the default, especially if you're trying to build communities.</p>"
                ),
                TimelineContent(
                    id = "1",
                    content = "<p>Milky Way over a Turquoise Wonderland</p><p>Image Credit &amp; Copyright: Petr Horálek / Institute of Physics in Opava, Sovena Jani</p><p><a href=\"https://apod.nasa.gov/apod/ap230529.html\" rel=\"nofollow noopener noreferrer\" target=\"_blank\" class=\"status-link unhandled-link\" title=\"https://apod.nasa.gov/apod/ap230529.html\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">apod.nasa.gov/apod/ap230529.ht</span><span class=\"invisible\">ml</span></a> <a href=\"/tags/APOD\" class=\"mention hashtag status-link\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>APOD</span></a></p>"
                ),
            )
        )
    }
}