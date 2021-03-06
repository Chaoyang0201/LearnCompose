package com.chaoy.compose.layouts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
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
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    @ExperimentalLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate: ")
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


@ExperimentalLayout
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
        Row(modifier = Modifier.fillMaxWidth().preferredHeight(intrinsicSize = IntrinsicSize.Min).background(color = Color.LightGray)) {
            Text(text = "Hi", modifier = Modifier.weight(1f).wrapContentWidth(align = Alignment.Start))
            Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().preferredWidth(1.dp))
            Text(text = "there", modifier = Modifier.weight(1f).wrapContentWidth(align = Alignment.End))
        }
    }
}

@Composable
fun DecoupledConstraintsLayoutExample() {
    WithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp)
        } else {
            decoupledConstraints(margin = 32.dp)
        }


        ConstraintLayout(constraintSet = constraints) {
            Button(onClick = {}, modifier = Modifier.layoutId("button")) {
                Text("Button")
            }

            Text("Text", modifier = Modifier.layoutId("text"))
        }
    }
}

fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            centerTo(parent)
        }

        constrain(text) {
            start.linkTo(button.end, margin = margin)
            top.linkTo(button.bottom, margin = margin)
        }
    }
}

@Composable
private fun StaggerGridExample() {
    val random = Random(1000)


    ScrollableRow {
        StaggeredGrid(rows = 4) {
            for (i in 0..100) {
                val randomColor = Color(red = random.nextInt(0, 255), green = random.nextInt(0, 255), blue = random.nextInt(0, 255))
                val length = random.nextInt(1, 20)
                val text = buildString {
                    (0..length).forEach { _ -> append("${length % 9}") }
                }

                Chip(modifier = Modifier.padding(4.dp), color = randomColor, text = text)

            }
        }
    }
}


@Composable
fun ConstraintLayoutExample() {
    Column(modifier = Modifier.fillMaxHeight()) {
        ConstraintLayout(modifier = Modifier.background(color = Color.LightGray).fillMaxWidth().fillMaxHeight(.5f)) {
            val (button, text, icon, row) = createRefs()
            //
            val barrier = createEndBarrier(button, icon)

            Button(
                    onClick = {},
                    modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(button) {
                                top.linkTo(parent.top, margin = 16.dp)

                            },
            ) {
                Text("Button")
            }

            Text(
                    text = "Text",
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(button.bottom, margin = 16.dp)
                    },
            )


            IconButton(
                    onClick = {},
                    modifier = Modifier.constrainAs(icon) { centerTo(parent) },
            ) {

                Row(modifier = Modifier.background(Color.Yellow)) {
                    Icon(asset = Icons.Default.Face)
                    Text("Face")
                }


            }

            Row(modifier = Modifier.background(Color.Yellow).constrainAs(row) {
                top.linkTo(icon.bottom)
                end.linkTo(barrier)
            }) {
                Icon(asset = Icons.Default.Face)
                Text("Face11111111122222222")
            }

        }


    }
}

@Composable
private fun GuidelineSample() {
    ConstraintLayout(modifier = Modifier.background(Color.DarkGray).fillMaxWidth(.6f).fillMaxHeight()) {
        val text = createRef()

        val guideline = createGuidelineFromStart(.5f)

        Text(
                text = "This is a very very very very very very very very very long text", color = Color.White,
                modifier = Modifier.constrainAs(text) {
                    width = Dimension.fillToConstraints
                    linkTo(start = guideline, end = parent.end)
                },
        )

    }
}

@Composable
private fun Chip(color: Color, text: String, modifier: Modifier = Modifier) {

    Card(
            modifier = modifier,
            border = BorderStroke(1.dp, color = color),
            shape = RoundedCornerShape(10.dp),
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.preferredSize(16.dp).background(color))
            Spacer(modifier = Modifier.preferredSize(4.dp))
            Text(text = text)
        }
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
fun DefaultPreview() {
    Chip(color = Color.Red, text = "This is a chip view")
}

@ExperimentalLayout
@Preview
@Composable
fun StaggerGridPreview() {
    LayoutsCodelab()
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


@Composable
fun StaggeredGrid(
        modifier: Modifier = Modifier,
        rows: Int = 3,
        children: @Composable() () -> Unit
) {
    println("StaggeredGrid rows = $rows")
    Layout(
            modifier = modifier,
            children = children,
    ) { measureables, constraints ->
        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }
        val placeables = measureables.mapIndexed { index, measurable ->
            val row = index % rows
            // 0 1 2
            //
            // 3(0) 4(1) 5(2)
            val placeable = measurable.measure(constraints)
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(placeable.height, rowHeights[row])

            placeable
        }


        val totalHeight = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)
        val maxWidth = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
                ?: constraints.minWidth


        val rowYs = IntArray(rows) { 0 }
        for (i in 1 until rowYs.size) {
            rowYs[i] = rowYs[i - 1] + rowHeights[i - 1]
        }


        layout(width = maxWidth, height = totalHeight) {


            var placeX = 0

            val rowXs = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->

                val row = index % rows

                placeable.place(rowXs[row], rowYs[row])

                rowXs[row] += placeable.width

            }

        }

    }
}