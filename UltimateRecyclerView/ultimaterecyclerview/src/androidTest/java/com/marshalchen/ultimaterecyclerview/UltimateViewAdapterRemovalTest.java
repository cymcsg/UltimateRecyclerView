package com.marshalchen.ultimaterecyclerview;

import android.test.InstrumentationTestCase;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UltimateViewAdapterRemovalTest extends InstrumentationTestCase {
    public void testRemoveFirstWithHeader() {
        checkRemoval(true, true);
    }

    public void testRemoveLastWithHeader() {
        checkRemoval(true, false);
    }

    public void testRemoveFirstWithoutHeader() {
        checkRemoval(false, true);
    }

    public void testRemoveLastWithoutHeader() {
        checkRemoval(false, false);
    }

    private void checkRemoval(final boolean header, final boolean first) {
        for (final boolean footer : new boolean[]{false, true}) {
            final ListAdapter[] adapters = new ListAdapter[1];
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    ListAdapter adapter = new ListAdapter();
                    adapters[0] = adapter;
                    if (header) {
                        adapter.setCustomHeaderView(new UltimateRecyclerView.CustomRelativeWrapper(
                                getInstrumentation().getTargetContext()));
                    }
                    if (footer) {
                        adapter.setCustomLoadMoreView(new View(getInstrumentation().getTargetContext()));
                        adapter.enableLoadMore(true);
                        adapter.internalExecuteLoadingView();
                    }
                }
            });
            getInstrumentation().waitForIdleSync();
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    final ListAdapter adapter = adapters[0];
                    final List<Integer> removedPositions = new ArrayList<>();
                    adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onItemRangeRemoved(int positionStart, int itemCount) {
                            assertEquals(1, itemCount);
                            removedPositions.add(positionStart);
                        }
                    });
                    int offset = header ? 1 : 0;
                    remove(adapter, first);
                    assertEquals(first ? Arrays.asList("b", "c") : Arrays.asList("a", "b"),
                            adapter.items);
                    assertEquals(Arrays.asList(first ? offset : offset + 2), removedPositions);
                    remove(adapter, first);
                    remove(adapter, first);
                    assertTrue(adapter.items.isEmpty());
                    assertEquals(first ? Arrays.asList(offset, offset, offset)
                            : Arrays.asList(offset + 2, offset + 1, offset), removedPositions);
                    assertEquals((header ? 1 : 0) + (footer ? 1 : 0), adapter.getItemCount());
                    remove(adapter, first);
                    assertEquals(3, removedPositions.size());
                }
            });
        }
    }

    private void remove(ListAdapter adapter, boolean first) {
        if (first) {
            adapter.removeFirstInternal(adapter.items);
        } else {
            adapter.removeLastInternal(adapter.items);
        }
    }

    private static class ListAdapter extends UltimateViewAdapter<RecyclerView.ViewHolder> {
        final List<String> items = new ArrayList<>(Arrays.asList("a", "b", "c"));

        @Override
        public int getAdapterItemCount() {
            return items.size();
        }

        @Override
        public RecyclerView.ViewHolder newFooterHolder(View view) {
            return newHolder(view);
        }

        @Override
        public RecyclerView.ViewHolder newHeaderHolder(View view) {
            return newHolder(view);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
            return newHolder(new View(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        }

        @Override
        public long generateHeaderId(int position) {
            return -1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            return newHolder(new View(parent.getContext()));
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        }

        private RecyclerView.ViewHolder newHolder(View view) {
            return new RecyclerView.ViewHolder(view) { };
        }
    }
}
