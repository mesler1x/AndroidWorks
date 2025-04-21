@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.androidpractice.content


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Favorite
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
                                Icons.Rounded.Favorite,
                                contentDescription = "Favourites",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Избранное",
                                color = Color.White,
                                style = Typography.labelSmall
                            )

                        },
                        selected = false,
                        onClick = {
                            navController.navigate("favourites")
                        }
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = "Профиль",
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = {
                            Text(
                                text = "Профиль",
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
