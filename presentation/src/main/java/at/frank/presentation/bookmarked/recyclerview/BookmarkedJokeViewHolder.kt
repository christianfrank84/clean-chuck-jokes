package at.frank.presentation.bookmarked.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.frank.domain.Joke
import at.frank.presentation.R

class BookmarkedJokeViewHolder(
    private val deleteListener: DeleteJokeFromBookmarksListener,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    fun bind(joke: Joke) {
        itemView.findViewById<TextView>(R.id.jokeText).text = joke.value
        itemView.findViewById<ImageView>(R.id.deleteButton).setOnClickListener {
            deleteListener.onDeleteJoke(joke)
        }

    }
}