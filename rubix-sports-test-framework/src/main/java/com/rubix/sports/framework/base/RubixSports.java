package com.rubix.sports.framework.base;


import com.rubix.sports.framework.pageobjects.LoginPage;
import com.rubix.sports.framework.pageobjects.MainPage;

/**
 * Created by payotj on 21/07/2016.
 */
public class RubixSports implements IRubixSports {

    @Override
    public LoginPage LoginPage() {
        return new LoginPage();
    }

    @Override
    public MainPage MainPage() {
        return new MainPage();
    }
}
