import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun MainScreen() {

    var gridSize by remember {
        mutableStateOf(8)
    }
    var solutions: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(arrayListOf())
    }
    var solutionShown by remember {
        mutableStateOf(0)
    }

    Column(
//        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Heading()
        Spacer(Modifier.height(20.dp))

        if (solutions.isNotEmpty())
            Grid(gridSize, solutions[solutionShown], solutionShown)
        else
            Grid(gridSize, null, solutionShown)

        Spacer(Modifier.height(20.dp))
        Controller(gridSize, onGridChange = {
            gridSize = it
            solutions = NQueenSolution.solve(it)
            solutionShown = 0
        }, onRunClicked = {
            if (solutionShown + 1 == solutions.size) {
                solutionShown = 0
            } else {
                solutionShown++
            }
        }, onResetClicked = {
            solutionShown = 0
        })
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
fun Controller(gridSize: Int, onGridChange: (Int) -> Unit, onRunClicked: () -> Unit, onResetClicked: () -> Unit) {


    var dropDownExpanded by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()

    ) {
        Button(onClick = onRunClicked, content = {
            Text(text = "Run")
        })
        Button(onClick = onResetClicked, content = {
            Text(text = "Reset")
        })

        Box(
            content = {
                Column (modifier = Modifier.verticalScroll(rememberScrollState())
                ){
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
fun Grid(gridSize: Int, solutions: ArrayList<Int>?, solutionShown: Int) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.border(width = 1.dp, color = Red)
        ) {
            repeat(gridSize) { i ->
                Column {
                    repeat(gridSize) { j ->

                        if (solutions == null) {
                            GridItem(White)
                        } else {
                            if (solutions[i] - 1 == j)
                                GridItem(Green)
                            else
                                GridItem(White)
                        }
                    }
                }
            }

        }



        solutions?.let {
            Text(
                text = "Solution Number ${solutionShown + 1}",
                color = Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun GridItem(color: Color) {
    var active by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.background(color).size(45.dp).border(1.dp, Green).pointerMoveFilter(
        onEnter = {
            active = true
            false
        },
        onExit = {
            active = false
            false
        }
    ))
}