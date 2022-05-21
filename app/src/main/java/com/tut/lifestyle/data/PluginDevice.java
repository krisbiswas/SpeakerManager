package com.tut.lifestyle.data;

import android.util.Pair;

import java.util.Arrays;
import java.util.List;

// If any functions value is null, means not supported
public class PluginDevice {

//    #############################################################################
    private String deviceName;
    private String modelName;
    private int locationID;
    private int deviceID;
    private int cloudID;
    private boolean isAI = true;
//    Device connected to cloud
    private boolean isCloudConnected = true;

    private List<String> functionsSupported = Arrays.asList("Audio Mode", "Equalizer", "Advanced Audio");

    //    <source Device, connection type>
    private Pair<String, String> soundFrom = null;
    private String equalizerState = "NORMAL";
    private List<Integer> advEqSetting = Arrays.asList(5,5,5,5);
    private List<Integer> basicEqSetting = Arrays.asList(5,5);
    private String advancedAudio = "Standard";
    private boolean spaceFit;
    private boolean woofer;
    private String firmwareVersion = "0.1.0";
    //    network rssi value on the scale of 100
    private int nwStatus = 100;
    private List<Integer> channelVol = Arrays.asList(5,5);
    private boolean alexaSupported;
    private boolean spotifySupported;
    private boolean chromeCastSupported;

    //region Getters and Setters

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getCloudID() {
        return cloudID;
    }

    public void setCloudID(Integer cloudID) {
        this.cloudID = cloudID;
    }

    public boolean isAI() {
        return isAI;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public boolean isCloudConnected() {
        return isCloudConnected;
    }

    public void setCloudConnected(boolean cloudConnected) {
        isCloudConnected = cloudConnected;
    }

    public List<String> getFunctionsSupported() {
        return functionsSupported;
    }

    public void setFunctionsSupported(List<String> functionsSupported) {
        this.functionsSupported = functionsSupported;
    }

    public Pair<String, String> getSoundFrom() {
        return soundFrom;
    }

    public void setSoundFrom(Pair<String, String> soundFrom) {
        this.soundFrom = soundFrom;
    }

    public String getEqualizerState() {
        return equalizerState;
    }

    public void setEqualizerState(String equalizerState) {
        this.equalizerState = equalizerState;
    }

    public String getAdvancedAudio() {
        return advancedAudio;
    }

    public void setAdvancedAudio(String advancedAudio) {
        this.advancedAudio = advancedAudio;
    }

    public Boolean getSpaceFit() {
        return spaceFit;
    }

    public void setSpaceFit(Boolean spaceFit) {
        this.spaceFit = spaceFit;
    }

    public Boolean getWoofer() {
        return woofer;
    }

    public void setWoofer(Boolean woofer) {
        this.woofer = woofer;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public int getNwStatus() {
        return nwStatus;
    }

    public void setNwStatus(int nwStatus) {
        this.nwStatus = nwStatus;
    }

    public Boolean getAlexaSupported() {
        return alexaSupported;
    }

    public void setAlexaSupported(Boolean alexaSupported) {
        this.alexaSupported = alexaSupported;
    }

    public Boolean getSpotifySupported() {
        return spotifySupported;
    }

    public void setSpotifySupported(Boolean spotifySupported) {
        this.spotifySupported = spotifySupported;
    }

    public Boolean getChromeCastSupported() {
        return chromeCastSupported;
    }

    public void setChromeCastSupported(Boolean chromeCastSupported) {
        this.chromeCastSupported = chromeCastSupported;
    }
    //endregion

    public void subScribe(){

    }
}
