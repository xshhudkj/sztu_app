package me.ckn.music.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.blankj.utilcode.util.AppUtils
import me.ckn.music.R
import me.ckn.music.common.BaseMusicActivity

/**
 * 关于轻聆音乐的 Activity
 * 展示应用相关信息、版本、开源地址等
 */
class AboutActivity : BaseMusicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置布局文件
        setContentView(R.layout.activity_about)
        // 加载 AboutFragment 到容器
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AboutFragment())
            .commit()
    }

    /**
     * 关于页面 Fragment
     * 展示版本号、分享、项目地址、微博、博客、GitHub、API 等信息
     */
    class AboutFragment : PreferenceFragmentCompat() {
        // 懒加载获取各个 Preference（设置项）
        private val mVersion: Preference by lazy {
            findPreference("version")!!
        }
        private val mShare: Preference by lazy {
            findPreference("share")!!
        }
        private val mStar: Preference by lazy {
            findPreference("star")!!
        }
        private val mWeibo: Preference by lazy {
            findPreference("weibo")!!
        }
        private val mBlog: Preference by lazy {
            findPreference("blog")!!
        }
        private val mGithub: Preference by lazy {
            findPreference("github")!!
        }
        private val api: Preference by lazy {
            findPreference("api")!!
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            // 加载 preference_about.xml 配置
            addPreferencesFromResource(R.xml.preference_about)
            // 设置版本号显示
            mVersion.summary = AppUtils.getAppVersionName()
            // 设置"分享"点击事件
            mShare.setOnPreferenceClickListener {
                share()
                true
            }
            // 设置"项目地址"点击事件
            mStar.setOnPreferenceClickListener {
                openUrl(getString(R.string.about_project_url))
                true
            }
            // 设置"微博"点击事件
            mWeibo.setOnPreferenceClickListener {
                openUrl(it.summary.toString())
                true
            }
            // 设置"博客"点击事件
            mBlog.setOnPreferenceClickListener {
                openUrl(it.summary.toString())
                true
            }
            // 设置"GitHub"点击事件
            mGithub.setOnPreferenceClickListener {
                openUrl(it.summary.toString())
                true
            }
            // 设置"API"点击事件
            api.setOnPreferenceClickListener {
                openUrl("https://github.com/Binaryify/NeteaseCloudMusicApi")
                true
            }
        }

        /**
         * 分享应用的方法
         */
        private fun share() {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                getString(
                    R.string.share_app,
                    getString(R.string.app_name),
                    getString(R.string.about_project_url)
                )
            )
            // 弹出系统分享选择框
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }

        /**
         * 打开网页的方法
         * @param url 网页地址
         */
        private fun openUrl(url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}