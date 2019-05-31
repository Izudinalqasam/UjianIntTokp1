package com.tokopedia.testproject.problems.androidView.slidingImagePuzzle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tokopedia.testproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SlidingImageGameActivity extends AppCompatActivity {
    public static final String X_IMAGE_URL = "x_image_url";
    private static final String KODE_USER_INPUT = "input_user";
    public static final int GRID_NO = 4;

    private String imageUrl;
    ImageView[][] imageViews = new ImageView[4][4];
    private GridLayout gridLayout;
    private ArrayList<Integer> randomNumber = new ArrayList<>();

    public static Intent getIntent(Context context, String imageUrl) {
        Intent intent = new Intent(context, SlidingImageGameActivity.class);
        intent.putExtra(X_IMAGE_URL, imageUrl);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUrl = getIntent().getStringExtra(X_IMAGE_URL);
        setContentView(R.layout.activity_sliding_image_game);
        gridLayout = findViewById(R.id.gridLayout);

        randomNumber.clear();
        randomNumber.add(0);
        randomNumber.add(1);
        randomNumber.add(2);
        randomNumber.add(3);


        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < GRID_NO; i++) {
            for (int j = 0; j < GRID_NO; j++) {
                final ImageView view = (ImageView) inflater.inflate(R.layout.item_image_sliding_image,
                        gridLayout, false);
                view.setTag(i);
                gridLayout.addView(view);
                imageViews[i][j] = view;

                final int finalI = i;
                final int finalJ = j;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int kanan = finalJ + 1;
                        int kiri = finalJ - 1;
                        int atas = finalI - 1;
                        int bawah = finalI + 1;

                        if (kanan <= 3){
                            if (imageViews[finalI][kanan].getDrawable() == null){
                                Drawable img = imageViews[finalI][finalJ].getDrawable();
                                int tag = (Integer) imageViews[finalI][finalJ].getTag();
                                int tagKanan = (Integer) imageViews[finalI][kanan].getTag();

                                imageViews[finalI][kanan].setImageDrawable(img);
                                imageViews[finalI][kanan].setTag(tag);

                                imageViews[finalI][finalJ].setImageDrawable(null);
                                imageViews[finalI][finalJ].setTag(tagKanan);
                            }
                        }

                        if (kiri >= 0){
                            if (imageViews[finalI][kiri].getDrawable() == null){
                                Drawable img = imageViews[finalI][finalJ].getDrawable();
                                int tag = (Integer) imageViews[finalI][finalJ].getTag();
                                int tagKiri = (Integer) imageViews[finalI][kiri].getTag();

                                imageViews[finalI][kiri].setImageDrawable(img);
                                imageViews[finalI][kiri].setTag(tag);

                                imageViews[finalI][finalJ].setImageDrawable(null);
                                imageViews[finalI][finalJ].setTag(tagKiri);
                            }
                        }

                        if (atas >= 0){
                            if (imageViews[atas][finalJ].getDrawable() == null){
                                Drawable img = imageViews[finalI][finalJ].getDrawable();
                                int tag = (Integer) imageViews[finalI][finalJ].getTag();
                                int tagAtas = (Integer) imageViews[atas][finalJ].getTag();

                                imageViews[atas][finalJ].setImageDrawable(img);
                                imageViews[atas][finalJ].setTag(tag);

                                imageViews[finalI][finalJ].setImageDrawable(null);
                                imageViews[finalI][finalJ].setTag(tagAtas);
                            }
                        }

                        if (bawah <= 3){
                            if (imageViews[bawah][finalJ].getDrawable() == null){
                                Drawable img = imageViews[finalI][finalJ].getDrawable();
                                int tag = (Integer) imageViews[finalI][finalJ].getTag();
                                int tagBawah = (Integer) imageViews[bawah][finalJ].getTag();

                                imageViews[bawah][finalJ].setImageDrawable(img);
                                imageViews[bawah][finalJ].setTag(tag);

                                imageViews[finalI][finalJ].setImageDrawable(null);
                                imageViews[finalI][finalJ].setTag(tagBawah);
                            }
                        }

                        isGameFinish();
                        gridLayout.invalidate();
                    }
                });
            }
        }

        Solution.sliceTo4x4(this, new Solution.onSuccessLoadBitmap() {
            @Override
            public void onSliceSuccess(List<Bitmap> bitmapList) {
                //TODO will randomize placement to grid. Note: the game must be solvable.
                //replace below implementation to your implementation.
                int counter = 0;
                int bitmapSize = bitmapList.size();
                for (int i = 0; i < GRID_NO; i++) {
                    for (int j = 0; j < GRID_NO; j++) {

                        if (counter >= bitmapSize) {
                            if (counter == bitmapSize){
                                imageViews[i][j].setTag(counter);
                            }
                            break;
                        }


                        if (i == 0) {
                            Collections.shuffle(randomNumber);
                            imageViews[i][j].setImageBitmap(bitmapList.get(randomNumber.get(j)));
                            imageViews[i][j].setTag(counter);

                        } else {
                            imageViews[i][j].setImageBitmap(bitmapList.get(counter));
                            imageViews[i][j].setTag(counter);
                        }
                        counter++;
                    }

                    if (counter >= bitmapSize) break;
                }
            }

            @Override
            public void onSliceFailed(Throwable throwable) {
                Toast.makeText(SlidingImageGameActivity.this,
                        throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, imageUrl);
    }

    private boolean isGameFinish(){
        int totChild = gridLayout.getChildCount();

        for (int i=0; i<totChild; i++){
            if (i != ((Integer) gridLayout.getChildAt(i).getTag())){
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KODE_USER_INPUT, imageViews);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        imageViews = (ImageView[][]) savedInstanceState.getSerializable(KODE_USER_INPUT);

        for (int i = 0; i < imageViews.length; i++){
            for (int j=0; j < imageViews[i].length; j++){
                imageViews[i][j].invalidate();
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

}
