package at.frank.data.remote

import com.google.gson.annotations.SerializedName

data class JokeDTO(
    val id: String = "",
    @SerializedName("icon_url") val iconUrl: String = "",
    val value: String = "",
    @SerializedName("created_at") val createdAt: String = ""
)