package com.avi.taxez.ui.main

import com.avi.taxez.R
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
            fragmentAnimation = FragmentAnimation(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right))
    }

}
