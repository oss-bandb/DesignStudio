package dev.bandb.design1.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TaskList(taskGroups: List<TaskGroup>, onTaskChanged : (TaskGroup) -> Unit, modifier: Modifier = Modifier, ) {
    val pagerState = rememberPagerState(pageCount = taskGroups.size)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onTaskChanged(taskGroups[page])
        }
    }
    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = pagerState,
        itemSpacing = 24.dp
    ) { page ->
        val item = taskGroups[page]


        Card(
            modifier = modifier.fillMaxWidth(0.8f),
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                TopAppBar(
                    modifier = Modifier.padding(bottom = 16.dp),
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White,
                    elevation = 0.dp
                ) {
                    val iconColor = colorResource(id = item.color)
                    val iconResource = item.icon ?: R.drawable.ic_person
                    Image(
                        painter = painterResource(id = iconResource),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(50))
                            .border(2.dp, Color.Black),
                        colorFilter = ColorFilter.tint(iconColor)
                    )
                }

                Text("${item.tasks.size} Tasks")
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(item.name, style = MaterialTheme.typography.h3)
                }

                val progress = if (item.tasks.isNotEmpty()) {
                    (1f / item.tasks.size) * item.tasks.count { task -> task.finished }
                } else 0f

                Row(verticalAlignment = Alignment.CenterVertically) {
                    LinearProgressIndicator(
                        progress = progress,
                        color = colorResource(
                            id = item.color
                        )
                    )

                    Text("${(progress * 100).toInt()}%", modifier = Modifier.padding(start = 12.dp))
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun TaskListPreview() {
    TaskList(SampleData.taskGroups, {})
}