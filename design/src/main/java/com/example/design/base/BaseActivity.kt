package com.example.design.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.common.ext.isDeveloperOptionsEnabled
import com.example.design.R
import com.example.design.listener.setOnThrottleClickListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

abstract class BaseActivity:AppCompatActivity() {

    var disposable: CompositeDisposable = CompositeDisposable()


    private val loadingDialog: Dialog by lazy {
        val self = Dialog(this)
        self.requestWindowFeature(Window.FEATURE_NO_TITLE)
        self.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        self.setCancelable(false)
        self.setContentView(R.layout.dialog_loading)
        return@lazy self
    }


    private val dialog: Dialog by lazy {
        val self = Dialog(this)
        self.requestWindowFeature(Window.FEATURE_NO_TITLE)
        self.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        self.setCancelable(false)
        self.setContentView(R.layout.dialog_material)
        return@lazy self
    }

    fun showLoadingDialog() {
        if(!isFinishing) {
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

            if(!isFinishing) {
                this.show()
            }
        }
    }

    fun dismissDialog() {
        dialog.dismiss()
    }


    open fun shouldCheckScreenIdle():Boolean = false

    fun getIdleTimeInSeconds(): Long = 60

    fun getSessionLogoutInSeconds(): Long = 30

    abstract fun onViewDidLoad()

    abstract fun onViewConfiguration()

    abstract fun onInitializeIntentData()

    abstract fun onObserveState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        onObserveState()
        onInitializeIntentData()
        onViewConfiguration()
        onViewDidLoad()
        if (shouldCheckScreenIdle()) {
            startIdleTimer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isDeveloperOptionsEnabled()) {
            showDialog(
                title = "Oops!",
                description = "Please disable Developer Options to continue.",
                positiveButtonText = "Go to Settings",
                negativeButtonText = "Exit Application",
                positiveButtonCallback = {
                    val intent = Intent(Settings.ACTION_SETTINGS)
                    startActivity(intent)
                },
                negativeButtonCallback = {
                    finishAffinity()
                }
            )
        }
    }
    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }


    private fun startIdleTimer() {
        Observable.interval(0,1,TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .takeWhile { it != getIdleTimeInSeconds()  }
            .doOnComplete {
                println(this::class.java.simpleName+" show logout warning")
                if (!dialog.isShowing) {
                    showLogoutWarningDialog()
                }
            }
            .subscribe ()
            .addTo(disposable)
    }

    @SuppressLint("SetTextI18n")
    private fun showLogoutWarningDialog(forceLogout: Boolean = false) {
        showDialog(
            title = if (forceLogout) "Session Expired" else "Screen Idle",
            description = "Due to inactivity, you will will soon logged out.",
            positiveButtonText = if (forceLogout) "Logout" else "Log out (30)",
            negativeButtonText = if (forceLogout) null else "Continue Browsing",
            positiveButtonCallback = {
                finishAffinity()
            },
            negativeButtonCallback = {
                startIdleTimer()
            }
        )

        if (!forceLogout) {
            Observable.interval(1,TimeUnit.SECONDS)
                .takeWhile { it != getSessionLogoutInSeconds()+1 || !dialog.isShowing }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it==getSessionLogoutInSeconds()) {
                        dialog.dismiss()
                        finishAffinity()
                    }
                    dialog.findViewById<Button>(R.id.button_positive)?.let { view->
                        view.text = "Log out (${getSessionLogoutInSeconds()-it})"
                        view.requestLayout()
                    }

                }.addTo(disposable)
        }
    }
}
