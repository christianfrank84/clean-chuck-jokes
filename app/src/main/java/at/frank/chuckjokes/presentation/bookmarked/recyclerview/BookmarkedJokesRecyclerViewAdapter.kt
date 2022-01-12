package at.frank.chuckjokes.presentation.bookmarked.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import at.frank.chuckjokes.R
import at.frank.chuckjokes.domain.Joke

class BookmarkedJokesRecyclerViewAdapter(private val deleteListener: DeleteJokeFromBookmarksListener) :
    RecyclerView.Adapter<BookmarkedJokeViewHolder>() {

    private var jokeList: List<Joke> = emptyList()


    fun setData(jokes: List<Joke>) {
        jokeList = jokes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkedJokeViewHolder {
        return BookmarkedJokeViewHolder(
            deleteListener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bookmarked_joke, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookmarkedJokeViewHolder, position: Int) {
        holder.bind(jokeList[position])
    }

    override fun getItemCount() = jokeList.size

}