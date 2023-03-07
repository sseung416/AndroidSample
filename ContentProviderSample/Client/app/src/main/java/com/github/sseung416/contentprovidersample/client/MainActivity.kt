package com.github.sseung416.contentprovidersample.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.github.sseung416.contentprovidersample.client.local.dto.User
import com.github.sseung416.contentprovidersample.client.ui.theme.ProviderTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAllUsers(contentResolver)

        setContent {
            ProviderTheme {
                val users by viewModel.users.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserList(list = users)
                }
            }
        }
    }
}

@Composable
fun UserList(list: List<User>) {
    LazyColumn {
        items(list) { user -> UserCard(user) }
    }
}

@Composable
fun UserCard(user: User) {
    Card {
        Row {
            Text(text = user.name)
            Text(text = "${user.age}살", color = Color.Gray)
        }
    }
}

val CONTENT_URI = "content://${UserProvider.PROVIDER_URL}/${UserProvider.PATH_USER}".toUri()