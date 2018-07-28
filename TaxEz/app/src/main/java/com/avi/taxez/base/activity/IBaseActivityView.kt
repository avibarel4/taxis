package com.avi.taxez.base.activity

import android.support.annotation.IdRes
import android.support.annotation.Nullable
import com.avi.taxez.base.IMVCView
import com.avi.taxez.base.fragment.BaseFragment

/**
 * Created by avi.barel on 27/07/2018.
 */
interface IBaseActivityView : IMVCView {

    @IdRes
    fun containerId(): Int

    @Nullable
    fun initialFragment(): BaseFragment<*, *>?


}