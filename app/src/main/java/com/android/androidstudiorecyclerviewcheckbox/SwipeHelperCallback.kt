package com.android.androidstudiorecyclerviewcheckbox

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class SwipeHelperCallback(private val recyclerViewAdapter: RecyclerViewAdapter) :
    ItemTouchHelper.Callback() {


    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    private var clamp = 0f

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        return makeMovementFlags(UP or DOWN, LEFT or RIGHT)
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        val fromPos: Int = viewHolder.adapterPosition
        val toPos: Int = target.adapterPosition
        recyclerViewAdapter.swapData(fromPos, toPos)
        return true
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }


    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f
        previousPosition = viewHolder.adapterPosition
        getDefaultUIUtil().clearView(getView(viewHolder))
    }


    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
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
        if (actionState == ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val newX = clampViewPositionHorizontal(dX, isClamped, isCurrentlyActive)

            if (newX == -clamp) {
                getView(viewHolder).animate().translationX(-clamp).setDuration(100L).start()
                return
            }

            currentDx = newX
            getDefaultUIUtil().onDraw(
                c,
                recyclerView,
                view,
                newX,
                dY,
                actionState,
                isCurrentlyActive
            )
        }
    }


    override fun getSwipeEscapeVelocity(defaultValue: Float): Float = defaultValue * 10
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {

        setTag(viewHolder, currentDx <= -clamp)
        return 2f
    }


    private fun getView(viewHolder: RecyclerView.ViewHolder): View =
        viewHolder.itemView.findViewById(R.id.swipe_view)


    private fun clampViewPositionHorizontal(
        dX: Float,
        isClamped: Boolean,
        isCurrentlyActive: Boolean
    ): Float {

        val max = 0f

        val newX = if (isClamped) {

            if (isCurrentlyActive)

                if (dX < 0) dX / 3 - clamp
                else dX - clamp
            else -clamp
        } else dX / 2

        return min(newX, max)
    }

    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        viewHolder.itemView.tag = isClamped
    }

    private fun getTag(viewHolder: RecyclerView.ViewHolder): Boolean =
        viewHolder.itemView.tag as? Boolean ?: false


    fun setClamp(clamp: Float) {
        this.clamp = clamp
    }

    fun removePreviousClamp(recyclerView: RecyclerView) {

        if (currentPosition == previousPosition) return


        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).animate().x(0f).setDuration(100L).start()
            setTag(viewHolder, false)
            previousPosition = null
        }

    }

}