package com.glut.news.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by yy on 2018/1/23.
 */

public class CircleImage extends BitmapTransformation {
    public CircleImage(Context c){
        super(c);


    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int diameter = Math.min(toTransform.getWidth(), toTransform.getHeight());

        final Bitmap toReuse = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        final Bitmap result;
        if (toReuse != null) {
            result = toReuse;
        } else {
            result = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        }

        int dx = (toTransform.getWidth() - diameter) / 2;
        int dy = (toTransform.getHeight() - diameter) / 2;
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP);
        if (dx != 0 || dy != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate(-dx, -dy);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float radius = diameter / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        if (toReuse != null && !pool.put(toReuse)) {
            toReuse.recycle();
        }
        return result;

    }


    @Override
    public String getId() {
        return "com.glut.news";
    }
}
