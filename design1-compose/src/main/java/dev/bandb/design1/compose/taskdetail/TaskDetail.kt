package dev.bandb.design1.compose.taskdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.bandb.design1.compose.R
import dev.bandb.design1.compose.ui.theme.Gray100
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup

@Composable
fun TaskDetail(navController: NavHostController, task: TaskGroup) {
    Column {
        TopAppBar(
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            elevation = 0.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.popBackStack() },
                colorFilter = ColorFilter.tint(color = Color.Black),
                alignment = Alignment.CenterStart
            )
            Image(
                painter = painterResource(id = R.drawable.ic_kebab_menu),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = Color.Black)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Image(
                painter = painterResource(id = task.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Gray100, CircleShape)
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(colorResource(id = task.color))
            )

            Text(text = "${task.tasks.size} Tasks")

            Text(task.name, style = MaterialTheme.typography.h3)

            val progress = if (task.tasks.isNotEmpty()) {
                (1f / task.tasks.size) * task.tasks.count { task -> task.finished }
            } else 0f

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    color = colorResource(
                        id = task.color
                    ),
                    modifier = Modifier
                        .height(2.dp)
                        .weight(1f)
                )

                Text("${(progress * 100).toInt()}%", modifier = Modifier.padding(start = 12.dp))
            }

            LazyColumn {
                items(task.tasks) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = it.finished,
                            onCheckedChange = {},
                            modifier = Modifier
                                .padding(8.dp)
                        )

                        Column {
                            Text(
                                text = it.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Divider(color = Gray100)
                        }
                    }
                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun TaskDetailPreview() {
    val navController = rememberNavController()
    TaskDetail(navController, SampleData.taskGroups.first())
}