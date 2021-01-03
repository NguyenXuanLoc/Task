package com.example.task.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.pview.ui.base.BasePresenterImp
import com.example.task.R
import com.example.task.common.ext.gone
import com.example.task.common.ext.showApiCallError
import com.example.task.common.ext.showNetworkError
import com.example.task.common.ext.visible
import com.example.task.common.util.CommonUtil
import kotlinx.android.synthetic.main.activity_base.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast


abstract class BaseActivity<V : BaseView, P : BasePresenterImp<V>> : AppCompatActivity(), BaseView {

    protected val self by lazy { this }
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()
        presenter.attachView(initView())
        getLayoutId()?.run {
            setContentView(R.layout.activity_base)
            layoutInflater.inflate(this, frlBase)

            // Set up toolbar
            applyToolbar()

            // Close keyboard when user touch outside
            CommonUtil.closeKeyboardWhileClickOutSide(self, contentView)
        }

        /* Base methods */
        initWidgets()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }


    /*
    * return view
    * */
    abstract fun initView(): V

    /*
    * Return presenter
    * */
    abstract fun initPresenter(): P

    /*
    * Return activity's layout id
    * */
    abstract fun getLayoutId(): Int?

    /*
    * Set up widgets such as EditText, TextView, RecyclerView, etc
    * */
    abstract fun initWidgets()
    protected fun translucentStatusBar() {
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    // Hide base toolbar
    protected fun hideToolbarBase() {
        toolbarBase.gone()
    }

    // Show base toolbar
    protected fun showToolbarBase() {
        toolbarBase.visible()
    }

    // Using toolbar
    protected fun showTitle(title: Any? = null) {
        // Set title
        when (title) {
            is CharSequence -> toolbarBase.title = title
            is String -> toolbarBase.title = title
            is Int -> toolbarBase.title = getString(title)
        }
    }


    protected fun applyToolbar(
        toolbar: Toolbar = toolbarBase,
        background: Int? = null,
        removeElevation: Boolean = false
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        background?.run {
            toolbar.setBackgroundColor(ContextCompat.getColor(ctx, background))
        }
        if (removeElevation) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                toolbar.elevation = 0f
            }
        }
    }

    // Show Back icon
    protected fun enableHomeAsUp(
        toolbar: Toolbar = toolbarBase,
        backArrowColorResId: Int? = null,
        up: () -> Unit
    ) {
        toolbar.run {
            navigationIcon = backArrowColorResId?.run {
                DrawerArrowDrawable(ctx).apply {
                    progress = 1f
                    color = ContextCompat.getColor(ctx, backArrowColorResId)
                }
            } ?: DrawerArrowDrawable(ctx).apply { progress = 1f }
            setNavigationOnClickListener { up() }
        }
    }

    override fun onNetworkError() {
        showNetworkError()
    }

    override fun onApiCallError(e: Throwable?, code: Int) {
        showApiCallError(code)
    }

    override fun onSendDataSuccess() {
        toast(getString(R.string.sent_data_success))
    }
}
