package com.tma.sparking.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by lnthao on 5/19/2017.
 */

public class CharacterIconResource {
    private String mText;
    private int mIconResId;
    private Context mContext;
    static private int TEXT_SIZE = 40;
    static private float STROKE_WIDTH = 1.5f;

    public CharacterIconResource(Context context, String text, int iconResId){
        this.mContext = context;
        this.mText = text;
        this.mIconResId = iconResId;
    }

    public Bitmap getBitmap() {
        Drawable drawable = mContext.getResources().getDrawable(mIconResId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        // draw the text
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(TEXT_SIZE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextAlign(Paint.Align.CENTER);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 3)) ;
        canvas.drawText(mText, xPos, yPos, paint);
        return bitmap;
    }
}
