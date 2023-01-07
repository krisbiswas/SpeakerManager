package com.tut.lifestyle;

import android.app.Instrumentation;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;

import androidx.annotation.NonNull;
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
public class TestSTAutomation {

    private static final String STPACKAGE = "com.samsung.android.oneconnect";
    private static final String STMAINACTIVITY = "com.samsung.android.oneconnect.ui.SCMainActivity";
    private static final int LAUNCH_TIMEOUT = 10_000;

    Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

    @Test
    public void main() throws IOException, RemoteException {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        UiDevice device = UiDevice.getInstance(instrumentation);

        startAutomation(device);
    }

    private void startAutomation(UiDevice device) throws IOException, RemoteException {
        unlockDevice(device);

        try {
            launchSmartThings(device);
            doInitialSetup(device);

            launchSmartThingsSetting(device);
            gotoAboutSmartThings();
            gotoDeveloperMode(device);
            enableTestMode(device);

            UiScrollable testModePage = new UiScrollable(new UiSelector().className("androidx.recyclerview.widget.RecyclerView"));
            copyPluginFiles(device, testModePage, "Documents", "SpeakerPlugin", "LifeStyleAudio");

            UiObject saveAndRestart = testModePage.getChildByText(new UiSelector().className("android.widget.TextView"), "Save & Restart app");
            saveAndRestart.clickAndWaitForNewWindow();
            device.findObject(new UiSelector().resourceId("android:id/button1")).click();
        } catch (UiObjectNotFoundException | InterruptedException e) {
            e.printStackTrace();
            device.executeShellCommand("am force-stop com.samsung.android.oneconnect");
        }

//        device.executeShellCommand("am force-stop com.samsung.android.oneconnect");
    }

    private void unlockDevice(UiDevice device) throws RemoteException, IOException {
        int deviceWidth = device.getDisplayWidth();
        int deviceHeight = device.getDisplayHeight();
        int startY = 3*deviceHeight/4;
        int endY = deviceHeight/4;
        if(!device.isScreenOn()){
//            device.executeShellCommand("input keyevent 26");
            device.wakeUp();
            device.swipe(deviceWidth/2, startY, deviceWidth/2, endY, 25);
        }
    }

    private void launchSmartThings(UiDevice device) throws IOException, InterruptedException {
//      close ST if already open
        String stPID = device.executeShellCommand("pidof "+STPACKAGE);
        if(!stPID.isEmpty()){
            device.executeShellCommand("am force-stop com.samsung.android.oneconnect");
        }

        device.executeShellCommand("am start -n "+STPACKAGE+"/"+STMAINACTIVITY);
        device.wait(Until.hasObject(By.pkg(STPACKAGE).depth(0)), LAUNCH_TIMEOUT);
        Thread.sleep(LAUNCH_TIMEOUT);
    }

    private void doInitialSetup(UiDevice device) throws UiObjectNotFoundException {
        UiObject stInitialSetup = device.findObject(new UiSelector()
                .className(android.widget.Button.class)
                .resourceId("com.samsung.android.oneconnect:id/intro_allow_button"));
        if(stInitialSetup.exists()){
            stInitialSetup.click();
            device.findObject(new UiSelector()
                    .className(android.widget.Button.class)
                    .resourceId("com.samsung.android.oneconnect:id/global_permission_allow_button")).click();
            device.findObject(new UiSelector()
                    .className(android.widget.Button.class)
                    .resourceId("com.android.permissioncontroller:id/permission_allow_foreground_only_button")).click();
            device.findObject(new UiSelector()
                    .className(android.widget.Button.class)
                    .resourceId("com.android.permissioncontroller:id/permission_allow_button")).click();
        }

        UiObject tipBallon = device.findObject(new UiSelector()
                .className(android.widget.FrameLayout.class)
                .resourceId("com.samsung.android.oneconnect:id/common_tip_popup_balloon_panel"));
        if (tipBallon.exists()){
            Rect bounds = tipBallon.getBounds();
            device.click(bounds.centerX(), bounds.bottom+100);
        }
    }

    private void launchSmartThingsSetting(@NonNull UiDevice device) throws UiObjectNotFoundException {
        device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/menu_more")).click();
        device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/settings_image")).clickAndWaitForNewWindow();
    }

    private void gotoAboutSmartThings() throws UiObjectNotFoundException {
        UiScrollable settingsItem = new UiScrollable(new UiSelector()
                .className("androidx.recyclerview.widget.RecyclerView"));
        UiObject addNewDeviceFeatureEnableSwitch = settingsItem.getChildByDescription(new UiSelector()
                .className(android.widget.Switch.class)
                .resourceId("android:id/switch_widget"), "Ask to add new devices");
        if(addNewDeviceFeatureEnableSwitch.exists() && addNewDeviceFeatureEnableSwitch.isChecked()){
            addNewDeviceFeatureEnableSwitch.click();
        }
        UiObject aboutSmartThings = settingsItem.getChildByText(new UiSelector()
                .className("android.widget.TextView"), "About SmartThings");
        aboutSmartThings.clickAndWaitForNewWindow();
    }

    private void gotoDeveloperMode(@NonNull UiDevice device) throws UiObjectNotFoundException {
        UiObject smartthingsText = device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/about_app_name"));
        Rect smartthingsBound = smartthingsText.getBounds();
        int clickX = smartthingsBound.left+smartthingsBound.width()/2;
        int clickY = smartthingsBound.top+ smartthingsBound.height()/2;
        try {
            for(int count=0;count<10;count++){
                device.click(clickX, clickY);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//      TODO  Add wait till loading to go
    }

    private void enableTestMode(@NonNull UiDevice device) throws UiObjectNotFoundException, IOException {
        UiObject allowFilePermissionBtn = device.findObject(new UiSelector().resourceId("com.android.permissioncontroller:id/permission_allow_button"));
        if(allowFilePermissionBtn.exists()){
            allowFilePermissionBtn.click();
        }
        UiObject testModeBtn = device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/mainSwitch"));
        if(!testModeBtn.isChecked()){
            testModeBtn.click();
        }
        UiObject inputPassword = device.findObject(new UiSelector().resourceId("com.samsung.android.oneconnect:id/custom"));
        if(inputPassword.exists()){
            device.executeShellCommand("input text exbasic@eco2");
            device.findObject(new UiSelector().resourceId("android:id/button1")).click();
        }
//      TODO Wait till verification loading to go
    }

    private void selectFile(int dirStructure, String fileName) throws UiObjectNotFoundException {
        UiScrollable dirListScrollable = new UiScrollable(new UiSelector().resourceId("com.google.android.documentsui:id/dir_list"));
        UiSelector selector;
        UiSelector childProps = new UiSelector().className(android.widget.TextView.class)
                .resourceId("android:id/title")
                .textStartsWith(fileName);
        if(dirStructure == 1){
            // For Grid View Layout
            selector = new UiSelector().className(androidx.cardview.widget.CardView.class)
                    .childSelector(childProps);
        }else{
            // For List View Layout
            selector = new UiSelector().className(android.widget.LinearLayout.class)
                    .childSelector(childProps);
        }
        UiObject selectedFile = dirListScrollable.getChild(selector);
        selectedFile.click();
    }

    private void gotoFileDirectory(@NonNull UiDevice device, int dirStructure, String folderName) throws UiObjectNotFoundException {
        device.findObject(new UiSelector().resourceId("com.google.android.documentsui:id/toolbar")
                .childSelector(new UiSelector().className(android.widget.ImageButton.class).instance(0))).click();
        UiObject drawerList = device.findObject(new UiSelector().resourceId("com.google.android.documentsui:id/roots_list"));
        UiObject div = drawerList.getChild(new UiSelector().className(android.widget.FrameLayout.class).instance(0));
        int childCount = drawerList.getChildCount();
        int dividerIndex = -1;
        for(int i = 0;i<childCount;i++){
            UiObject item = drawerList.getChild(new UiSelector().index(i));
            if(item.getClassName().equalsIgnoreCase(div.getClassName())){
                dividerIndex = i;break;
            }
        }
        drawerList.getChild(new UiSelector().index(dividerIndex+1)).click();

        UiScrollable dirListScrollable = new UiScrollable(new UiSelector().resourceId("com.google.android.documentsui:id/dir_list"));

        UiSelector selector;
        if(dirStructure == 1){
            // For Grid View Layout
            selector = new UiSelector().className(androidx.cardview.widget.CardView.class)
                    .childSelector(new UiSelector().resourceId("android:id/title").text(folderName));
        }else{
            // For List View Layout
            selector = new UiSelector().className(android.widget.LinearLayout.class)
                    .childSelector(new UiSelector().resourceId("android:id/title").text(folderName));
        }
        UiObject selectedFolder = dirListScrollable.getChild(selector);
        selectedFolder.click();
    }

    private void copyPluginFiles(@NonNull UiDevice device,
                                 @NonNull UiScrollable testModePage,
                                 String rootFolderInInternalStorage,
                                 String pluginFile,
                                 String apkFile) throws UiObjectNotFoundException {
        UiObject copyPluginFileText = testModePage.getChildByText(new UiSelector().className("android.widget.TextView"), "Copy plugin file");
        UiObject copyPluginFileBtn = copyPluginFileText.getFromParent(new UiSelector().resourceId("com.samsung.android.oneconnect:id/mainButton"));
        copyPluginFileBtn.click();
        UiObject listViewIcon = device.findObject(new UiSelector().resourceId("com.google.android.documentsui:id/directory_header")
                .childSelector(new UiSelector().className(android.widget.TextView.class)
                        .descriptionContains("List view")
                        .resourceId("com.google.android.documentsui:id/sub_menu_list")));
        gotoFileDirectory(device, listViewIcon.exists()?1:0, rootFolderInInternalStorage);
        selectFile(listViewIcon.exists()?1:0, pluginFile);

        UiObject copyPluginApkText = testModePage.getChildByText(new UiSelector().className("android.widget.TextView"), "Copy apk/ppk file");
        UiObject copyPluginApkBtn = copyPluginApkText.getFromParent(new UiSelector().resourceId("com.samsung.android.oneconnect:id/mainButton"));
        copyPluginApkBtn.click();
        selectFile(listViewIcon.exists()?1:0, apkFile);
    }
}
