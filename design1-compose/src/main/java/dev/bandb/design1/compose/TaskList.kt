package dev.bandb.design1.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import dev.bandb.designstudio.design1.common.SampleData
import dev.bandb.designstudio.design1.common.TaskGroup

@Composable
fun TaskList(taskGroups: List<TaskGroup>, modifier: Modifier = Modifier) {

    val listState = rememberLazyListState()
//    println("${lazyListState.firstVisibleItemIndex}")

    Box(modifier = Modifier.nestedScroll(NestedScrollThing(listState))) {
        LazyRow(modifier = modifier, state = listState) {
            items(taskGroups) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        TopAppBar {
                            val iconResource = it.icon ?: R.drawable.ic_person
                            Image(
                                painter = painterResource(id = iconResource),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(50))
                                    .border(2.dp, Color.Black)
                            )
                        }

                        Text("${it.tasks.size} Tasks")
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomStart) {
                            Text(it.name, style = MaterialTheme.typography.h3)
                        }

                        val progress = if (it.tasks.isNotEmpty()) {
                            (1f / it.tasks.size) * it.tasks.count { task -> task.finished }
                        } else 0f

                        Row {
                            LinearProgressIndicator(
                                progress = progress,
                                color = colorResource(id = it.color ?: R.color.default_background_color)
                            )

                            Text("${(progress * 100).toInt()}%")
                        }
                    }
                }
            }
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun TaskListPreview() {
    TaskList(SampleData.taskGroups)
}

class NestedScrollThing(private val listState: LazyListState) : NestedScrollConnection {
    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        println("NestedScrollThing.onPostFling")
        println("consumed = [${consumed}], available = [${available}]")
        return super.onPostFling(consumed, available)
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        println("NestedScrollThing.onPostScroll")
        println("consumed = [${consumed}], available = [${available}], source = [${source}]")
        println("${listState.layoutInfo.visibleItemsInfo.first().index}")
        return super.onPostScroll(consumed, available, source)
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        println("NestedScrollThing.onPreFling")
        println("available = [${available}]")
        return super.onPreFling(available)
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        println("NestedScrollThing.onPreScroll")
        println("available = [${available}], source = [${source}]")
        return super.onPreScroll(available, source)
    }
}