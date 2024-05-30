package ahmed.hk.posts.ui.navigation

import ahmed.hk.posts.ui.details.PostDetailsScreen
import ahmed.hk.posts.ui.posts.PostsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun PostsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = PostsDestinations.POSTS_LIST
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(PostsDestinations.POSTS_LIST) {
            PostsScreen { postId ->
                navController.navigate("${PostsDestinations.POST_DETAILS}/$postId"){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }


        composable(
            route = "${PostsDestinations.POST_DETAILS}/{${PostsArguments.POST_ID}}",
            arguments = listOf(
                navArgument(PostsArguments.POST_ID) { type = NavType.IntType }
            )
        ) {
            PostDetailsScreen()
        }
    }
}