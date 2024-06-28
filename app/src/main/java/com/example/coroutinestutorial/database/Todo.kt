package com.example.coroutinestutorial.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    var item:String?
) {
    //@Primarykey(autoGenerate=true)
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null


}
