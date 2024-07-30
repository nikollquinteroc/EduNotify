package com.nocountry.edunotify.ui.screens.courses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nocountry.edunotify.R
import com.nocountry.edunotify.domain.model.CourseInfoDomain
import com.nocountry.edunotify.domain.model.LevelDomain
import com.nocountry.edunotify.domain.model.YearDomain
import com.nocountry.edunotify.ui.components.CircleButtonComponent
import com.nocountry.edunotify.ui.components.SpacerComponent
import com.nocountry.edunotify.ui.components.TopAppBarComponent
import com.nocountry.edunotify.ui.theme.EduNotifyTheme

@Composable
fun CoursesScreen(
    coursesViewModel: CoursesViewModel = viewModel(factory = CoursesViewModel.provideFactory()),
    onBackClicked: () -> Unit,
    schoolId: Int,
    userId: Int,
    onAddCoursesClicked: (CourseInfoDomain) -> Unit
) {
    val coursesUiState by coursesViewModel.uiState.collectAsState()
    var selectedCourse by rememberSaveable { mutableStateOf<CourseInfoDomain?>(null) }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = R.string.courses,
                navigationIcon = {
                    CircleButtonComponent(
                        onClick = { onBackClicked() },
                        icon = R.drawable.arrow_back
                    )
                },
                actions = null
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                coursesUiState.isLoading -> {
                    CircularProgressIndicator()
                }

                coursesUiState.error.isNotEmpty() -> {
                    Text(text = "Error: ${coursesUiState.error}")
                }

                else -> {
                    Box(modifier = Modifier.weight(1f)) {
                        CoursesList(
                            levels = coursesUiState.coursesUI.schoolInfoDomain?.levels
                                ?: emptyList(),
                            selectedCourse = selectedCourse,
                            onCourseSelected = { courseInfoDomain ->
                                selectedCourse = courseInfoDomain
                            }
                        )
                    }
                    Button(
                        modifier = Modifier
                            .width(350.dp)
                            .height(52.dp),
                        shape = RoundedCornerShape(size = 8.dp),
                        onClick = {
                            selectedCourse?.let { courseInfoDomain ->
                                coursesViewModel.assignCourseToUser(
                                    courseId = courseInfoDomain.courseId,
                                    userId = userId
                                )
                                onAddCoursesClicked(courseInfoDomain)
                            }
                        },
                        enabled = selectedCourse != null
                    ) {
                        Text(
                            text = stringResource(id = R.string.bottom_add_courses),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    SpacerComponent(height = 20.dp)
                }
            }
        }
    }
}

@Composable
fun CoursesList(
    levels: List<LevelDomain>,
    selectedCourse: CourseInfoDomain?,
    onCourseSelected: (CourseInfoDomain) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        levels.forEach { levelDomain ->
            item {
                Text(
                    text = levelDomain.schoolLevel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                SpacerComponent(height = 10.dp)
            }
            items(levelDomain.years) { yearDomain ->
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Año: ${yearDomain.year}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 10.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        yearDomain.courses.forEach { courseInfoDomain ->
                            CourseItem(
                                courseInfo = courseInfoDomain,
                                isSelected = selectedCourse == courseInfoDomain,
                                onCourseSelected = onCourseSelected
                            )
                        }
                    }
                }
                SpacerComponent(height = 20.dp)
            }
        }
    }
}

@Composable
fun CourseItem(
    courseInfo: CourseInfoDomain,
    isSelected: Boolean,
    onCourseSelected: (CourseInfoDomain) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable {
                if (!isSelected) {
                    onCourseSelected(courseInfo)
                }
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = courseInfo.course,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoursesScreenPreview() {
    EduNotifyTheme {
        //CoursesScreen(onAddCoursesClicked = {}, schoolId = 1, userId = 1, )
        CoursesList(
            levels = listOf(
                LevelDomain(
                    "PREESCOLAR",
                    listOf(
                        YearDomain(
                            year = "1",
                            courses = listOf(
                                CourseInfoDomain("sala roja", 1),
                                CourseInfoDomain("sala roja", 1),
                                CourseInfoDomain("sala roja", 1)
                            )
                        )
                    )
                )
            ), onCourseSelected = {}, selectedCourse = null
        )
    }
}
