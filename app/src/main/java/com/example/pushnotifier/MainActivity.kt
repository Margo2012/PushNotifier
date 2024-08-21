package com.example.pushnotifier

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.messaging.FirebaseMessaging
import com.example.pushnotifier.ui.theme.PushNotifierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PushNotifierTheme {
                MainView()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView() {
    var firebaseToken by remember { mutableStateOf("") }

    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("FCM", "Fetching FCM registration token failed", task.exception)
            return@addOnCompleteListener
        }

        // Получаем и отображаем новый токен
        firebaseToken = task.result ?: "Error getting token"
        Log.d("FCM", "FCM Token: $firebaseToken")
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Your token:")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = firebaseToken)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Дополнительная логика (например, копирование токена, отправка на сервер)
            }) {
                Text(text = "Update token")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PushNotifierTheme {
        MainView()
    }
}