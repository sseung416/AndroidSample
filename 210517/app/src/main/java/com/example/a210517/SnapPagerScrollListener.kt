package com.example.a210517

import android.view.View
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView


class SnapPagerScrollListener(
    private val snapHelper: PagerSnapHelper,
    private val type: Int,
    private val notifyOnInit: Boolean,
    private val listener: OnChangeListener
) : RecyclerView.OnScrollListener() {

    companion object {
        const val ON_SCROLL = 0;
        const val ON_SETTLED = 1;
    }

    private var snapPosition: Int = 0

    interface OnChangeListener {
        fun onSnapped(position: Int)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if((type == ON_SCROLL) || !hasItemPosition()) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if(type == ON_SETTLED && newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    private fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager
        if(layoutManager == null) {
            return RecyclerView.NO_POSITION
        }

        val snapView: View? = snapHelper.findSnapView(layoutManager)
        if (snapView == null) {
            return RecyclerView.NO_POSITION
        }

        return layoutManager.getPosition(snapView)
    }

    private fun notifyListenerIfNeeded(newSnapPosition: Int) {
        if(snapPosition != newSnapPosition) {
            if(notifyOnInit && !hasItemPosition()) {
                listener.onSnapped(newSnapPosition)
            } else if(hasItemPosition()) {
                listener.onSnapped(newSnapPosition)
            }

            snapPosition = newSnapPosition
        }
    }

    private fun hasItemPosition(): Boolean {
        return snapPosition != RecyclerView.NO_POSITION
    }

}