package com.luxbet.qa.framework.utils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Created by ashoka on 24/06/2016.
 * This class sets the webdriver path
 * 1) If the chromedriver is present in the Drivers folder in the client, it takes it from there
 * 2) TODO: Otherwise, it takes the chromedriver from the resources directory in the project
 * This would give us the flexibility to choose between chromedriver versions if required
 */
public class OSDriverPathHelper {

    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String driverPath;
    private static String driverLocation;

    private static final String WINDOWS_DRIVER_PATH = "C:\\Selenium\\Drivers\\chromedriver.exe";
    private static final String LINUX_DRIVER_PATH = "/usr/local/share/chromedriver";
    private static final String WINDOWS_DRIVER_LOCATION = "C:\\Selenium\\Drivers\\";
    private static final String LINUX_DRIVER_LOCATION = "/usr/local/share/";

    public static String getDriverPath() {
        return OSDriverPathHelper.driverPath;
    }

    public static void setDriverPath(String driverPath) {
        OSDriverPathHelper.driverPath = driverPath;
    }

    public static String getDriverLocation() {
        return driverLocation;
    }

    public static void setDriverLocation(String driverLocation) {
        OSDriverPathHelper.driverLocation = driverLocation;
    }

    public static void setDriverPathByCheckingOS() {
        // Check if the file is in the folder location otherwise take from the project
        // Currently implemented for Windows & Linux. Can be extended to more OSs
        if (isWindows()) {
            File file = new File(WINDOWS_DRIVER_PATH);
            // If found, get the path from that location in the client
            if (file.exists()) {
                setDriverPath(WINDOWS_DRIVER_PATH);
                setDriverLocation(WINDOWS_DRIVER_LOCATION);
            }
            // else, take it from the resources directory in the project
            else {
               // TODO
            }
        }
        else if (isUnix()) {
            File file2 = new File(LINUX_DRIVER_PATH);
            // If found, get the path from that location in the client
            if (file2.exists()) {
                setDriverPath(LINUX_DRIVER_PATH);
                setDriverLocation(LINUX_DRIVER_LOCATION);
            }
            // else, take it from the resources directory in the project
            else {
                // TODO
            }
        }
    }

    // Check if the OS is windows
    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    // Check if the OS is Linux
    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
}