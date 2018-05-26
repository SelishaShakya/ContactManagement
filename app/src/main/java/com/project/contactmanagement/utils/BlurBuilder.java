package com.project.contactmanagement.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v8.renderscript.*;
import android.view.View;

public class BlurBuilder {
    private static final float BITMAP_SCALE =0.9f;
    private static final float BLUR_RADIUS = 25f;

    public static Bitmap blur(View v) {
        return blur(v.getContext(), getScreenshot(v));
    }

    public static Bitmap blur(Context ctx, Bitmap image) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(ctx);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    private static Bitmap getScreenshot(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_4444
        );
        Canvas c = new Canvas(b);
        v.draw(c);
        //Make it frosty
        Paint paint = new Paint();
        paint.setXfermode(
                new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        ColorFilter filter =
//                new LightingColorFilter(0xFFFFFFFF, 0x00222222); // lighten
        ColorFilter filter =
           new LightingColorFilter(0xFF7F7F7F, 0x00000000); // darken
        paint.setColorFilter(filter);
        c.drawBitmap(b, 0, 0, paint);
        return b;
    }
}
