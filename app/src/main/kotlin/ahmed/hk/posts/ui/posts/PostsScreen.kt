package ahmed.hk.posts.ui.posts

import ahmed.hk.posts.data.models.Post
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun PostsScreen(
    postsViewModel: PostsViewModel = hiltViewModel(),
    navigateToPostDetails: (Int) -> Unit
) {
    val postPagingItems: LazyPagingItems<Post> = postsViewModel.posts.collectAsLazyPagingItems()
    Scaffold {
        LazyColumn {
            items(postPagingItems.itemCount) { index: Int ->
                PostItem(postPagingItems[index]!!) {
                    navigateToPostDetails.invoke(it)
                }
            }
        }
    }
}

@Composable
@Preview
private fun PostItem(post: Post, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(post.id) }.padding(16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = post.title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

    }
}