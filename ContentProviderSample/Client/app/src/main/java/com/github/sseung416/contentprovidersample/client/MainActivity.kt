package com.github.sseung416.contentprovidersample.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.sseung416.contentprovidersample.client.local.dto.User
import com.github.sseung416.contentprovidersample.client.ui.theme.ProviderTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProviderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(LocalContext.current.contentResolver)),
) {
    val users by viewModel.users.collectAsState()

    Column {
        Buttons(
            viewModel::getAllUsers,
            viewModel::getTeenagerUsers,
            viewModel::insertUser,
            viewModel::updateUser,
            viewModel::deleteUser
        )
        UserList(list = users)
    }
}

@Composable
fun Buttons(
    getAllUsers: () -> Unit,
    getTeenagerUsers: () -> Unit,
    insertUser: (User) -> Unit,
    updateUser: (User) -> Unit,
    deleteUser: (id: Int) -> Unit,
) {
    var newUserId by remember { mutableStateOf(0) }

    Row {
        Button(onClick = { getAllUsers.invoke() }) {
            Text(text = "모든 유저 조회하기")
        }
        Button(onClick = { getTeenagerUsers.invoke() }) {
            Text(text = "10대 유저 조회하기")
        }
        Button(onClick = {
            newUserId++
            insertUser.invoke(
                User(
                    name = "더미$newUserId",
                    age = newUserId * 3,
                    favoriteColorId = 1
                )
            )
        }) {
            Text(text = "유저 추가")
        }
        Button(onClick = {
            updateUser.invoke(
                User(
                    id = newUserId,
                    name = "수정더미$newUserId",
                    age = newUserId * 3,
                    favoriteColorId = 1
                )
            )
        }) {
            Text(text = "최근에 추가된 유저 수정")
        }
        Button(onClick = {
            deleteUser.invoke(newUserId)
            newUserId--
        }) {
            Text(text = "최근에 추가된 유저 삭제")
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
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentSize()
    ) {
        Row {
            Text(text = user.name)
            Spacer(modifier = Modifier.padding(6.dp))
            Text(text = "${user.age}살")
        }
    }
}

val USER_CONTENT_URI = "content://${UserProvider.PROVIDER_URL}/${UserProvider.PATH_USER}".toUri()