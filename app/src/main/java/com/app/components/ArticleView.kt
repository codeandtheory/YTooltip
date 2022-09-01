package com.app.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.data.model.Article
import com.app.ui.theme.*

@Composable
fun ArticleView(
    article: Article,
) {
    Card(
        elevation = DEFAULT_ELEVATION,
        modifier = Modifier
            .padding(DEFAULT_INNER_PADDING)
            .fillMaxWidth(),
        border = BorderStroke(DEFAULT_BORDER_WIDTH, color = Color.Gray),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            article.urlToImage?.let {
                NetworkImageBanner(
                    url = it
                )
            }
            Spacer(modifier = Modifier.padding(DEFAULT_INNER_PADDING))
            article.author?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = FONT_TITLE,
                        textAlign = TextAlign.Start
                    ),

                    modifier = Modifier.padding(horizontal = DEFAULT_INNER_PADDING)
                )
            }
            Spacer(modifier = Modifier.padding(PADDING_5))
            article.title?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = FONT_CONTENT,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.padding(horizontal = DEFAULT_INNER_PADDING)
                )
            }
            Spacer(modifier = Modifier.padding(DEFAULT_INNER_PADDING))
        }
    }
}