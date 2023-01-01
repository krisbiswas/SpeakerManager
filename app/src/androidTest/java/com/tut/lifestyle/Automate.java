package com.tut.lifestyle;

import android.app.Instrumentation;
import android.graphics.Rect;
import android.os.RemoteException;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
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
    public void main() throws IOException, RemoteException {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        UiDevice device = UiDevice.getInstance(instrumentation);

        if(!device.isScreenOn()){
            device.executeShellCommand("input keyevent 26");
//            device.swipe()
        }

//        String stPID = device.executeShellCommand("pidof "+STPACKAGE);
//        if(!stPID.isEmpty()){
//            device.executeShellCommand("am force-stop com.samsung.android.oneconnect");
//        }

//      Step 1
        device.executeShellCommand("am start -n "+STPACKAGE+"/"+STMAINACTIVITY);
        device.wait(Until.hasObject(By.pkg(STPACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
        try {
//          Step 2
            device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/menu_more")).click();
//          Step 3
            device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/settings_image")).clickAndWaitForNewWindow();
//          Step 4
            UiScrollable settingsItem = new UiScrollable(new UiSelector()
                    .className("androidx.recyclerview.widget.RecyclerView"));
            UiObject aboutSmartThings = settingsItem.getChildByText(new UiSelector()
                    .className("android.widget.TextView"), "About SmartThings");
            aboutSmartThings.clickAndWaitForNewWindow();
//          Step 5
            UiObject smartthingsText = device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/about_app_name"));
            Rect smartthingsBound = smartthingsText.getBounds();
            int clickX = smartthingsBound.left+smartthingsBound.width()/2;
            int clickY = smartthingsBound.top+ smartthingsBound.height()/2;
            for(int count=0;count<10;count++){
                device.click(clickX, clickY);
                Thread.sleep(100);
            }
//            Add wait
//          Step 6
            UiObject allowFilePermissionBtn = device.findObject(new UiSelector().resourceId("com.android.permissioncontroller:id/permission_allow_button"));
            if(allowFilePermissionBtn.exists()){
                allowFilePermissionBtn.click();
            }
            UiObject testModeBtn = device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/mainSwitch"));
            if(!testModeBtn.isChecked()){
                testModeBtn.click();
            }
//          Step 7
            UiObject inputPassword = device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/custom"));
            if(inputPassword.exists()){
                device.executeShellCommand("input text exbasic@eco2");
                device.findObject(new UiSelector().resourceId("android:id/button1")).click();
            }
//          TODO wait for loading to dismiss
            UiScrollable testModePage = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));
            UiObject copyPluginFileText = testModePage.getChildByText(new UiSelector().className("android.widget.TextView"), "Copy plugin file");
            UiObject copyPluginFileBtn = copyPluginFileText.getFromParent(new UiSelector().resourceId("com.samsung.android.oneconnect:id/mainButton"));
            copyPluginFileBtn.click();
            //TODO go to required directory and choose the file


//            UiObject copyApkText = testModePage.getChildByText(new UiSelector().className("android.widget.TextView"), "Copy apk/ppk file");
//            UiObject copyApkBtn = copyApkText.getFromParent(new UiSelector().resourceId("com.samsung.android.oneconnect:id/mainButton"));
//            UiObject copyApkBtn = copyPluginFileText.getFromParent(new UiSelector().resourceId("com.samsung.android.oneconnect:id/mainButton"));
//            copyApkBtn.click();

//            UiObject saveAndRestart = testModePage.getChildByText(new UiSelector().className("android.widget.TextView"), "Save & Restart app");
//            saveAndRestart.clickAndWaitForNewWindow();

        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

//        device.executeShellCommand("am force-stop com.samsung.android.oneconnect");
    }
}
