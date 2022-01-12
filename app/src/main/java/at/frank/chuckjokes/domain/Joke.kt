package at.frank.chuckjokes.domain

import at.frank.chuckjokes.data.local.JokeDBE
import at.frank.chuckjokes.data.remote.JokeDTO

data class Joke(
    val id: String = "",
    val iconUrl: String = "",
    val value: String = "",
    val createdAt: String = "",
    var bookmarked: Boolean = false
) {
    fun mapToDTO(): JokeDTO =
        JokeDTO(id = id, iconUrl = iconUrl, value = value, createdAt = createdAt)

    fun mapToDBE(): JokeDBE =
        JokeDBE(id = id, iconUrl = iconUrl, value = value, createdAt = createdAt)

    companion object {
        fun mapFromDTO(jokeDTO: JokeDTO): Joke =
            Joke(
                id = jokeDTO.id,
                iconUrl = jokeDTO.iconUrl,
                value = jokeDTO.value,
                createdAt = jokeDTO.createdAt,
                bookmarked = false
            )

        fun mapFromDBE(jokeDBE: JokeDBE): Joke =
            Joke(
                id = jokeDBE.id,
                iconUrl = jokeDBE.iconUrl,
                value = jokeDBE.value,
                createdAt = jokeDBE.createdAt,
                bookmarked = true
            )
    }
}