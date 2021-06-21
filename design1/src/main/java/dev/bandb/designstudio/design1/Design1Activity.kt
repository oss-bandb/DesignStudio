package dev.bandb.designstudio.design1

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dev.bandb.designstudio.design1.databinding.Design1ActivityBinding

class Design1Activity : AppCompatActivity() {
    private lateinit var binding: Design1ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = Design1ActivityBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        // Remove scrim on navigationBar to make it fully transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
    }
}