package com.kody.backgroundimageplus.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.kody.backgroundimageplus.MyMessageBundle
import com.kody.backgroundimageplus.service.BackgroundImageManager

class ClearBackgroundAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val backgroundImageManager = BackgroundImageManager.getInstance()
        backgroundImageManager.clearBackground()
    }

    override fun update(e: AnActionEvent) {
        e.presentation.text = MyMessageBundle.message("action.clear.background")
        e.presentation.description = MyMessageBundle.message("action.clear.background.desc")
    }

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}