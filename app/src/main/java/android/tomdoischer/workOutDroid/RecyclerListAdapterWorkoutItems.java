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

public class RecyclerListAdapterWorkoutItems extends RecyclerView.Adapter<RecyclerListAdapterWorkoutItems.ViewHolder> {

    private ArrayList<Integer> ids = new ArrayList<>();
    private ArrayList<String> exerciseNamesWI = new ArrayList<>();
    private ArrayList<Integer> numbersOfSets = new ArrayList<>();
    private ArrayList<Integer> numbersofRepsOrTime = new ArrayList<>();
    private Context mContext;

    public RecyclerListAdapterWorkoutItems(ArrayList<Integer> ids, ArrayList<String> exerciseNamesWI, ArrayList<Integer> numbersOfSets, ArrayList<Integer> numbersofRepsOrTime, Context mContext) {
        this.ids = ids;
        this.exerciseNamesWI = exerciseNamesWI;
        this.numbersOfSets = numbersOfSets;
        this.numbersofRepsOrTime = numbersofRepsOrTime;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_workout_item, viewGroup, false);
        ViewHolder viewHolderWorkoutItem = new ViewHolder(view);
        return viewHolderWorkoutItem;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolderWorkoutItem, int i) {
        viewHolderWorkoutItem.exerciseNameWI.setText(exerciseNamesWI.get(i));
        viewHolderWorkoutItem.numberOfSets.setText(String.format("%s sets", numbersOfSets.get(i).toString()));
        viewHolderWorkoutItem.numberOfRepsOrTime.setText(String.format("%s reps / s", numbersofRepsOrTime.get(i).toString()));

        int id = ids.get(i);

        viewHolderWorkoutItem.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return exerciseNamesWI.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView exerciseNameWI;
        TextView numberOfSets;
        TextView numberOfRepsOrTime;
        RelativeLayout parentLayoutWorkoutItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseNameWI = itemView.findViewById(R.id.exerciseNameWI);
            numberOfSets = itemView.findViewById(R.id.numberOfSets);
            numberOfRepsOrTime = itemView.findViewById(R.id.numberOfRepsOrTime);
            parentLayoutWorkoutItem = itemView.findViewById(R.id.parentLayoutWorkoutItem);
        }
    }
}
