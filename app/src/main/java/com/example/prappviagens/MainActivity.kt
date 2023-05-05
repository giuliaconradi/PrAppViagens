package com.example.prappviagens
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prappviagens.ui.theme.MenuBar
import com.example.prappviagens.ui.theme.PrAppViagensTheme

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
    val navController = rememberNavController()
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
            NovoCadastro ()
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