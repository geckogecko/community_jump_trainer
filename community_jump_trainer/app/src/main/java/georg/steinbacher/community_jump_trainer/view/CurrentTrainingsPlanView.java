package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import georg.steinbacher.community_jump_trainer.Configuration;
import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.TrainingActivity;
import georg.steinbacher.community_jump_trainer.TrainingsPlanDetailActivity;
import georg.steinbacher.community_jump_trainer.core.TrainingsPlan;
import georg.steinbacher.community_jump_trainer.drawables.CategorySummaryDrawable;

import static georg.steinbacher.community_jump_trainer.TrainingsPlanDetailActivity.TRAININGS_PLAN_ID;

/**
 * Created by georg on 04.04.18.
 */

public class CurrentTrainingsPlanView extends CardView implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{

    private View mRootView;
    private Context mContext;
    private TrainingsPlan mTrainingsPlan;
    private ProgressBar mCategorySummery;

    public CurrentTrainingsPlanView(Context context, TrainingsPlan trainingsPlan) {
        super(context);
        init(context, trainingsPlan);
    }

    private void init(Context context, TrainingsPlan trainingsPlan) {
        mContext = context;
        mTrainingsPlan = trainingsPlan;
        mRootView = inflate(context, R.layout.view_current_trainings_plan, this);
        setOnClickListener(this);
        setOnLongClickListener(this);

        TextView txtView = mRootView.findViewById(R.id.name);
        txtView.setText(mTrainingsPlan.getName());

        Button button = mRootView.findViewById(R.id.button_start);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra(TrainingActivity.TRAININGS_PLAN_ID, mTrainingsPlan.getId());
                mContext.startActivity(intent);
            }
        });

        mCategorySummery = findViewById(R.id.categorySummery);
        setCategorySummary(trainingsPlan);
    }

    private void setCategorySummary(TrainingsPlan trainingsPlan) {
        CategorySummaryDrawable categorySummaryDrawable = new CategorySummaryDrawable(trainingsPlan, mContext);
        mCategorySummery.setProgressDrawable(categorySummaryDrawable);

        //set the background color of the card
        CardView card = findViewById(R.id.cardView);
        card.setCardBackgroundColor(categorySummaryDrawable.getIndicatorPaint().getColor());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), TrainingsPlanDetailActivity.class);
        intent.putExtra(TRAININGS_PLAN_ID, mTrainingsPlan.getId());
        mContext.startActivity(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        PopupMenu popup = new PopupMenu(mContext, v, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.current_trainingsplan_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getTitle().equals(mContext.getString(R.string.remove))) {
            mRootView.setVisibility(View.GONE);

            //remove from config
            int[] currentConfig = Configuration.getIntArray(mContext, Configuration.CURRENT_TRAININGSPLANS_ID_KEY);
            int[] newConfig = new int[currentConfig.length-1];
            int j = 0;
            for (int i = 0; i < currentConfig.length; i++) {
                if(currentConfig[i] != mTrainingsPlan.getId()) {
                    newConfig[j] = currentConfig[i];
                    j++;
                }
            }
            Configuration.set(mContext, Configuration.CURRENT_TRAININGSPLANS_ID_KEY, newConfig);
        }
        return false;
    }
}
