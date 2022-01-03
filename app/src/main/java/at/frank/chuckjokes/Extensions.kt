package at.frank.chuckjokes

import android.content.Context

fun Context.getJokeApp(): JokeAppContract {
    return this.applicationContext as JokeAppContract
}