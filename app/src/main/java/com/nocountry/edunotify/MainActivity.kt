package com.nocountry.edunotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nocountry.edunotify.ui.navigation.EduNotifyApp
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EduNotifyTheme {
                EduNotifyApp()
            }
        }
    }
}

