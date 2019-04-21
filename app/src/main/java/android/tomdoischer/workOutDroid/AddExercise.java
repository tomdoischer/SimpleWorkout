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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddExercise extends AppCompatActivity implements RecyclerListAdapterExercises.OnExerciseListener {

    private ArrayList<String> exerciseNames = new ArrayList<>();
    private ArrayList<String> exerciseTypes = new ArrayList<>();
    DatabaseHelperExercise dbex;
    Button createNewExercise;
    Button addSelectedExercise;
    EditText reps_edittext;
    EditText sets_edittext;

    String exerciseSelectedName;
    String workoutName;
    String typeSelected;
    int numberOfRepsOrTime = 1;
    int numberOfSets = 1;
    int inTime = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        createNewExercise = (Button) findViewById(R.id.createNewExercise);
        addSelectedExercise = (Button) findViewById(R.id.addSelectedExercise);
        reps_edittext = (EditText) findViewById(R.id.reps_edittext);
        sets_edittext = (EditText) findViewById(R.id.sets_edittext);

        workoutName = getIntent().getStringExtra("WORKOUT_NAME");

        initExerciseDataView();

        initRecycleView();
    }


    public void onCreateNewExercise(View v) {
        startActivity(new Intent(AddExercise.this, NewExercise.class));
    }

    public void onAddExercise(View v) {
        // this method takes the name of the selected exercise and workout and reads data from edittexts
        try {
            if(saveExercise(exerciseSelectedName)) {
                Toast.makeText(this, "Exercise " + exerciseSelectedName + " added to the workout " + workoutName, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        Intent intent = new Intent(AddExercise.this, EditWorkout.class);
        intent.putExtra("WORKOUT_NAME", workoutName);
        startActivity(intent);
    }

    private void initExerciseDataView() {
        dbex = new DatabaseHelperExercise(this);

        Cursor data = dbex.getDataExercise();

        if(data.getCount() == 0) {
            Toast.makeText(this, "There are no exercises in the database!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                exerciseNames.add(data.getString(0));
                String type;
                if(data.getString(data.getColumnIndex(DatabaseHelperExercise.COL2)).equals("1")) {
                    type = "Measured in time";
                } else {
                    type = "Measured in reps";
                }

                exerciseTypes.add(type);
            }
        }
    }

    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView_addExercise);
        RecyclerListAdapterExercises adapter = new RecyclerListAdapterExercises(exerciseNames, exerciseTypes, this, this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { // delete exercise by swiping
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeExercise((String) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void removeExercise(String name) {
        dbex.getWritableDatabase().delete(DatabaseHelperExercise.TABLE_NAME,
                DatabaseHelperExercise.COL1 + "='" + name + "'", null);
    }

    @Override
    public void onExerciseClick(int position) {
        exerciseSelectedName = exerciseNames.get(position);
        typeSelected = exerciseTypes.get(position);
        System.out.println("clicking works " + exerciseSelectedName);
    }

    public boolean saveExercise(String exerciseName) {
        if(reps_edittext.toString().trim().length() > 0) {
            numberOfRepsOrTime = Integer.parseInt(reps_edittext.getText().toString().trim());
        } else {
            Toast.makeText(this, "Please fill out number of reps or time.", Toast.LENGTH_LONG).show();
        }

        if(sets_edittext.toString().trim().length() > 0) {
            numberOfSets = Integer.parseInt(sets_edittext.getText().toString().trim());
        } else {
            Toast.makeText(this, "Please fill out number of sets.", Toast.LENGTH_LONG).show();
        }

        if(typeSelected.equals("Measured in time")) {
            inTime = 1;
        } else {
            inTime = 0;
        }

        return dbex.addDataWorkoutItem(workoutName, exerciseName, numberOfSets, numberOfRepsOrTime, inTime);
    }

}
