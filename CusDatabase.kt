package com.example.roomdemo2

import android.content.Context
import androidx.room.*

@Database(entities = arrayOf(Custommer::class), version = 1)
abstract class CusDatabase : RoomDatabase() {

    abstract fun callDAO(): CusDAO
}

@Entity
data class Custommer(
    @PrimaryKey(autoGenerate = true) val cusId: Int?,
    @ColumnInfo(name = "cus_name") val cusName: String?,
    @ColumnInfo(name = "cus_age") val cusAge: Int?
)
