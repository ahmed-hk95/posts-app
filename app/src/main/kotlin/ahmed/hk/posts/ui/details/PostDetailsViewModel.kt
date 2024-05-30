package ahmed.hk.posts.ui.details

import ahmed.hk.posts.data.models.Post
import ahmed.hk.posts.data.repository.PostsRepository
import ahmed.hk.posts.ui.base.BaseViewModel
import ahmed.hk.posts.ui.navigation.PostsArguments
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PostsRepository
) : BaseViewModel() {

    private val postId = savedStateHandle.get<Int>(PostsArguments.POST_ID)

    private val _post: MutableStateFlow<Post?> = MutableStateFlow(null)
    val post: StateFlow<Post?> = _post

    init {
        getPostDetails(postId)
    }

    private fun getPostDetails(postId: Int?) {
        postId?.let {
            viewModelScope.launch {
                _post.value = repository.getPostDetails(it)
            }
        }
    }
}