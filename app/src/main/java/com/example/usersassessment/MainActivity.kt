package com.example.usersassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.usersassessment.ui.screen.main.MainScreenBridge
import com.example.usersassessment.ui.theme.UsersAssessmentTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                UsersAssessmentTheme {
                    MainScreenBridge()
                }
            }
        }
    }
}