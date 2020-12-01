package com.chaoy.compose.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.chaoy.compose.layouts.ui.JetpackComposeTryoutTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTryoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LayoutsCodelab()
                }
            }
        }
    }
}

@Composable
fun PhotographerProfile(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier

            .padding(8.dp)
            .background(color = MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = {})
            .padding(16.dp)
            .fillMaxWidth()

    ) {
        Surface(
            modifier = Modifier.preferredSize(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = .2f),
        ) {

        }
        Column(modifier = Modifier.padding(start = 8.dp).align(Alignment.CenterVertically)) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)

            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
            }

        }
    }

}


@Composable
fun LayoutsCodelab() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Layouts codelab")
        },
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(Icons.Rounded.Favorite)
                }
            }
        )
    }) {
        BodyContent(it)
    }
}

@Composable
fun BodyContent(it: PaddingValues) {
    Column(modifier = Modifier.padding(it)) {
        Text(text = "Hi there")
        Text(text = "Go through layouts codelab")
    }
}

fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) =
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseLine = placeable[FirstBaseline]
        val placeableY = firstBaselineToTop.toIntPx() - firstBaseLine
        val height = placeable.height + placeableY

        layout(placeable.width, height) {
            placeable.place(0, placeableY)
        }


    }

//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeTryoutTheme {
        LayoutsCodelab()
    }
}

//@Preview(showBackground = true)
@Composable
fun TextWithPaddingToBaselinePreview() {
    Text(text = "Hi there!", modifier = Modifier.firstBaselineToTop(32.dp))
}

//@Preview(showBackground = true)
@Composable
fun TextWithNormalPaddingPreview() {
    Text(text = "Hi there!", modifier = Modifier.padding(top = 32.dp))
}

@Preview(showBackground = true)
@Composable
fun MyColumnPreview() {
    MyColumn(modifier = Modifier.padding(start = 10.dp).background(Color.Yellow)) {
        Text("123")
        Text("12344444444")
    }
}


@Composable
fun MyColumn(
    modifier: Modifier = Modifier,
    children: @Composable () -> Unit
) {
    Layout(modifier = modifier, children = children) { mearsurables, constrains ->


        val placeables = mearsurables.map { it.measure(constrains) }.toList()

        val columnWidth = placeables.maxOf { it.width }
        val columnHeight = placeables.sumOf { it.height }


        layout(columnWidth, columnHeight) {
            var height = 0;
            for (placeable in placeables) {
                placeable.place(0, height)
                height += placeable.height
            }
        }


    }

}