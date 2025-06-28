package com.madarsoft.task.presentation.userslist.compos

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madarsoft.task.R
import com.madarsoft.task.domain.model.User
import com.madarsoft.task.presentation.userslist.viewmodel.UsersListVIewModel
import com.madarsoft.task.utils.errors.generateReadableLightColor

@Composable
fun UsersListScreen(
    viewModel: UsersListVIewModel,
) {
    val uiState by viewModel.state.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            if (it.isNotBlank())
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    if (uiState.isEmpty) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.no_users_found))
        }
    } else {
        UsersListScreenContent(
            users = uiState.users,
        )
    }
}

@Composable
fun UsersListScreenContent(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            UserItem(user)
            HorizontalDivider(
                color = Color.Gray.copy(alpha = 0.7f), thickness = .4.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
fun UserItem(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(generateReadableLightColor())
        ) {
            Text(
                text = user.name.first().toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(text = user.name, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(text = "Job title: ${user.jobTitle}", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Age: ${user.age}", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = user.gender, fontSize = 14.sp, color = Color.Gray)
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    UserItem(User(id = 1, name = "John Doe", 30, "Software Engineer", "Male"))
}

@Preview(showBackground = true)
@Composable
fun UsersListScreenContentPreview() {
    UsersListScreenContent(
        listOf(
            User(id = 1, name = "John Doe", 30, "Software Engineer", "Male"),
            User(id = 1, name = "John Doe", 30, "Software Engineer", "Male"),
            User(id = 1, name = "John Doe", 30, "Software Engineer", "Male"),
            User(id = 1, name = "John Doe", 30, "Software Engineer", "Male"),
        )
    )
}