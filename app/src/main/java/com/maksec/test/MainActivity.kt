package com.maksec.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.maksec.test.ui.CallBlockerScreen
import com.maksec.test.ui.theme.Maksec_testTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Maksec_testTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CallBlockerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
