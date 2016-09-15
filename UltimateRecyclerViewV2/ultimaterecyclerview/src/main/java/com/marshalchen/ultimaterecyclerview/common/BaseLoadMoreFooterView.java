package com.marshalchen.ultimaterecyclerview.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.R;


/**
 * @author YanLu
 * @since 16/4/10
 */
public abstract class BaseLoadMoreFooterView extends FrameLayout {
    private static final String TAG = "LoadMoreFooterViewHolder";

    public View mProgressView;
    public TextView mTvContent;

    //when loading more data, display content
    private String loadingText;
    //when no more data, display content
    private String noMoreText;

    public abstract int getLoadMoreLayoutResource();

    public BaseLoadMoreFooterView(Context context) {
        super(context);
        initViews(context, null);
    }

    public BaseLoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public BaseLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs);
    }

    protected void initViews(Context context, AttributeSet attrs) {
        View header = LayoutInflater.from(context).inflate(getLoadMoreLayoutResource(), this);
        final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lp);
        mTvContent = (TextView) header.findViewById(R.id.tv_content);
        mProgressView = header.findViewById(R.id.progress_view);

        loadingText = context.getString(R.string.app_loading_more);
        noMoreText = context.getString(R.string.app_no_more_data);
    }

    protected void updateLoadMoreView(String content, boolean isVisible){
        //for AVLoadingIndicatorView
        mProgressView.setVisibility(!isVisible ? VISIBLE : GONE);
        mProgressView.setVisibility( isVisible ? VISIBLE : GONE);
        mTvContent.setText(content);
    }

    public void showLoading(){
        updateLoadMoreView(loadingText, true);
    }

    public void showNoMoreData(){
        updateLoadMoreView(noMoreText, false);
    }

    public String getLoadingText() {
        return loadingText;
    }

    public void setLoadingText(@StringRes int rid) {
        this.loadingText = getContext().getString(rid);
    }
    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    public String getNoMoreText() {
        return noMoreText;
    }

    public void setNoMoreText(@StringRes int rid) {
        this.noMoreText = getContext().getString(rid);
    }

    public void setNoMoreText(String noMoreText) {
        this.noMoreText = noMoreText;
    }
}
