package com.mytaxi.android_demo;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.contrib.DrawerActions;

import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Assert;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.action.ViewActions.scrollTo;

public class Utils {


    int userName=R.id.edt_username;
    int passWord=R.id.edt_password;
    int loginButton=R.id.btn_login;
    int drawer=R.id.drawer_layout;
    int navUser=R.id.nav_username;
    int logout=R.id.nav_logout;
    int driverName=R.id.textViewDriverName;
    int searchText=R.id.textSearch;
    int callButton=R.id.fab;


    Matcher userNameMatcher = ViewMatchers.withId(userName);

    Matcher passWordMatcher = ViewMatchers.withId(passWord);
    Matcher loginButtonMatcher = ViewMatchers.withId(loginButton);
    Matcher drawerMatcher = ViewMatchers.withId(drawer);
    Matcher userMatcher = ViewMatchers.withId(navUser);
    Matcher logoutMatcher = ViewMatchers.withId(logout);
    Matcher driverNameMatcher = ViewMatchers.withId(driverName);
    Matcher searchTextMatcher = ViewMatchers.withId(searchText);
    Matcher callButtonMatcher = ViewMatchers.withId(callButton);


    public void loginAttempt(String user,String pass)
    {
         if (!isLoggedIn()) {
             setText(userNameMatcher, user);
             setText(passWordMatcher, pass);
             onClick(loginButtonMatcher);
             waitTime(drawerMatcher, 1000);
         }



    }

    public void setText(Matcher v , String text){
        onView(v).check(matches(isDisplayed())).perform(typeText(text));
        onView(v).check(matches(ViewMatchers.withText(text)));
    }

    public boolean isLoggedIn(){
        if(checkIfElementIsDisplayed(userNameMatcher)){
            return true;
        }
        else{
            return false;
        }
    }

    boolean isUserLoggedin(String user) {
        if (checkIfElementIsDisplayed(drawerMatcher)) {
            Espresso.onView(drawerMatcher).check((matches(isEnabled()))).perform(DrawerActions.open());
            if (checkIfElementIsDisplayed(userMatcher)) {
                if(assertText(userMatcher, user)){

                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfElementIsDisplayed(Matcher m){
        try {
            onView(m).check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean nameInSearchSuggestion(String text,MainActivity mActivity){
        try {
            onView(withText(text))
                    .inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                    .check(matches(isDisplayed()));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void clickOnName(String s, MainActivity mActivity){
        try {
            onView(withText(s))
                    .inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                    .perform(scrollTo()).perform(click());


        }
        catch (Exception e){
            Assert.fail("cannot  click on  the text  "+e.getMessage());
        }
    }

    public void searchText(String text,Matcher matcher)
    {
        Espresso.onView(matcher).check(matches(isDisplayed())).perform(typeText(text));
        Espresso.onView(matcher).check(ViewAssertions.matches(ViewMatchers.withText(text)));
    }

    public void onClick(Matcher matcher)
    {
        Espresso.onView(matcher).check(matches(isDisplayed())).perform(click());
    }

    public boolean assertText(Matcher matcher,String texttoAssert)
    {
        onView(matcher).check(matches(ViewMatchers.withText(texttoAssert)));
        return true;

    }

    public void waitTime(Matcher matcher,long time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (checkIfElementIsDisplayed(matcher))
        {
            return ;
        }

        Assert.fail("no element");
    }


    public void searchDriver(String name, MainActivity mActivity){
        if(nameInSearchSuggestion(name,mActivity)){
            clickOnName(name,mActivity);
            if(checkIfElementIsDisplayed(driverNameMatcher)){
                if(!assertText(driverNameMatcher,name)){
                    Assert.fail("Error Searching the driver");
                }
            }
        }
        else{
            Assert.fail("expected Driver is not shown in the suggestion");
        }

    }


    public void selectDriver(String name) {
        try {
            Thread.sleep(4000);
            setText(searchTextMatcher, name);
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void callDriver(){
        waitTime(callButtonMatcher,3000);
        onClick(callButtonMatcher);
    }
}
