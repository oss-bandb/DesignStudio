package dev.bandb.designstudio.design1.common

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import dev.bandb.designstudio.design1.common.utils.today
import dev.bandb.designstudio.design1.common.utils.todayTime
import kotlinx.datetime.*

data class Task(
    val name: String,
    val createdAt: LocalDateTime,
    val dueAt: LocalDate,
    val finished: Boolean = false
)

data class TaskGroup(
    val name: String,
    val tasks: List<Task>,
    @DrawableRes val icon: Int? = null,
    @ColorRes val color: Int = R.color.default_background_color        // TODO can we make this a gradient?
)

object SampleData {
    val taskGroups = listOf(
        TaskGroup(
            name = "Personal",
            tasks = listOf(
                Task(
                    name = "1",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "2",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "3",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                ),
                Task(
                    name = "4",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "5",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "6",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                ),
                Task(
                    name = "7",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "8",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "9",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
            ),
            icon = R.drawable.ic_person,
            color = R.color.salmon
        ),

        TaskGroup(
            name = "Work",
            tasks = listOf(
                Task(
                    name = "Meet Clients",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "Design Sprint",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "Icon Set Design for Mobile App",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                ),
                Task(
                    name = "HTML/CSS Study",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                ),
                Task(
                    name = "Weekly Report",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today().plus(
                        1,
                        DateTimeUnit.DAY
                    ),
                ),
                Task(
                    name = "Design Meeting",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today().plus(
                        1,
                        DateTimeUnit.DAY
                    ),
                ),
                Task(
                    name = "Quick Prototyping",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today().plus(
                        1,
                        DateTimeUnit.DAY
                    ),
                ),
                Task(
                    name = "UX Conference",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today().plus(
                        1,
                        DateTimeUnit.DAY
                    ),
                ),
                Task(
                    name = "Design1 Finish",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today().plus(
                        2,
                        DateTimeUnit.DAY
                    ),
                ),
                Task(
                    name = "Next Project",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today().plus(
                        2,
                        DateTimeUnit.DAY
                    ),
                ),
            ),
            icon = R.drawable.ic_work,
            color = R.color.cornflower_blue
        ),

        TaskGroup(
            name = "Home",
            tasks = listOf(
                Task(
                    name = "Clean Kitchen",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "Re-order books",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "Go for a walk with the dogs",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                ),
                Task(
                    name = "Baking a cake",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                ),
            ),
            icon = R.drawable.ic_home,
            color = R.color.puerto_rico
        ),

        TaskGroup(
            name = "Vacation",
            tasks = listOf(
                Task(
                    name = "Pack clothes",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "Pack medication",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                    finished = true
                ),
                Task(
                    name = "Buy hand sanitizer",
                    createdAt = Clock.System.now().todayTime(),
                    dueAt = Clock.System.now().today(),
                ),
            ),
            icon = R.drawable.ic_airplane,
            color = R.color.default_background_color
        )
    )
}