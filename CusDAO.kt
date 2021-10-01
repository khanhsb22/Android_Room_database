package com.example.roomdemo2

import androidx.room.*

@Dao
interface CusDAO {
    @Insert
    fun insertCustommer(custommer: Custommer)

    @Query("SELECT * FROM custommer")
    fun getAllCustommer(): List<Custommer>

    @Query("UPDATE custommer SET cus_name = :cusName, cus_age = :cusAge WHERE cusId = :cusId")
    fun updateCustommer(cusName: String, cusAge: Int, cusId: Int)

    @Query("DELETE FROM custommer WHERE cusId = :cusId")
    fun deleteCustommer(cusId: Int)

    @Query("DELETE FROM custommer")
    fun deleteAllCustommer()
}