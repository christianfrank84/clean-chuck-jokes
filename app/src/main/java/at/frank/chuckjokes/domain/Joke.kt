package at.frank.chuckjokes.domain

data class Joke(
    val iconUrl: String = "",
    val id: String = "",
    val value: String = "",
    val createdAt: String = "",
    var isBookmarked: Boolean = false
)
