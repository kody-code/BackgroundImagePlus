package com.kody.backgroundimageplus.config

import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.kody.backgroundimageplus.BackgroundImageManager
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JSlider
import javax.swing.SwingUtilities

class BackgroundImageConfigurable : Configurable {
    
    private var panel: JPanel? = null
    private var enabledCheckBox: JBCheckBox? = null
    private var imagePathField: JBTextField? = null
    private var opacitySlider: JSlider? = null
    private var browseButton: JButton? = null
    
    private val settings = BackgroundImageSettings.getInstance()
    private val backgroundImageManager = BackgroundImageManager.getInstance()
    
    override fun getDisplayName(): String = "Background Image Plus"
    
    override fun getHelpTopic(): String? = null
    
    override fun createComponent(): JComponent {
        enabledCheckBox = JBCheckBox("启用背景图片")
        imagePathField = JBTextField()
        imagePathField?.isEditable = false
        opacitySlider = JSlider(0, 100, settings.opacity) // 使用设置中的当前值
        browseButton = JButton("浏览...")
        
        browseButton!!.addActionListener {
            // 在后台线程中执行文件选择操作
            SwingUtilities.invokeLater {
                val descriptor = FileChooserDescriptor(true, false, false, false, false, false)
                    .withTitle("选择背景图片")
                    .withDescription("请选择要作为背景的图片文件")
                
                descriptor.withFileFilter { file ->
                    val extension = file.extension?.lowercase()
                    extension == "png" || extension == "jpg" || extension == "jpeg"
                }
                
                val dialog = FileChooserFactory.getInstance().createFileChooser(descriptor, null, null)
                val files: Array<VirtualFile> = dialog.choose(null)
                if (files.isNotEmpty()) {
                    // 在 EDT 上更新 UI
                    SwingUtilities.invokeLater {
                        imagePathField?.text = files[0].presentableUrl
                    }
                }
            }
        }
        
        val filePanel = JPanel().apply {
            layout = java.awt.BorderLayout()
            add(imagePathField!!, java.awt.BorderLayout.CENTER)
            add(browseButton!!, java.awt.BorderLayout.EAST)
        }
        
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("启用设置："), enabledCheckBox!!, 1)
            .addLabeledComponent(JBLabel("图片路径："), filePanel, 1)
            .addLabeledComponent(JBLabel("透明度："), opacitySlider!!, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
        
        return panel!!
    }
    
    override fun isModified(): Boolean {
        return enabledCheckBox?.isSelected != settings.isEnabled ||
               imagePathField?.text != settings.imagePath ||
               opacitySlider?.value != settings.opacity
    }
    
    override fun apply() {
        settings.isEnabled = enabledCheckBox?.isSelected ?: false
        settings.imagePath = imagePathField?.text ?: ""
        settings.opacity = opacitySlider?.value ?: 30
        
        // 应用背景图片设置
        backgroundImageManager.updateBackgroundImage()
    }
    
    override fun reset() {
        enabledCheckBox?.isSelected = settings.isEnabled
        imagePathField?.text = settings.imagePath
        opacitySlider?.value = settings.opacity
    }
    
    override fun disposeUIResources() {
        panel = null
        enabledCheckBox = null
        imagePathField = null
        opacitySlider = null
        browseButton = null
    }
}