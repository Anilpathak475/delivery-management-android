package org.buffer.android.boilerplate.ui.onboardingv2

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_welcome_wizard.*
import kotlinx.android.synthetic.main.fragment_welcome_wizard.view.*
import org.buffer.android.boilerplate.ui.R
import org.buffer.android.boilerplate.ui.onboarding.LaunchActivity

class WelcomeWizardActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    private var indicators: Array<ImageView?>? = null

    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_wizard)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        indicators = arrayOf(intro_indicator_0, intro_indicator_1, intro_indicator_2)

        container.currentItem = 0
        updateIndicators(0)

        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {
                Log.d("wizard", "Position $position")

                page = position;
                updateIndicators(position)

                intro_btn_next.visibility = if (position == 2) View.GONE else View.VISIBLE
                intro_btn_finish.visibility = if (position == 2) View.VISIBLE else View.GONE
                intro_btn_back.visibility = if (position ==0) View.GONE else View.VISIBLE
            }

        })

        intro_btn_next.setOnClickListener {
            page += 1
            container.currentItem = page
        }

        intro_btn_back.setOnClickListener {
            page -= 1
            container.currentItem = page
        }

        intro_btn_finish.setOnClickListener {
            startActivity(Intent(WelcomeWizardActivity@this, LaunchActivity::class.java))
            finish()
        }
    }

    private fun updateIndicators(position: Int) {
        for (i in 0 until indicators!!.size) {
            indicators!![i]?.setBackgroundResource(
                    if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_welcome_wizard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> FirstFragment.newInstance()
                1 -> SecondFragment.newInstance()
                2 -> ThirdFragment.newInstance()
                else -> {
                    FirstFragment.newInstance()
                }
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_welcome_wizard, container, false)
            //rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
