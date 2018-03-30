package project.ecse428.mcgill.ca.snowmore;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RevampingUIPathTest {

    @Rule
    public ActivityTestRule<Login> mActivityRule =
            new ActivityTestRule(Login.class);
    @Test
    public void LoginTest1() throws Exception {
        //user enters credentials then logs out

        onView(withId(R.id.emailLogin)).perform(typeText("test@mail.com"));
        onView(withId(R.id.passwordLogin)).perform(typeText("Test@1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginbutton)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.Client)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.logout)).perform(click());


    }


    @Test
    public void LoginTest2() throws Exception {
        //user enters credentials then  goes back

        onView(withId(R.id.emailLogin)).perform(typeText("test@mail.com"));
        onView(withId(R.id.passwordLogin)).perform(typeText("Test@1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginbutton)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.Client)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.backButton)).perform(click());
        Thread.sleep(2000);


    }

    @Test
    public void LoginTest3() throws Exception {
        //user enters credentials then  logs out

        onView(withId(R.id.emailLogin)).perform(typeText("test@mail.com"));
        onView(withId(R.id.passwordLogin)).perform(typeText("Test@1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginbutton)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.logoutButton)).perform(click());
        Thread.sleep(2000);


    }

    @Test
    public void postingRequestTest() throws Exception {
        //user enters credentials then  posts a shoveling request

        onView(withId(R.id.emailLogin)).perform(typeText("test@mail.com"));
        onView(withId(R.id.passwordLogin)).perform(typeText("Test@1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginbutton)).perform(click());
        Thread.sleep(10000);
        onView(withId(R.id.Client)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.add)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.streetAddress)).perform(typeText("Saint-Catherine"));
        onView(withId(R.id.city)).perform(typeText("Montreal")).perform(closeSoftKeyboard());
        onView(withId(R.id.postalCode)).perform(typeText("H2X3P9")).perform(closeSoftKeyboard());
        onView(withId(R.id.phoneNumber)).perform(typeText("5149699222")).perform(closeSoftKeyboard());
        onView(withId(R.id.requestDate)).perform(typeText("7-3-2018")).perform(closeSoftKeyboard());
        onView(withId(R.id.requestTime)).perform(typeText("19:1")).perform(closeSoftKeyboard());
        onView(withId(R.id.postButton)).perform(click());
        Thread.sleep(2000);


    }



    @Test
    public void RegisterTest() throws Exception {
        onView(withId(R.id.registerbuttonLogin)).perform(click());
        onView(withId(R.id.fullname)).perform(typeText("Test Dummy"));
        onView(withId(R.id.username)).perform(typeText("Test1")).perform(closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("testdum@mail.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("Test@1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withText("Confirm email?")).check(matches(isDisplayed()));

    }


}
