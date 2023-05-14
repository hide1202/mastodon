package io.viewpoint.mastodon.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import io.viewpoint.mastodon.api.model.TimelineResponseItem
import kotlinx.serialization.json.Json

class DefaultMastodonApiClient(baseUrl: String = DEFAULT_URL) : MastodonApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                // default
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = false
                useArrayPolymorphism = false

                // customize
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url("${baseUrl.trim('/')}/")
        }
    }

    override suspend fun publicTimelines(): List<TimelineResponseItem> {
        return client.get("api/v1/timelines/public")
            .body()
    }

    companion object {
        private const val DEFAULT_URL = "https://mastodon.social/"
    }
}