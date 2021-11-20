package com.thebahrimedia.moocommerce.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) var itemID: Long?,
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "Description") var description: String,
    @ColumnInfo(name = "Category") var category: String,
    @ColumnInfo(name = "IsPurchased") var isPurchased: Boolean,
    @ColumnInfo(name = "Price") var Price: Float,
): Serializable