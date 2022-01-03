package at.frank.chuckjokes.domain

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("icon_url") val iconUrl: String,
    val id: String,
    val value: String,
    @SerializedName( "created_at") val createdAt: String
)
