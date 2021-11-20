package com.thebahrimedia.moocommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.thebahrimedia.moocommerce.adapter.ShoppingItemAdapter
import com.thebahrimedia.moocommerce.databinding.ActivityShoppingListBinding
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.thebahrimedia.moocommerce.callbacks.SwipeToDeleteCallback


class ShoppingListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingListBinding
    lateinit var itemAdapter: ShoppingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemAdapter = ShoppingItemAdapter(this)
        binding.rvItemList.adapter = itemAdapter
        binding.rvItemList.layoutManager= LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(itemAdapter))
        itemTouchHelper.attachToRecyclerView(binding.rvItemList)

        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rvItemList.addItemDecoration(itemDecoration)
        val topMenu = findViewById<MaterialToolbar>(R.id.topAppBar)
        topMenu.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.btnAddItem -> {
                    val i = Intent(this,AddItemActivity::class.java)
                    startActivity(i)
                    finish()
                    true
                }
                R.id.btnDeleteAll -> {
                    itemAdapter.deleteAllItems()
                    true
                }
                else -> false
            }
        }

    }

//    private fun setUpRecyclerView(recyclerView:RecyclerView) {
//        recyclerView.adapter = itemAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(itemAdapter))
//        itemTouchHelper.attachToRecyclerView(recyclerView)
//    }





}