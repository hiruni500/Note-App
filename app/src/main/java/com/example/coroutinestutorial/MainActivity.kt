package com.example.coroutinestutorial

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinestutorial.database.MainActivityData
import com.example.coroutinestutorial.database.Todo
import com.example.coroutinestutorial.database.TodoDatabase
import com.example.coroutinestutorial.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var adapter:TodoAdapter
    private lateinit var viewModel:MainActivityData

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView:RecyclerView=findViewById(R.id.rvTodoList)
        val repository = TodoRepository(TodoDatabase.getInstance(this))

        viewModel = ViewModelProvider(this)[MainActivityData::class.java]

        viewModel.data.observe(this){
            adapter = TodoAdapter(it,repository, viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllTodoItems()
            runOnUiThread {
                viewModel.setData(data)
            }
        }

        val addItem: Button = findViewById(R.id.btnAddItem)

            addItem.setOnClickListener {
            displayAlert(repository)
        }




    }

    private fun displayAlert(repository: TodoRepository){
        val builder=AlertDialog.Builder(this)

        builder.setTitle("Add Note :")
        builder.setMessage("Enter the description below :")

        val input= EditText(this)
        input.inputType=InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Ok"){dialog, which->
            val item=input.text.toString()
            CoroutineScope(Dispatchers.IO).launch{
                repository.insert(Todo(item))
                val data= repository.getAllTodoItems()
                runOnUiThread{
                    viewModel.setData(data)
                }
            }

        }

        builder.setNegativeButton("Cancel"){dialog, which->
            dialog.cancel()
        }

        //create and show the alert dialog
        val alertDialog=builder.create()
        alertDialog.show()


    }



}