package com.melardev.android.crud.todos.list;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.melardev.android.crud.datasource.local.entities.Todo;

import java.util.List;

class TodoDiffUtil extends DiffUtil.Callback {

    private List<Todo> oldTodoList;
    private List<Todo> newTodoList;

    public TodoDiffUtil(List<Todo> oldTodoList, List<Todo> newTodoList) {
        this.oldTodoList = oldTodoList;
        this.newTodoList = newTodoList;
    }

    @Override
    public int getOldListSize() {
        return oldTodoList.size();
    }

    @Override
    public int getNewListSize() {
        return newTodoList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTodoList.get(oldItemPosition).getId().equals(newTodoList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTodoList.get(oldItemPosition).equals(newTodoList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //you can return particular field for changed item.
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
