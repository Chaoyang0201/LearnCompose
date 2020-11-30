package com.chaoy.compose.tryout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.chaoy.compose.tryout.ui.JetpackComposeTryoutTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }


}

@Composable
fun MyScreenContent(names: List<String> = listOf("Android", "there")) {

    val counterState = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(weight = 1.0f)) {
            for (name in names) {
                Greeting(name)
                Divider(color = Color.Black)
            }


        }
        Counter(count = counterState.value, updateCount = { cnt -> counterState.value = cnt })
    }
}


@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {

    Button(
        onClick = { updateCount.invoke(count + 1) },
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text("Click $count times")
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetpackComposeTryoutTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.Green) {
            content()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        modifier = Modifier.padding(24.dp),
        style = MaterialTheme.typography.h1,
    )

}

@Preview(showBackground = true, name = "Text Preview")
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}