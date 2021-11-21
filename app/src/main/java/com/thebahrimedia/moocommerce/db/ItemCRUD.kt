package com.thebahrimedia.moocommerce.db

import androidx.room.*

@Dao
interface ItemCRUD {
    @Query("SELECT * FROM item")
    fun getAllItems(): List<Item>

    @Insert
    fun insertItem(item: Item) : Long

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Query("DELETE FROM item")
    fun deleteAllItems()
}