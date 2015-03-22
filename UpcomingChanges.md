###Upcoming changes in 0.3.0:
- [x] add a empty view when the adapter do not have data
- [ ] add some colorful styles of  `swipe to refresh`
- [x] add swapAdapter() ,getAdapter() etc.
- [x] Custom FAB style


###Other changes:
* More animations
* New recyclerview v22
* setSwipeToDismissCallback() throws a null pointer exception if there is no adapter set;
* trigger to bring the item back when swipe to dismiss
* add swipe to refresh at the bottom
* add support for scrollbars of RecyclerView
* etc...  

Set scrollbars of RecyclerView by set attributes of UltimateRecyclerView in xml layout:
Note that set scrollbars of RecyclerView dynamically by code is **NOT SUPPORTED** refer to [this](http://stackoverflow.com/questions/27056379/is-there-any-way-to-enable-scrollbars-for-recyclerview-in-code)
```xml
<com.marshalchen.ultimaterecyclerview.UltimateRecyclerView
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:id="@+id/ultimate_recycler_view"
        app:recyclerviewClipToPadding="true"
        app:recyclerviewPadding="2dp"
        app:recyclerviewScrollbars="vertical" />
```


If you have some good idea, please tell us.My email is cymcsg # gmail.com.And it is a good idea to put your idea on the issue.
