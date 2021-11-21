package com.thebahrimedia.moocommerce.callbacks

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.thebahrimedia.moocommerce.adapter.ShoppingItemAdapter
import com.thebahrimedia.moocommerce.db.AppDb

class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback{
    lateinit var itemAdapter: ShoppingItemAdapter;

    private final lateinit var background : ColorDrawable;
    lateinit var icon : Drawable;
    constructor(_itemAdapter: ShoppingItemAdapter) : super(0,ItemTouchHelper.LEFT shl ItemTouchHelper.RIGHT) {
        this.itemAdapter=_itemAdapter;
        this.icon = ContextCompat.getDrawable(itemAdapter.context,
            android.R.drawable.ic_menu_delete)!!;
        background = ColorDrawable(Color.rgb(255,164,182));
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position: Int =viewHolder.adapterPosition
        itemAdapter.deleteItem(position)
    }


    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        val itemView : View = viewHolder.itemView;
        val backgroundCornerOffset = 20;


        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        if (dX > 0) { // Swiping to the right
            val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
            val iconRight = itemView.left + iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background.setBounds(
                itemView.left, itemView.top,
                itemView.left + dX.toInt() + backgroundCornerOffset,
                itemView.bottom
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
            icon.setBounds(0,0,0,0)
        }
        background.draw(c)
        icon.draw(c)
    }
}