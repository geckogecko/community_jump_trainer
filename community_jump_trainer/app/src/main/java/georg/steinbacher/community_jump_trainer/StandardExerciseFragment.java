package georg.steinbacher.community_jump_trainer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import georg.steinbacher.community_jump_trainer.core.Equipment;
import georg.steinbacher.community_jump_trainer.core.Exercise;
import georg.steinbacher.community_jump_trainer.core.ExerciseDescription;
import georg.steinbacher.community_jump_trainer.core.ExerciseStep;
import georg.steinbacher.community_jump_trainer.core.StandardExercise;
import georg.steinbacher.community_jump_trainer.drawables.CategoryPaints;
import georg.steinbacher.community_jump_trainer.util.OnSwipeTouchListener;
import georg.steinbacher.community_jump_trainer.view.EquipmentView;
import georg.steinbacher.community_jump_trainer.view.ExerciseStepsView;

import static android.content.ContentValues.TAG;

public class StandardExerciseFragment extends Fragment {
    private static final String TAG = "StandardExerciseFragmen";

    private StandardExercise mExercise;
    private View mView;

    private ExerciseStepsView mSteps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_standard_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view;

        //Name
        TextView nameTextView = mView.findViewById(R.id.exercise_name);
        nameTextView.setText(mExercise.getName());

        //ExerciseSteps
        mSteps = mView.findViewById(R.id.exercise_step_view);
        mSteps.setTrainingsplan(mExercise);

        //Sets
        TextView setsTextView = mView.findViewById(R.id.exercise_sets);
        setsTextView.setText(getString(R.string.exercise_sets, Integer.toString(mExercise.getSets())));

        //Repetitions
        TextView repetitionsTextView = mView.findViewById(R.id.exercise_repetitions);
        int[] reps = mExercise.getRepetitions();

        if(reps[0] == -1) {
            repetitionsTextView.setText(getString(R.string.exercise_repetitions_standard_as_much));
        } else {
            String repsString = Integer.toString(reps[0]);

            if (reps.length > 1) {
                for (int i = 1; i < reps.length; i++) {
                    repsString += ",";
                    repsString += Integer.toString(reps[i]);
                }
            }
            repetitionsTextView.setText(getString(R.string.exercise_repetitions, repsString));
        }

        //Category
        TextView txtCategory = mView.findViewById(R.id.exercise_category);
        String category = mExercise.getCategory().name().toLowerCase();
        category = category.substring(0, 1).toUpperCase() + category.substring(1);
        txtCategory.setText(category);
        txtCategory.setTextColor(CategoryPaints.getPrimaryColor(getContext(), mExercise.getCategory()).getColor());

        //Equipment
        EquipmentView equipmentViewHolder = mView.findViewById(R.id.equipment_view);
        List<Equipment> equipmentList = mExercise.getNeededEquipment();
        equipmentViewHolder.setEquipment(equipmentList);

        //Swipe listener
        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();

                if(mExercise.getDescription().getSteps().size() -1 > mSteps.getCurrentShownStep()) {
                    Animation animSlide = AnimationUtils.loadAnimation(getContext(),
                            R.anim.slide_out_left);
                    animSlide.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mSteps.viewStep(mSteps.getCurrentShownStep() + 1);

                            Animation animSlide = AnimationUtils.loadAnimation(getContext(),
                                    R.anim.slide_in_right);
                            mSteps.startAnimation(animSlide);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    mSteps.startAnimation(animSlide);

                }
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();

                if(mSteps.getCurrentShownStep() > 0) {
                    Animation animSlide = AnimationUtils.loadAnimation(getContext(),
                            R.anim.slide_out_right);
                    animSlide.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mSteps.viewStep(mSteps.getCurrentShownStep() - 1);
                            
                            Animation animSlide = AnimationUtils.loadAnimation(getContext(),
                                    R.anim.slide_in_left);
                            mSteps.startAnimation(animSlide);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    mSteps.startAnimation(animSlide);
                }
            }
        });
    }

    public void setExercise(StandardExercise exercise) {
        mExercise = exercise;
    }
}
