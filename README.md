# PictureInPictureLifecycleObserver
Helps you to manage lifecycle flags related to Picture in picture mode<br>
Now this project is way in progress, it is going to be released soon

## Usage
```kotlin
class PIPActivity : AppCompatActivity() {

    lateinit var pipLifecycleObserver: PictureInPictureLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pip)
        
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
}
```
