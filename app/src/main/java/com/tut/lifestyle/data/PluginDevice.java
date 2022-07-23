package com.tut.lifestyle.data;

import android.util.Pair;

import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.ui.ai.funtions.AdvancedAudioFragment;
import com.tut.lifestyle.ui.ai.funtions.EqualizerFragment;
import com.tut.lifestyle.ui.ai.funtions.SoundModeFragment;

import java.util.Arrays;
import java.util.List;

// If any functions value is null, means not supported
public class PluginDevice {

    private static final String TAG = "PluginDevice";
    //    #############################################################################
    private String deviceName;
    private String deviceLoc;
    private String modelName;
    private int locationID = 1;
    private int deviceID = 1;
    private int cloudID = 0;
    private Boolean isAISoundbar = true;
//    Device connected to cloud
    private Boolean isCloudConnected = true;

    private List<String> functionsSupported = Arrays.asList(SoundModeFragment.TAG, EqualizerFragment.TAG, AdvancedAudioFragment.TAG);

    //    <source Device, connection type>
    private Pair<String, String> soundFrom = null;
    private Integer spkVol = 0;
    private String soundMode = "Standard";
    private String equalizerState = "Metal";
    private List<Integer> advEqSetting = Arrays.asList(5,5,5,5);
    private List<Integer> basicEqSetting = Arrays.asList(5,5);
    private String advancedAudio = "Standard";
    private Boolean spaceFit = null;
    private Boolean voiceAssistant = null;
    private Integer wooferSetting = null;
    private String firmwareVersion = "0.0.0";
    //    network strength value on the scale of 5
    private Integer nwStatus = 0;
    private List<Integer> channelVol = Arrays.asList(5,5);
    private Boolean spotifySupported;
    private Boolean chromeCastSupported;
    private Pair<Integer, Integer> channelSetting = new Pair<>(60,60);
    private Boolean autoEq = null;
    private String smartHubStatus = "Not Using";
    private String audioFeedback = null;
    private Boolean btPairStatus = null;

    //region Getters and Setters
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceLoc() {
        return deviceLoc;
    }

    public void setDeviceLoc(String deviceLoc) {
        this.deviceLoc = deviceLoc;
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

    public boolean isAISoundbar() {
        return isAISoundbar;
    }

    public void setIsAISoundbar(boolean AI) {
        isAISoundbar = AI;
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

    public Integer getSpkVol() {
        return spkVol;
    }

    public void setSpkVol(Integer spkVol) {
        this.spkVol = spkVol;
    }

    public String getSoundMode() {
        return soundMode;
    }

    public void setSoundMode(String soundMode) {
        this.soundMode = soundMode;
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

    public Boolean hasAutoEq() {
        return autoEq;
    }

    public void setAutoEq(boolean autoEq) {
        this.autoEq = autoEq;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public Integer getNwStatus() {
        return nwStatus;
    }

    public void setNwStatus(int nwStatus) {
        this.nwStatus = nwStatus;
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

    public Integer getWooferSetting() {
        return wooferSetting;
    }

    public void setWooferSetting(Integer wooferSetting) {
        this.wooferSetting = wooferSetting;
    }

    public Boolean getVoiceAssistant() {
        return voiceAssistant;
    }

    public void setVoiceAssistant(Boolean voiceAssist) {
        this.voiceAssistant = voiceAssist;
    }

    public Boolean getBtPairingModeStatus(){
        return btPairStatus;
    }

    public void setBtPairingModeStatus(boolean pair){
        this.btPairStatus = pair;
    }

    public Pair<Integer, Integer> getChannelSetting() {
        return channelSetting;
    }

    public void setChannelSetting(Pair<Integer, Integer> channelSetting) {
        this.channelSetting = channelSetting;
    }

    public String getAudioFeedback() {
        return audioFeedback;
    }

    public void setAudioFeedback(String lang) {
        this.audioFeedback = lang;
    }

    public String getSmartHubStatus() {
        return smartHubStatus;
    }

    public void setSmartHubStatus(String status) {
        this.smartHubStatus = status;
    }
    //endregion

    public void subScribe(){

    }

    @Override
    public String toString() {
        return "PluginDevice{" +
                "deviceName='" + deviceName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", locationID=" + locationID +
                ", deviceID=" + deviceID +
                ", cloudID=" + cloudID +
                ", isAI=" + isAISoundbar +
                ", isCloudConnected=" + isCloudConnected +
                ", functionsSupported=" + functionsSupported +
                ", soundFrom=" + soundFrom +
                ", equalizerState='" + equalizerState + '\'' +
                ", advEqSetting=" + advEqSetting +
                ", basicEqSetting=" + basicEqSetting +
                ", advancedAudio='" + advancedAudio + '\'' +
                ", spaceFit=" + spaceFit +
                ", voiceAssitant=" + voiceAssistant +
                ", woofer=" + wooferSetting +
                ", autoEq=" + autoEq +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", nwStatus=" + nwStatus +
                ", channelVol=" + channelVol +
                ", spotifySupport=" + spotifySupported +
                ", chromeCastSupport=" + chromeCastSupported +
                '}';
    }

    public String getData(String uri){
        String response = "";
        switch (uri){
            case OCFAttributes.device_name:
                response = getDeviceName();
                break;
            case OCFAttributes.device_loc:
                response = getDeviceLoc();
                break;
            case OCFAttributes.soundFrom:
                response = getSoundFrom().first+","+getSoundFrom().second;
                break;
            case OCFAttributes.spk_vol:
                response = getSpkVol().toString();
                break;
            case OCFAttributes.soundMode:
                response = getSoundMode();
                break;
            case OCFAttributes.eq:
                response = getEqualizerState();
                break;
            case OCFAttributes.eq_basic:
                // Stream to String
                response = basicEqSetting.toString();
                break;
            case OCFAttributes.eq_adv:
                // Stream to string
                response = advEqSetting.toString();
                break;
            case OCFAttributes.woofer:
                response = (getWooferSetting() != null)?getWooferSetting().toString() : null;
                break;
            case OCFAttributes.spaceFit:
                response = (getSpaceFit() != null)?getSpaceFit().toString() : null;
                break;
            case OCFAttributes.ava:
                response = (getVoiceAssistant() != null)?getVoiceAssistant().toString() : null;
                break;
            case OCFAttributes.adv_audio:
                response = getAdvancedAudio();
                break;
            case OCFAttributes.smart_hub:
                response = getSmartHubStatus();
                break;
            case OCFAttributes.nw_status:
                response = getNwStatus().toString();
                break;
            case OCFAttributes.bt_pair:
                response = (getBtPairingModeStatus() != null)?getBtPairingModeStatus().toString():null;
                break;
            case OCFAttributes.channel_vol:
                response = getChannelSetting().first+","+getChannelSetting().second;
                break;
            case OCFAttributes.auto_eq:
                response = (hasAutoEq() != null)?hasAutoEq().toString():null;
                break;
            case OCFAttributes.audio_feedback:
                response = getAudioFeedback();
                break;
        }
        System.out.println(TAG+"Getting data "+uri+"-"+response);
        return response;
    }

    public OCFResponse setData(String uri, Object val){
        System.out.println(TAG+"Setting data "+uri+"-"+val);
        OCFResponse response = new OCFResponse();
        switch (uri){
            case OCFAttributes.device_name:
                setDeviceName(val.toString());
                break;
            case OCFAttributes.device_loc:
                break;
            case OCFAttributes.soundFrom:
                String[] soundFromData = ((String)val).split(",");
                setSoundFrom(new Pair<>(soundFromData[0], soundFromData[1]));
                break;
            case OCFAttributes.spk_vol:
                setSpkVol(Integer.parseInt(val.toString()));
                break;
            case OCFAttributes.soundMode:
                setSoundMode(val.toString());
                break;
            case OCFAttributes.eq:
                setEqualizerState(val.toString());
                break;
            case OCFAttributes.woofer:
                setWooferSetting(Integer.parseInt(val.toString()));
                break;
            case OCFAttributes.spaceFit:
                setSpaceFit(Boolean.parseBoolean(val.toString()));
                break;
            case OCFAttributes.ava:
                setVoiceAssistant(Boolean.parseBoolean(val.toString()));
                break;
            case OCFAttributes.adv_audio:
                setAdvancedAudio(val.toString());
                break;
            case OCFAttributes.nw_status:
                setNwStatus(Integer.parseInt(val.toString()));
                break;
            case OCFAttributes.channel_vol:
                String[] channelVolData = ((String)val).split(",");
                setChannelSetting(new Pair<Integer, Integer>(Integer.parseInt(channelVolData[0]), Integer.parseInt(channelVolData[1])));
                break;
            case OCFAttributes.auto_eq:
                setAutoEq(Boolean.parseBoolean(val.toString()));
                break;
            case OCFAttributes.audio_feedback:
                setAudioFeedback(val.toString());
                break;
        }
        response.setAttr(uri);
        response.setVal(getData(uri));
        response.setStatus(OCFConstants.RESULT_OK);
        return response;
    }
}
