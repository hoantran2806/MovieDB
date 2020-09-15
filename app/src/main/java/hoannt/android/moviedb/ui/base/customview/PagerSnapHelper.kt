package hoannt.android.moviedb.ui.base.customview

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class PagerSnapHelper : LinearSnapHelper() {
    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {

        var out = IntArray(2)

        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
        } else {
            out[1] = 0
        }
        return out
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper): Int {
        return helper.getDecoratedStart(targetView) - helper.startAfterPadding
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {

        val centerView = findSnapView(layoutManager!!) ?: return RecyclerView.NO_POSITION

        val position = layoutManager.getPosition(centerView)
        var targetPosition = -1
        if (layoutManager.canScrollHorizontally()) {
            if (velocityX < 0) {
                targetPosition = position - 1
            } else {
                targetPosition = position + 1
            }
        }
        if (layoutManager.canScrollVertically()) {
            if (velocityY < 0) {
                targetPosition - 1
            } else {
                targetPosition + 1
            }
        }

        val firstItem = 0
        val lastItem = layoutManager.itemCount - 1
        targetPosition = Math.min(lastItem, Math.max(firstItem, targetPosition))

        return targetPosition
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager is LinearLayoutManager) {
            if (layoutManager.canScrollHorizontally()) {
                return getStartView(layoutManager, getHorizontalHelper(layoutManager))
            } else {
                return getStartView(layoutManager, getVerticalHelper(layoutManager))
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun getStartView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper
    ): View? {
        if (layoutManager is LinearLayoutManager) {
            var firstChild: Int = layoutManager.findFirstVisibleItemPosition()

            var isLastItem: Boolean =
                layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1

            if (firstChild == RecyclerView.NO_POSITION || isLastItem) {
                return null
            }

            var child: View = layoutManager.findViewByPosition(firstChild)!!

            return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                && helper.getDecoratedEnd(child) > 0
            ) {
                child
            } else {
                if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                    null
                } else {
                    layoutManager.findViewByPosition(firstChild + 1)
                }
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return this.mHorizontalHelper!!
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper!!
    }
}