package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.Gray5A
import com.tawajood.the_community_user.app.ui.theme.Gray9E
import com.tawajood.the_community_user.app.ui.theme.RedF44
import com.tawajood.the_community_user.domain.models.society.Comment

@Composable
fun CommentCard(
    comment: Comment,
    onLikePressed:(Int?)-> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = comment.user?.image,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(shape = CircleShape),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                UiText(
                    text = comment.user?.name ?: "",
                    fontSize = 12.sp,
                    color = Black1f,
                    fontWeight = FontWeight.W700
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UiText(
                text = comment.comment ?: "",
                modifier = Modifier.weight(1f, fill = false),
                fontSize = 10.sp,
                color = Gray5A,
                fontWeight = FontWeight.W400
            )
            IconButton(onClick = {
                onLikePressed(comment.id)
            }) {
                Icon(
                    imageVector = if (comment.likeStatus == true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (comment.likeStatus == true) RedF44 else Gray9E,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        DisplayReplies(Modifier.padding(start = 16.dp).fillMaxWidth(),
            comment.replies?.filterNotNull()?:emptyList(),)
    }
}

@Composable
private fun DisplayReplies(modifier: Modifier, comments: List<Comment>) {
    Column(modifier = modifier) {
        comments.forEach { comment->
            CommentCard(comment, onLikePressed = {})
        }
    }
}