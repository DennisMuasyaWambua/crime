package com.example.criminalintent2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){
        //Read the dimensions of the image on the disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(path,options);
        float srcWidth= options.outWidth;
        float srcHeight = options.outHeight;

        //Find how much to scale down by
        int inSampleSize =1;
        if(srcHeight>destHeight||srcWidth>destWidth){
            float heightScale = srcHeight/destHeight;
            float widthScale = srcWidth/destWidth;
            inSampleSize = Math.round(Math.max(heightScale, widthScale));
        }
        options = new BitmapFactory.Options();
        options.inSampleSize= inSampleSize;

        //Read and create the final Bitmap
        return BitmapFactory.decodeFile(path,options);
    }
    public static Bitmap getScaledBitmap(String path,
                                         Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
}
