# UltimateRecyclerView

 [ ![Download](https://api.bintray.com/packages/marshalchen/UltimateRecyclerview/UltimateRecyclerview/images/download.svg) ](https://bintray.com/marshalchen/UltimateRecyclerview/UltimateRecyclerview/_latestVersion)[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)[![Stories in Ready](https://badge.waffle.io/cymcsg/UltimateRecyclerView.svg?label=ready&title=Ready)](http://waffle.io/cymcsg/UltimateRecyclerView)




#### Master branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=master)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

#### Dev branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=dev)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

##### Project website:[https://github.com/cymcsg/UltimateRecyclerView](https://github.com/cymcsg/UltimateRecyclerView)



### Description

UltimateRecyclerView is a RecyclerView(advanced and flexible version of ListView) with pulling to refresh, loading more, swiping to dismiss, draging and drop, animations ,sticky header,show or hide toolbar and FAB when scrolling and many other features.You can use it ```just like RecyclerView```. Support AndroidX now.

Notice that UltimateRecyclerView is a project under development.

[Your donations is highly appreciated. Thank you!](#donations)

### Features:

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
* Expandable view in recyclerview


#### Quick Setup (Basic Usage)
##### 1.Using Gradle:
```groovy
repositories {
    jcenter()
    }
dependencies {
    ...
    compile 'com.marshalchen.ultimaterecyclerview:library:0.9.0'
}
```

or grab via Maven

```xml
<dependency>
  <groupId>com.marshalchen.ultimaterecyclerview</groupId>
  <artifactId>library</artifactId>
  <version>0.9.0</version>
</dependency>
```

##### 2.Usage:

``` xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/ultimate_recycler_view"
/>
```
For more details, you can read the Wiki and the demo of the project.




### Version Log
* ***v0.8.0*** Migrate to AndroidX
* ***v0.7.0*** Support most features in Recyclerview 24.0.0. Improve the UltimateAdapter. Reduce the size of the library. Fix some bugs.

* ***v0.5.8*** In this version we are now based on support library 23.4.0. We have fixed the load more and disable load more function from early triggers. There is no need to change anything from their implementations. Please read up on the example code if you have any questions from the implementations.

* ***v0.5.6*** In this version we now have 23.3.0 support library and the min version is supported all the ways to v13. New added feature that allow us to adding have node connector on each item on `linearlayoutmanager`. By extending `TimeLineView` you will now have unlimited builds from the things that connected to each dot.
* ***v0.5.0*** this library will be based on v23.2.1 from now on. if you need have the v23.1.1 please go back to the previous release. detail of this upgrade please see [#342](https://github.com/cymcsg/UltimateRecyclerView/issues/342)
* ***v0.4.9*** This is the last version that will be based on V23.1.1. and this library will not be supported on this version. For further supports please refer to the latest release.
* ***v0.3.11*** There are still version that is based on 22.+



### Upcoming features:
* Refer to discussion for headers [#299](https://github.com/cymcsg/UltimateRecyclerView/issues/299)
* Refer to discussion for the loading more [#276](https://github.com/cymcsg/UltimateRecyclerView/issues/276)
* Swipe issue and discussion [#296](https://github.com/cymcsg/UltimateRecyclerView/issues/296)
* Wishlist for [UltiamteRecyclerview 0.4.2](UpcomingChanges.md)
* and more.

> Notice that it might not be the latest version

### Demo App / Sample Code:
* Due to rapid updates and developments we have decided to host the demo APK on github
* Check out this link for [latest demonstration for the code](https://github.com/cymcsg/UltimateRecyclerView/releases)
* Video demo for [grid layout demo](https://www.youtube.com/watch?v=iTnIf-N8m1Y)
* or you can check it out already compiled at [Google Play](https://play.google.com/store/apps/details?id=com.marshalchen.ultimaterecyclerview.demo)
* You can clone the project and compile it yourself (it includes a sample), or you can check it out already compiled at Google Play
* You can read more usage in [wiki](https://github.com/cymcsg/UltimateRecyclerView/wiki) and welcome to make your own tutorials in here.

#### Welcome to fork and PR (pull request)
If you have some good ideas, please tell us. My email is cymcsg # gmail.com.And it is a good idea to put your idea on the issue. If you want to use a rapid development framework for developing apps, you can try [UltimateAndroid Framework](https://github.com/cymcsg/UltimateAndroid).

### Screenshot
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview11.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview12.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview7.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/f4794974d8de71ab1d0f0efddda556df7e792df2/ultimaterecyclerview/ultimate_recyclerview3.gif)
![ultimate_recyclerview](https://bytebucket.org/marshalchen/images/raw/44beb162121c719ea4094bd7ea1c9f0cd7de4c04/ultimaterecyclerview/ultimate_recyclerview9.gif)
![grid_layout](http://i.giphy.com/UVKEWEGu64z60.gif)
![grid_layout](http://i.giphy.com/UKxCkkUHVH8Fq.gif)
![admob](http://i.giphy.com/bExwitMhjtUqI.gif)
![expandable](http://i.giphy.com/pLWHKsEdVlsKA.gif)
![node](http://i.giphy.com/Xjf7Y8pZ84OxW.gif)
![multitype](http://i.giphy.com/bvU4HcWvMhejm.gif)



### Thanks
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

Alipay:![donate](https://bytebucket.org/marshalchen/images/raw/9c442645492ddc10474416debf511a57a0367397/others/alipay.png)

Bitcoin Donation Accepted
![wallet](http://s32.postimg.org/sdd1oio1t/qrwallet.jpg)

## License

``` 
Copyright 2014-present Marshal Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
