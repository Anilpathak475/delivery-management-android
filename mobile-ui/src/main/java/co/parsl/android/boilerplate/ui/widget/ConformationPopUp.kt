package co.parsl.android.boilerplate.ui.widget

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.ImageView
import co.parsl.android.ui.R

class ConformationPopUp constructor(activity: Activity, drawableId: Int, var dialogCallback: DialogCallback) {

    init {
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.pop_up_layout)
        val imgIcon: ImageView = dialog.findViewById(R.id.img_notify_icon)
        if (drawableId !=0) {
            imgIcon.visibility = View.VISIBLE
            imgIcon.setImageDrawable(activity.getDrawable(drawableId))
        }
        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
        btnYes.setOnClickListener {
            dialogCallback.onYes()
            dialog.dismiss()
        }
        val btnNo: Button = dialog.findViewById(R.id.btn_cancel)
        btnNo.setOnClickListener {
            dialogCallback.onNO()
            dialog.dismiss()
        }
        dialog.show()
    }
}