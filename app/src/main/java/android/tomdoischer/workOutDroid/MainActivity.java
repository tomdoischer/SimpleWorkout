package android.tomdoischer.workOutDroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerListAdapterWorkouts.OnWorkoutListener {

    private ArrayList<String> workouts = new ArrayList<>();
    DatabaseHelperExercise db;
    String workoutSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWorkoutsDataView();

        initRecycleView();
    }

    private void initWorkoutsDataView() {
        db = new DatabaseHelperExercise(this);

        Cursor data = db.getDataWorkouts();

        if(data.getCount() == 0) {
            Toast.makeText(this, "There are no exercises in the database!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                workouts.add(data.getString(1));
            }
        }
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewWorkouts);
        RecyclerListAdapterWorkouts adapterWorkouts = new RecyclerListAdapterWorkouts(workouts, this, this);
        recyclerView.setAdapter(adapterWorkouts);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeWorkout((String) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);


        workoutSelected = adapterWorkouts.getWorkoutSelected();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void removeWorkout(String name) {
        db.getWritableDatabase().delete(DatabaseHelperExercise.TABLE_NAME_WORKOUTS,
                DatabaseHelperExercise.COL2_W + "='" + name + "'", null);
    }


    public void openEditWorkoutsView (View v) {
        Intent intent = new Intent(MainActivity.this, EditWorkout.class);
        intent.putExtra("WORKOUT_NAME", workoutSelected);
        startActivity(intent);
    }

    public void openStartWorkoutView (View v) {
        Intent intent = new Intent(MainActivity.this, StartWorkout.class);
        intent.putExtra("WORKOUT_NAME", workoutSelected);
        startActivity(intent);
    }

    public void openAddNewWorkout(View v) {
        startActivity(new Intent(MainActivity.this, AddNewWorkout.class));
    }

    @Override
    public void onWorkoutClick(int position) {
        workoutSelected = workouts.get(position);
    }
}
