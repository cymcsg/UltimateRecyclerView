package com.marshalchen.ultimaterecyclerview.swipe;




import java.util.List;

public interface SwipeItemManagerInterface {

    public void openItem(int position);

    public void closeItem(int position);

    public void closeAllExcept(SwipeLayout layout);

    public List<Integer> getOpenItems();

    public List<SwipeLayout> getOpenLayouts();

    public void removeShownLayouts(SwipeLayout layout);

    public boolean isOpen(int position);

    public Mode getMode();

    public void setMode(Mode mode);


    public static enum Mode{
        Single, Multiple
    }
}
