# UltimateRecyclerView
###Version:0.4.0

[![Throughput Graph](https://graphs.waffle.io/cymcsg/UltimateRecyclerView/throughput.svg)](https://waffle.io/cymcsg/UltimateRecyclerView/metrics)

####Master branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=master)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

####Dev branch:[![Build Status](https://travis-ci.org/cymcsg/UltimateRecyclerView.svg?branch=dev)](https://travis-ci.org/cymcsg/UltimateRecyclerView)

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

[![Stories in Ready](https://badge.waffle.io/cymcsg/UltimateRecyclerView.svg?label=ready&title=Ready)](http://waffle.io/cymcsg/UltimateRecyclerView)

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

###Changes in 0.4.0:
- [ ] a major fix for load more mechanism for both linear layout and gridlayout

###Upcoming features:
* More animations
* Optimise UltimateViewAdapter
* ...



[Upcoming changes in UltiamteRecyclerview 0.4.0](UpcomingChanges.md):

If you have some good ideas, please tell us. My email is cymcsg # gmail.com.And it is a good idea to put your idea on the issue.

####Welcome to fork and pull request.

###If you want to use a rapid development framework for developing apps,you can try [UltimateAndroid Framework](https://github.com/cymcsg/UltimateAndroid).


###Sample

You can clone the project and compile it yourself (it includes a sample), or you can check it out already compiled at Google Play

> Notice that it might not be the latest version

###Quick Setup（Basic Usage）
######1.Integration jcenter
```gradle
repositories {
        jcenter()
    }
dependencies {
    ...
    compile 'com.marshalchen.ultimaterecyclerview:library:0.3.18'
}
```
######2.Bintray integration
```gradle
dependencies{
    compile 'com.marshalchen.ultimaterecyclerview:urvlib:0.4.0'
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
