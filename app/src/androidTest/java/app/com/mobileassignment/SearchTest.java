package app.com.mobileassignment;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.com.mobileassignment.views.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyString;

/**
 * Created by ngarrido on 26/11/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSuccess_chooseFirstCity() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(allOf(withId(R.id.citiesList))).atPosition(0).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.insert_point)).check(matches(isDisplayed()));
    }

    @Test
    public void test_insertNumberInSearchOption() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(typeText("665"), closeSoftKeyboard());
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText("665 Site Colonia, US")));
    }

    /**/
    @Test
    public void test_searchParticularCity() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(typeText("Brasilia"));
        onView(withId(R.id.search)).perform(closeSoftKeyboard());
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText("Brasilia, BR")));
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(1).check(matches(withText("Brasilia de Minas, BR")));
    }

    @Test
    public void test_scrollForCity() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(allOf(withId(R.id.citiesList))).onChildView(allOf(withId(R.id.cityName))).atPosition(77).check(matches(withText("Abadia, ES")));
        onData(anything()).inAdapterView(allOf(withId(R.id.citiesList))).onChildView(allOf(withId(R.id.cityName))).atPosition(77).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.insert_point)).check(matches(isDisplayed()));
    }

    @Test
    public void test_searchForCityName() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(typeText("A"));
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("A"))));
        onView(withId(R.id.search)).perform(typeText("r"));
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("Ar"))));
        onView(withId(R.id.search)).perform(typeText("rab"), closeSoftKeyboard());
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("Arrab"))));
    }

    /**/
    @Test
    public void test_searchCityNameAndThenClear() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(typeText("Cordoba"), closeSoftKeyboard());
        Thread.sleep(3000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("Cordoba"))));
        onView(withId(R.id.search)).perform(clearText());
        Thread.sleep(5000);
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("'t"))));

    }

    @Test
    public void test_searchCityAndThenReturn() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(allOf(withId(R.id.citiesList))).onChildView(allOf(withId(R.id.cityName))).atPosition(65).check(matches(withText("Aba, CN")));
        onData(anything()).inAdapterView(allOf(withId(R.id.citiesList))).atPosition(65).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.insert_point)).check(matches(isDisplayed()));
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("'t"))));

    }

    @Test
    public void test_nonCaseSensitiveFilter() throws InterruptedException {
        onView(withId(R.id.citiesList)).check(matches(isDisplayed()));
        onView(withId(R.id.search)).perform(typeText("C"), closeSoftKeyboard());
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("C"))));
        onView(withId(R.id.search)).perform(clearText());
        Thread.sleep(5000);
        onView(withId(R.id.search)).perform(typeText("c"), closeSoftKeyboard());
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.citiesList)).onChildView(allOf(withId(R.id.cityName))).atPosition(0).check(matches(withText(containsString("C"))));
    }

}