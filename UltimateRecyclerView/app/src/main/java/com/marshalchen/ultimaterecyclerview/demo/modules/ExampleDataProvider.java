/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.marshalchen.ultimaterecyclerview.demo.modules;


import com.marshalchen.ultimaterecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.LinkedList;
import java.util.List;

public class ExampleDataProvider extends AbstractDataProvider {
    private List<ConcreteData> mData;
    private ConcreteData mLastRemovedData;
    private int mLastRemovedPosition = -1;

    public ExampleDataProvider() {
        final String atoz = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        mData = new LinkedList<>();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < atoz.length(); j++) {
                final long id = mData.size();
                final int viewType = 0;
                final String text = Character.toString(atoz.charAt(j));
                final int swipeReaction = RecyclerViewSwipeManager.REACTION_CAN_SWIPE_LEFT | RecyclerViewSwipeManager.REACTION_CAN_SWIPE_RIGHT;
                mData.add(new ConcreteData(id, viewType, text, swipeReaction));
            }
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Data getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mData.get(index);
    }

    @Override
    public int undoLastRemoval() {
        if (mLastRemovedData != null) {
            int insertedPosition;
            if (mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = mData.size();
            }

            mData.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        final ConcreteData item = mData.remove(fromPosition);

        mData.add(toPosition, item);
        mLastRemovedPosition = -1;
    }

    @Override
    public void removeItem(int position) {
        //noinspection UnnecessaryLocalVariable
        final ConcreteData removedItem = mData.remove(position);

        mLastRemovedData = removedItem;
        mLastRemovedPosition = position;
    }

    public static final class ConcreteData extends Data {

        private final long mId;
        private final String mText;
        private final int mViewType;
        private final int mSwipeReaction;
        private boolean mPinnedToSwipeLeft;

        ConcreteData(long id, int viewType, String text, int swipeReaction) {
            mId = id;
            mViewType = viewType;
            mText = makeText(id, text, swipeReaction);
            mSwipeReaction = swipeReaction;
        }

        private static String makeText(long id, String text, int swipeReaction) {
            final StringBuilder sb = new StringBuilder();

            sb.append(id);
            sb.append(" - ");
            sb.append(text);

            return sb.toString();
        }

        @Override
        public boolean isSectionHeader() {
            return false;
        }

        @Override
        public int getViewType() {
            return mViewType;
        }

        @Override
        public long getId() {
            return mId;
        }

        @Override
        public String toString() {
            return mText;
        }

        @Override
        public int getSwipeReactionType() {
            return mSwipeReaction;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public boolean isPinnedToSwipeLeft() {
            return mPinnedToSwipeLeft;
        }

        @Override
        public void setPinnedToSwipeLeft(boolean pinedToSwipeLeft) {
            mPinnedToSwipeLeft = pinedToSwipeLeft;
        }
    }
}
