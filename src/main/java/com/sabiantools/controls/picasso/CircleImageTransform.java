package com.sabiantools.controls.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Created by Brian Sabana on 10/03/2018.
 */
public class CircleImageTransform implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {

        int size=Math.min(source.getWidth(),source.getHeight()); //Gets the radius
        int x=(source.getWidth()-size)/2;
        int y=(source.getHeight()-size)/2;

        Bitmap squareBitmap=Bitmap.createBitmap(source,x,y,size,size);

        if(squareBitmap!=source){
            source.recycle();
        }

        Bitmap bitmap=Bitmap.createBitmap(size,size,source.getConfig());

        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        BitmapShader shader=new BitmapShader(squareBitmap, BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float radius=size/2f;

        canvas.drawCircle(radius, radius, radius, paint);
        squareBitmap.recycle();

        return bitmap;

    }

    @Override
    public String key() {
        return "sfi_circle_image";
    }
}
