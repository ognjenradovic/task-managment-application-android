package rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import java.io.Serializable;
import java.util.function.Consumer;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Day;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Priority;

public class CalendarAdapter extends ListAdapter<Day, CalendarAdapter.ViewHolder> implements Serializable {

    private final Consumer<Day> onDayClicked;


    //TODO Dodaj current day i current task u model i metod get i se tza to


    public CalendarAdapter(@NonNull DiffUtil.ItemCallback<Day> diffCallback, Consumer<Day> onDayClicked) {
        super(diffCallback);
        this.onDayClicked = onDayClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Day Day = getItem(position);
            onDayClicked.accept(Day);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = getItem(position);
        boolean isFirstRow = position < 7; // First row contains only days of the month
        holder.bind(day, isFirstRow);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(Day day, boolean isFirstRow) {
            if(day.isEmptyDay()){
                ((TextView) itemView.findViewById(R.id.day_text)).setText("");
                itemView.setBackgroundColor(0x00000000);
            }
            else{
                ((TextView) itemView.findViewById(R.id.day_text)).setText(String.valueOf(day.getDate().getDayOfMonth()));
                Priority priority=day.highestPriority();
                if (priority.equals(Priority.HIGH)) {
                    itemView.setBackgroundColor(0xFFFF0000);
                } else if (priority.equals(Priority.MEDIUM)) {
                    itemView.setBackgroundColor(0xFFFFFF00);
                } else {
                    itemView.setBackgroundColor(0xFF00FF00);
                }
            }
        }






    }
    public Day getItem(int position) {
        return getCurrentList().get(position);
    }
}
