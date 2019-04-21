package android.tomdoischer.workOutDroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperExercise extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "data.db";
    public static final int DATABASE_VERSION = 1;

    // saved exercises
    public static final String TABLE_NAME = "execises_data";
    public static final String COL1 = "EXERCISE_NAME";
    public static final String COL2 = "MEASURED_IN_TIME";

    // saved workouts
    public static final String TABLE_NAME_WORKOUTS = "workouts_data";
    public static final String COL1_W = "ID";
    public static final String COL2_W = "NAME";

    // saved workout items
    public static final String TABLE_NAME_WORKOUT_ITEMS = "workoutItems";
    public static final String COL1_WI = "ID";
    public static final String COL2_WI = "WORKOUT";
    public static final String COL3_WI = "EXERCISE_NAME";
    public static final String COL4_WI = "SETS";
    public static final String COL5_WI = "REPS_OR_TIME";
    public static final String COL6_WI = "MEASURED_IN_TIME";



    public DatabaseHelperExercise(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create statement exercise table
    String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( "
            + COL1 + " TEXT, "
            + COL2 + " INTEGER ); ";
    // create statement workouts table
    String createTableWorkouts = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_WORKOUTS + "( "
            + COL1_W + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL2_W + " TEXT ); ";
    // create statement workoutItems table
    String createTableWorkoutItems = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_WORKOUT_ITEMS + "( "
            + COL1_WI + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL2_WI + " TEXT, "
            + COL3_WI + " TEXT, "
            + COL4_WI + " INTEGER, "
            + COL5_WI + " INTEGER, "
            + COL6_WI + " INTEGER ); ";

    // creates tables from statements
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(createTableWorkouts);
        db.execSQL(createTableWorkoutItems);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME_WORKOUTS);
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME_WORKOUT_ITEMS);

        onCreate(db);
    }

    // adds exercise
    public boolean addDataExercise(String exerciseName, boolean measuredInTime) {
        int inTime;
        if(measuredInTime) {
            inTime = 1;
        } else {
            inTime = 0;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, exerciseName);
        contentValues.put(COL2, inTime);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data is inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // adds workout
    public boolean addDataWorkout(String workoutName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_W, workoutName);

        long result = db.insert(TABLE_NAME_WORKOUTS, null, contentValues);

        //if data is inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // adds workout item
    public boolean addDataWorkoutItem(String workout, String exerciseName, int numberOfSets,
                                      int repsOrTime, int measuredInTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_WI, workout);
        contentValues.put(COL3_WI, exerciseName);
        contentValues.put(COL4_WI, numberOfSets);
        contentValues.put(COL5_WI, repsOrTime);
        contentValues.put(COL6_WI, measuredInTime);

        long result = db.insert(TABLE_NAME_WORKOUT_ITEMS, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // gets cursor from exercises table
    public Cursor getDataExercise(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    // gets cursor from workouts table
    public Cursor getDataWorkouts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_WORKOUTS, null);
        return data;
    }

    // gets cursor from workout items table
//    public Cursor getDataWorkoutItems(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_WORKOUT_ITEMS, null);
//        return data;
//    }

    public Cursor getDataWorkoutItems(String workout) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("gets database");
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_WORKOUT_ITEMS
                + " WHERE WORKOUT = '" + workout + "'", null);
        System.out.println("reads data");
        return data;
    }
}
