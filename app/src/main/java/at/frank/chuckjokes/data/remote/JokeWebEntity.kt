package at.frank.chuckjokes.data.remote

import com.google.gson.annotations.SerializedName

data class JokeWebEntity(
    val id: String = "",
    @SerializedName("icon_url") val iconUrl: String = "",
    val value: String = "",
    @SerializedName("created_at") val createdAt: String = ""
)