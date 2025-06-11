/**
 * WhisperPlay Music Player
 *
 * 文件描述：管理账户相关的偏好设置
 * File Description: Manages account-related preferences.
 *
 * @author ckn
 * @since 2024-12-19
 * @version 2.3.0
 */
package me.ckn.music.account

import me.ckn.music.account.bean.ProfileData
import me.ckn.music.consts.PreferenceName
import top.wangchenyan.common.CommonApp
import top.wangchenyan.common.storage.IPreferencesFile
import top.wangchenyan.common.storage.PreferencesFile

/**
 * 账户偏好设置
 * Account Preferences
 *
 * 主要功能：
 * Main Functions:
 * - 存储和读取用户登录凭证 (cookie) / Store and retrieve user login credentials (cookie)
 * - 存储和读取用户个人资料 (profile) / Store and retrieve user profile
 *
 * @author ckn
 * @since 2025-06-10
 */
object AccountPreference :
    IPreferencesFile by PreferencesFile(CommonApp.app, PreferenceName.ACCOUNT, false) {
    /**
     * 用户登录凭证
     * User login credential
     */
    var cookie by IPreferencesFile.StringProperty("cookie", "")

    /**
     * 用户个人资料
     * User profile
     */
    var profile by IPreferencesFile.ObjectProperty("profile", ProfileData::class.java)
}