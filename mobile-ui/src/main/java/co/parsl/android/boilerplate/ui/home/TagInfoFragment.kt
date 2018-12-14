package co.parsl.android.boilerplate.ui.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import co.parsl.android.boilerplate.remote.BufferooServiceFactory
import co.parsl.android.boilerplate.remote.ParslService
import co.parsl.android.ui.BuildConfig
import co.parsl.android.ui.R
import kotlinx.android.synthetic.main.fragment_tag_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TagInfoFragment : Fragment() {
    val parslService: ParslService = BufferooServiceFactory.makeParslService(BuildConfig.DEBUG)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uniqueIdValue = TagInfoFragmentArgs.fromBundle(arguments).uniqueId
        val assignedUrlValue = TagInfoFragmentArgs.fromBundle(arguments).assignedUrl
        val status = TagInfoFragmentArgs.fromBundle(arguments).status

        tagId.text = uniqueIdValue
        infoUrl.text = assignedUrlValue
        tagStatus.text = status
        initListeners()
        hand.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_tagInfoFragment_to_scanRange)
        }

        assignButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_tagInfoFragment_to_getProductCategories)

        }
    }

    private fun initListeners() {
        cancleButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_tagInfoFragment_to_scanRange)
        }
    }

    private fun getToken(): String {
        val idToken = MainActivity.readSharedSetting(activity!!, MainActivity.PREF_USER_ID_TOKEN, "")
        return "Bearer $idToken"
    }
}
