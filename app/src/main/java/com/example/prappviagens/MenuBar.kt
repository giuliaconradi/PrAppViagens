package com.example.prappviagens.ui.theme


import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prappviagens.ListaViagem
import com.example.prappviagens.NovaViagem

@Composable
fun MenuBar() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(selected = true,
                    onClick = {
                        navController.navigate("novo")
                    },
                    label = {
                        Text(text = "Novo")
                    },
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = "")
                    }
                )

                BottomNavigationItem(selected = false,
                    onClick = {
                        navController.navigate("viagens")
                    },
                    label = {
                        Text(text = "Viagens")
                    },
                    icon = {
                        Icon(Icons.Filled.AccountBox, contentDescription = "")
                    }
                )

                BottomNavigationItem(selected = false,
                    onClick = {
                        navController.navigate("sobre")
                    },
                    label = {
                        Text(text = "Sobre")
                    },
                    icon = {
                        Icon(Icons.Filled.Add, contentDescription = "")
                    }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "novo",
            modifier = Modifier.padding(paddingValues = it)
        ) {
            composable("novo/{userID}",
                arguments = listOf(navArgument("userId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("userID")
                NovaViagem()
            }
            composable("viagens") {
                ListaViagem()
            }
            composable("sobre") {
                SobreViagem()
            }
        }
    }
}