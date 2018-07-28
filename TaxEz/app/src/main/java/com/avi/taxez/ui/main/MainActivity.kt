package com.avi.taxez.ui.main

import com.avi.taxez.base.activity.BaseActivity
import com.avi.taxez.base.fragment.FragmentAnimation
import com.avi.taxez.ui.taxis.TaxisFragment

class MainActivity : BaseActivity<MainActivityViewModel, MainActivityViewImpl>() {

    override fun provideActivityView(): MainActivityViewImpl {
        return MainActivityViewImpl(this, null)
    }

    override fun provideViewModel(): Class<MainActivityViewModel> {
        return MainActivityViewModel::class.java
    }

    fun openTaxisList(origin: String, destination: String) {

        val fragment = TaxisFragment.newInstance(origin, destination)

        newFragment(
            fragment,
            replace = true,
            addToStack = true,
            tag = fragment.getFragmentTag(),
            fragmentAnimation = FragmentAnimation(android.R.anim.slide_in_left, 0, 0, android.R.anim.slide_out_right))
    }

}
