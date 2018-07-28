package com.avi.taxez.base.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.avi.taxez.base.BaseViewModel
import com.avi.taxez.base.fragment.BaseFragment
import com.avi.taxez.base.fragment.FragmentAnimation

/**
 * Created by avi.barel on 27/07/2018.
 */
abstract class BaseActivity<VM : BaseViewModel, AV : BaseActivityViewImpl> : AppCompatActivity() {

    protected var isActive: Boolean = false
    protected lateinit var viewImpl: AV
    protected lateinit var viewModel: VM

    protected abstract fun provideActivityView(): AV
    protected abstract fun provideViewModel(): Class<VM>

    override fun onBackPressed() {
        val topFragment = supportFragmentManager.findFragmentById(viewImpl.containerId())
        if (topFragment is BaseFragment<*, *> && topFragment.handleBackPressed()) {
            return // let the current fragment to handle the back press
        } else {
            super.onBackPressed() // apply default behavior
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        viewImpl = provideActivityView()

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(provideViewModel())

        val rootView = viewImpl.getRootView()

        setContentView(rootView)
        viewImpl.initViews(rootView)

        if (savedInstanceState == null) {
            val initialFragment = viewImpl.initialFragment()

            if (initialFragment != null) {
                newFragment(fragment = initialFragment, addToStack = false)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        isActive = true
    }

    override fun onPause() {
        super.onPause()

        isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()

        viewImpl.releaseViews()
    }

    protected fun newFragment(fragment: BaseFragment<*, *>, replace: Boolean = true, addToStack: Boolean = true, tag: String? = null, clearStack: Boolean = false, fragmentAnimation: FragmentAnimation? = null) {

        if (clearStack) {
            clearBackStack()
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragmentAnimation != null) {
            fragmentTransaction.setCustomAnimations(
                fragmentAnimation.animEnter,
                fragmentAnimation.animExit,
                fragmentAnimation.animPopEnter,
                fragmentAnimation.animPopExit
            )
        }

        if (replace) {
            fragmentTransaction.replace(viewImpl.containerId(), fragment)
        } else {
            fragmentTransaction.add(viewImpl.containerId(), fragment)
        }

        if (addToStack) {
            fragmentTransaction.addToBackStack(tag ?: fragment.getFragmentTag())
        }

        fragmentTransaction.commit()
    }

    private fun clearBackStack() {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}