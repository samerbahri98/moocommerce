package com.thebahrimedia.moocommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import com.thebahrimedia.moocommerce.databinding.ActivityAddItemBinding
import com.thebahrimedia.moocommerce.db.AppDb
import com.thebahrimedia.moocommerce.db.Item

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var categoryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )

        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.ddvCategory.adapter = categoryAdapter

    }

    override fun onResume() {
        super.onResume()
        binding.btnCancel.setOnClickListener(cancelListener)
        binding.btnSubmit.setOnClickListener(submitListener)
        binding.topReturnBar.setNavigationOnClickListener(cancelListener)
    }
    private val submitListener = View.OnClickListener {
        if(binding.nvPrice.text.toString().toFloat()>=0){
            Thread {
                AppDb.getInstance(this).itemCRUD().insertItem(
                    Item(
                        null,
                        binding.tvName.text.toString(),
                        binding.mltvDescription.text.toString(),
                        binding.ddvCategory.selectedItem.toString(),
                        binding.swvIsPurchased.isChecked,
                        binding.nvPrice.text.toString().toFloat()
                    )
                )
                runOnUiThread {
                    val i = Intent(this, ShoppingListActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }.start()
        }
        else{
            binding.nvPrice.error="This field is required and positive!"
        }
    }
    private val cancelListener = View.OnClickListener {
        finish()
    }
}