package io.viewpoint.mastodon.api.model

import kotlinx.serialization.Serializable

@Serializable
data class TimelineResponseItem(
    val account: Account,
    val content: String,
)