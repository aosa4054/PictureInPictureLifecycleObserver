package io.github.aosa4054.pipobserver.sample

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pip.*
import pipobserver.sample.R

class PIPActivity : AppCompatActivity() {

    companion object {
        const val PIP_ACTION = "pip action"
    }

    private lateinit var pipLifecycleObserver: PictureInPictureLifecycleObserver
    private var pipReceiver : BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pipLifecycleObserver = object : PictureInPictureLifecycleObserver(this) {
            override val removeTaskAfterDismiss = true

            override fun onBackToFullScreenMode() {
                Toast.makeText(this@PIPActivity, "Returned", Toast.LENGTH_SHORT).show()
            }

            override fun onDismissPictureInPictureWindow() {
                Toast.makeText(this@PIPActivity, "Dismissed", Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(R.layout.activity_pip)

        fab.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val params = PictureInPictureParams.Builder()
                    .setAspectRatio(Rational(2, 1))
                    .setSourceRectHint(Rect(1, 1, 1, 1))
                    .setActions(
                        listOf(
                            RemoteAction(
                                Icon.createWithResource(this, android.R.drawable.ic_dialog_dialer),
                                "title",
                                "description",
                                PendingIntent.getBroadcast(this, 0, Intent(PIP_ACTION), 0)
                            )
                        )
                    )
                    .build()
                enterPictureInPictureMode(params)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                enterPictureInPictureMode()
            }
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode) {
            supportActionBar!!.hide()
            fab.visibility = View.GONE

            pipReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent == null || intent.action != PIP_ACTION) return
                    Toast.makeText(this@PIPActivity, "RemoteAction", Toast.LENGTH_SHORT).show()
                }
            }
            registerReceiver(pipReceiver, IntentFilter(PIP_ACTION))
        } else {
            fab.visibility = View.VISIBLE

            unregisterReceiver(pipReceiver)
            pipReceiver = null
        }
    }
}
