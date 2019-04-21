package android.tomdoischer.workOutDroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class NewExercise extends AppCompatActivity {

    EditText nameOfTheExercise;
    DatabaseHelperExercise dbex;
    Switch measuredInTime;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);

        nameOfTheExercise = findViewById(R.id.nameOfTheExercise);
        measuredInTime = findViewById(R.id.measuredInTimeSwt);
        dbex = new DatabaseHelperExercise(this);
    }

    public void addExercise(String newExerciseName, boolean measuredInTime) {
        if(newExerciseName.trim().length() > 0) {
            boolean insertData = dbex.addDataExercise(newExerciseName, measuredInTime);

            if(insertData==true){
                Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Something went wrong.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Set the name of the exercise", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickSave(View v) {
        String name = nameOfTheExercise.getText().toString().trim();
        boolean measure = measuredInTime.isChecked();
//        System.out.println(measure);

        addExercise(name, measure);

        Intent intent = new Intent(NewExercise.this, AddExercise.class);
        startActivity(intent);
    }
}
