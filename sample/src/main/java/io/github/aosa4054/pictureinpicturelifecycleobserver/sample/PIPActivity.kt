package io.github.aosa4054.pictureinpicturelifecycleobserver.sample

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.github.aosa4054.pictureinpicturelifecycleobserver.PictureInPictureLifecycleObserver
import kotlinx.android.synthetic.main.activity_pip.*

class PIPActivity : AppCompatActivity() {

    lateinit var pipLifecycleObserver: PictureInPictureLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pip)

        fab.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                enterPictureInPictureMode(PictureInPictureParams.Builder().build())
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                enterPictureInPictureMode()
            }
        }

        pipLifecycleObserver = object : PictureInPictureLifecycleObserver(this) {
            override val finishDuplicatedTask = true

            override fun onReturnFromPictureInPicture() {
                Toast.makeText(this@PIPActivity, "Returned", Toast.LENGTH_SHORT).show()
            }

            override fun onClosePictureInPictureWindow() {
                Toast.makeText(this@PIPActivity, "Ended", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        fab.visibility = if (isInPictureInPictureMode) View.GONE else View.VISIBLE
    }
}
