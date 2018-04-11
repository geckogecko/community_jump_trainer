package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by georg on 28.03.18.
 */

public class TrainingsPlan implements Exercise.IExerciseListener {
    private static final String TAG = "TrainingsPlan";

    private String mName;
    private List<Exercise> mExercises;
    private long mCreationDate;
    private Rating mRating;
    private Exercise mCurrentExercise;

    private ITrainingsPlanListener mListener;

    public interface ITrainingsPlanListener {
        void onCurrentExerciseCompleted(Exercise currentCompletedExercise);
        void onTrainingsPlanCompleted(TrainingsPlan completedTrainingsPlan);
    }

    public TrainingsPlan(String name, List<Exercise> exercises, long creationDate, Rating rating) {
        mName = name;
        mExercises = exercises;
        mCreationDate = creationDate;
        mRating = rating;

        if (mExercises.size() > 0) {
            mCurrentExercise = exercises.get(0);
            mCurrentExercise.setListener(this);
        }
    }

    @Override
    public void onExerciseCompleted(Exercise completedExercise) {
        int completedExerciseIndex = mExercises.indexOf(completedExercise);

        // is this the last exercise ?
        if (completedExerciseIndex != mExercises.size() - 1) {
            Exercise nextExercise = mExercises.get(completedExerciseIndex + 1);
            mCurrentExercise = nextExercise;
            mCurrentExercise.setListener(this);
        } else {
            mCurrentExercise = null;

            if (mListener != null) {
                mListener.onTrainingsPlanCompleted(this);
            }
        }

        if(mListener != null) {
            mListener.onCurrentExerciseCompleted(completedExercise);
        }
    }

    public String getName() {
        return mName;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public Rating getRating() {
        return mRating;
    }

    public Exercise getCurrentExercise() {
        return mCurrentExercise;
    }

    public void setListener(ITrainingsPlanListener trainingsPlanListener) {
        mListener = trainingsPlanListener;
    }
}
