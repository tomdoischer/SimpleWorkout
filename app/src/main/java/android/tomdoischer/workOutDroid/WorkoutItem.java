package android.tomdoischer.workOutDroid;

public class WorkoutItem {
    Exercise exercise;
    int sets; // how many sets
    int reps; // reps to be repeated
    int time; // time to be repeated in seconds
    int weight; // leave at 0 for BW


    public WorkoutItem() {

    }

    public WorkoutItem(Exercise exercise, int reps, int weight, int sets) { // classic free weight lifting
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        this.sets = sets;
    }

    public WorkoutItem(Exercise exercise, int timeOrReps) { // BW exercise (either time or reps in UI)
        this.exercise = exercise;
        if(exercise.isMeasureTime()) {
            this.time = timeOrReps;
        } else {
            this.reps = timeOrReps;
        }
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
