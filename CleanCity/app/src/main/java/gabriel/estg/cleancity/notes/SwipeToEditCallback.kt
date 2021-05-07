package gabriel.estg.cleancity.notes

import android.R
import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

abstract class SwipeToEditCallback(context: Context, dragDir: Int, swipeDir: Int) :
    ItemTouchHelper.SimpleCallback(dragDir, swipeDir) {

    val backgroundColor = ContextCompat.getColor(context, R.color.holo_green_light)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
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
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addBackgroundColor(backgroundColor)
            .addActionIcon(gabriel.estg.cleancity.R.drawable.ic_action_edit)
            .create()
            .decorate()
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder, dX, dY, actionState, isCurrentlyActive
        )
    }
}