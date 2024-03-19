package br.com.greenview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.com.greenview.ui.theme.GreenViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Blue
                ) {
                    Tela()
                }
            }
        }
    }
}


@Composable
fun Tela() {
    Column(
        modifier = Modifier
            .background(Color(android.graphics.Color.parseColor("#2BABF0")))
            .fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPreview() {
    GreenViewTheme {
        Tela()
    }
}