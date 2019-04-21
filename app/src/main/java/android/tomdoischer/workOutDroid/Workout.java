package android.tomdoischer.workOutDroid;

import java.util.LinkedList;

public class Workout {
    private String name;
    private LinkedList<WorkoutItem> workoutItems;

    public Workout(String name) {
        this.name = name;
        this.workoutItems = new LinkedList<>();
    }

    public void addWorkoutItem(WorkoutItem item) { //adds the item to the end of the list
        workoutItems.add(workoutItems.size(), item);
    }
}
