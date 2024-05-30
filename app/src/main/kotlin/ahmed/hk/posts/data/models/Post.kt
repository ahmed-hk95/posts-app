package ahmed.hk.posts.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(val id: Int, val title: String, @SerialName(value = "imageUrl") val image: String) {
}