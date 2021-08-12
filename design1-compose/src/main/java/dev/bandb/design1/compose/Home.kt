package dev.bandb.design1.compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.bandb.design1.compose.ui.theme.Gray100
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup

@Composable
fun Home(taskGroups: List<TaskGroup>, onTaskClicked: (Int) -> Unit) {
    val backgroundColor = remember { mutableStateOf(taskGroups[0].color) }
    val color by animateColorAsState(
        targetValue = colorResource(id = backgroundColor.value),
        animationSpec = tween(durationMillis = 500)
    )

    Surface(color = color) {
        Column(modifier = Modifier.fillMaxHeight()) {
            TopAppBar(
                title = {
                    Text(
                        text = "TODO",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                },
                backgroundColor = Color.Transparent,
                contentColor = Color.White,
                elevation = 0.dp
            )

            HomeDetails(
                modifier = Modifier.padding(
                    start = 24.dp,
                    top = 24.dp,
                    end = 24.dp,
                    bottom = 12.dp
                )
            )

            TaskList(taskGroups, onTaskChanged = { taskGroup ->
                backgroundColor.value = taskGroup.color
            }, modifier = Modifier.padding(bottom = 64.dp), onTaskClicked = onTaskClicked)
        }
    }
}

@Composable
fun HomeDetails(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.avatar_9_raster),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .shadow(elevation = 12.dp, shape = CircleShape, clip = false)
        )

        Text(
            text = "Hello, Jane.",
            style = MaterialTheme.typography.h4,
            color = Color.White,
            modifier = Modifier.padding(top = 28.dp)
        )

        Text(
            "Looks like feel good.\nYou have 3 tasks to do today.",
            color = Gray100,
            modifier = Modifier.padding(top = 14.dp)
        )

        Text(
            "TODAY: SEPTEMBER 12, 2017", color = Color.White,
            modifier = Modifier.padding(top = 48.dp),
            style = MaterialTheme.typography.overline
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun HomePreview() {
    Home(SampleData.taskGroups) {}
}