package org.buffer.android.boilerplate.ui.home


import android.animation.Animator
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.fragment_home.*
import org.buffer.android.boilerplate.ui.R


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        (floatingActionButton as FloatingActionButton).setOnClickListener {
           if (toolbar.visibility == View.VISIBLE) {
               hideToolbar()
           } else {
               revealToolbar()
           }
        }
    }

    private fun revealToolbar() {

        val x = floatingActionButton.x + floatingActionButton.width/2
        val y = floatingActionButton.height/2

        val startRadius = 0
        val endRadius = (Math.hypot(toolbar.width.toDouble(), toolbar.height.toDouble()))

        val animator = ViewAnimationUtils.createCircularReveal(toolbar, x.toInt(), y, startRadius.toFloat(), endRadius.toFloat())
        toolbar.visibility = View.VISIBLE
        animator.start()

        floatingActionButton.rotation = 45f
    }

    private fun hideToolbar() {

        val x = floatingActionButton.x + floatingActionButton.width/2
        val y = floatingActionButton.height/2

        val startRadius = Math.max(toolbar.width, toolbar.height)
        val endRadius = 0

        val animator = ViewAnimationUtils.createCircularReveal(toolbar, x.toInt(), y, startRadius.toFloat(), endRadius.toFloat())
        animator.start()

        floatingActionButton.rotation = 0f

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                toolbar.visibility = View.INVISIBLE
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
    }


}
