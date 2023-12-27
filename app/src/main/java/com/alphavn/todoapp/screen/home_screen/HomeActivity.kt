package com.alphavn.todoapp.screen.home_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alphavn.todoapp.R
import com.alphavn.todoapp.component.DialogDetail
import com.alphavn.todoapp.component.EmptyComponent
import com.alphavn.todoapp.component.HeaderText
import com.alphavn.todoapp.component.IconButton
import com.alphavn.todoapp.component.InputSearch
import com.alphavn.todoapp.component.RowItem
import com.alphavn.todoapp.model.NavigationRoutes
import com.alphavn.todoapp.screen.detail_screen.DetailActivity
import com.alphavn.todoapp.screen.detail_screen.DetailViewModel
import com.alphavn.todoapp.ui.theme.Black
import com.alphavn.todoapp.ui.theme.TodoAppTheme
import com.alphavn.todoapp.ui.theme.White

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TodoAppTheme {
                val navController = rememberNavController()

                val homeViewModel =
                    viewModel<HomeViewModel>(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return HomeViewModel(navController = navController) as T
                        }
                    })
                val detailViewModel =
                    viewModel<DetailViewModel>(factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return DetailViewModel(navController = navController) as T
                        }
                    })

                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.HomeScreen.name,
                ) {
                    composable(route = NavigationRoutes.HomeScreen.name) {
                        HomeScreen()
                    }
                    composable(
                        route = NavigationRoutes.DetailScreen.name + "/{todo-detail}",
                        arguments = listOf(navArgument("todo-detail") {
                            type = NavType.StringType
                            defaultValue = ""
                            nullable = false
                        })
                    ) { entry ->
                        DetailActivity(
                            navController = navController,
                            idNote = entry.arguments?.getString("todo-detail") ?: ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = viewModel()
    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.Black
    ) {
        Scaffold(
            topBar = {
                HeaderHome()
            },
            floatingActionButton = {
                if (homeViewModel.isShowHeaderSearch) {
                    return@Scaffold
                }
                FloatingActionButton(
                    onClick = {
                              homeViewModel.goToNoteDetail(null)
                    },
                    shape = CircleShape,
                    containerColor = Black,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 10.dp
                    ),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "icon_floating",
                        modifier = Modifier.size(48.dp),
                        colorFilter = ColorFilter.tint(color = White)
                    )
                }
            },
            containerColor = Black,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogDetail()
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    modifier = Modifier.padding(
                        horizontal = 24.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val countList = homeViewModel.todoShowList.size
                    val isSearch = homeViewModel.isShowHeaderSearch

                    items(count = countList) { index ->
                        val item = homeViewModel.todoShowList[index]
                        RowItem(item = item,
                            modifier = Modifier.clickable {
                                homeViewModel.goToNoteDetail(item)
                            })
                    }

                    if (countList == 0) {
                        item() {
                            EmptyComponent(isSearch = isSearch)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderHome() {
    val homeViewModel: HomeViewModel = viewModel()
    if (homeViewModel.isShowHeaderSearch) {
        InputSearch(
            onValueChange = { homeViewModel.onSearchChange(it) },
            onPressClose = {
                homeViewModel.closeInputSearch()
            },
            valueInput = homeViewModel.valueInputSearch,
        )
    } else {
        Row(
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 20.dp)
        ) {
            HeaderText("Notes", Modifier.weight(1f))
            IconButton(
                onClick = {
                    homeViewModel.showInputSearch()
                },
                icon = painterResource(id = R.drawable.search),
            )
            Spacer(modifier = Modifier.width(21.dp))
            IconButton(
                onClick = {
                    homeViewModel.openDialogDetail()
                },
                icon = painterResource(id = R.drawable.info_outline),
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoAppTheme {
        HomeScreen()
    }
}