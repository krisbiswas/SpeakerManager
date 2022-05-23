package com.tut.lifestyle.ocfs;

import com.tut.lifestyle.data.OCFResponse;

public interface RemoteRepresentationListener {
    void onRepresentationReceived(OCFResponse response);
}
