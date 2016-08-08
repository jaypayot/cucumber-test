package com.rubix.sports.framework.ipageobjects;

import com.luxbet.qa.framework.clcm.rubix_controls.ITextBox_Password;
import com.luxbet.qa.framework.elements.icontrols.IButton;
import com.luxbet.qa.framework.elements.icontrols.ILink;
import com.luxbet.qa.framework.elements.icontrols.ITextBox;

/**
 * Created by payotj on 19/07/2016.
 */
public interface ILoginPage {
    ITextBox Username();
    ITextBox_Password Password();
    IButton LoginButton();
    ILink ForgotYourPassword();
    void VerifyErrorMessage(String expected);
}
