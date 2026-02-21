package com.kody.backgroundimageplus

import com.intellij.ide.AppLifecycleListener
import com.kody.backgroundimageplus.config.BackgroundImageSettings
import javax.swing.SwingUtilities

class StartupListener : AppLifecycleListener {

    fun appStarting(cliProject: com.intellij.openapi.project.Project?) {
        // 应用启动时初始化背景图片设置
        SwingUtilities.invokeLater {
            try {
                val settings = BackgroundImageSettings.getInstance()
                val manager = BackgroundImageManager.getInstance()

                // 如果设置已启用且有图片路径，则应用背景
                if (settings.isEnabled && settings.imagePath.isNotBlank()) {
                    manager.updateBackgroundImage()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
