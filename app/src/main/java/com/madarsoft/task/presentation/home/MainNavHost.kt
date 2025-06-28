package com.madarsoft.task.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.madarsoft.task.presentation.adduser.AddUserViewModel
import com.madarsoft.task.presentation.adduser.compos.AddUserScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "AddUserScreen",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("UserListFragment") {

        }
        composable("AddUserScreen") {
            val viewModel = hiltViewModel<AddUserViewModel>()
            AddUserScreen(viewModel = viewModel) {

            }
        }

    }
}
