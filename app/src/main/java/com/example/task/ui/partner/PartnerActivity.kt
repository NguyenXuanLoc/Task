package com.example.task.ui.partner

import android.util.Log
import android.webkit.WebView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.common.Constant
import com.example.task.common.Key
import com.example.task.common.ext.openActivity
import com.example.task.common.util.PefUtil
import com.example.task.data.model.SettingModel
import com.example.task.ui.base.BaseActivity
import com.example.task.ui.listdata.ListDataActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.toast
import java.io.File


class PartnerActivity : BaseActivity<PartnerView, PartnerPresenter>(), PartnerView {
    private var settings: ArrayList<SettingModel> = ArrayList()
    private var partners: ArrayList<String> = ArrayList()
    private val partnerAdapter: PartnerAdapter by lazy {
        PartnerAdapter(this, partners) {
            itemClick(
                it
            )
        }
    }

    override fun initView(): PartnerView {
        return this
    }

    override fun initPresenter(): PartnerPresenter {
        return PartnerPresenter(this)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_home
    }

    override fun initWidgets() {
        clearCache()
        applyToolbar()
        PefUtil.putString(Constant.SETTING_AGENT, WebView(this).settings.userAgentString)
        showTitle(R.string.home_page)
        presenter.showLoading()
        rclPartner.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclPartner.adapter = partnerAdapter
        partnerAdapter.notifyDataSetChanged()
        presenter.getPartnerCode(true)
        presenter.getSetting()
    }

    private fun itemClick(ob: String) {
        var settingModel: SettingModel? = null
        settings.forEach { it ->
            if (it.appCode.equals(Key.ON_MOBI)) {
                settingModel = it
            }
        }
        settingModel?.let {
            bundleOf(Key.PARTNER_CODE to ob, Key.DURATION to settingModel!!.duration).run {
                openActivity(ListDataActivity::class.java, this)
            }
        }

    }

    override fun loadSettingSuccess(results: List<SettingModel>) {
        settings.addAll(results)
    }

    override fun sendDataSuccess() {
        toast(R.string.send_success)
    }

    override fun loadPartnerSuccess(response: List<String>) {
        presenter.hideLoading()
        partners.addAll(response)
        partnerAdapter.notifyDataSetChanged()
    }

    private fun clearCache() {
        try {
            deleteDir(cacheDir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile()) {
            dir.delete()
        } else {
            false
        }
    }
}