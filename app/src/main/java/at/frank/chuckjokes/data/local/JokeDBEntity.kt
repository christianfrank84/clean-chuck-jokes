package at.frank.chuckjokes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeDBEntity(
    @PrimaryKey val id: String = "",
    val iconUrl: String = "",
    val value: String = "",
    val createdAt: String = "",
    var isBookmarked: Boolean = false
)
