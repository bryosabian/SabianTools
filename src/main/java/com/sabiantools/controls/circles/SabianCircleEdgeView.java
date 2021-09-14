package com.sabiantools.controls.circles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SabianCircleEdgeView extends View {
    private Paint mainPaint;
    private Paint secondPaint;
    private Paint textPaint;
    private int radius_main = 130;
    private int menuRadialButtonsCount = 7;
    private int menuInnerPadding = 0;
    private int radialCircleRadius = 60;
    private int textPadding = 25;
    private double startAngle = -Math.PI / 2f;

    public SabianCircleEdgeView(Context context) {
        super(context);
    }

    public SabianCircleEdgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SabianCircleEdgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainPaint = new Paint();
        mainPaint.setColor(Color.BLUE);
        secondPaint = new Paint();
        secondPaint.setColor(Color.DKGRAY);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        canvas.drawCircle(centerX, centerY, radius_main, mainPaint);

        for (int i = 0; i < menuRadialButtonsCount; i++) {
            double angle = 0;
            if (i == 0) {
                angle = startAngle;
            } else {
                angle = startAngle + (i * ((2 * Math.PI) / menuRadialButtonsCount));
            }
            int x = (int) (centerX + Math.cos(angle) * (radius_main + menuInnerPadding + radialCircleRadius));
            int y = (int) (centerY + Math.sin(angle) * (radius_main + menuInnerPadding + radialCircleRadius));


            canvas.drawCircle(x, y, radialCircleRadius, secondPaint);

            float tW = textPaint.measureText("Text " + i);
            canvas.drawText("Text " + i, x - tW / 2, y + radialCircleRadius + textPadding, textPaint);
        }
    }
}
