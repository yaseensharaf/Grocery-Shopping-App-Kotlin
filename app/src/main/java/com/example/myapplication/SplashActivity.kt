package com.example.myapplication
import com.google.firebase.FirebaseApp
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.splash_logo)

        // Animation for logo
        val fadeIn = AlphaAnimation(0.0f, 1.0f).apply {
            duration = 2500 // 1.5 seconds
            fillAfter = true
        }
        logo.startAnimation(fadeIn)

        // Delay for splash screen
        lifecycleScope.launch {
            delay(5000) // Wait 3 seconds
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        val user = FirebaseAuth.getInstance().currentUser
        val nextActivity = if (user != null) {
            HomePage::class.java // Navigate to Home if logged in
        } else {
            LoginActivity::class.java // Navigate to Login if not logged in
        }
        startActivity(Intent(this, nextActivity))
        finish() // Close SplashActivity
    }
}
