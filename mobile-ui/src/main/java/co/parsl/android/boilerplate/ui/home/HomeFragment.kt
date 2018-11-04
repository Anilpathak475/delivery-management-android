package co.parsl.android.boilerplate.ui.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.parsl.android.ui.R
import kotlinx.android.synthetic.main.fragment_home.*

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
        assignButton.setOnClickListener {
            it.isEnabled = !it.isEnabled

            if (it.isEnabled) {
                enableAssign()
            } else {
                disableAssign()
            }
        }
    }

    private fun disableAssign() {
        assignButton.isEnabled = false
        assignButton.background = resources.getDrawable(android.R.color.darker_gray, null)
    }

    private fun enableAssign() {
        assignButton.isEnabled = true
        assignButton.background = resources.getDrawable(R.color.parslLightGreen, null)
    }

    override fun onResume() {
        super.onResume()
    }


}
