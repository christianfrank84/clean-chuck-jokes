package at.frank.chuckjokes.domain

import com.squareup.moshi.Json

data class Joke(
    @Json(name = "icon_url") val iconUrl: String,
    val id: String,
    val value: String,
    @Json(name = "created_at") val createdAt: String
)
