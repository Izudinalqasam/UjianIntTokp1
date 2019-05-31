package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tokopedia.testproject.R;
import com.tokopedia.testproject.UtilKt;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public interface onSuccessLoadBitmap{
        void onSliceSuccess(List<Bitmap> bitmapList);
        void onSliceFailed(Throwable throwable);
    }

    private static Bitmap downloadedImage;
    private static ArrayList<Bitmap> bitmapList = new ArrayList<>();
    private static onSuccessLoadBitmap listener;

    public static void sliceTo4x4(Context context, onSuccessLoadBitmap onSuccessLoadBitmap, String imageUrl) {
        // TODO, download the image, crop, then sliced to 15 Bitmap (4x4 Bitmap). ignore the last Bitmap
        // below is stub, replace with your implementation!

        dowloadImage(context, imageUrl);
        listener = onSuccessLoadBitmap;
    }

    private static void returnImage(){
        bitmapList.clear();

        int widthSlices = downloadedImage.getWidth() / 4;
        int heightSlices = downloadedImage.getHeight() / 4;

        int finalWidth = widthSlices;
        int finalHeigth = heightSlices;

        int x = 0;
        int y = 0;

        for (int i=0; i < 4; i++){
            for (int j=0; j < 4; j++){

                bitmapList.add(getNewBitmap(x, y, finalWidth, finalWidth));

                if (j == 0) {
                    x += 1;
                }

                x += finalWidth;
                widthSlices += finalWidth;
            }
            x = 0;
            widthSlices = finalWidth;
            y += finalHeigth;
        }

        bitmapList.remove(bitmapList.size() - 1);
        listener.onSliceSuccess(bitmapList);
    }

    private static Bitmap getNewBitmap(int x, int y, int targetFrom, int targetTo){
        return Bitmap.createBitmap(downloadedImage, x, y, targetFrom, targetTo, null, false);
    }

    private static void checkImageSize(Context context){
        if (downloadedImage.getHeight() >= 400 && downloadedImage.getWidth() >= 400) {
            cropImageSquare();
        }else {
            Toast.makeText(context, "Image must be more than equals 400 X 400 px", Toast.LENGTH_SHORT).show();
        }
    }

    private static void cropImageSquare() {
        int targetWidth;
        int targetHeight;

        if (downloadedImage.getHeight() > downloadedImage.getWidth()) {
            targetWidth = downloadedImage.getWidth();
            targetHeight = targetWidth;
        }else {
            targetHeight = downloadedImage.getHeight();
            targetWidth = targetHeight;
        }

        if (downloadedImage.getWidth() != downloadedImage.getHeight()){
            try {
                downloadedImage = Bitmap.createBitmap(downloadedImage,0, 0, targetWidth, targetHeight, null, false);

            }catch (Exception e){
                Log.d("Croping", "cropImageSquare: Croping Image failed " + e.getLocalizedMessage());
            }
        }

        returnImage();
    }

    private static void dowloadImage(final Context context, String url) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        downloadedImage = resource;
                        checkImageSize(context);
                    }
                });
    }

}
