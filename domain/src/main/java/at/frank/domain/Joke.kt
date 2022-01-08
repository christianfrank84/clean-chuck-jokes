package at.frank.domain

data class Joke(
    val id: String = "",
    val iconUrl: String = "",
    val value: String = "",
    val createdAt: String = "",
    val bookmarked: Boolean = false
)