package com.example.design.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.design.R
import com.example.design.listener.setOnThrottleClickListener
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment:Fragment() {

    var disposable: CompositeDisposable = CompositeDisposable()


    private val loadingDialog: Dialog by lazy {
        val self = Dialog(requireContext())
        self.requestWindowFeature(Window.FEATURE_NO_TITLE)
        self.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        self.setCancelable(false)
        self.setContentView(R.layout.dialog_loading)
        return@lazy self
    }


    private val dialog: Dialog by lazy {
        val self = Dialog(requireContext())
        self.requestWindowFeature(Window.FEATURE_NO_TITLE)
        self.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        self.setCancelable(false)
        self.setContentView(R.layout.dialog_material)
        return@lazy self
    }

    fun showLoadingDialog() {
        if(!isRemoving) {
            loadingDialog.show()
        }
    }

    fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    fun showDialog(
        title: String? = null,
        description:String? = null,
        iconLarge:Int? = null,
        iconSmall:Int? = null,
        lottie:Int? = null,
        positiveButtonText:String? = null,
        negativeButtonText:String? = null,
        positiveButtonCallback:()->Unit = {},
        negativeButtonCallback:()->Unit = {}
    ) {
        with(dialog) {
            this.findViewById<TextView>(R.id.tv_title)?.let { view->
                view.text = title
                view.visibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            this.findViewById<TextView>(R.id.tv_description)?.let { view->
                view.text = description
                view.visibility = if (description.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            val ivImageLarge = this.findViewById<ImageView>(R.id.iv_image_large)
            val ivImageSmall = this.findViewById<ImageView>(R.id.iv_image_small)
            val lottieView = this.findViewById<LottieAnimationView>(R.id.lottie)

            if (lottie !=null) {
                lottieView?.setAnimation(lottie)
                lottieView?.visibility = View.VISIBLE
                ivImageLarge?.visibility = View.GONE
                ivImageSmall?.visibility = View.GONE
            }
            else if (iconLarge !=null) {
                ivImageLarge?.setImageResource(iconLarge)
                lottieView?.visibility = View.GONE
                ivImageLarge?.visibility = View.VISIBLE
                ivImageSmall?.visibility = View.GONE
            }
            else if (iconSmall !=null) {
                ivImageLarge?.setImageResource(iconSmall)
                lottieView?.visibility = View.GONE
                ivImageLarge?.visibility = View.GONE
                ivImageSmall?.visibility = View.VISIBLE
            }

            this.findViewById<Button>(R.id.button_positive)?.let { view->
                view.text = positiveButtonText
                view.visibility = if (positiveButtonText.isNullOrEmpty()) View.GONE else View.VISIBLE
                view.setOnThrottleClickListener {
                    if (lottieView?.isAnimating == true) {
                        lottieView.cancelAnimation()
                    }
                    this.dismiss()
                    positiveButtonCallback()
                }
            }
            this.findViewById<Button>(R.id.button_negative)?.let { view->
                view.text = negativeButtonText
                view.visibility = if (negativeButtonText.isNullOrEmpty()) View.GONE else View.VISIBLE
                view.setOnThrottleClickListener {
                    if (lottieView?.isAnimating == true) {
                        lottieView.cancelAnimation()
                    }
                    this.dismiss()
                    negativeButtonCallback()
                }
            }

            if(!isRemoving) {
                this.show()
            }
        }
    }

    fun dismissDialog() {
        dialog.dismiss()
    }



    abstract fun onViewDidLoad()

    abstract fun onViewConfiguration(): View?

    abstract fun onInitializeIntentData()

    abstract fun onObserveState()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onObserveState()
        onInitializeIntentData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return onViewConfiguration()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewDidLoad()
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
