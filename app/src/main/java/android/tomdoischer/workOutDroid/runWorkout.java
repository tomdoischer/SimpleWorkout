package android.tomdoischer.workOutDroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class runWorkout extends AppCompatActivity {
    String workoutName;
    int setsRemaining;
    boolean inTime;
    int move;
    int length;
    int count;
    DatabaseHelperExercise db;
    ArrayList<WorkoutItem> workoutItems;

    TextView runNameOfExercise;
    TextView timeOrRepsRemaining;
    TextView exerciseRemaining;
    TextView reps;
    Button nextRep;
    Button nextEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_workout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // keeps the screen on

        workoutName = getIntent().getStringExtra("WORKOUT_NAME");

        runNameOfExercise = findViewById(R.id.runNameOfExercise);
        timeOrRepsRemaining = findViewById(R.id.timeOrRepsRemaining);
        exerciseRemaining = findViewById(R.id.exerciseRemaining);
        nextRep = findViewById(R.id.nextRep);
        nextEx = findViewById(R.id.nextEx);
        reps = findViewById(R.id.reps);

        System.out.println("reached ifs");

        if(workoutName != null) {
            System.out.println("reached run");
            run();
        } else {
            Toast.makeText(this, "You have not selected a workout!", Toast.LENGTH_LONG).show();
        }
    }

    public void run() {
        // a method which reads individual workoutItems from the database and returns an ArrayList
        // of workoutItems
        workoutItems = createRunnableWorkout(workoutName);

        System.out.println("array created");
        // length of the workout
        length = workoutItems.size();
        move = 0;

        setUpTextViews(move);
    }

    public ArrayList<WorkoutItem> createRunnableWorkout(String workoutName) {
        ArrayList<WorkoutItem> workoutItems = new ArrayList<>();

        db = new DatabaseHelperExercise(this);

        Cursor data = db.getDataWorkoutItems(workoutName);

        if (data.getCount() == 0) {
            Toast.makeText(this, "This workout is empty!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                WorkoutItem workoutItem = new WorkoutItem();

                boolean inTime;
                if(data.getInt(5) == 1) {
                    inTime = true;
                } else {
                    inTime = false;
                }

                workoutItem.setExercise(new Exercise(data.getString(2),inTime));
                workoutItem.setSets(data.getInt(3));

                if (workoutItem.getExercise().isMeasureTime()) {
                    workoutItem.setTime(data.getInt(4));
                } else {
                    workoutItem.setReps(data.getInt(4));
                }

                workoutItems.add(workoutItem);
            }
        }

        return workoutItems;
    }


    public void onNextEx(View v) {
        move++;
        if(move<length) {
            setUpTextViews(move);
        } else {
            Intent newIntent = new Intent(runWorkout.this, runWorkoutDone.class);
            newIntent.putExtra("WORKOUT_NAME", workoutName);
            startActivity(newIntent);
        }
    }

    public void onNextSet(View v) {
        if (inTime) {

        } else {
            if(setsRemaining > 1) {
                setsRemaining--;
                timeOrRepsRemaining.setText(String.format("%s set(s)", String.valueOf(setsRemaining)));
            } else {
                timeOrRepsRemaining.setText("Done!");
            }
        }
    }

    public void setUpTextViews(int move) {
        runNameOfExercise.setText(workoutItems.get(move).getExercise().getName());

        setsRemaining = workoutItems.get(move).getSets();
        inTime = workoutItems.get(move).getExercise().isMeasureTime();

        if(workoutItems.get(move).getExercise().isMeasureTime()) {
            reps.setText(String.valueOf(setsRemaining) + " set(s)");
            new CountDownTimer(workoutItems.get(move).time * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeOrRepsRemaining.setText(millisUntilFinished / 1000 + " s");
                }

                @Override
                public void onFinish() {
                    timeOrRepsRemaining.setText("Done!");
                }
            }.start();


        } else {
            reps.setText(String.valueOf(workoutItems.get(move).getSets()) + " rep(s)");
            timeOrRepsRemaining.setText(String.valueOf(workoutItems.get(move).getReps()) + " set(s)");
        }
        exerciseRemaining.setText((move + 1) + " / " + length);
    }
}
