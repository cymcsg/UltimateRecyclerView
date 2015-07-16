package com.marshalchen.ultimaterecyclerview.expanx.Util;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.R;
import com.marshalchen.ultimaterecyclerview.expanx.ExpandableItemData;

/**
 * Created by hesk on 16/7/15.
 * this is the enhancer class
 */
public abstract class easyTemplateParent<T extends ExpandableItemData, H extends RelativeLayout, B extends TextView> extends parent<T> {
    public ImageView image;
    public B text, count;
    public ImageView expand;


    public H relativeLayout;
    public RelativeLayout adjustmentlayout;

    public easyTemplateParent(View itemView) {
        super(itemView);
        text = (B) itemView.findViewById(R.id.exp_section_title);
        expand = (ImageView) itemView.findViewById(R.id.exp_indication_arrow);
        count = (B) itemView.findViewById(R.id.exp_section_notification_number);
        relativeLayout = (H) itemView.findViewById(R.id.exp_section_ripple_wrapper_click);   //clickable
        adjustmentlayout = (RelativeLayout) itemView.findViewById(R.id.exp_section_adjustment_layout);
        itemMargin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.item_margin);
    }

    @Override
    protected void setCountVisible(int visibility) {
        count.setVisibility(visibility);
    }

    @Override
    protected void updateCountNumber(String text) {
        count.setText(text);
    }

    @Override
    public void bindView(final T itemData, final int position, final ItemDataClickListener imageClickListener) {
        adjustmentlayout.setLayoutParams(getParamsLayout(adjustmentlayout, itemData));
        text.setText(itemData.getText());
        setHandleInitiatedViewStatus(itemData, expand, count);
        setRelativeLayoutClickable(relativeLayout, itemData, imageClickListener, position);
        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), "longclick",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public void onParentItemClick(String path) {

    }

    @Override
    public int openDegree() {
        return 90;
    }

    @Override
    public int closeDegree() {
        return 0;
    }

    /**
     * <p>Notifies the occurrence of another frame of the animation.</p>
     *
     * @param animation The animation which was repeated.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        expand.setRotation((Float) animation.getAnimatedValue());
    }


}
