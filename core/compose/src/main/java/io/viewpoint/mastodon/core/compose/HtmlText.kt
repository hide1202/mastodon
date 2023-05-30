package io.viewpoint.mastodon.core.compose

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.HtmlCompat

@OptIn(ExperimentalFoundationApi::class, ExperimentalTextApi::class)
@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    htmlText: String,
    style: TextStyle = TextStyle.Default,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onLinkClicked: (String) -> Unit = {},
) {
    val convertedText = HtmlCompat
        .fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        .toAnnotatedString()

    ClickableText(
        modifier = modifier,
        text = convertedText,
        onHover = {},
        style = style,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        onClick = { index ->
            convertedText.getUrlAnnotations(start = index, end = index)
                .firstOrNull()
                ?.let { annotation ->
                    onLinkClicked(annotation.item.url)
                }
        },
    )
}

@OptIn(ExperimentalTextApi::class)
fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString

    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic
                    ), start, end
                )
            }

            is UnderlineSpan -> addStyle(
                SpanStyle(textDecoration = TextDecoration.Underline),
                start,
                end
            )

            is ForegroundColorSpan -> addStyle(
                SpanStyle(color = Color(span.foregroundColor)),
                start,
                end
            )

            is URLSpan -> {
                addStyle(
                    SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
                    start,
                    end
                )
                addUrlAnnotation(UrlAnnotation(span.url), start, end)
            }
        }
    }
}

@Preview
@Composable
fun HtmlTextPreview() {
    Column {
        HtmlText(
            modifier = Modifier.background(Color.White),
            htmlText = "<p>Whenever someone implies \"free speech\" is a required feature of social media more of my hair goes grey.</p><p>Nobody thought that until Jack and his tech libertarian friends pushed it on us. Before then social networks mostly had aggressive ToS and CoC rules. And even Twitter was eventually forced to back down from its lassiez faire moderation policy.</p><p>There might be a place for free-for-all social media, but it shouldn't be the default, especially if you're trying to build communities.</p>",
        )
        HtmlText(
            modifier = Modifier.background(Color.White),
            htmlText = "<p>Milky Way over a Turquoise Wonderland</p><p>Image Credit &amp; Copyright: Petr Horálek / Institute of Physics in Opava, Sovena Jani</p><p><a href=\"https://apod.nasa.gov/apod/ap230529.html\" rel=\"nofollow noopener noreferrer\" target=\"_blank\" class=\"status-link unhandled-link\" title=\"https://apod.nasa.gov/apod/ap230529.html\"><span class=\"invisible\">https://</span><span class=\"ellipsis\">apod.nasa.gov/apod/ap230529.ht</span><span class=\"invisible\">ml</span></a> <a href=\"/tags/APOD\" class=\"mention hashtag status-link\" rel=\"nofollow noopener noreferrer\" target=\"_blank\">#<span>APOD</span></a></p>",
        )
    }
}