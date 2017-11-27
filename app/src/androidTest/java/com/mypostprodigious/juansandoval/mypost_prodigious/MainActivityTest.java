package com.mypostprodigious.juansandoval.mypost_prodigious;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mypostprodigious.juansandoval.mypost_prodigious.mvp.View.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkListView() throws Exception {
        Espresso.registerIdlingResources(mainActivityActivityTestRule.getActivity().getCountingIdlingResource());
        Espresso.onView(ViewMatchers.withId(R.id.my_list_view)).perform(ViewActions.click());
    }

}
