package android.tomdoischer.workOutDroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewWorkout extends AppCompatActivity {

    DatabaseHelperExercise db;

    EditText editText_workout_name;
    Button add_workout_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_workout);

        editText_workout_name = findViewById(R.id.et_workout_name);
        add_workout_add = findViewById(R.id.add_workout_add);
        db = new DatabaseHelperExercise(this);
    }

    public void addWorkout(String workoutName) {
        if(workoutName.trim().length() > 0) {
            boolean insertData = db.addDataWorkout(workoutName);

            if(insertData==true){
                Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Set the name of the workout", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickAdd(View v) {
        String name = editText_workout_name.getText().toString().trim();

        addWorkout(name);

        Intent intent = new Intent(AddNewWorkout.this, MainActivity.class);
        startActivity(intent);
    }
}
