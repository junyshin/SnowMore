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
    public void RegisterTest() throws Exception {
        onView(withId(R.id.registerbuttonLogin)).perform(click());
        onView(withId(R.id.fullname)).perform(typeText("Test Dummy"));
        onView(withId(R.id.username)).perform(typeText("Test1")).perform(closeSoftKeyboard());
        onView(withId(R.id.email)).perform(typeText("testdum@mail.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("Test@1234")).perform(closeSoftKeyboard());
        onView(withId(R.id.registerbutton)).perform(click());
        onView(withText("Confirm email?")).check(matches(isDisplayed()));

    }

    @Test
    public void LoginTest() throws Exception {

        onView(withId(R.id.emailLogin)).perform(typeText("test@hotmail.com"));
        onView(withId(R.id.passwordLogin)).perform(typeText("Test@1234"));
        onView(withId(R.id.loginbutton)).perform(click());
    }




  /*  @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("project.ecse428.mcgill.ca.snowmore", appContext.getPackageName());
    }
    */
}
