# SimpleWorkout
This is an Android app allowing the user to define the exercises they want to perform, organize them into workouts and then run the workouts to make sure they persorm the exact number of sets and reps in the exact order they defined. Exercises can be measured either in reps or in time (for planks etc.). Those measured in time utilize a timer.

The app as such contains no exercises, the content is supposed to be completely user generated.

All the defined items are stored and read from a SQLite database. The code could benefit from more comments (which is planned) but the more crucial and harder to read parts are commented. 

Known bugs in current version: 
  1) items in workouts cannot be deleted.
  2) items which are measured in time can only have one set.

Planned improvements:
  1) adding highlighting selected item
  2) adding changing order of items in workouts
  3) improving showing time in exercises measured in time
