package georg.steinbacher.community_jump_trainer;

public class ExerciseStep {
    private static final String TAG = "ExerciseDescription.Steps";

    private int mStepNr;
    private String mStepDescription;

    ExerciseStep(int stepNr, String stepDescription) {
        mStepNr = stepNr;
        mStepDescription = stepDescription;
    }

    public int getStepNr() {
        return mStepNr;
    }

    public String getDescription() {
        return mStepDescription;
    }
}