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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prappviagens.ui.theme.MenuBar
import com.example.prappviagens.ui.theme.NovoCadastro
import com.example.prappviagens.ui.theme.PrAppViagensTheme
import com.example.prappviagens.viewModel.RegisterNewUserViewModel
import com.example.prappviagens.viewModel.RegisterNewUserViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrAppViagensTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PrAppViagem()
                }
            }
        }
    }
}

@Composable
fun PrAppViagem() {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewUserViewModel = viewModel(
        factory = RegisterNewUserViewModelFactory(application)
    )
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(onNavigateMenuBar = {
                navController.navigate("menubar")
            },
                onNavigateNovoCadastro = {
                    navController.navigate("novocadastro")
                })
        }
        composable("menubar") {
            MenuBar()
        }
        composable("novocadastro") {
            NovoCadastro(onAfterSave = {
                navController.navigateUp()
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "User registered"
                    )
                }
            },
                onBack = {
                    navController.navigateUp()
                }
            )
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
       PrAppViagem()
    }
}