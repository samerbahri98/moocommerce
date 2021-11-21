package com.thebahrimedia.moocommerce

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.thebahrimedia.moocommerce.adapter.ShoppingItemAdapter
import com.thebahrimedia.moocommerce.databinding.ActivityShoppingListBinding
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.thebahrimedia.moocommerce.callbacks.SwipeToDeleteCallback
import com.thebahrimedia.moocommerce.db.AppDb
import com.thebahrimedia.moocommerce.db.Item
import java.text.FieldPosition


class ShoppingListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingListBinding
    lateinit var itemAdapter: ShoppingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topMenu = findViewById<MaterialToolbar>(R.id.topAppBar)
        topMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.btnAddItem -> {
                    val i = Intent(this,AddItemActivity::class.java)
                    startActivity(i)
                    true
                }
                R.id.btnDeleteAll -> {
                    deleteAllItems()
                    true
                }
                else -> false
            }



        }
        Thread{
            var items = AppDb.getInstance(this).itemCRUD().getAllItems()
            runOnUiThread{
            setUpRecyclerView(binding.rvItemList,this,items)

            }
        }.start()
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView, context: Context, items:List<Item>) {
        itemAdapter = ShoppingItemAdapter(context, items)
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(itemAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
    }

    fun togglePurchased(position: Int){
        itemAdapter.togglePurchased(position)
    }

    private fun deleteAllItems(){
        Thread{
            AppDb.getInstance(this).itemCRUD().deleteAllItems()

            runOnUiThread{
                itemAdapter.deleteAllItems()
            }
        }.start()

    }




}