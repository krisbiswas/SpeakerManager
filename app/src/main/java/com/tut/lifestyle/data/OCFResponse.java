package com.tut.lifestyle.data;

import com.tut.lifestyle.constants.OCFConstants;

import java.util.List;

public class OCFResponse {
    OCFConstants status;
    String attr;
    String val;

    public OCFResponse(OCFConstants status, String uri, String val){
        this.status = status;
        this.attr = uri;
        this.val = val;
    }
    public OCFResponse(){}

    public OCFConstants getStatus() {
        return status;
    }

    public void setStatus(OCFConstants result) {
        this.status = result;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    List<String> values;
}
