package android.tomdoischer.workOutDroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class StartWorkout extends AppCompatActivity {

    Button runWorkoutBtn;

    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> exerciseNamesWI = new ArrayList<>();
    private ArrayList<Integer> numbersOfSets = new ArrayList<>();
    private ArrayList<Integer> numbersofRepsOrTime = new ArrayList<>();
    DatabaseHelperExercise dbex;
    String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        workoutName = getIntent().getStringExtra("WORKOUT_NAME");

        if(workoutName == null) {
            Toast.makeText(this, "You have not selected a workout!", Toast.LENGTH_LONG).show();
        } else {
            runWorkoutBtn = findViewById(R.id.runWorkoutBtn);
            runWorkoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StartWorkout.this, runWorkout.class);
                    intent.putExtra("WORKOUT_NAME", workoutName);
                    startActivity(intent);
                }
            });

            initWorkoutItemsDataView(workoutName);

            initRecycleView();
        }
    }

    private void initWorkoutItemsDataView(String workoutName) {
        dbex = new DatabaseHelperExercise(this);

        Cursor data = dbex.getDataWorkoutItems(workoutName);

        if (data.getCount() == 0) {
            Toast.makeText(this, "This workout is empty!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                ids.add(data.getInt(1));
                exerciseNamesWI.add(data.getString(2));
                numbersOfSets.add(data.getInt(3));
                numbersofRepsOrTime.add(data.getInt(4));
            }
        }
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_overview_start);
        RecyclerListAdapterWorkoutItems adapter = new RecyclerListAdapterWorkoutItems(ids, exerciseNamesWI, numbersOfSets, numbersofRepsOrTime, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
