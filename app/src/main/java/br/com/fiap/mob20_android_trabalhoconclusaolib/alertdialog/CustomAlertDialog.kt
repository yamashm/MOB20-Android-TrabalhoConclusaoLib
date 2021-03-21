package br.com.fiap.mob20_android_trabalhoconclusaolib.alertdialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import br.com.fiap.mob20_android_trabalhoconclusaolib.R

class CustomAlertDialog: View.OnClickListener  {

    private lateinit var ivAlertDialogImage: ImageView
    private lateinit var tvAlertDialogTitle: TextView
    private lateinit var tvAlertDialogMessage: TextView
    private lateinit var btAlertDialogOk: Button
    private lateinit var btAlertDialogCancel: Button

    private var dialog: Dialog? = null
    private var activity: Activity? = null

    fun showDialog(
            activity: Activity,
            resId: Int?,
            title: String?,
            msg: String?,
            buttonOkText: String?,
            buttonOkClickListener: View.OnClickListener?,
            buttonCancelText: String?,
            buttonCancelClickListener: View.OnClickListener?,
            isCancelable: Boolean
    ) {
        createDialog(
                activity, resId, title, msg, buttonOkText,
                buttonOkClickListener, buttonCancelText, buttonCancelClickListener, isCancelable
        )
    }

    private fun createDialog(
            activity: Activity,
            isCancelable: Boolean
    ) {
        this.activity = activity
        dialog = Dialog(activity, R.style.Dialog_No_Border)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(isCancelable)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setContentView(R.layout.custom_alert_dialog)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog?.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = lp
    }

    private fun createDialog(
            activity: Activity,
            resId: Int?,
            title: String?,
            msg: String?,
            buttonOkText: String?,
            buttonOkClickListener: View.OnClickListener?,
            buttonCancelText: String?,
            buttonCancelClickListener: View.OnClickListener?,
            isCancelable: Boolean
    ) {
        if (dialog != null) {
            dismissDialog()
        }

        createDialog(activity, isCancelable)
        ivAlertDialogImage = dialog?.findViewById(R.id.ivAlertDialogImage) as ImageView
        tvAlertDialogTitle = dialog?.findViewById(R.id.tvAlertDialogTitle) as TextView
        tvAlertDialogMessage = dialog?.findViewById(R.id.tvAlertDialogMessage) as TextView
        btAlertDialogOk = dialog?.findViewById(R.id.btAlertDialogOk) as Button
        btAlertDialogCancel = dialog?.findViewById(R.id.btAlertDialogCancel) as Button

        tvAlertDialogTitle.setTextColor(Color.parseColor("#bdbdbd"))
        tvAlertDialogMessage.setTextColor(Color.parseColor("#bdbdbd"))

        tvAlertDialogTitle.text = title ?: ""
        tvAlertDialogMessage.text = msg ?: ""

        btAlertDialogOk.text = buttonOkText ?: "Ok"
        btAlertDialogCancel.text = buttonCancelText ?: "Cancel"

        resId?.let {
            val bm = ResourcesCompat.getDrawable(activity.resources, it, null)
            ivAlertDialogImage.setImageDrawable(bm)
        }

        if(buttonOkClickListener != null){
            btAlertDialogOk.setOnClickListener(buttonOkClickListener)
        }

        if(buttonCancelClickListener != null){
            btAlertDialogCancel.setOnClickListener(buttonCancelClickListener)
        }


        if (isCancelable) {
            dialog?.setOnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP)
                    dialog.dismiss()
                false
            }
        }

        if (!(activity).isFinishing) {
            if (dialog?.isShowing == false) {
                dialog?.show()
            }
        }
    }

    fun dismissDialog() {
        if (dialog != null) {
            if (!isActivityFinish() && dialog!!.isShowing) {
                dialog?.dismiss()
                dialog = null
            }
        }
    }

    private fun isActivityFinish(): Boolean {
        if (activity == null)
            return true
        return activity?.isDestroyed!!
    }

    override fun onClick(view: View) {
        if (dialog != null) {
            dialog?.dismiss()
            dialog = null
        }

        fun isShowing(): Boolean? {
            if (dialog == null) return false
            return dialog?.isShowing
        }
    }
}