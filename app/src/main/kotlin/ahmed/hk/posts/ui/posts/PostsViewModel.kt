package ahmed.hk.posts.ui.posts

import ahmed.hk.posts.data.models.Post
import ahmed.hk.posts.data.repository.PostsRepository
import ahmed.hk.posts.ui.base.BaseViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: PostsRepository) :
    BaseViewModel() {

    private val _posts = MutableStateFlow<PagingData<Post>>(PagingData.empty())
    val posts: Flow<PagingData<Post>> = _posts

    init {
        initPagingData()
        refreshPosts()
    }

    private fun initPagingData() {
        viewModelScope.launch {
            repository.getPosts(10)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _posts.value = it
                }
        }
    }

    private fun refreshPosts() {
        api(func = { repository.refreshPosts() },
            error = {
                _posts.value = PagingData.empty()
            }) {

        }
    }
}