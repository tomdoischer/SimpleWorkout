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

public class RecyclerListAdapterExercises extends RecyclerView.Adapter<RecyclerListAdapterExercises.ViewHolder> {

    private ArrayList<String> exerciseNames = new ArrayList<>();
    private ArrayList<String> exerciseTypes = new ArrayList<>();
    private Context mContext;
    private OnExerciseListener monExerciseListener;

    String exerciseSelected;

    public RecyclerListAdapterExercises(ArrayList<String> exerciseNames, ArrayList<String> exerciseTypes, Context mContext, OnExerciseListener onExerciseListener) {
        this.exerciseNames = exerciseNames;
        this.exerciseTypes = exerciseTypes;
        this.mContext = mContext;
        this.monExerciseListener = onExerciseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_exercise_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, monExerciseListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.exerciseName.setText(exerciseNames.get(i));
        viewHolder.exerciseType.setText(exerciseTypes.get(i));

        String name = exerciseNames.get(i);

        viewHolder.itemView.setTag(name);
    }

    @Override
    public int getItemCount() {
        return exerciseNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView exerciseName;
        TextView exerciseType;
        RelativeLayout parentLayoutExercise;
        OnExerciseListener onExerciseListener;

        public ViewHolder(@NonNull View itemView, OnExerciseListener onExerciseListener) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseName);
            exerciseType = itemView.findViewById(R.id.exerciseType);
            parentLayoutExercise = itemView.findViewById(R.id.parentLayoutExercise);
            this.onExerciseListener = onExerciseListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onExerciseListener.onExerciseClick(getAdapterPosition());
        }
    }

    public String getExerciseSelected() {
        return exerciseSelected;
    }


    public interface OnExerciseListener {
        void onExerciseClick(int position);
    }
}
