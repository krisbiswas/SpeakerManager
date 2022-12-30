package com.tut.lifestyle;

import android.app.Instrumentation;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class Automate {

    private static final String STPACKAGE = "com.samsung.android.oneconnect";
    private static final String STMAINACTIVITY = "com.samsung.android.oneconnect.ui.SCMainActivity";
    private static final int LAUNCH_TIMEOUT = 3000;

    @Test
    public void main() throws IOException {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        UiDevice device = UiDevice.getInstance(instrumentation);

        device.executeShellCommand("am start -n "+STPACKAGE+"/"+STMAINACTIVITY);
        device.wait(Until.hasObject(By.pkg(STPACKAGE).depth(0)),
                LAUNCH_TIMEOUT);

        try {
            device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/menu_more")).click();
            device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/settings_image")).clickAndWaitForNewWindow();

            UiScrollable settingsItem = new UiScrollable(new UiSelector()
                    .className("androidx.recyclerview.widget.RecyclerView"));
            UiObject aboutSmartThings = settingsItem.getChildByText(new UiSelector()
                    .className("android.widget.TextView"), "About SmartThings");
            aboutSmartThings.clickAndWaitForNewWindow();

            UiObject smartthingsText = device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/about_app_name"));
            for(int count=0;count<11;count++){
                smartthingsText.click();
                Thread.sleep(50);
            }

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

        device.executeShellCommand("am force-stop com.samsung.android.oneconnect");
    }
}
