package com.marshalchen.ultimaterecyclerview.Utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

/**
 * This saved state class is a Parcelable and should not extend
 * {@link android.view.View.BaseSavedState} nor {@link android.view.AbsSavedState}
 * because its super class AbsSavedState's constructor
 * {@link android.view.AbsSavedState#AbsSavedState(Parcel)} currently passes null
 * as a class loader to read its superstate from Parcelable.
 * This causes {@link android.os.BadParcelableException} when restoring saved states.
 * <p/>
 * The super class "RecyclerView" is a part of the support library,
 * and restoring its saved state requires the class loader that loaded the RecyclerView.
 * It seems that the class loader is not required when restoring from RecyclerView itself,
 * but it is required when restoring from RecyclerView's subclasses.
 */
public class SavedStateScrolling implements Parcelable {
    public static final SavedStateScrolling EMPTY_STATE = new SavedStateScrolling() {
    };

    public int prevFirstVisiblePosition;
    public int prevFirstVisibleChildHeight = -1;
    public int prevScrolledChildrenHeight;
    public int prevScrollY;
    public int scrollY;
    public  SparseIntArray childrenHeights;

    // This keeps the parent(RecyclerView)'s state
    public Parcelable superState;

    /**
     * Called by EMPTY_STATE instantiation.
     */
    public SavedStateScrolling() {
        superState = null;
    }

    /**
     * Called by onSaveInstanceState.
     */
    public SavedStateScrolling(Parcelable superState) {
        this.superState = superState != EMPTY_STATE ? superState : null;
    }

    /**
     * Called by CREATOR.
     */
    public SavedStateScrolling(Parcel in) {
        // Parcel 'in' has its parent(RecyclerView)'s saved state.
        // To restore it, class loader that loaded RecyclerView is required.
        Parcelable superState = in.readParcelable(RecyclerView.class.getClassLoader());
        this.superState = superState != null ? superState : EMPTY_STATE;

        prevFirstVisiblePosition = in.readInt();
        prevFirstVisibleChildHeight = in.readInt();
        prevScrolledChildrenHeight = in.readInt();
        prevScrollY = in.readInt();
        scrollY = in.readInt();
        childrenHeights = new SparseIntArray();
        final int numOfChildren = in.readInt();
        if (0 < numOfChildren) {
            for (int i = 0; i < numOfChildren; i++) {
                final int key = in.readInt();
                final int value = in.readInt();
                childrenHeights.put(key, value);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(superState, flags);

        out.writeInt(prevFirstVisiblePosition);
        out.writeInt(prevFirstVisibleChildHeight);
        out.writeInt(prevScrolledChildrenHeight);
        out.writeInt(prevScrollY);
        out.writeInt(scrollY);
        final int numOfChildren = childrenHeights == null ? 0 : childrenHeights.size();
        out.writeInt(numOfChildren);
        if (0 < numOfChildren) {
            for (int i = 0; i < numOfChildren; i++) {
                out.writeInt(childrenHeights.keyAt(i));
                out.writeInt(childrenHeights.valueAt(i));
            }
        }
    }

    public Parcelable getSuperState() {
        return superState;
    }

    public static final Parcelable.Creator<SavedStateScrolling> CREATOR
            = new Parcelable.Creator<SavedStateScrolling>() {
        @Override
        public SavedStateScrolling createFromParcel(Parcel in) {
            return new SavedStateScrolling(in);
        }

        @Override
        public SavedStateScrolling[] newArray(int size) {
            return new SavedStateScrolling[size];
        }
    };
}
