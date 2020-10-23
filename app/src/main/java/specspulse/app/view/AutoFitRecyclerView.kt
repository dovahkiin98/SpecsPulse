package specspulse.app.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import specspulse.app.R
import specspulse.app.ui.list.DevicesAdapter
import specspulse.app.utils.toDp
import java.lang.Integer.max

class AutoFitRecyclerView(context: Context, attrs: AttributeSet? = null) : RecyclerView(context, attrs) {
    private var columnWidth = -1

    private val manager = GridLayoutManager(context, GridLayoutManager.VERTICAL)

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

        val width = resources.displayMetrics.widthPixels
        val spanCount = max(1, width / columnWidth)
        manager.spanCount = spanCount
    }
}