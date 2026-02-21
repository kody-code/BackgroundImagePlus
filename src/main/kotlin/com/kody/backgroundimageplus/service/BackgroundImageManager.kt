package com.kody.backgroundimageplus.service

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.wm.impl.IdeBackgroundUtil
import com.kody.backgroundimageplus.config.BackgroundImageSettings
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@Service
class BackgroundImageManager {

    private val settings = BackgroundImageSettings.getInstance()
    private var scheduler: ScheduledExecutorService? = null
    private var currentImageIndex = 0
    private var imageFiles: List<File> = emptyList()
    private var isPaused = false

    fun updateBackgroundImage() {
        stopAutoSwitch()

        // 直接使用文件夹模式
        if (settings.folderPath.isNotEmpty()) {
            startAutoSwitch()
        }
    }

    private fun setIdeBackground(imagePath: String, opacity: Int) {
        try {
            val imageFile = File(imagePath)
            if (!imageFile.exists() || !imageFile.canRead()) {
                println("警告: 背景图片文件不存在或不可读: $imagePath")
                return
            }

            val props = PropertiesComponent.getInstance()
            val spec =
                "$imagePath,$opacity,${IdeBackgroundUtil.Fill.PLAIN.name},${IdeBackgroundUtil.Anchor.CENTER.name}"

            props.setValue(IdeBackgroundUtil.EDITOR_PROP, spec)
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

    private fun startAutoSwitch() {
        val folder = File(settings.folderPath)
        if (!folder.exists() || !folder.isDirectory) {
            println("警告: 壁纸文件夹不存在或不是目录: ${settings.folderPath}")
            return
        }

        imageFiles = folder.listFiles { file ->
            file.isFile && isSupportedImageFile(file) && file.exists() && file.canRead()
        }?.toList() ?: emptyList()

        if (imageFiles.isEmpty()) {
            println("警告: 在文件夹中没有找到有效的图片文件: ${settings.folderPath}")
            return
        }

        currentImageIndex = 0
        scheduler = Executors.newSingleThreadScheduledExecutor()

        // 立即显示第一张图片
        showCurrentImage()

        // 定时切换（除非已暂停）
        if (!isPaused) {
            scheduler?.scheduleWithFixedDelay({
                switchToNextImage()
            }, settings.switchInterval.toLong(), settings.switchInterval.toLong(), TimeUnit.MINUTES)
        }
    }

    private fun stopAutoSwitch() {
        scheduler?.shutdown()
        scheduler = null
    }

    private fun showCurrentImage() {
        if (imageFiles.isNotEmpty()) {
            val currentImage = imageFiles[currentImageIndex]
            setIdeBackground(currentImage.absolutePath, settings.opacity)
        }
    }

    private fun switchToNextImage() {
        if (imageFiles.isNotEmpty() && !isPaused) {
            currentImageIndex = (currentImageIndex + 1) % imageFiles.size
            showCurrentImage()
        }
    }

    // 新增公共方法
    fun pauseAutoSwitch() {
        isPaused = true
        stopAutoSwitch()
    }

    fun resumeAutoSwitch() {
        isPaused = false
        if (settings.folderPath.isNotEmpty()) {
            startAutoSwitch()
        }
    }

    fun isAutoSwitchPaused(): Boolean = isPaused

    fun showNextImage() {
        if (imageFiles.isNotEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % imageFiles.size
            showCurrentImage()
        }
    }

    fun clearBackground() {
        stopAutoSwitch()
        clearIdeBackground()
        imageFiles = emptyList()
        currentImageIndex = 0
        isPaused = false
    }

    private fun isSupportedImageFile(file: File): Boolean {
        val extension = file.extension.lowercase()
        return extension == "png" || extension == "jpg" || extension == "jpeg"
    }

    companion object {
        fun getInstance(): BackgroundImageManager =
            ApplicationManager.getApplication().getService(BackgroundImageManager::class.java)
    }
}
