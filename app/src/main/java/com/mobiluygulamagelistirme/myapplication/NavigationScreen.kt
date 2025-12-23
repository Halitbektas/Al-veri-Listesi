package com.mobiluygulamagelistirme.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mobiluygulamagelistirme.myapplication.feature.HomeScreen
import com.mobiluygulamagelistirme.myapplication.feature.ListScreen
import com.mobiluygulamagelistirme.myapplication.feature.samplemarketList
import com.mobiluygulamagelistirme.myapplication.feature.viewmodel.AppViewModelProvider
import com.mobiluygulamagelistirme.myapplication.feature.viewmodel.CreateListViewModel
import com.mobiluygulamagelistirme.myapplication.feature.viewmodel.HomeViewModel


// ... importlar ...

@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        modifier = modifier
    ) {
        // --- ANA EKRAN ---
        composable(route = Screens.HomeScreen.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)

            // Ekran her göründüğünde listeyi yenile
            LaunchedEffect(Unit) { homeViewModel.updateUiList() }

            HomeScreen(
                onNavigateListScreen = { id, isClone ->
                    if (id != null) {
                        // isClone bilgisini de URL'ye ekliyoruz (1 = true, 0 = false)
                        val cloneInt = if (isClone) 1 else 0
                        navController.navigate("listScreen?id=$id&isClone=$cloneInt")
                    } else {
                        navController.navigate("listScreen")
                    }
                },
                viewModel = homeViewModel
            )
        }

        // --- LİSTE EKRANI ---
        composable(
            route = Screens.ListScreen.route + "&isClone={isClone}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType; defaultValue = -1 },
                navArgument("isClone") { type = NavType.IntType; defaultValue = 0 }
            )
        ) { backStackEntry ->
            val createListViewModel: CreateListViewModel = viewModel(factory = AppViewModelProvider.Factory)

            val listId = backStackEntry.arguments?.getInt("id") ?: -1
            val isClone = backStackEntry.arguments?.getInt("isClone") == 1

            // LaunchedEffect ekran döndüğünde tekrar çalışır.
            // AMA ViewModel içindeki kontrol sayesinde veriler sıfırlanmaz.
            LaunchedEffect(listId, isClone) {
                createListViewModel.initialize(listId, isClone)
            }

            ListScreen(
                marketItemList = samplemarketList,
                viewModel = createListViewModel,
                onNavigateHomeScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}
