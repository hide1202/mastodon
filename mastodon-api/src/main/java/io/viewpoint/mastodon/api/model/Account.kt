package io.viewpoint.mastodon.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: String,
    val username: String,
)