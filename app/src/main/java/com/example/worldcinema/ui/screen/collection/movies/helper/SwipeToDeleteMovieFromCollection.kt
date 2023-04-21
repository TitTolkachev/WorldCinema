package com.example.worldcinema.ui.screen.collection.movies.helper

import android.graphics.Canvas
import android.graphics.Rect
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.worldcinema.R

interface SwipeListener {
    fun deleteMovie(position: Int)
}

class SimpleItemTouchHelperCollectionCallback(private val listener: SwipeListener) :
    ItemTouchHelper.Callback() {

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val trashIcon =
            AppCompatResources.getDrawable(viewHolder.itemView.context, R.drawable.trash_icon)

        c.clipRect(0f, viewHolder.itemView.top.toFloat(), dX, viewHolder.itemView.bottom.toFloat())

        if (trashIcon != null) {
            val intrinsicHeight = trashIcon.intrinsicHeight
            val xMarkTop =
                viewHolder.itemView.top + ((viewHolder.itemView.bottom - viewHolder.itemView.top) - intrinsicHeight) / 2
            val xMarkBottom = xMarkTop + intrinsicHeight

            trashIcon.bounds = Rect(
                viewHolder.itemView.left + 75,
                xMarkTop,
                viewHolder.itemView.left + trashIcon.intrinsicWidth + 75,
                xMarkBottom
            )
            trashIcon.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.deleteMovie(viewHolder.absoluteAdapterPosition)
    }
}