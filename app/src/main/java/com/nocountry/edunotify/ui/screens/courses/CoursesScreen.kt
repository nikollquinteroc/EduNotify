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

data class SchoolTest(
    val id: Int,
    val cours: List<CourseTest>
)

data class CourseTest(
    val id: Int,
    val name: String
)

val schoolTest1 = SchoolTest(
    id = 1,
    cours = listOf(
        CourseTest(1, "A"),
        CourseTest(2, "B"),
        CourseTest(3, "C"),
        CourseTest(4, "D"),
        CourseTest(5, "E"),
        CourseTest(6, "F"),
    )
)
val schoolTest2 = SchoolTest(
    id = 1,
    cours = listOf(
        CourseTest(1, "G"),
        CourseTest(2, "H"),
        CourseTest(3, "I"),
        CourseTest(4, "J"),
        CourseTest(5, "K"),
        CourseTest(6, "L"),
    )
)

@Composable
fun CoursesScreen(schoolTest: SchoolTest, onAddCoursesClicked: () -> Unit) {
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
fun CoursesList(schoolTest: SchoolTest) {

}

@Composable
fun CourseItem(courseTest: CourseTest) {
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
        CoursesScreen(schoolTest = schoolTest1, onAddCoursesClicked = {})
    }
}
