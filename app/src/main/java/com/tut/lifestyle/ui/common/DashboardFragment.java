package com.tut.lifestyle.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class DashboardFragment extends Fragment implements RemoteRepresentationListener {
    public static final String TAG = "DashboardFragment";

    private View root;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        boolean isAi = D2SManager.getInstance().getPluginDevice().isAISoundbar();
        root = inflater.inflate((isAi ? R.layout.fragment_dashboard_ai : R.layout.fragment_dashboard_non_ai), container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        initialGets();
    }

    @Override
    public void onDestroy() {
        D2SManager.getInstance().unregister(this);
        super.onDestroy();
    }

    private void initialGets(){
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.device_name);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.device_loc);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.soundFrom);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.soundMode);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.eq);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.woofer);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.auto_eq);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.spaceFit);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.ava);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.adv_audio);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.smart_hub);
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK){
            String attr = response.getAttr();
            System.out.println(TAG+".onRepresentationReceived : " + response.getAttr() +" : "+response.getVal());

            try{
                switch(attr) {
                    case OCFAttributes.device_name:
                        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                        assert actionBar != null;
                        if(response.getVal() != null){
                            actionBar.setTitle(response.getVal());
                        }
                        break;
                    case OCFAttributes.device_loc:
                        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                        assert actionBar != null;
                        if(response.getVal() != null){
                            actionBar.setSubtitle(response.getVal());
                        }
                        break;
                }
            }catch (Exception npe){
                System.out.println(TAG+"Attr null");
            }
        }
    }
}