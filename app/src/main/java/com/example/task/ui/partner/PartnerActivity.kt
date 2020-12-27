package com.example.task.ui.partner

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.common.Key
import com.example.task.common.ext.openActivity
import com.example.task.data.model.AccountModel
import com.example.task.ui.base.BaseActivity
import com.example.task.ui.listdata.ListDataActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.toast


class PartnerActivity : BaseActivity<PartnerView, PartnerPresenter>(), PartnerView {
    /*   lateinit var locationManager: LocationManager
       private lateinit var threadTotal: Thread
       private lateinit var threadPin: Thread
       private lateinit var threadGps: Thread
       private lateinit var bm: BatteryManager
       private lateinit var shareData: ShareData
       private var RC_ACCESS_FINE_LOCATION = 1
       private val RC_ACCESS_COARSE_LOCATION = 2
       private val mHandler: Handler by lazy {
           @SuppressLint("HandlerLeak")
           object : Handler() {
               override fun handleMessage(msg: Message) {
                   when (msg.what) {
                       Constant.WHAT_PUSH_TO_SERVER -> {
                           var result = msg.data.getString(Constant.RESULT)
                           result?.let { presenter.sendData(it) }
                           lblResult.text = result
                       }
                       Constant.WHAT_RESULT -> {
                           toast(msg.data.getString(Constant.RESULT).toString())
                           lblResult.append("\n" + msg.data.getString(Constant.RESULT))
                       }
                       Constant.WHAT_NOTIFY -> {
                           lblResult.text = msg.data.getString(Constant.RESULT) + "\n"
                       }
                   }
               }
           }
       }
       private val list: ArrayList<String> = ArrayList()
       private val key = "QAD"
       val shref: SharedPreferences by lazy {
           getSharedPreferences(
               "MyPREFERENCES",
               Context.MODE_PRIVATE
           )
       } */
    private var partners: ArrayList<String> = ArrayList();
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
        applyToolbar()
        showTitle(R.string.home_page)
        rclPartner.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclPartner.adapter = partnerAdapter
        partnerAdapter.notifyDataSetChanged()
        presenter.getPartnerCode(true)
//        presenter.login(true, AccountModel("143", "0934667446", "152528", "3"))
    }

    private fun itemClick(ob: String) {
        bundleOf(Key.PARTNER_CODE to ob).run {
            openActivity(ListDataActivity::class.java, this)
        }
    }

    override fun sendDataSuccess() {
        toast(R.string.send_success)
    }

    override fun loadPartnerSuccess(response: List<String>) {
        partners.addAll(response)
        partnerAdapter.notifyDataSetChanged()
    }
}