package com.thebahrimedia.moocommerce.adapter

import android.content.Context
import android.content.Intent

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.thebahrimedia.moocommerce.AddItemActivity
import com.thebahrimedia.moocommerce.ItemDetailsActivity
import com.thebahrimedia.moocommerce.R
import com.thebahrimedia.moocommerce.ShoppingListActivity
import com.thebahrimedia.moocommerce.databinding.ShoppingRvItemBinding
import com.thebahrimedia.moocommerce.db.Item
import com.thebahrimedia.moocommerce.callbacks.DoubleTapToToggle
import com.thebahrimedia.moocommerce.db.AppDb


class ShoppingItemAdapter: RecyclerView.Adapter<ShoppingItemAdapter.ItemViewHolder>{
    inner class ItemViewHolder(val binding: ShoppingRvItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    val context: Context
//    private var items = mutableListOf<Item>(
//        Item(null,"Soup", "Campbells Chili Veg","food",false,30514f),
//        Item(null,"Wheels", "Saddles","car",false,78342f),
//        Item(null,"Headset", "Fresh","electronics",false,66264f),
//        Item(null,"Soup", "Campbells Chili Veg","food",false,30514f),
//        Item(null,"Engine", "Saddles","car",false,78342f),
//        Item(null,"Think And Grow Rich", "Fresh","books",false,66264f),
//    )

    private var items = mutableListOf<Item>()



    constructor(context: Context,items: List<Item>) {
        this.context = context
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ShoppingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name
        holder.binding.tvPrice.text = item.Price.toString() + " HUF"

        val categoryUri = "@drawable/"+item.category
        val imageResource = context.resources.getIdentifier(categoryUri,null,context.packageName)
        holder.binding.ivCategory.setImageResource(imageResource)
        holder.binding.llBackground.setOnClickListener(object : DoubleTapToToggle(){
            override fun onDoubleClick() {
                togglePurchased(holder.adapterPosition)

            }

            override fun onSingleClick() {
                val i = Intent(context, ItemDetailsActivity::class.java)
                i.putExtra("itemId",items[holder.adapterPosition])
                i.putExtra("itemPosition",holder.adapterPosition)

                context.startActivity(i)
            }
        })


        if(items[holder.adapterPosition].isPurchased){
            holder.binding.llBackground.setBackgroundColor(R.color.teal_200)
        }
        else{
            holder.binding.llBackground.setBackgroundColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun togglePurchased (position: Int){
        Thread{
            val oldItem = items[position]
            val isPurchased = oldItem.isPurchased.not();
            val newItem = oldItem
            oldItem.isPurchased=isPurchased
            AppDb.getInstance(context).itemCRUD().updateItem(newItem)
            (context as ShoppingListActivity).runOnUiThread{
                oldItem.isPurchased=isPurchased
                notifyItemChanged(position)

            }
        }.start()
    }

    fun deleteAllItems(){
        items.removeAll(items)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        Thread{
            val item = items[position]
            AppDb.getInstance(context).itemCRUD().deleteItem(item)

            (context as ShoppingListActivity).runOnUiThread{
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }


}