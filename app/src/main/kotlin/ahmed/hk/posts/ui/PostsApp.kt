package ahmed.hk.posts.ui

import ahmed.hk.posts.ui.navigation.PostsNavGraph
import ahmed.hk.posts.ui.theme.PostsAppTheme
import androidx.compose.runtime.Composable

@Composable
fun PostsApp() {
    PostsAppTheme {
        PostsNavGraph()
    }
}