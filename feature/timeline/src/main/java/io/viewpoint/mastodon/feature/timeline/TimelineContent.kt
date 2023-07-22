package io.viewpoint.mastodon.feature.timeline

data class TimelineContent(
    val id: String,
    val content: String,
    val isExpand: Boolean = false,
)