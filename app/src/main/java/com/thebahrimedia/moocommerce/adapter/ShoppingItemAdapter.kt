package com.thebahrimedia.moocommerce.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thebahrimedia.moocommerce.R
import com.thebahrimedia.moocommerce.databinding.ShoppingRvItemBinding
import com.thebahrimedia.moocommerce.db.Item
import com.thebahrimedia.moocommerce.callbacks.DoubleTapToToggle



class ShoppingItemAdapter: RecyclerView.Adapter<ShoppingItemAdapter.ItemViewHolder>{
    inner class ItemViewHolder(val binding: ShoppingRvItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    val context: Context
    private var items = mutableListOf<Item>(
        Item(null,"Soup", "Campbells Chili Veg","food",false,30514f),
        Item(null,"Wheels", "Saddles","car",false,78342f),
        Item(null,"Headset", "Fresh","electronics",false,66264f),
        Item(null,"Soup", "Campbells Chili Veg","food",false,30514f),
        Item(null,"Engine", "Saddles","car",false,78342f),
        Item(null,"Think And Grow Rich", "Fresh","books",false,66264f),
    )



//    constructor(context: Context, items: List<Item>) {
//        this.context = context
//        this.items.addAll(items)
//    }

    constructor(context: Context) {
        this.context = context
//        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ShoppingRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvName.text = item.name

        val categoryUri = "@drawable/"+item.category
        val imageResouce = context.resources.getIdentifier(categoryUri,null,context.packageName)
//        val imageDrawable :Int = context.resources.getDrawable(imageResouce)
        holder.binding.ivCategory.setImageResource(imageResouce)

        holder.binding.llBackground.setOnClickListener(object : DoubleTapToToggle(){
            override fun onDoubleClick() {
                togglePurchased(holder.adapterPosition)
            }
        })
        
        holder.binding.llBackground
        if(items[holder.adapterPosition].isPurchased){
            holder.binding.llBackground.setBackgroundColor(Color.GREEN)
        }
        else{
            holder.binding.llBackground.setBackgroundColor(Color.WHITE)
        }
        Log.v("ShoppingItemAdapter",item.name);
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun togglePurchased (position: Int){
        val isPurchased = items[position].isPurchased;
        items[position].isPurchased= isPurchased.not();
        notifyItemChanged(position)
    }

    fun deleteAllItems(){
        items.removeAll(items)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }


}