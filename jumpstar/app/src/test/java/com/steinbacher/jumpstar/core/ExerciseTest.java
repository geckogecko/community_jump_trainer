package com.steinbacher.jumpstar.core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by georg on 25.03.18.
 */

public class ExerciseTest {
    final int id = 12;
    final String name = "testExercise";
    ExerciseDescription exerciseDescription;
    Equipment equipment0 = new Equipment("TestEquipment0", Equipment.Type.GYM);
    List<Equipment> equipmentList;
    final Difficulty difficulty = new Difficulty(7.5);
    final Rating rating = new Rating(8.4);
    final Exercise.TargetArea targetArea = Exercise.TargetArea.FULL_BODY;
    final int sets = 3;
    final Exercise.Category category= Exercise.Category.STRENGTH;

    @InjectMocks
    Exercise exercise;

    @Before
    public void init() throws ExerciseDescription.MissingExerciseStepException {
        exerciseDescription = new ExerciseDescription(new ArrayList<ExerciseStep>());
        equipmentList = new ArrayList<>();
        equipmentList.add(equipment0);

        exercise = new Exercise(id, name, exerciseDescription, equipmentList, difficulty, rating, targetArea, sets, category);

        // listenerCalledTest
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructorTest() {
        assertEquals(id, exercise.getId());
        assertEquals(name, exercise.getName());
        assertEquals(exerciseDescription, exercise.getDescription());
        assertEquals(equipmentList, exercise.getNeededEquipment());
        assertEquals(difficulty, exercise.getDifficulty());
        assertEquals(rating, exercise.getRating());
        assertEquals(Exercise.Type.UNKNOWN, exercise.getType());
        assertEquals(targetArea, exercise.getTargetArea());
        assertEquals(sets, exercise.getSets());
        assertEquals(category, exercise.getCategory());
        assertEquals(false, exercise.isCompleted());
    }

    @Test
    public void setTest() {
        final int newSets = 5;
        exercise.setSets(newSets);
        assertEquals(newSets, exercise.getSets());
    }

    @Mock
    TrainingsPlanEntry.ITrainingsPlanEntryListener exerciseListener;

    @Test
    public void listenerCalledTest() {
        assertEquals(false, exercise.isCompleted());
        exercise.complete();
        verify(exerciseListener, times(1)).onEntryCompleted(exercise);
        assertEquals(true, exercise.isCompleted());
    }
}
