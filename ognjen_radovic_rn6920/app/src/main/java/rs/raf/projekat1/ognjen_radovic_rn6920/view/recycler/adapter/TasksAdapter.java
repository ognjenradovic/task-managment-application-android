package rs.raf.projekat1.ognjen_radovic_rn6920.view.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import rs.raf.projekat1.R;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Priority;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.Task;
import rs.raf.projekat1.ognjen_radovic_rn6920.models.TaskTimeComparator;
import rs.raf.projekat1.ognjen_radovic_rn6920.view.activities.EditTaskActivity;

public class TasksAdapter extends ListAdapter<Task, TasksAdapter.ViewHolder> {

    private final Consumer<Task> taskClicked;

    //TODO Dodaj current day i current task u model i metod get i se tza to


    public TasksAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, Consumer<Task> taskClicked) {
        super(diffCallback);
        this.taskClicked = taskClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view, parent.getContext(),
                position -> {
                    Task task = getItem(position);
                    taskClicked.accept(task);
                },
                position -> {
                    Task task = getItem(position);
                    Intent intent = new Intent(parent.getContext(), EditTaskActivity.class);
                    intent.putExtra(EditTaskActivity.EXTRA_TASK_1, task);
                    parent.getContext().startActivity(intent);
                },
                position -> {
                    Task task = getItem(position);
                    Snackbar snackbar = Snackbar.make(view, "Are you sure you want to delete this item?", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = getCurrentList().get(position);
        holder.bind(task);

//        holder.setEditButtonClickListener(position -> {
//            Task editedTask = getItem(position);
//            Intent intent = new Intent(context, EditActivity.class);
//            intent.putExtra("task_id", editedTask.getId());
//            context.startActivity(intent);
//        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private ImageView editIconImageView;
        private ImageView deleteIconImageView;


        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked, Consumer<Integer> onEditClicked, Consumer<Integer> onDeleteClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
            editIconImageView = itemView.findViewById(R.id.item_edit);
            deleteIconImageView = itemView.findViewById(R.id.item_delete);
            // Set a click listener on the edit icon
            editIconImageView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onEditClicked.accept(getBindingAdapterPosition());
                }
            });
            deleteIconImageView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onDeleteClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(Task task) {
            ((TextView) itemView.findViewById(R.id.task_name)).setText(String.valueOf(task.getName()));
            Priority priority=task.getPriority();
            if (priority.equals(Priority.HIGH)) {
                itemView.setBackgroundColor(0xFFFF0000);
            } else if (priority.equals(Priority.MEDIUM)) {
                itemView.setBackgroundColor(0xFFFFFF00);
            } else {
                itemView.setBackgroundColor(0xFF00FF00);
            }

            if(task.getEndTime().isBefore(LocalTime.now())){
                itemView.setBackgroundColor(0xD3D3D3FF);
            }
        }






    }
    @Override
    public List<Task> getCurrentList() {
        List<Task> currentList = super.getCurrentList();

        List<Task> modifiableList = new ArrayList<>(currentList);

        modifiableList.sort(new TaskTimeComparator());
        return modifiableList;
    }

    public Task getItem(int position) {
        return getCurrentList().get(position);
    }
}