package com.immanuela.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_Add;
    EditText et_Tasks;
    RecyclerView rv_Tasks;
    TasksAdapter taskAdapter;
    ArrayList<String> tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Add = findViewById(R.id.btn_add);
        et_Tasks= findViewById(R.id.et_tasks);
        rv_Tasks = findViewById(R.id.rv_tasks);

        loadTasks();
        tasks.add("Complete all tasks!");
        tasks.add("Create tasks!");

       TasksAdapter.OnLongClickListener onLongClickListener = new TasksAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                //Delete the task from list
                tasks.remove(position);
                //Notify the adapter
                taskAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
                saveTasks();
            }
        };

        taskAdapter =  new TasksAdapter(tasks, onLongClickListener);
        rv_Tasks.setAdapter(taskAdapter);
        rv_Tasks.setLayoutManager(new LinearLayoutManager(this));

        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = et_Tasks.getText().toString();
                //Add item to model
                tasks.add(todoItem);
                //Notify the adapter that and item is inserted
                taskAdapter.notifyItemInserted(tasks.size()-1);
                et_Tasks.setText("");
                Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
                saveTasks();
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(), "dir.txt");
    }
    //Read from file line by line

    private static final String TAG = "Main Activity" ;
    private void loadTasks(){
    {
        try {
            tasks = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e){
            Log.e(TAG,"Error Reading Tasks",e);
            tasks = new ArrayList<>();
        }
    }
    //Write to file
}
    private void saveTasks(){
        try {
            FileUtils.writeLines(getDataFile(), tasks);
        } catch (IOException e) {
            Log.e(TAG,"Error Writing Tasks",e);
        }
    }
}
