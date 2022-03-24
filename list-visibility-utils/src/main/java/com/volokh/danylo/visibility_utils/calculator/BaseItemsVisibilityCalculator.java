package com.volokh.danylo.visibility_utils.calculator;

package com.camerasideas.collagemaker.video.calculator;

import android.widget.AbsListView;

import com.camerasideas.collagemaker.BuildConfig;
import com.camerasideas.collagemaker.video.scroll_utils.ItemsPositionGetter;
import com.camerasideas.collagemaker.video.scroll_utils.ScrollDirectionDetector;
import com.camerasideas.collagemaker.video.utils.Logger;


/**
 * This class encapsulates some basic logic of Visibility calculator.
 * In onScroll event it calculates Scroll direction using {@link ScrollDirectionDetector}
 * and then calls appropriate methods
 */
public abstract class BaseItemsVisibilityCalculator implements ListItemsVisibilityCalculator, ScrollDirectionDetector.OnDetectScrollListener{

    private static final boolean SHOW_LOGS = BuildConfig.DEBUG;
    private static final String TAG = BaseItemsVisibilityCalculator.class.getSimpleName();
    private final ScrollDirectionDetector mScrollDirectionDetector = new ScrollDirectionDetector(this);

    @Override
    public void onScroll(ItemsPositionGetter itemsPositionGetter, int firstVisibleItem, int visibleItemCount, int scrollState/*TODO: add current item here. start tracking from it*/) {
        if (SHOW_LOGS) Logger.v(TAG, ">> onScroll");

        if (SHOW_LOGS)
            Logger.v(TAG, "onScroll, firstVisibleItem " + firstVisibleItem + ", visibleItemCount " + visibleItemCount + ", scrollState " + scrollStateStr(scrollState));
        mScrollDirectionDetector.onDetectedListScroll(itemsPositionGetter, firstVisibleItem);

        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                onStateTouchScroll(itemsPositionGetter);//统一在TouchScroll中判断焦点条目
                break;
        }
    }

    protected abstract void onStateFling(ItemsPositionGetter itemsPositionGetter);
    protected abstract void onStateTouchScroll(ItemsPositionGetter itemsPositionGetter);

    private String scrollStateStr(int scrollState){
        switch (scrollState){
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                return "SCROLL_STATE_FLING";
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                return "SCROLL_STATE_IDLE";
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                return "SCROLL_STATE_TOUCH_SCROLL";
            default:
                throw new RuntimeException("wrong data, scrollState " + scrollState);
        }
    }

}

