package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var TaskList = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClick{
            override fun onItem(position: Int) {
                TaskList.removeAt(position)
                adapter.notifyDataSetChanged()
                save()

            }

        }
        load()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler) as RecyclerView
        adapter = TaskItemAdapter(TaskList, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.button).setOnClickListener{
            val Task = findViewById<EditText>(R.id.TaskField).text.toString()
            TaskList.add(Task)

            adapter.notifyItemInserted(TaskList.size-1)

            findViewById<EditText>(R.id.TaskField).setText("")
            save()
        }

    }

    fun getDataFile(): File {
        return  File(filesDir, "data.txt")
    }

    fun load(){
        try {
            TaskList =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    fun save(){
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), TaskList)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }


    }
}