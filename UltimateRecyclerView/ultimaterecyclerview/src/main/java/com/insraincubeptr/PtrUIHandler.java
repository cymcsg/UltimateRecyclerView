package com.insraincubeptr;


import com.insraincubeptr.indicator.PtrIndicator;

/*
 *  Copyright 2016 https://github.com/liaohuqiu
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    from https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh
 *
 */
public interface PtrUIHandler {

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame na
     */
    void onUIReset(PtrFrameLayout frame);

    /**
     * prepare for loading
     *
     * @param frame na
     */
    void onUIRefreshPrepare(PtrFrameLayout frame);

    /**
     * perform refreshing UI
     *
     * @param frame na
     */
    void onUIRefreshBegin(PtrFrameLayout frame);

    /**
     * perform UI after refresh
     *
     * @param frame na
     */
    void onUIRefreshComplete(PtrFrameLayout frame);

    void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
