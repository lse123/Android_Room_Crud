package com.melardev.android.crud.todos.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.melardev.android.crud.R;
import com.melardev.android.crud.datasource.local.entities.Todo;
import com.melardev.android.crud.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private final ArrayList<Todo> todos;
    private final TodoRowEventListener todoRowEventListener;


    interface TodoRowEventListener {
        void onClicked(Todo todo);
    }


    TodoListAdapter(TodoRowEventListener todoRowEventListener) {
        this.todoRowEventListener = todoRowEventListener;
        this.todos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater view = LayoutInflater.from(parent.getContext());
        View itemBinding = view.inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Todo todo = todos.get(position);
        viewHolder.txtId.setText(String.valueOf(todo.getId()));
        viewHolder.txtTitle.setText(todo.getTitle());
        viewHolder.txtDescription.setText(todo.getDescription());
        viewHolder.checkboxCompleted.setChecked(todo.isCompleted());
        viewHolder.txtCreatedAt.setText(DateUtils.getFormatted(todo.getCreatedAt()));
        viewHolder.txtUpdatedAt.setText(DateUtils.getFormatted(todo.getUpdatedAt()));


        viewHolder.itemView.setOnClickListener(v -> todoRowEventListener.onClicked(todo));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setItems(List<Todo> todos) {
  /*      this.todos.clear();
        addItems(todos);
*/
        TodoDiffUtil todoDiffUtil = new TodoDiffUtil(this.todos, todos);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(todoDiffUtil);
        this.todos.clear();
        this.todos.addAll(todos);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addItems(List<Todo> todos) {
        int startPosition = this.todos.size();
        this.todos.addAll(todos);
        notifyItemRangeChanged(startPosition, todos.size());
    }

    public void addItem(Todo todo) {
        int startPosition = this.todos.size();
        todos.add(todo);
        notifyItemRangeChanged(startPosition, todos.size());
    }

    public Todo getItem(int position) {
        return todos.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCreatedAt;
        private TextView txtUpdatedAt;
        private CheckBox checkboxCompleted;
        TextView txtId, txtTitle, txtDescription;

        public ViewHolder(View view) {
            super(view);

            txtId = view.findViewById(R.id.txtId);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtDescription = view.findViewById(R.id.txtDescription);
            checkboxCompleted = view.findViewById(R.id.checkboxCompleted);
            txtCreatedAt = view.findViewById(R.id.txtCreatedAt);
            txtUpdatedAt = view.findViewById(R.id.txtUpdatedAt);

        }

    }
}
