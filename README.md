# UltimateRecyclerView
###Version:0.3.18

####Master branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=master)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

####Dev branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=dev)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

#####Project website:[https://github.com/cymcsg/UltimateRecyclerView](https://github.com/cymcsg/UltimateRecyclerView)

###Description
UltimateRecyclerView is a RecyclerView(advanced and flexible version of ListView) with pulling to refresh, loading more, swiping to dismiss, draging and drop, animations ,sticky header,show or hide toolbar and FAB when scrolling and many other features.You can use it ```just like RecyclerView```.


Notice that UltimateRecyclerView is a project under development.

[Your donations is highly appreciated. Thank you!](#donations)

###Features:
* Swipe to refresh(using android.support.v4.widget.SwipeRefreshLayout)
* Many kinds of animations
* Swipe to dismiss
* Parallax or normal head view
* Drag and drop items
* Loading more when reach the last item(infinite scrolling)
* Custom views in loading more
* Showing or hiding toolbar and floating button when scrolling
* Scrollbars
* Colorful styles of ``swipe to refresh``
* Sticky header like instagram
* Support different layout in adapter
* Loading adapter with animation

###Changes in 0.3.18:
- [x] new style of drag and swipe
- [x] normal head view
- [x] add Generics in the UltimateViewAdapter
- [x] expandable item of recyclerview
- [x] quick adapter
- [x] Change attr name from ``fab_**`` to ``urv_fab_**``

###Changes in 0.3.8:
- [x] support different layout in adapter
- [x] loading adapter with animations
- [x] support minSdk to 8
- [x] Upgrade recyclerview to 22.2.0


###Changes in 0.3.2:
- [x] add a empty view when the adapter do not have data
- [x] add some colorful styles of  `swipe to refresh`
- [x] add swapAdapter() ,getAdapter() etc.
- [x] Custom FAB style
- [x] add support for scrollbars of RecyclerView
- [x] add method set background color of recyclerview
- [x] add method to set default swipe to dismiss color
- [x] support easy way to use admob


###Upcoming features:
* More animations
* Optimise UltimateViewAdapter
* ...


[Upcoming changes in UltiamteRecyclerview 0.4.0](UpcomingChanges.md):



If you have some good ideas, please tell us. My email is cymcsg # gmail.com.And it is a good idea to put your idea on the issue.

####Welcome to fork and pull request.

###If you want to use a rapid development framework for developing apps,you can try [UltimateAndroid Framework](https://github.com/cymcsg/UltimateAndroid).


###Screenshot
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview11.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview12.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview7.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview3.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview8.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview9.gif)


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
    compile 'com.marshalchen.ultimaterecyclerview:library:0.3.18'
}
```

#####2.Usage:
```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:id="@+id/ultimate_recycler_view"
       >
        </com.marshalchen.ultimaterecyclerview.UltimateRecyclerView>
```


#####3.Features:  
Loading more:

```java 
  ultimateRecyclerView.enableLoadmore();
```

```java
 ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        simpleRecyclerViewAdapter.insert("More " + moreNum++, simpleRecyclerViewAdapter.getAdapterItemCount());
                    }
                }, 1000);
            }
        });

```

######Set ParallaxHeader:

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


######Set swipe to refresh:

```java
ultimateRecyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert(moreNum++ + "  Refresh things", 0);
                        ultimateRecyclerView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
```

######Set swipe to dismiss:

```java
  ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(simpleRecyclerViewAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
```
 
###### Drag and drop:
 
 ```java
   simpleRecyclerViewAdapter.setOnDragStartListener(new SimpleAdapter.OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                mItemTouchHelper.startDrag(viewHolder);
            }
        });
```

Animations:

```java
  ultimateRecyclerView.setItemAnimator(Type.values()[position].getAnimator());
  ultimateRecyclerView.getItemAnimator().setAddDuration(300);
  ultimateRecyclerView.getItemAnimator().setRemoveDuration(300);
 ```
        
######Showing and hiding toolbar and floating button:

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
 
######Show empty view when the adapter is null:
```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
...
app:recyclerviewEmptyView="@layout/empty_view"/>
```

OR

```java
ultimateRecyclerView.setEmptyView(getResources().getIdentifier("empty_view","layout",getPackageName()));

ultimateRecyclerView.showEmptyView();
```

######Show custom FloatingView(Both menu and button are fine. It is easy to set click event on them) when the adapter is null:
```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
...
 app:recyclerviewFloatingActionView="@layout/floating_view"/>
```

######Set custom colorful style of pull to refresh:
```xml
 <com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview
 .../>
``` 
######Using CustomUltimateRecyclerview instead of UltimateRecyclerView 
``ultimateRecyclerView.setCustomSwipeToRefresh();``


######Set scrollbars of RecyclerView by set attributes of UltimateRecyclerView in xml layout:

```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
        app:recyclerviewScrollbars="vertical" />                
```
Note that set scrollbars of RecyclerView dynamically by code is **NOT SUPPORTED** refer to [this](http://stackoverflow.com/questions/27056379/is-there-any-way-to-enable-scrollbars-for-recyclerview-in-code)

######Add sticky header:

In MainActivity:
```java
   StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(simpleRecyclerViewAdapter);
  ultimateRecyclerView.addItemDecoration(headersDecor);
```

In the adapter:
```java
 @Override
    public long generateHeaderId(int position) {
        if (getItem(position).length() > 0)
            return getItem(position).charAt(0);
        else return -1;
    }
    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stick_header_item, viewGroup, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }
```
Refresh adapter:
```java
mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override public void onChanged() {
        headersDecor.invalidateHeaders();
      }
    });
```

######Using different layout in an adapter:
You should define a MultiViewAdapter which extends UltimateDiffernetViewTypeAdapter and then your custom differnt view adapters.
```java
public class MultiViewTypesRecyclerViewAdapter extends UltimateDifferentViewTypeAdapter{
    @Override
    public Enum getEnumFromPosition(int position) {
        if (position % 2 == 1) {
            return SampleViewType.SAMPLE1;
        } else {
            return SampleViewType.SAMPLE2;
        }
    }
    
    public MultiViewTypesRecyclerViewAdapter(List<String> dataSet) {
      putBinder(SampleViewType.SAMPLE1, new Sample1Binder(this,dataSet));
      putBinder(SampleViewType.SAMPLE2, new Sample2Binder(this,dataSet));
      ...
    }
    ...
}
```
```java
public class Sample1Binder extends DataBinder<Sample1Binder.ViewHolder> {
   
    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.simple_binder1, parent, false);
        return new ViewHolder(view);
    }
    ...
}
    
```


######Admob implementation
```java
 private AdView createadmob() {
        AdView mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        mAdView.setAdUnitId("__GOOGLE_AD_UNIT__ID__");
        mAdView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        if (admob_test_mode)
            // Optionally populate the ad request builder.
            adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());
        return mAdView;
    }
```
```java
       simpleRecyclerViewAdapter = new admobdfpadapter(createadmob(), 5, stringList, new AdmobAdapter.AdviewListener() {
            @Override
            public AdView onGenerateAdview() {
                return createadmob();
            }
        });
```

######Loading adapter with animations :
```java
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       ...
        if (!isFirstOnly || position > mLastPosition) {
            for (Animator anim : getAdapterAnimations(holder.itemView, AdapterAnimationType.ScaleIn)) {
                anim.setDuration(mDuration).start();
                anim.setInterpolator(mInterpolator);
            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(holder.itemView);
        }

    }
```

####If you want to see more details,you can check the demo.







###Thanks

* Use animators from  [recyclerview-animators](https://github.com/wasabeef/recyclerview-animators)
* Deal with different types of LayoutManager from[SuperRecyclerView](https://github.com/Malinskiy/SuperRecyclerView)
* Divider of recyclerview[RecyclerView-FlexibleDivider](https://github.com/yqritc/RecyclerView-FlexibleDivider)
* Parallax header of the recyclerview[android-parallax-recyclerview](https://github.com/kanytu/android-parallax-recyclerview)
* Floating action button [FloatingActionButton](https://github.com/futuresimple/android-floating-action-button)
* Colorful pull to refresh [Ultra Pull To Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)
* Sticky section headers in  RecyclerView [StickHeader](https://github.com/eowise/recyclerview-stickyheaders)
* Swipe[AndroidSwipeLayout](https://github.com/daimajia/AndroidSwipeLayout)
* Thanks [jjhesk](https://github.com/jjhesk) for doing so many work on the project

If there are someone who I do not mention here,please accept my sincerely appologies and tell me.

<h2 ><a name="donations"></a>Donations:</h2>

Donate $9.99: [![$9.99](https://bytebucket.org/marshalchen/images/raw/9c442645492ddc10474416debf511a57a0367397/others/donate.jpg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=5GYRYZVNAK2G2)

Donate $19.99: [![$19.99](https://bytebucket.org/marshalchen/images/raw/9c442645492ddc10474416debf511a57a0367397/others/donate.jpg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2NTS85GHJLRT6)

Donate $39.99: [![$39.99](https://bytebucket.org/marshalchen/images/raw/9c442645492ddc10474416debf511a57a0367397/others/donate.jpg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=EWVECWFKAPBTN)

Donate $59.99: [![$59.99](https://bytebucket.org/marshalchen/images/raw/9c442645492ddc10474416debf511a57a0367397/others/donate.jpg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=ZHSRHTBMUHEMN)


Alipay:![donate](https://bytebucket.org/marshalchen/images/raw/9c442645492ddc10474416debf511a57a0367397/others/alipay.png)

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
