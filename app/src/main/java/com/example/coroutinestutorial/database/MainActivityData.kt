package com.example.coroutinestutorial.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityData:ViewModel() {

    private val _data=MutableLiveData<List <Todo> >()

    val data:LiveData<List<Todo> > = _data

    fun setData(data:List<Todo>){
        _data.value=data
    }

}