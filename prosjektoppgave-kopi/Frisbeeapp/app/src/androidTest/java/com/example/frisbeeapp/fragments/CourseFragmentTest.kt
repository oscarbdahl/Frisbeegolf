
package com.example.frisbeeapp.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.frisbeeapp.R
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class CourseFragmentTest : AppCompatActivity(){

    @Test
    fun testNavigationToInGameScreen() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val titleScenario = launchFragmentInContainer<RulesFragment>()

        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        //onView(ViewMatchers.withId(R.id.mapsFragment)).perform(ViewActions.click())
        // onView(ViewMatchers.withId(R.id.courseListFragment)).perform(ViewActions.click())
        //onView(ViewMatchers.withId(R.id.spinner)).perform(ViewActions.click())
        //assertEquals(navController.currentDestination?.id, R.id.)
    }
    
}

