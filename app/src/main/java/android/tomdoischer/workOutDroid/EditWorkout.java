package android.tomdoischer.workOutDroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditWorkout extends AppCompatActivity {
    Button addExerciseBtn;
    Button saveWorkoutBtn;

    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> exerciseNamesWI = new ArrayList<>();
    private ArrayList<Integer> numbersOfSets = new ArrayList<>();
    private ArrayList<Integer> numbersofRepsOrTime = new ArrayList<>();
    public TextView textView_editWorkout;
    DatabaseHelperExercise dbex;
    String workoutName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        System.out.println(getIntent().getStringExtra("WORKOUT_NAME"));
        workoutName = getIntent().getStringExtra("WORKOUT_NAME");
        addExerciseBtn = findViewById(R.id.addExerciseBtn);
        saveWorkoutBtn = findViewById(R.id.saveWorkoutBtn);
        textView_editWorkout = findViewById(R.id.textView_editWorkout);

        initWorkoutItemsDataView(workoutName);

        initRecycleView();

        textView_editWorkout.setText("Edit workout: " + workoutName);
    }

    private void initWorkoutItemsDataView(String workoutName) {
        dbex = new DatabaseHelperExercise(this);

        try {
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
        } catch (SQLException e) {
            Toast.makeText(this, "This workout is empty!", Toast.LENGTH_LONG).show();
        }
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_edit_workouts);
        RecyclerListAdapterWorkoutItems adapter = new RecyclerListAdapterWorkoutItems(ids, exerciseNamesWI, numbersOfSets, numbersofRepsOrTime, this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                System.out.println(viewHolder.itemView.getTag());
                for(int id : ids) {
                    System.out.println(id);
                }
                removeExercise((Integer) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void removeExercise(int id) {
        System.out.println("deleting " + id);
        dbex.getWritableDatabase().delete(DatabaseHelperExercise.TABLE_NAME_WORKOUT_ITEMS,
                DatabaseHelperExercise.COL1_WI + "='" + id + "'", null);
    }

    public void onAddExercise(View v) {
        Intent mIntent = new Intent(EditWorkout.this, AddExercise.class);
        mIntent.putExtra("WORKOUT_NAME", workoutName);
        startActivity(mIntent);
    }

    // doesn't really save, it is saved already; rather, it starts main activity again
    public void onSaveWorkout(View v) {
        startActivity(new Intent(EditWorkout.this, MainActivity.class));
    }

}
