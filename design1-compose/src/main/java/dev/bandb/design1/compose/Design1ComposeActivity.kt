package dev.bandb.design1.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.bandb.design1.compose.taskdetail.TaskDetail
import dev.bandb.design1.compose.ui.theme.DesignStudioTheme
import dev.bandb.designstudio.design1.common.SampleData

class Design1ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignStudioTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            Home(SampleData.taskGroups) {
                                navController.navigate("details/$it")
                            }
                        }
                        composable(
                            "details/{taskId}",
                            arguments = listOf(navArgument("taskId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            // TODO: 06-07-2021 21:57 check argument and throw exception if missing
                            TaskDetail(
                                navController = navController,
                                task = SampleData.taskGroups[backStackEntry.arguments!!.getInt(
                                    "taskId"
                                )]
                            )
                        }
                    }
                }
            }
        }
    }
}
