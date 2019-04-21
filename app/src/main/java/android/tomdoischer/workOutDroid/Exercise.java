package android.tomdoischer.workOutDroid;

public class Exercise {
    private String name;
    boolean measureTime; //is it measured in time or reps?

    public Exercise(String name, boolean measureTime) {
        this.name = name;
        this.measureTime = measureTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(boolean measureTime) {
        this.measureTime = measureTime;
    }

    @Override
    public String toString() {
        return name + ", " + measureTime;
    }
}
