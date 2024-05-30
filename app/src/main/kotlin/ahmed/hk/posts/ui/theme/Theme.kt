package ahmed.hk.posts.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PostsAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}