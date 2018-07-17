package com.steinbacher.jumpstar.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.TrainingsPlanDetailActivity;
import com.steinbacher.jumpstar.core.TrainingsPlan;
import com.steinbacher.jumpstar.drawables.CategorySummaryDrawable;

import static android.content.ContentValues.TAG;
import static com.steinbacher.jumpstar.TrainingsPlanDetailActivity.TRAININGS_PLAN_ID;


/**
 * Created by georg on 04.04.18.
 */

public class TrainingsPlanView extends LinearLayoutCompat implements View.OnClickListener{
    private Context mContext;
    private TrainingsPlan mTrainingsPlan;
    private ProgressBar mCategorySummery;

    public TrainingsPlanView(Context context) {
        super(context);
        init(context);
    }

    public TrainingsPlanView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.view_trainings_plan, this);
        mCategorySummery = findViewById(R.id.categorySummery);
        setOnClickListener(this);
    }

    public void setTrainingsPlan(TrainingsPlan trainingsPlan) {
        mTrainingsPlan = trainingsPlan;
        setCategorySummary(trainingsPlan);

        //title
        TextView txtTitle = findViewById(R.id.name);
        txtTitle.setText(mTrainingsPlan.getName());

        //description
        TextView txtDescription = findViewById(R.id.description);
        txtDescription.setText(mTrainingsPlan.getDescription());

        //image
        ImageView imgView = findViewById(R.id.image);
        final String imageName = "trainingsplan_" + trainingsPlan.getId();
        final int resourceId = mContext.getResources().getIdentifier(imageName, "drawable",
                getContext().getPackageName());

        if(resourceId != 0) {
            imgView.setBackgroundResource(resourceId);
        } else {
            Log.e(TAG, "image for exercise: " + imageName + " not found");
            //TODO what should we do if the image is not found?
        }
    }

    private void setCategorySummary(TrainingsPlan trainingsPlan) {
        CategorySummaryDrawable categorySummaryDrawable = new CategorySummaryDrawable(trainingsPlan, mContext);
        mCategorySummery.setProgressDrawable(categorySummaryDrawable);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), TrainingsPlanDetailActivity.class);
        intent.putExtra(TRAININGS_PLAN_ID, mTrainingsPlan.getId());
        mContext.startActivity(intent);
    }
}