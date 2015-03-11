# UltimateRecyclerView
###Version:0.2.1

####Master branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=master)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

####Dev branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=dev)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)


###Description
UltimateRecyclerView is a RecyclerView(advanced and flexible version of ListView) with pulling to refresh, loading more, swiping to dismiss, draging and drop, animations ,show or hide toolbar and FAB when scrolling and many other features.You can use it ```just like RecyclerView```.

Notice that UltimateRecyclerView is a project under development.




###Features:
* Swipe to refresh(using android.support.v4.widget.SwipeRefreshLayout)
* Many kinds of animations
* Swipe to dismiss
* Parallax head view
* Drag and drop
* Loading more when reach the last item(infinite scrolling)
* Custom views in loading more
* Showing or hiding toolbar and floating button when scrolling




###Upcoming features:
* More animations
* Colorful style of Swipe to refresh
* ...

If you have some good idea, please mention us.My email is cymcsg # gmail.com

####Welcome to fork.

###If you want to use a rapid development framework for developing apps,you can try [UltimateAndroid Framework](https://github.com/cymcsg/UltimateAndroid).


###Screenshot

![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview1.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview2.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview3.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/b93b542a517f7c32a72010813c82fdd9c2b97857/ultimaterecyclerview/ultimate_recyclerview4.gif)



###Sample

You can clone the project and compile it yourself (it includes a sample), or you can check it out already compiled at Google Play

[![Google Play](http://developer.android.com/images/brand/en_generic_rgb_wo_45.png)](https://play.google.com/store/apps/details?id=com.marshalchen.ultimaterecyclerview.demo)

> Notice that it might not be the latest version

###Quick Setup（Basic Usage）
######1.Integration
```groovy
repositories {
        jcenter()
    }
dependencies {
    ...
    compile 'com.marshalchen.ultimaterecyclerview:library:0.2.1'
}
```

#####2.Usage:
```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:id="@+id/ultimate_recycler_view"
        app:recyclerviewClipToPadding="true"
        app:recyclerviewPadding="2dp">
        </com.marshalchen.ultimaterecyclerview.UltimateRecyclerView>
```
#####3.Features:  
Loading more:

```java 
  ultimateRecyclerView.enableLoadmore();
```


Set ParallaxHeader:

```java
 ultimateRecyclerView.setParallaxHeader(getLayoutInflater().inflate(R.layout.parallax_recyclerview_header, ultimateRecyclerView.mRecyclerView, false));
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                Drawable c = toolbar.getBackground();
                c.setAlpha(Math.round(127 + percentage * 128));
                toolbar.setBackgroundDrawable(c);
            }
        });
```


Set swipe to refresh:

```java
 ultimateRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
                        ultimateRecyclerView.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                    }
                }, 1000);
            }
        });
```

Set swipe to dismiss:

```java
  ultimateRecyclerView.setSwipeToDismissCallback(new SwipeToDismissTouchListener.DismissCallbacks() {
            @Override
            public SwipeToDismissTouchListener.SwipeDirection dismissDirection(int position) {
                return SwipeToDismissTouchListener.SwipeDirection.BOTH;
            }
            @Override
            public void onDismiss(RecyclerView view, List<SwipeToDismissTouchListener.PendingDismissData> dismissData) {
                for (SwipeToDismissTouchListener.PendingDismissData data : dismissData) {
                    simpleRecyclerViewAdapter.remove(data.position);
                }
            }
            @Override
            public void onResetMotion() {
                isDrag = true;
            }
            @Override
            public void onTouchDown() {
                isDrag = false;
            }
        });
 ```
 
 Drag and drop:
 
 ```java
    dragDropTouchListener = new DragDropTouchListener(ultimateRecyclerView.mRecyclerView, this) {
            @Override
            protected void onItemSwitch(RecyclerView recyclerView, int from, int to) {
                simpleRecyclerViewAdapter.swapPositions(from, to);
                simpleRecyclerViewAdapter.clearSelection(from);
                simpleRecyclerViewAdapter.notifyItemChanged(to);
                if (actionMode != null) actionMode.finish();
                Logs.d("switch----");
            }
            @Override
            protected void onItemDrop(RecyclerView recyclerView, int position) {
                Logs.d("drop----");
                ultimateRecyclerView.enableSwipeRefresh(true);
            }
        };
        dragDropTouchListener.setCustomDragHighlight(getResources().getDrawable(R.drawable.custom_drag_frame));
        ultimateRecyclerView.mRecyclerView.addOnItemTouchListener(dragDropTouchListener);
```

Animations:

```java
  ultimateRecyclerView.setItemAnimator(Type.values()[position].getAnimator());
  ultimateRecyclerView.getItemAnimator().setAddDuration(300);
  ultimateRecyclerView.getItemAnimator().setRemoveDuration(300);
 ```
        
Showing and hiding toolbar and floating button:

```java
  ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {           
             }
            @Override
            public void onDownMotionEvent() {
            }
            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                if (observableScrollState == ObservableScrollState.DOWN) {
                     ultimateRecyclerView.showToolbar(toolbar, ultimateRecyclerView,getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.UP) {
                      ultimateRecyclerView.hideToolbar(toolbar,ultimateRecyclerView,getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.STOP) {
                }
            }
        });        
 ```
####If you want to see more details,you can check the demo.







###Thanks

* Use animators from  [recyclerview-animators](https://github.com/wasabeef/recyclerview-animators)
* Deal with different types of LayoutManager from[SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView)
* Divider of recyclerview[RecyclerView-FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
* Another kind of swipe[ScrollableItemList](https://github.com/rohaanhamid/ScrollableItemList)
* Parallax header of the recyclerview[android-parallax-recyclerview](https://github.com/kanytu/android-parallax-recyclerview)
* Swipe to dismiss and drag drop[DynamicRecyclerView](https://github.com/ismoli/DynamicRecyclerView)
* Floating action button [FloatingActionButton](https://github.com/futuresimple/android-floating-action-button)

If there are someone who I do not mention here,please accept my sincerely appologies and tell me.



License
--------

    Copyright 2015 Marshal Chen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
