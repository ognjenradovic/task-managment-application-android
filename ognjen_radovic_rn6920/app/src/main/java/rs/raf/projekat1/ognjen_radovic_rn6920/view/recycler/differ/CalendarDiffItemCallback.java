package rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat1.ognjen_radovic_rn6920.models.Day;

public class CalendarDiffItemCallback extends DiffUtil.ItemCallback<Day> {
    @Override
    public boolean areItemsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return oldItem.getDate() == newItem.getDate();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return oldItem.getTasks().equals(newItem.getTasks());
    }
}
