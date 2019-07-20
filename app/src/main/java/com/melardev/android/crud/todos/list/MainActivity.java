package com.melardev.android.crud.todos.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.melardev.android.crud.R;
import com.melardev.android.crud.datasource.local.TodoRepository;
import com.melardev.android.crud.datasource.local.entities.Todo;
import com.melardev.android.crud.todos.base.BaseActivity;
import com.melardev.android.crud.todos.show.TodoDetailsActivity;
import com.melardev.android.crud.todos.write.TodoCreateEditActivity;

public class MainActivity extends BaseActivity
        implements TodoListAdapter.TodoRowEventListener {

    private RecyclerView rvTodos;
    private TodoRepository todoRepository;
    private TodoListAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoRepository = TodoRepository.getInstance(getApplicationContext());

        rvTodos = findViewById(R.id.rvTodos);
        rvTodos.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        setupUi();
    }

    private void setupUi() {
        displayLoader();
        todoRepository.getAll().observe(this, todos -> {
            hideLoader();
            if (todos.size() > 0) {
                if (todoListAdapter == null) {
                    todoListAdapter = new TodoListAdapter(MainActivity.this);
                    todoListAdapter.setItems(todos);
                    rvTodos.setAdapter(todoListAdapter);
                }

                todoListAdapter.setItems(todos);
            }
        });
    }

    public void createTodo(View view) {
        Intent intent = new Intent(this, TodoCreateEditActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClicked(Todo todo) {
        Intent intent = new Intent(this, TodoDetailsActivity.class);
        intent.putExtra("TODO_ID", todo.getId());
        startActivity(intent);
    }

}
