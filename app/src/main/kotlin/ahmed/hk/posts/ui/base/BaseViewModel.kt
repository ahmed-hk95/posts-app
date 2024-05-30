package ahmed.hk.posts.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    protected fun <T> api(
        func: suspend CoroutineScope.()-> T,
        loading:()-> Unit = {},
        error:(Throwable) -> Unit = {},
        success:(T) -> Unit = {},
    ){
        viewModelScope.launch {
            try {
                loading().also { _loading.value = true }
                success(func()).also { _loading.value = false }
            }catch (e:Exception){
                error(e).also { _loading.value = false }
            }
        }
    }
}