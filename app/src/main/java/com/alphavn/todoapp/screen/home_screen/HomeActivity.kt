package com.alphavn.todoapp.screen.home_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alphavn.todoapp.R
import com.alphavn.todoapp.component.AdmobWidget
import com.alphavn.todoapp.component.DialogDetail
import com.alphavn.todoapp.component.EmptyComponent
import com.alphavn.todoapp.component.HeaderText
import com.alphavn.todoapp.component.IconButton
import com.alphavn.todoapp.component.InputSearch
import com.alphavn.todoapp.component.RowItem
import com.alphavn.todoapp.model.NavigationRoutes
import com.alphavn.todoapp.screen.detail_screen.DetailActivity
import com.alphavn.todoapp.ui.theme.Black
import com.alphavn.todoapp.ui.theme.Red
import com.alphavn.todoapp.ui.theme.TodoAppTheme
import com.alphavn.todoapp.ui.theme.White
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(
                    listOf(
                        AdRequest.DEVICE_ID_EMULATOR,
                        "BB55DD4AA96D76191718C5446D74EC7"
                    )
                ).build()
        )

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TodoAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.HomeScreen.name,
                ) {
                    composable(route = NavigationRoutes.HomeScreen.name) {
                        HomeScreen(onClickItem = {
                            navController.navigate(NavigationRoutes.DetailScreen.name + "/${it ?: "a"}")
                        })
                    }
                    composable(
                        route = NavigationRoutes.DetailScreen.name + "/{todo-detail}",
                        arguments = listOf(navArgument("todo-detail") {
                            type = NavType.StringType
                            defaultValue = ""
                            nullable = false
                        })
                    ) { entry ->
                        DetailActivity(idNote = entry.arguments?.getString("todo-detail") ?: "a",
                            onTapBack = {
                                navController.navigate(NavigationRoutes.HomeScreen.name)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onClickItem: (String?) -> Unit) {
    val homeViewModel: HomeViewModel = hiltViewModel()
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
                        onClickItem(null)
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
            bottomBar = {
                AdmobWidget(
                    modifier = Modifier.fillMaxWidth()
                )
            },
            containerColor = Black,
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogDetail()
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(25.dp), modifier = Modifier.padding(
                        horizontal = 24.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    reverseLayout = true
                ) {
                    val countList = homeViewModel.todoShowList.size
                    val isSearch = homeViewModel.isShowHeaderSearch

                    items(count = countList,
                        key = { ind ->
                            homeViewModel.todoShowList[ind].id
                        }) { index ->
                        val item = homeViewModel.todoShowList[index]
                        val dismissState = rememberDismissState()

                        if (dismissState.isDismissed(direction = DismissDirection.EndToStart)) {
                            homeViewModel.deleteNote(item)
                        }

                        SwipeToDismiss(state = dismissState, directions = setOf(
                            DismissDirection.EndToStart
                        ),
                            background = {
                                val backgroundColor by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        DismissValue.DismissedToStart -> Red.copy(alpha = 0.8f)
                                        else -> Red
                                    }, label = ""
                                )

                                val iconScale by animateFloatAsState(
                                    targetValue = if (dismissState.targetValue == DismissValue.DismissedToStart) 1f else 0.2f,
                                    label = ""
                                )

                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = backgroundColor,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(end = 16.dp),
                                    contentAlignment = Alignment.CenterEnd // place the icon at the end (left)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.delete),
                                        contentDescription = "icon_floating",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .scale(iconScale),
                                        colorFilter = ColorFilter.tint(color = White)
                                    )

                                }
                            },
                            dismissContent = {
                                RowItem(item = item,
                                    modifier = Modifier.clickable {
                                        onClickItem(item.id)
                                    }
                                )
                            })
                    }

                    if (countList == 0) {
                        item {
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
    val homeViewModel: HomeViewModel = hiltViewModel()
    if (homeViewModel.isShowHeaderSearch) {
        InputSearch(
            onValueChange = { homeViewModel.onSearchChange(it) },
            onPressClose = {
                homeViewModel.closeInputSearch()
            },
            valueInput = homeViewModel.valueInputSearch,
            modifier = Modifier.padding(top = 38.dp),
        )
    } else {
        Row(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 38.dp, bottom = 20.dp)
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
