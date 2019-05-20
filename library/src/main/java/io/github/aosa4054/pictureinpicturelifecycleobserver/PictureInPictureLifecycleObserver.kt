package io.github.aosa4054.pictureinpicturelifecycleobserver

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class PictureInPictureLifecycleObserver(
    val activity: FragmentActivity
) {
    protected var augmentedIsInPictureInPictureMode = false
    private var onAnotherTask = false

    abstract val finishDuplicatedTask: Boolean

    init {
        val originalTask = activity.taskId
        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                if (augmentedIsInPictureInPictureMode) {
                    onReturnFromPictureInPicture()
                    augmentedIsInPictureInPictureMode = false
                }
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                if (activity.isInPictureInPictureMode) {
                    augmentedIsInPictureInPictureMode = true
                    if (originalTask != activity.taskId) onAnotherTask = true
                }
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                if (augmentedIsInPictureInPictureMode) {
                    onClosePictureInPictureWindow()
                    augmentedIsInPictureInPictureMode = false
                    if (finishDuplicatedTask && onAnotherTask) activity.finishAndRemoveTask()
                }
            }
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                activity.lifecycle.removeObserver(this)

            }
        })
    }

    abstract fun onReturnFromPictureInPicture()
    abstract fun onClosePictureInPictureWindow()
}
