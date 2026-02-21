package com.kody.backgroundimageplus.config

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "BackgroundImagePlusSettings",
    storages = [Storage("background-image-plus.xml")]
)
class BackgroundImageSettings : PersistentStateComponent<BackgroundImageSettings.State> {

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) {
        myState = state
    }

    var isEnabled: Boolean
        get() = myState.enabled
        set(value) {
            myState.enabled = value
        }

    var imagePath: String
        get() = myState.imagePath
        set(value) {
            myState.imagePath = value
        }

    var opacity: Int
        get() = myState.opacity
        set(value) {
            myState.opacity = value
        }

    class State {
        var enabled: Boolean = false
        var imagePath: String = ""
        var opacity: Int = 10  // 统一默认值为 30
    }

    companion object {
        fun getInstance(): BackgroundImageSettings =
            ApplicationManager.getApplication().getService(BackgroundImageSettings::class.java)
    }
}
