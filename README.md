# PictureInPictureLifecycleObserver
![Platform](https://img.shields.io/badge/Platform-Android-orange.svg)
![API](https://img.shields.io/badge/API-24%2B-green.svg)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)


Helps you to manage lifecycle flags related to Picture in picture mode

# Usage

### Gradle

```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation 'com.github.aosa4054:PictureInPictureLifecycleObserver:1.0.0'
}
```

### Activity

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

# License
```
Copyright 2019 aosa4054

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```