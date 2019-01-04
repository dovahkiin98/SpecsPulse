package specspulse.app.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import specspulse.app.adapters.DevicesAdapter
import specspulse.app.R
import specspulse.app.utils.toDp

class AutoFitRecyclerView(context: Context, attrs: AttributeSet? = null) : RecyclerView(context, attrs) {
    private var columnWidth = -1
    private val manager = GridLayoutManager(context, GridLayoutManager.VERTICAL)
    var collapsed = true
        set(value) {
            println("C")
            if (!value) {
                val width = resources.displayMetrics.widthPixels
                val spanCount = Math.max(1, width / columnWidth)
                manager.spanCount = spanCount

                (adapter as DevicesAdapter).viewType = DevicesAdapter.ViewType.TILE

                updatePaddingRelative(start = 4.toDp(resources).toInt(), end = 4.toDp(resources).toInt())
            } else {
                manager.spanCount = 1

                (adapter as DevicesAdapter).viewType = DevicesAdapter.ViewType.CARD
                updatePaddingRelative(start = 0, end = 0)
            }
            field = value
        }

    override fun getLayoutManager() = manager

    init {
        if (attrs != null) context.obtainStyledAttributes(attrs, intArrayOf(R.attr.columnWidth)).run {
            columnWidth = getDimensionPixelSize(0, -1)
            recycle()
        }

        layoutManager = manager
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
//        collapsed = collapsed
    }
}