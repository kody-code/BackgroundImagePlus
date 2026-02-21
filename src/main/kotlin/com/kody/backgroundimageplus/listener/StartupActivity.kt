package com.kody.backgroundimageplus.listener

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.kody.backgroundimageplus.service.BackgroundImageManager

class StartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        com.intellij.openapi.application.ApplicationManager.getApplication().executeOnPooledThread {
            try {
                val backgroundImageManager = BackgroundImageManager.getInstance()
                backgroundImageManager.updateBackgroundImage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}