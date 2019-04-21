package android.tomdoischer.workOutDroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerListAdapterWorkouts extends RecyclerView.Adapter<RecyclerListAdapterWorkouts.ViewHolder> {
    private ArrayList<String> workouts = new ArrayList<>();
    private Context mContext;
    private OnWorkoutListener monWorkoutListener;

    String workoutSelected;

    public RecyclerListAdapterWorkouts(ArrayList<String> workouts, Context mContext, OnWorkoutListener onWorkoutListener) {
        this.workouts = workouts;
        this.mContext = mContext;
        this.monWorkoutListener = onWorkoutListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_workout, viewGroup, false);
        ViewHolder viewHolderWorkout = new ViewHolder(view, monWorkoutListener);
        return viewHolderWorkout;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolderWorkout, int i) {
        viewHolderWorkout.workoutName.setText(workouts.get(i));

        String name = workouts.get(i);

        viewHolderWorkout.itemView.setTag(name);
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView workoutName;
        RelativeLayout parentLayoutWorkout;
        OnWorkoutListener onWorkoutListener;

        public ViewHolder(@NonNull View itemView, OnWorkoutListener onWorkoutListener) {
            super(itemView);

            workoutName = itemView.findViewById(R.id.workoutName);
            parentLayoutWorkout = itemView.findViewById(R.id.parentLayoutWorkout);
            this.onWorkoutListener = onWorkoutListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onWorkoutListener.onWorkoutClick(getAdapterPosition());
        }
    }

    public String getWorkoutSelected() {
        return workoutSelected;
    }

    public interface OnWorkoutListener {
        void onWorkoutClick(int position);
    }
}
