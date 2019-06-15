package io.github.aosa4054.pictureinpicturelifecycleobserver

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class PictureInPictureLifecycleObserver(
    val activity: FragmentActivity
) {
    /**
     * Activity.isInPictureInPictureMode changes soon after transforming the window,
     * therefore it is impossible to distinguish either the action is to dismiss or to expand.
     * this param is used for distinguish that and call either onReturnFromPictureInPicture or onClosePictureInPictureWindow
     * @see removeTaskAfterDismiss
     *
     * @see onReturnFromPictureInPicture
     * @see onClosePictureInPictureWindow
     */
    var augmentedIsInPictureInPictureMode = false

    protected var onAnotherTask = false

    /**
     * Normally, when user drag down pip window or tap close button to dismiss pip window,
     * only onStop is called and the task on which pip activity is survives.
     * if removeTaskAfterDismiss is set as true, after some actions to dismiss pip window, activity.finishAndRemoveTask() is called.
     * if set as false only onStop is called as same as default
     * @see removeTaskAfterDismiss
     */
    protected abstract val removeTaskAfterDismiss: Boolean

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
                    if (removeTaskAfterDismiss && onAnotherTask) activity.finishAndRemoveTask()
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
