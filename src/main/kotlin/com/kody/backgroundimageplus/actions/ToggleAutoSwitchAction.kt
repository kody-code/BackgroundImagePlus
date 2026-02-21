package com.kody.backgroundimageplus.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.kody.backgroundimageplus.MyMessageBundle
import com.kody.backgroundimageplus.service.BackgroundImageManager

class ToggleAutoSwitchAction : AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        val backgroundImageManager = BackgroundImageManager.getInstance()
        
        if (backgroundImageManager.isAutoSwitchPaused()) {
            backgroundImageManager.resumeAutoSwitch()
        } else {
            backgroundImageManager.pauseAutoSwitch()
        }
    }
    
    override fun update(e: AnActionEvent) {
        val backgroundImageManager = BackgroundImageManager.getInstance()
        val isPaused = backgroundImageManager.isAutoSwitchPaused()
        
        if (isPaused) {
            e.presentation.text = MyMessageBundle.message("action.resume.autoswitch")
            e.presentation.description = MyMessageBundle.message("action.resume.autoswitch.desc")
        } else {
            e.presentation.text = MyMessageBundle.message("action.pause.autoswitch")
            e.presentation.description = MyMessageBundle.message("action.pause.autoswitch.desc")
        }
    }
}