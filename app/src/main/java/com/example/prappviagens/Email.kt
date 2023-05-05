package com.example.prappviagens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun SecondApp(){
    val context= LocalContext.current
    Column(verticalArrangement = Arrangement.Center) {
        Button(
            onClick = { /* Handle login */
                composeEmail(context, arrayOf("email@email.com"), "Assunto do email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue
            )
        ){
            Text("Send a email", color = Color.White)
        }
    }
}

fun composeEmail(context: Context, address: Array<String>, subject:String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "*/"
        putExtra(Intent.EXTRA_EMAIL, address)
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }

    ContextCompat.startActivity(
        context, Intent.createChooser(intent, "Send Email"),
        null
    )
}