package com.luxbet.qa.framework.core;

import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

/**
 * Created by nakuladevas on 5/03/2015.
 */
public abstract class APIBase extends Base {
    protected Assertion hardAssert = new Assertion();
    protected SoftAssert softAssert = new SoftAssert();
}
