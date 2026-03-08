package com.kody.backgroundimageplus.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.kody.backgroundimageplus.MyMessageBundle
import com.kody.backgroundimageplus.service.BackgroundImageManager

class ShowRandomImageAction : AnAction() {
    override fun actionPerformed(p0: AnActionEvent) {
        val backgroundImageManager = BackgroundImageManager.getInstance()
        backgroundImageManager.showRandomImage()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.text = MyMessageBundle.message("action.random.image")
        e.presentation.description = MyMessageBundle.message("action.random.image.desc")
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}