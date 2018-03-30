package georg.steinbacher.community_jump_trainer.core;

import java.util.List;

/**
 * Created by georg on 15.03.18.
 */

public class Exercise {
    private String mName;
    private ExerciseDescription mDescription;
    private List<Equipment> mEquipmentList;
    private Difficulty mDifficulty;
    private Rating mRating;
    private TargetArea mTargetArea;
    private int mSets;

    public enum Type {
        UNKNOWN,
        STANDARD,
        TIME
    }

    public enum TargetArea {
        FULL_BODY,
        LEGS,
        ARMS,
        CORE
    }

    Exercise(String name,
             ExerciseDescription description,
             List<Equipment> equipment,
             Difficulty difficulty,
             Rating rating,
             TargetArea targetArea,
             int sets) {
        mName = name;
        mDescription = description;
        mEquipmentList = equipment;
        mDifficulty = difficulty;
        mRating = rating;
        mTargetArea = targetArea;
        mSets = sets;
    }

    public String getName() {
        return mName;
    }

    public List<Equipment> getNeededEquipment() {
        return mEquipmentList;
    }

    public ExerciseDescription getDescription() {
        return mDescription;
    }

    public Difficulty getDifficulty() {
        return mDifficulty;
    }

    public Rating getRating() {
        return mRating;
    }

    public Type getType() {
        return Type.UNKNOWN;
    }

    public TargetArea getTargetArea() {
        return mTargetArea;
    }

    public int getSets() {
        return mSets;
    }
}