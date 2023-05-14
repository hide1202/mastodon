package io.viewpoint.mastodon.api

import io.viewpoint.mastodon.api.model.TimelineResponseItem

interface MastodonApiClient {

    suspend fun publicTimelines(): List<TimelineResponseItem>
}
