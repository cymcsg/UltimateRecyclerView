package com.marshalchen.ultimaterecyclerview.gridSection;


import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.R;

/**
 * An extension of SectionedRecyclerViewAdapter for simple sectioned RecyclerViews. In most cases,
 * you will not need a footer for your sections and your header will consist only of a TextView.
 * SimpleSectionedAdapter simplifies the creation of such sectioned collections where you only
 * need to provide header titles and implement the rendering of your items.
 */
public abstract class SimpleSectionedAdapter<VH extends RecyclerView.ViewHolder> extends SectionedRecyclerViewAdapter<HeaderViewHolder,
        VH, RecyclerView.ViewHolder> {

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(getLayoutResource(), parent, false);
        HeaderViewHolder holder = new HeaderViewHolder(view, getTitleTextID());
        return holder;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, int section) {
        String title = getSectionHeaderTitle(section);
        holder.render(title);
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
    }

    /**
     * Provides a layout identifier for the header. Override it to change the appearance of the
     * header view.
     *
     * @return int layout id
     */
    @LayoutRes
    protected int getLayoutResource() {
        return R.layout.slm_header;
    }

    /**
     * Provides the identifier of the TextView to render the section header title. Override it if
     * you provide a custom layout for a header.
     *
     * @return the field id
     */
    @IdRes
    protected int getTitleTextID() {
        return R.id.title_text;
    }

    /**
     * Returns the title for a given section
     *
     * @param section section index
     * @return string in return
     */
    protected abstract String getSectionHeaderTitle(int section);
}