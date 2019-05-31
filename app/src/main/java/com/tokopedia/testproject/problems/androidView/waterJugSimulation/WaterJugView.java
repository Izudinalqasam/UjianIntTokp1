package com.tokopedia.testproject.problems.androidView.waterJugSimulation;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class WaterJugView extends View {

    private int maxWater = 0;
    private int waterFill = 0;
    private Paint blackColorPaint;
    private Paint blueColorPaint;

    public WaterJugView(Context context) {
        super(context);
        initView();
    }

    public WaterJugView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public WaterJugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        blackColorPaint = new Paint();
        blackColorPaint.setColor(Color.BLACK);
        blackColorPaint.setAntiAlias(true);

        blueColorPaint = new Paint();
        blueColorPaint.setColor(Color.BLUE);
        blueColorPaint.setAntiAlias(true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaterJugView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setMaxWater(int maxWater) {
        this.maxWater = maxWater * 10;
    }

    public void setWaterFill(int waterFill) {
        this.waterFill = waterFill * 10;
    }

    public void validateChange(){
        invalidate();
    }

    private float xStartPos = 10f;
    private float xStartPos2 = 90f;
    private float yStartPos = 10f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        yStartPos = getHeight();

        canvas.drawLine(xStartPos, yStartPos, xStartPos, (yStartPos - maxWater), blackColorPaint);
        canvas.drawLine(xStartPos2, yStartPos, xStartPos2,(yStartPos - maxWater), blackColorPaint);
        canvas.drawLine(xStartPos, yStartPos, xStartPos2, yStartPos, blackColorPaint);

        canvas.drawLine(xStartPos, ((yStartPos - maxWater) + (maxWater - waterFill)), xStartPos2, ((yStartPos - maxWater) + (maxWater - waterFill)), blueColorPaint);
    }

    //TODO
    /*
    Based on these variables: maxWater and waterFill, draw the jug with the water

    Example a:
    maxWater = 10
    waterFill = 0

    Result,
    View will draw like below
    |        |
    |        |
    |        |
    |        |
    `--------'

    Example b:
    maxWater = 10
    waterFill = 5

    Result,
    View will draw like below
    |        |
    |        |
    |--------|
    |        |
    `--------'

    Example c:
    maxWater = 10
    waterFill = 8

    Result,
    View will draw like below
    |        |
    |--------|
    |        |
    |        |
    `--------'

    Example d:
    maxWater = 10
    waterFill = 10

    Result,
    View will draw like below
     ________
    |        |
    |        |
    |        |
    |        |
    `--------'
    */

}
