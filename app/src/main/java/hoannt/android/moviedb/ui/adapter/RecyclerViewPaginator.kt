package hoannt.android.moviedb.ui.adapter

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewPaginator(private val recyclerView: RecyclerView) :
    RecyclerView.OnScrollListener() {
    private val TAG = "RecyclerPagi_MinhLam"
    private val thresHold = 2
    private var endWithAuto = false

    private var layoutManager: RecyclerView.LayoutManager

    abstract val isLastPage: Boolean

    init {
        recyclerView.addOnScrollListener(this)
        this.layoutManager = recyclerView.layoutManager!!

        if ((recyclerView.adapter as MovieListAdapter).getList().isNullOrEmpty()) {

            loadFirst()
        }

    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val visibleItemCount = layoutManager.childCount
            Log.i(TAG, "onScrollStateChanged: visibleItemCount = $visibleItemCount")
            val totalItemCount = layoutManager.itemCount
            Log.i(TAG, "onScrollStateChanged: totalItemCount = $totalItemCount")

            var firstVisibleItemPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//                Log.i(TAG, "onScrollStateChanged  layoutManager is LinearLayoutManager: firstVisibleItemPosition = $firstVisibleItemPosition")
//                Log.i(TAG, "onScrollStateChanged: layoutManager is LinearLayoutManager: findFirstVisibleItemPosition = ${(layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()}")
//                Log.i(TAG, "onScrollStateChanged: layoutManager is LinearLayoutManager: findFirstCompletelyVisibleItemPosition = ${(layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()}")
//                Log.i(TAG, "onScrollStateChanged: layoutManager is LinearLayoutManager: findFirstVisibleItemPosition = ${(layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()}")

            } else if (layoutManager is GridLayoutManager) {
                firstVisibleItemPosition =
                    (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
//                Log.i(TAG, "onScrollStateChanged  layoutManager is GridLayoutManager: firstVisibleItemPosition = $firstVisibleItemPosition")
            }

            if (isLastPage) return

            if (visibleItemCount + firstVisibleItemPosition + thresHold >= totalItemCount) {
                var tempTotalCount = visibleItemCount + firstVisibleItemPosition + thresHold
                Log.i(
                    TAG,
                    "onScrollStateChanged: tempTotalCount = $tempTotalCount && totalItemCount = $totalItemCount "
                )
                if (!endWithAuto) {
                    Log.i(TAG, "onScrollStateChanged: if (!endWithAuto) endWithAuto = $endWithAuto")
                    endWithAuto = true
                    Log.i(TAG, "onScrollStateChanged: set endWithAuto value = $endWithAuto")
                    loadMore()
                    Log.i(
                        TAG,
                        "onScrollStateChanged: call loadMore"
                    )
                } else {
                    endWithAuto = false
                }
            }
        }
    }

    abstract fun loadFirst()
    abstract fun loadMore()
}