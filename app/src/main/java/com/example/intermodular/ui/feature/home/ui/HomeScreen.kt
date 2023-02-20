package com.example.intermodular.ui.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.intermodular.core.route.model.Route
import com.example.intermodular.ui.component.global.*
import com.example.intermodular.ui.component.route.RouteCard
import kotlinx.coroutines.delay

@Composable
fun Home(homeviewmodel : HomeViewModel, navigationController: NavHostController){

    val routes: Array<Route> by homeviewmodel.routes.observeAsState(initial = arrayOf())

    LaunchedEffect(true) {
        homeviewmodel.fetchRoutes()
    }
    LaunchedEffect(true) {
        while (true) {
            delay(1000)
            homeviewmodel.refreshRoutes()
        }
    }

    WikihonkBaseScreen(navigationController = navigationController) {
        RoutesContainer(
            routes = routes.toList(),
        ) {
            homeviewmodel.requestMoreRoutes()
        }
    }
}

@Composable
fun RoutesContainer(
    routes: List<Route>,
    onLoadMore: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier= Modifier
            .background(color = Color.Gray)
            .fillMaxSize()
    ) {
        items(items = routes) { route ->
            Row {
                RouteCard(route = route)
            }
        }

        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                CircularProgressIndicator()
            }
        }


        if (lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == routes.lastIndex) {
            onLoadMore()
        }

    }
}



