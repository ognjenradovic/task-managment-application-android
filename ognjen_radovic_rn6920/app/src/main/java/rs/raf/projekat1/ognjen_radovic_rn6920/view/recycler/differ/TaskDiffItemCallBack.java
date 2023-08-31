package rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.ognjen_radovic_rn6920.models.Task;

public class TaskDiffItemCallBack extends DiffUtil.ItemCallback<Task> {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getPriority().equals(newItem.getPriority()) && oldItem.getDescription().equals(newItem.getDescription());
    }
}
