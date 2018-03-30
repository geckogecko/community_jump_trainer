package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by stge on 30.03.18.
 */

public class TimeExercise extends Exercise {
    private static final String TAG = "TimeExercise";

    private int mTime; //Seconds

    TimeExercise(String name,
                 ExerciseDescription description,
                 List<Equipment> equipment,
                 Difficulty difficulty,
                 Rating rating,
                 TargetArea targetArea,
                 int sets,
                 int time) {
        super(name, description, equipment, difficulty, rating, targetArea, sets);

        mTime = time;
    }

    @Override
    public Type getType() {
        return Type.TIME;
    }

    public int getTime() {
        return mTime;
    }
}