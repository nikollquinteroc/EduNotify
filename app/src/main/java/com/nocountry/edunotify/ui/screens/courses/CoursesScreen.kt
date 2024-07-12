package com.nocountry.edunotify.ui.screens.courses

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

data class School(
    val id: Int,
    val courses: List<Course>
)

data class Course(
    val id: Int,
    val name: String
)

val school1 = School(
    id = 1,
    courses = listOf(
        Course(1, "A"),
        Course(2, "B"),
        Course(3, "C"),
        Course(4, "D"),
        Course(5, "E"),
        Course(6, "F"),
    )
)
val school2 = School(
    id = 1,
    courses = listOf(
        Course(1, "G"),
        Course(2, "H"),
        Course(3, "I"),
        Course(4, "J"),
        Course(5, "K"),
        Course(6, "L"),
    )
)

@Composable
fun CoursesScreen(school: School, onAddCoursesClicked: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Courses Screen")
            Button(onClick = onAddCoursesClicked) {
                Text(text = "add courses")
            }
        }
    }
}

@Composable
fun CoursesList(school: School) {

}

@Composable
fun CourseItem(course: Course) {
    val (checkedState, onStateChange) = rememberSaveable { mutableStateOf(true) }
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable(
                value = checkedState,
                onValueChange = { onStateChange(!checkedState) },
                role = Checkbox
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = null
        )
        Text(
            text = "Option selection",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview
@Composable
fun CoursesScreenPreview() {
    EduNotifyTheme {
        CoursesScreen(school = school1, onAddCoursesClicked = {})
    }
}
