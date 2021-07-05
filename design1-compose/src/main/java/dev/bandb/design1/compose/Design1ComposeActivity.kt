package dev.bandb.design1.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import dev.bandb.design1.compose.ui.theme.DesignStudioTheme
import dev.bandb.designstudio.design1.common.SampleData

class Design1ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignStudioTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Home(SampleData.taskGroups)
                }
            }
        }
    }
}
