package com.kody.backgroundimageplus

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.wm.impl.IdeBackgroundUtil
import com.kody.backgroundimageplus.config.BackgroundImageSettings
import java.io.File
import javax.swing.SwingUtilities

@Service
class BackgroundImageManager {
    
    private val settings = BackgroundImageSettings.getInstance()
    
    fun updateBackgroundImage() {
        SwingUtilities.invokeLater {
            if (settings.isEnabled && settings.imagePath.isNotBlank()) {
                val imageFile = File(settings.imagePath)
                if (imageFile.exists()) {
                    setIdeBackground(imageFile.absolutePath, settings.opacity)
                }
            } else {
                clearIdeBackground()
            }
        }
    }
    
    private fun setIdeBackground(imagePath: String, opacity: Int) {
        try {
            val props = PropertiesComponent.getInstance()
            
            // 使用 0-100 的透明度值，平台会自动处理
            // 在 PainterHelper.java 中可以看到：newAlpha = Math.abs(Math.min(StringUtil.parseInt(parts.length > 1 ? parts[1] : "", 10) / 100f, 1f))
            val spec = "$imagePath,$opacity,${IdeBackgroundUtil.Fill.PLAIN.name},${IdeBackgroundUtil.Anchor.CENTER.name}"
            
            // 设置编辑器背景属性
            props.setValue(IdeBackgroundUtil.EDITOR_PROP, spec)
            
            // 重置背景画家缓存并重绘所有窗口
            IdeBackgroundUtil.resetBackgroundImagePainters()
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun clearIdeBackground() {
        try {
            val props = PropertiesComponent.getInstance()
            props.unsetValue(IdeBackgroundUtil.EDITOR_PROP)
            
            IdeBackgroundUtil.resetBackgroundImagePainters()
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    companion object {
        fun getInstance(): BackgroundImageManager =
            ApplicationManager.getApplication().getService(BackgroundImageManager::class.java)
    }
}
