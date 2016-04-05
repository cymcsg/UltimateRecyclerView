package com.marshalchen.ultimaterecyclerview.swipe;


import java.util.List;

public interface SwipeItemManagerInterface {

    void openItem(int position);

    void closeItem(int position);

    void closeAllExcept(SwipeLayout layout);

    List<Integer> getOpenItems();

    List<SwipeLayout> getOpenLayouts();

    void removeShownLayouts(SwipeLayout layout);

    boolean isOpen(int position);

    Mode getMode();

    void setMode(Mode mode);


    enum Mode {
        Single, Multiple
    }
}
