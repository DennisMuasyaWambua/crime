package com.example.criminalintent2;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Boolean mSolved;
    private String mSuspect;

    public Crime(){
        mId = UUID.randomUUID();
        mDate= new Date();
    }
    public Crime(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public Boolean getmSolved() {
        return mSolved;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setmSolved(Boolean mSolved) {
        this.mSolved = mSolved;
    }
    public boolean isSolved(){
        return true;
    }
    public String getSuspect(){
        return mSuspect;
    }
    public void setSuspect(String suspect){
        mSuspect = suspect;
    }
    public String getPhotoFileName(){
        return "IMG_"+getmId().toString()+".jpg";
    }
}
