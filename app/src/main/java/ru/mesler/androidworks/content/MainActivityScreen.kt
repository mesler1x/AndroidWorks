@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidpractice.content


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import ru.mesler.androidworks.content.NavGraph
import ru.mesler.androidworks.theme.AndroidPracticeTheme
import ru.mesler.androidworks.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen() {
    val navController = rememberNavController()

    AndroidPracticeTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.Gray
                ) {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Filled.List,
                                contentDescription = "List",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Список фильмов",
                                color = Color.White,
                                style = Typography.labelSmall
                            )

                        },
                        selected = false,
                        onClick = {
                            navController.navigate("list")
                        }
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                Icons.Rounded.AccountCircle,
                                contentDescription = "Profile",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Мой Профиль",
                                color = Color.White,
                                style = Typography.labelSmall
                            )

                        },
                        selected = false,
                        onClick = {
                            navController.navigate("profile")
                        }
                    )
                }
            }
        ) { _ ->
            NavGraph(navController)
        }
    }
}
