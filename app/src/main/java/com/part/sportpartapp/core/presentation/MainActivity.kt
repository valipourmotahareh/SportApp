package com.part.sportpartapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.part.sportpartapp.sportList.util.Screen
import com.part.sportpartapp.ui.theme.SportAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.Modifier
import com.part.sportpartapp.detail.presentation.DetailScreen


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportAppTheme {
               Surface (modifier = Modifier.fillMaxSize(),color= MaterialTheme.colorScheme.background){

                 val navController = rememberNavController()

                   NavHost(navController = navController,
                       startDestination = Screen.Home.rout
                   ){
                       composable(Screen.Home.rout){
                         HomeScreen(navController)
                       }

                       composable(
                           Screen.Details.rout+"/{sportId}",
                       arguments = listOf(
                           navArgument("sportId"){type= NavType.IntType}
                       )
                       ){
                           DetailScreen()

                       }
                   }
               }
            }

        }
    }
}