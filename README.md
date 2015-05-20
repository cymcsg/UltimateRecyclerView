# UltimateRecyclerView
###Version:0.3.2

####Master branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=master)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

####Dev branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=dev)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

#####Project website:[UltimateRecyclerView](https://github.com/cymcsg/UltimateRecyclerView)

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
* scrollbars
* Colorful styles of ``swipe to refresh``



###Changes in 0.3.2:
- [x] add a empty view when the adapter do not have data
- [x] add some colorful styles of  `swipe to refresh`
- [x] add swapAdapter() ,getAdapter() etc.
- [x] Custom FAB style
- [x] add support for scrollbars of RecyclerView
- [x] add method set background color of recyclerview
- [x] add method to set default swipe to dismiss color


###Upcoming features:
* More animations
* Add sticky header like instagram
* ...


[Upcoming changes in UltiamteRecyclerview 0.4.0](UpcomingChanges.md):



If you have some good idea, please tell us.My email is cymcsg # gmail.com.And it is a good idea to put your idea on the issue.

####Welcome to fork and pull request.

###If you want to use a rapid development framework for developing apps,you can try [UltimateAndroid Framework](https://github.com/cymcsg/UltimateAndroid).


###Screenshot
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/0bed76fcdecb604afab39df9ce1a509af4b6f995/ultimaterecyclerview/ultimate_recyclerview6.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview1.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview2.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview3.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/b93b542a517f7c32a72010813c82fdd9c2b97857/ultimaterecyclerview/ultimate_recyclerview4.gif)

####Admob implementation
![](demo/adbanner.gif)

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
    compile 'com.marshalchen.ultimaterecyclerview:library:0.3.0'
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
 
Show empty view when the adapter is null:
```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
...
app:recyclerviewEmptyView="@layout/empty_view"/>
```

Show custom FloatingView(Both menu and button are fine. It is easy to set click event on them) when the adapter is null:
```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
...
 app:recyclerviewFloatingActionView="@layout/floating_view"/>
```

Set custom colorful style of pull to refresh:
```xml
 <com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview
 .../>
``` 
Using CustomUltimateRecyclerview instead of UltimateRecyclerView 
``ultimateRecyclerView.setCustomSwipeToRefresh();``


Set scrollbars of RecyclerView by set attributes of UltimateRecyclerView in xml layout:

```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
        app:recyclerviewScrollbars="vertical" />                
```
Note that set scrollbars of RecyclerView dynamically by code is **NOT SUPPORTED** refer to [this](http://stackoverflow.com/questions/27056379/is-there-any-way-to-enable-scrollbars-for-recyclerview-in-code)


####If you want to see more details,you can check the demo.







###Thanks

* Use animators from  [recyclerview-animators](https://github.com/wasabeef/recyclerview-animators)
* Deal with different types of LayoutManager from[SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView)
* Divider of recyclerview[RecyclerView-FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
* Another kind of swipe[ScrollableItemList](https://github.com/rohaanhamid/ScrollableItemList)
* Parallax header of the recyclerview[android-parallax-recyclerview](https://github.com/kanytu/android-parallax-recyclerview)
* Swipe to dismiss and drag drop[DynamicRecyclerView](https://github.com/ismoli/DynamicRecyclerView)
* Floating action button [FloatingActionButton](https://github.com/futuresimple/android-floating-action-button)
* Colorful pull to refresh [Ultra Pull To Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)

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
