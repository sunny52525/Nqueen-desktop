import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {

    var gridSize by remember {
        mutableStateOf(8)
    }
    Column {
        Spacer(Modifier.height(20.dp))
        Heading()
        Spacer(Modifier.height(20.dp))
        Controller(gridSize, onGridChange = {
            gridSize = it
        })
        Spacer(Modifier.height(20.dp))
        Grid(gridSize, arrayListOf())
    }
}


@Composable
fun Heading() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {

        Text("NQueens Visualizer", color = Black)
    }
}

@Composable
fun Controller(gridSize: Int, onGridChange: (Int) -> Unit) {


    var dropDownExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()

    ) {
        Button(onClick = {

        }, content = {
            Text(text = "Run")
        })
        Button(onClick = {

        }, content = {
            Text(text = "Reset")
        })

        Box(
            content = {
                Column {
                    Button(
                        onClick = {
                            dropDownExpanded = true
                        }, content = {
                            Text(gridSize.toString(), color = White)
                        }
                    )
                    DropdownMenu(expanded = dropDownExpanded, content = {
                        Constants.grids.forEach {
                            DropdownMenuItem(onClick = {
                                onGridChange(it)
                                dropDownExpanded = false
                            }, content = {
                                Text(
                                    text = it.toString(),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = Black
                                )
                            })
                        }
                    }, onDismissRequest = {
                        dropDownExpanded = false
                    })
                }

            }
        )

    }
}


@Composable
fun Grid(gridSize: Int, solutions: ArrayList<ArrayList<Int>>) {


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier.border(width = 1.dp, color = Red)
        ) {
            repeat(gridSize) {
                Row {
                    repeat(gridSize) {
                        GridItem(White)
                    }
                }
            }

        }


    }
}


@Composable
fun GridItem(color: Color) {
    Box(modifier = Modifier.background(color).size(45.dp).border(1.dp, Green))
}