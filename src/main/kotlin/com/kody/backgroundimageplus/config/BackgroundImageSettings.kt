package com.kody.backgroundimageplus.config

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "BackgroundImageSettings",
    storages = [Storage("backgroundImagePlus.xml")]
)
class BackgroundImageSettings : PersistentStateComponent<BackgroundImageSettings> {

    var opacity: Int = 30

    // 新增自动切换相关设置
    var folderPath: String = ""
    var switchInterval: Int = 5 // 分钟

    override fun getState(): BackgroundImageSettings = this

    override fun loadState(state: BackgroundImageSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): BackgroundImageSettings =
            ApplicationManager.getApplication().getService(BackgroundImageSettings::class.java)
    }
}
