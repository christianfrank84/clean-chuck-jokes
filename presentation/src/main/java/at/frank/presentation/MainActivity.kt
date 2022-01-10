package at.frank.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val randomJokeFragment: Fragment by lazy { supportFragmentManager.findFragmentById(R.id.jokeFragment) as Fragment }
    private val bookmarkedJokesFragment: Fragment by lazy {
        supportFragmentManager.findFragmentById(
            R.id.bookmarkedJokesFragment
        ) as Fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigationRandom -> showRandomJokeFragment()
                R.id.navigationBookmarks -> showBookmarkedJokesFragment()
                else -> true

            }
        }
    }

    private fun showRandomJokeFragment(): Boolean {
        showFragment(randomJokeFragment)
        hideFragment(bookmarkedJokesFragment)
        return true
    }

    private fun showBookmarkedJokesFragment(): Boolean {
        showFragment(bookmarkedJokesFragment)
        hideFragment(randomJokeFragment)
        return true
    }

    private fun hideFragment(randomJokeFragment: Fragment?) {
        randomJokeFragment?.let { frag ->
            supportFragmentManager.beginTransaction()
                .hide(frag).commit()
        }
    }

    private fun showFragment(randomJokeFragment: Fragment?) {
        randomJokeFragment?.let { frag ->
            supportFragmentManager.beginTransaction()
                .show(frag).commit()
        }
    }


}