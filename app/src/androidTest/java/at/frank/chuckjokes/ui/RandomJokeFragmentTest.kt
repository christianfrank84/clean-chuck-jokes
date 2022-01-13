package at.frank.chuckjokes.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import at.frank.chuckjokes.R
import at.frank.chuckjokes.presentation.randomjokes.RandomJokeFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RandomJokeFragmentTest {

    @Before
    fun setup() {

    }


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val scenario = launchFragmentInContainer<RandomJokeFragment>(initialState = Lifecycle.State.INITIALIZED)


        scenario.moveToState(Lifecycle.State.RESUMED)

        onView(withId(R.id.jokeText)).check(matches(isDisplayed()))
    }
}