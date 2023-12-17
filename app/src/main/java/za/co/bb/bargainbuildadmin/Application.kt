package za.co.bb.bargainbuildadmin

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
    }
}