package com.mytaxi.android_demo;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.rule.ActivityTestRule;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.mytaxi.android_demo.activities.MainActivity;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class SearchDriverTest
{

    private static final String TAG = "ActivityTesting";

    @Rule
    public ActivityTestRule<MainActivity> rule=new ActivityTestRule<>(MainActivity.class);
    public MainActivity activity=null;
    private CountingIdlingResource mIdlingResource;
    Utils helper=new Utils();
    public static final String userName="sa";
    public static final String passWord="sa";
    public static final String text="sa";
    public static final String driver="Sarah Scott";



    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    protected void setUp() throws Exception {

        activity=rule.getActivity();
        IdlingPolicies.setIdlingResourceTimeout(5L, java.util.concurrent.TimeUnit.MINUTES);


    }


    public void testLogin()
    {

    }



    public void testSearchDriver() {


        Log.i("testSearchDriverAndCall", "#Search Driver Name and Call");

        IdlingRegistry.getInstance().register(mIdlingResource);

        if (!helper.isUserLoggedin(userName))
        {
            helper.loginAttempt(userName,passWord);
        }
        helper.selectDriver(text);
        helper.searchDriver(driver,activity);
        helper.callDriver();


        Log.i("Test for search and call driver", "Search driver and call");

    }

    @Ignore
    public void whenAppStarts_SleepForTwentySeconds() {

        Log.d(TAG, "Sleeping...");
        SystemClock.sleep(20000);
    }



    @After
    public void tearDown(){
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}
