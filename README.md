# bio-met
Biometric authentication in Kivy

### TODO: update readme; improve code/example
> However the important bit is to find SDLActivity.java, change "extends Activity" to "extends FragmentActivity"

> I found SDLActivity.java (.buildozer/android/platform/build-armeabi-v7a/dists/youtube__armeabi-v7a/src/main/java/org/libsdl) after building the app.

> add this import statement to SDLActivity.java
```java
import androidx.fragment.app.FragmentActivity;
```
