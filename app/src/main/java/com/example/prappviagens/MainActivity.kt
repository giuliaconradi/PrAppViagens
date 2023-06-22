package com.example.prappviagens
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prappviagens.ui.theme.NovoCadastro
import com.example.prappviagens.ui.theme.PrAppViagensTheme
import com.example.prappviagens.viewModel.RegisterNewUserViewModel
import com.example.prappviagens.viewModel.RegisterNewUserViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrAppViagensTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(
                onNavigateMenuBar = { id ->
                    navController.navigate("MenuBar/$id")
                },
                onNavigateNovoCadastro = {
                    navController.navigate("NovoCadastro")
                },
                onNavigateNovaViagem = { id ->
                    navController.navigate("NovaViagem/$id")
                },
                onNavigateListaViagens = {
                    navController.navigate("ListaViagens")
                },
            )
        }

        composable(
            "MenuBar/{userID}",
            arguments = listOf(navArgument("userID") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("userID")
            if (id != null) {
                MenuBar(
                    id
                )
            }
        }
        composable("NovoCadastro") {
            NovoCadastro {
                navController.navigateUp()
            }
        }
        composable(
            "ListaViagens",
            arguments = listOf(navArgument("userID") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("userID")
        }

        composable(
            "NovaViagem/{userID}",
            arguments = listOf(navArgument("userID") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("userID")
            if (id != null) {
                NovaViagem(
                    onNavigateMenuBar = { navController.navigateUp() },
                    id
                )
            }
        }

    }
}
        
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    PrAppViagensTheme {
        Greeting("Android")
    }
}