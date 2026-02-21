package com.kody.backgroundimageplus.config

import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.kody.backgroundimageplus.MyMessageBundle
import com.kody.backgroundimageplus.service.BackgroundImageManager
import javax.swing.*

class BackgroundImageConfigurable : Configurable {

    private var panel: JPanel? = null
    private var folderPathField: JBTextField? = null
    private var folderBrowseButton: JButton? = null
    private var opacitySpinner: JSpinner? = null
    private var intervalSpinner: JSpinner? = null

    private val settings = BackgroundImageSettings.getInstance()
    private val backgroundImageManager = BackgroundImageManager.getInstance()

    override fun getDisplayName(): String = MyMessageBundle.message("config.backgroundimage.title")

    override fun getHelpTopic(): String? = null

    override fun createComponent(): JComponent {
        folderPathField = JBTextField()
        folderPathField?.isEditable = false
        folderBrowseButton = JButton(MyMessageBundle.message("config.backgroundimage.browse.folder"))

        // 使用计步器替代滑块
        opacitySpinner = JSpinner(SpinnerNumberModel(settings.opacity, 0, 100, 1))
        intervalSpinner = JSpinner(SpinnerNumberModel(settings.switchInterval, 1, 60, 1))

        // 文件夹浏览按钮事件
        folderBrowseButton!!.addActionListener {
            SwingUtilities.invokeLater {
                val descriptor = FileChooserDescriptor(false, true, false, false, false, false)
                    .withTitle(MyMessageBundle.message("config.backgroundimage.select.folder"))
                    .withDescription(MyMessageBundle.message("config.backgroundimage.folder.path"))

                val dialog = FileChooserFactory.getInstance().createFileChooser(descriptor, null, null)
                val files: Array<VirtualFile> = dialog.choose(null)
                if (files.isNotEmpty()) {
                    SwingUtilities.invokeLater {
                        folderPathField?.text = files[0].presentableUrl
                    }
                }
            }
        }

        val folderPanel = JPanel().apply {
            layout = java.awt.BorderLayout()
            add(folderPathField!!, java.awt.BorderLayout.CENTER)
            add(folderBrowseButton!!, java.awt.BorderLayout.EAST)
        }

        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel(MyMessageBundle.message("config.backgroundimage.folder.path")), folderPanel, 1)
            .addLabeledComponent(
                JBLabel(MyMessageBundle.message("config.backgroundimage.switch.interval")),
                intervalSpinner!!,
                1
            )
            .addLabeledComponent(
                JBLabel(MyMessageBundle.message("config.backgroundimage.opacity")),
                opacitySpinner!!,
                1
            )
            .addComponentFillVertically(JPanel(), 0)
            .panel

        return panel!!
    }

    override fun isModified(): Boolean {
        return folderPathField?.text != settings.folderPath ||
                (opacitySpinner?.value as Int) != settings.opacity ||
                (intervalSpinner?.value as Int) != settings.switchInterval
    }

    override fun apply() {
        settings.folderPath = folderPathField?.text ?: ""
        settings.opacity = opacitySpinner?.value as Int
        settings.switchInterval = intervalSpinner?.value as Int

        // 应用背景图片设置
        backgroundImageManager.updateBackgroundImage()
    }

    override fun reset() {
        folderPathField?.text = settings.folderPath
        opacitySpinner?.value = settings.opacity
        intervalSpinner?.value = settings.switchInterval
    }

    override fun disposeUIResources() {
        panel = null
        folderPathField = null
        folderBrowseButton = null
        opacitySpinner = null
        intervalSpinner = null
    }
}

