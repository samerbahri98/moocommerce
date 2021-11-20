package com.thebahrimedia.moocommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.thebahrimedia.moocommerce.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCancel.setOnClickListener(cancelListener)


    }

    private val cancelListener = View.OnClickListener {
        val i = Intent(this,ShoppingListActivity::class.java)
        startActivity(i)
        finish()
    }
}