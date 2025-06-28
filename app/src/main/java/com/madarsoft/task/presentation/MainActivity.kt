package com.madarsoft.task.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.madarsoft.task.R
import com.madarsoft.task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        binding.xml.setOnClickListener {
            navController.navigate(R.id.add_user_fragment)
        }
        binding.compose.setOnClickListener {
            navController.navigate(R.id.add_user_screen)
        }
    }
}
