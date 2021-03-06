package com.steinbacher.jumpstar.drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.steinbacher.jumpstar.core.Exercise;
import com.steinbacher.jumpstar.core.TrainingsPlan;

/**
 * Created by georg on 18.04.18.
 */

public class TrainingsPlanProgressDrawable extends Drawable {
    private static final String TAG = "TrainingsPlanProgressDr";

    private int mSegmentsNumber;
    private Context mContext;
    private TrainingsPlan mTraingsPlan;

    public TrainingsPlanProgressDrawable(TrainingsPlan trainingsPlan, Context context) {
        mTraingsPlan = trainingsPlan;
        mContext = context;
        mSegmentsNumber = mTraingsPlan.getEntryCount();
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Rect b = getBounds();
        final float gapWidth = b.height() / 2f;
        final float segmentWidth = (b.width() - (mSegmentsNumber - 1) * gapWidth) / mSegmentsNumber;
        RectF segment = new RectF(0, 0, segmentWidth, b.height());


        for (int i = 0; i < mSegmentsNumber; i++) {
            final Exercise exercise = (Exercise) mTraingsPlan.getEntries().get(i);
            Paint paint;
            if (exercise.equals(mTraingsPlan.getCurrentEntry())) {
                paint = CategoryPaints.getPrimaryColor(mContext, exercise.getCategory());
            } else {
                paint = CategoryPaints.getSecondaryColor(mContext, exercise.getCategory());
            }
            canvas.drawRoundRect(segment, 5, 5, paint);
            segment.offset(segment.width() + gapWidth, 0);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
