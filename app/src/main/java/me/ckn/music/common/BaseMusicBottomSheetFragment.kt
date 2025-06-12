package me.ckn.music.common

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.WindowCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * 基础音乐BottomSheetDialogFragment
 * 自动应用全屏沉浸式设置
 *
 * Original: Created by wangchenyan.top on 2023/12/20
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：基础音乐底部弹窗Fragment
 * File Description: Base music bottom sheet fragment
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
abstract class BaseMusicBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // 设置窗口属性，确保从屏幕真正底部弹出
        dialog.window?.let { window ->
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        // 设置BottomSheet默认弹出到屏幕1/2高度
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                val displayMetrics = resources.displayMetrics
                val screenHeight = displayMetrics.heightPixels

                // 设置BottomSheet的peek高度为屏幕高度的3/4
                behavior.peekHeight = screenHeight * 3 / 4
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                behavior.skipCollapsed = false
            }
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 应用全屏沉浸式
        enableImmersiveMode()
    }

    override fun onResume() {
        super.onResume()
        // 确保全屏状态
        enableImmersiveMode()
    }
}
