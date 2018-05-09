package georg.steinbacher.community_jump_trainer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.TimeExercise;

import static android.content.ContentValues.TAG;
import static georg.steinbacher.community_jump_trainer.Configuration.PREPARATION_COUNTDOWN_TIME;
import static georg.steinbacher.community_jump_trainer.Configuration.PREPARATION_COUNTDOWN_TIME_DEFAULT;

public class TimeExerciseFragment extends Fragment implements CountdownView.OnCountdownEndListener, CountdownView.OnCountdownIntervalListener{
    private TimeExercise mExercise;
    private View mView;
    private CountdownView mCountdownView;
    private ProgressBar mProgressBar;
    private TextView mSets;
    private int mCompletedSets; //TODO show the completed sets on the UI
    private boolean mPreperationCountdown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;

        //Name
        TextView textView = mView.findViewById(R.id.exercise_name);
        textView.setText(mExercise.getName());

        //Countdown
        mCountdownView = mView.findViewById(R.id.exercise_countdown);
        mCountdownView.setOnCountdownEndListener(this);
        mCountdownView.setOnCountdownIntervalListener(1000, this); //trigger every second

        //Button
        Button exerciseStart = mView.findViewById(R.id.exercise_start_button);
        exerciseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreperationCountdown = true;
                mCountdownView.start(getMaxTime(mPreperationCountdown) * 1000);
            }
        });

        //ProgressBar
        mProgressBar = mView.findViewById(R.id.time_exercise_progress_bar);

        //Sets
        mCompletedSets = 0;
        mSets = mView.findViewById(R.id.exercise_sets);
        mSets.setText(getString(R.string.exercise_sets, Integer.toString(mExercise.getSets())));

        //Equipment
        TextView equipmentTextView = mView.findViewById(R.id.exercise_equipment);
        List<Equipment> equipmentList = mExercise.getNeededEquipment();
        if(equipmentList.size() > 0) {
            String equipmentString = "";
            for (Equipment equipment : equipmentList) {
                equipmentString += equipment.getName() + ",";
            }
            equipmentString = equipmentString.substring(0, equipmentString.length() - 1);


            equipmentTextView.setText(getString(R.string.exercise_equipment, equipmentString));
        } else {
            equipmentTextView.setVisibility(View.GONE);
        }
    }

    public void setExercise(TimeExercise exercise) {
        mExercise = exercise;
    }

    @Override
    public void onEnd(CountdownView cv) {
        if(mPreperationCountdown) {
            mPreperationCountdown = false;
            mProgressBar.setProgress(0);
            mCountdownView.start(mExercise.getTime() * 1000);
        } else {
            mCompletedSets++;
            if (mCompletedSets >= mExercise.getSets()) {
                mExercise.complete();
            }
        }
    }

    @Override
    public void onInterval(CountdownView cv, long remainTime) {
        int maxTime = getMaxTime(mPreperationCountdown);
        int passedTime = maxTime - (int)(remainTime/1000);
        int progressPercent = (passedTime * 100) / maxTime;
        mProgressBar.setProgress(progressPercent);
    }

    private int getMaxTime(boolean preparation) {
        if(preparation) {
            return Integer.valueOf(Configuration.getString(mView.getContext(),
                    PREPARATION_COUNTDOWN_TIME,
                    PREPARATION_COUNTDOWN_TIME_DEFAULT));
        } else {
            return mExercise.getTime();
        }
    }
}
