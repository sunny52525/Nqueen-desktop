import NQueenSolution.Companion.toSolution
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MainScreenInteractive(gotoSolution:()->Unit) {
    var grid: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(Constants.empty)
    }

    var results: ArrayList<ArrayList<Int>> by remember {
        mutableStateOf(NQueenSolution.solve(8))
    }
    var isDialog by remember {
        mutableStateOf(false)
    }

    var dialogMessage by remember {
        mutableStateOf("Uh Oh Try Again")
    }
    var checkEnabled by remember {
        mutableStateOf(true)
    }





    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Heading()


        GridInteractive(
            gridSize = 8, grid = grid,
            onBlockClicked = { i: Int, j: Int ->
                println(grid.size)
                printGrid(grid)
                grid[j][i] = 1

                val grid2: ArrayList<ArrayList<Int>> = arrayListOf()

                repeat(8) { ii ->
                    val oneRow = arrayListOf<Int>()
                    repeat(8) { jj ->
                        oneRow.add(grid[ii][jj])
                    }
                    grid2.add(oneRow)
                }

                grid.clear()

                grid = grid2

            }, undo = { i, j ->
                grid[j][i] = 0
            })


            AnimatedVisibility(isDialog){
                Text(
                    dialogMessage,
                    color = Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                println(grid.size)
                printGrid(grid)

                val result = NQueenSolution.checkSolutions(grid, 8)
                println(
                    result
                )

                val grid2: ArrayList<ArrayList<Int>> = arrayListOf()

                repeat(8) { ii ->
                    val oneRow = arrayListOf<Int>()
                    repeat(8) { jj ->
                        oneRow.add(grid[ii][jj])
                    }
                    grid2.add(oneRow)
                }
                println(grid2.size)


                result?.forEach {
                    grid2[it.first][it.second] = 2
                }

                if (results.contains(grid.toSolution(8))) {
                    dialogMessage = "Woo Hoo, You're correct"
                    isDialog = true
                } else {
                    dialogMessage = "Uh oh , Please try Again"
                    isDialog = true
                }

                grid = grid2


                println(grid.size)




                checkEnabled = false
            }, content = {
                Text(text = "Check")
            }, enabled = checkEnabled)

            Spacer(modifier = Modifier.width(20.dp))

            Button(onClick = gotoSolution, content = {
                Text(text = "Goto Solutions")
            })

            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                println("grisSzie" + grid.size)
                val grid2: ArrayList<ArrayList<Int>> = arrayListOf()
                repeat(8) {
                    val oneRow = arrayListOf<Int>()
                    repeat(8) {
                        oneRow.add(0)
                    }
                    grid2.add(oneRow)
                }
                grid.clear()
                grid = grid2
                println(grid.size)

                checkEnabled = true
                isDialog = false
            }, content = {
                Text(text = "Reset")
            })
        }
    }

}

private fun printGrid(grid: ArrayList<ArrayList<Int>>) {
    grid.forEach {
        it.forEach { num ->
            print("$num ,")
        }
        println()
    }
}

@ExperimentalFoundationApi
@Composable
fun GridInteractive(
    gridSize: Int,
    grid: ArrayList<ArrayList<Int>>,
    onBlockClicked: (Int, Int) -> Unit,
    undo: (Int, Int) -> Unit

) {
    print("**********")
    printGrid(grid)
    print("**********")


    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.border(width = 1.dp, color = Color.Red)
        ) {
            repeat(gridSize) { i ->
                Column {
                    repeat(gridSize) { j ->

                        var blockColor by remember {
                            mutableStateOf(White)
                        }

                        GridItemInteractive(
                            i = i, j = j,
                            onBlockClicked = onBlockClicked,
                            grid = grid,
                            gridSize = gridSize,
                            undo = undo,
                            blockColor = blockColor
                        ) {
                            blockColor = it
                        }
                    }
                }
            }

        }
    }
}


@ExperimentalFoundationApi
@Composable
fun GridItemInteractive(

    i: Int,
    j: Int,
    onBlockClicked: (Int, Int) -> Unit,
    grid: ArrayList<ArrayList<Int>>,
    gridSize: Int,
    undo: (Int, Int) -> Unit,
    blockColor: Color,
    onColorChange: (Color) -> Unit
) {
    var active by remember {
        mutableStateOf(false)
    }

    val density = LocalDensity.current





    Box(modifier = Modifier.background(
        if (grid.isNotEmpty()) {
            when {
                grid[j][i] == 1 -> Green
                grid[j][i] == 2 -> {
                    Color.Red
                }
                else -> {
                    blockColor

                }
            }
        } else {
            blockColor
        }
    ).size(45.dp).border(1.dp, Green)
        .pointerMoveFilter(
            onEnter = {
                active = true
                onColorChange(Color.Green)
//                onColorChange(
//                    if (NQueenSolution.isSafe(grid, i, j, gridSize)) {
//                        Green
//                    } else {
//                        Color.Red
//                    }
//                )
                false
            },
            onExit = {
                active = false
                onColorChange(White)
                false
            },
        ).combinedClickable(
            onClick = {
                if (grid.isNotEmpty()) {


                    if (grid[j][i] == 1) {
                        undo(i, j)
                    } else {
                        onBlockClicked(i, j)

                    }
                }
            }

        )) {

        if (grid.isNotEmpty())
            if (grid[j][i] == 1 || grid[j][i] == 2) {

                Image(
                    painter = painterResource("queen.svg"),
                    contentDescription = "Idea logo",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )
            }
    }
}


@Composable
fun Result(isVisible: Boolean, msg: String, onclose: () -> Unit) {

    Dialog(visible = isVisible, content = {

        Box(
            modifier = Modifier.width(400.dp).height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(msg, color = Black)
        }
    }, onCloseRequest = {
        onclose()
    })
}