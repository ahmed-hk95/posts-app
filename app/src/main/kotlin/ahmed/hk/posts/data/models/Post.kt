package ahmed.hk.posts.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "posts")
@Serializable
data class Post(
    @PrimaryKey val id: Int,
    val title: String,
    @SerialName(value = "imageUrl") val image: String)