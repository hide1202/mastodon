package io.viewpoint.mastodon

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.viewpoint.mastodon.api.DefaultMastodonApiClient
import io.viewpoint.mastodon.core.compose.theme.MastodonTheme
import io.viewpoint.mastodon.feature.timeline.TimelineContent
import io.viewpoint.mastodon.feature.timeline.TimelineScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val timelineViewModel: TimelineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MastodonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val timelines by timelineViewModel.timelines.collectAsState()
                    TimelineScreen(
                        modifier = Modifier.fillMaxSize(),
                        contents = timelines,
                        onLinkClicked = { url ->
                            startActivity(Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(url)
                            })
                        },
                        onClicked = {
                            timelineViewModel.toggleExpand(it)
                        }
                    )
                }
            }
        }
    }
}

class TimelineViewModel : ViewModel() {
    private val client = DefaultMastodonApiClient()
    private val _timelines = MutableStateFlow<List<TimelineContent>>(emptyList())
    val timelines: StateFlow<List<TimelineContent>> = _timelines.asStateFlow()

    fun toggleExpand(content: TimelineContent) {
        _timelines.update { previous ->
            val index = previous.indexOfFirst { it.id == content.id }
            if (index == -1) {
                previous
            } else {
                previous.toMutableList().apply {
                    val previousExpand = this[index].isExpand
                    this[index] = this[index].copy(isExpand = !previousExpand)
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            val response = client.publicTimelines()
            _timelines.value = response.map {
                TimelineContent(it.id, it.content)
            }.also {
                Log.d("Timeline", it.toString())
            }
        }
    }
}