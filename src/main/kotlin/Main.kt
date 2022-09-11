// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.*
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
@Preview
fun App() {

    var gameVisible by remember {
        mutableStateOf(true)
    }

    var gridSize by remember {
        mutableStateOf(8)
    }
    DesktopMaterialTheme {

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            GameAndSolution(gameVisible, gridSize, onGridSizeChange = {
                gridSize = it
            }, onGameVisibilityChange = {
                gameVisible = it
            })


            Spacer(modifier = Modifier.height(20.dp))
            Text(
                textAlign = TextAlign.Center,
                text = "Get the mobile version",
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(
                    Alignment.CenterHorizontally

                )
            )
            Spacer(modifier = Modifier.height(10.dp))


            Image(
                painter = painterResource("nqueens.svg"),
                contentDescription = "qrcode",
                modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )
        }


    }
}


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun GameAndSolution(
    gameVisible: Boolean,
    gridSize: Int,
    onGameVisibilityChange: (Boolean) -> Unit,
    onGridSizeChange: (Int) -> Unit
) {

    Box(modifier = Modifier.animateContentSize()) {
        AnimatedVisibility(gameVisible, exit = fadeOut(), enter = fadeIn()) {

            MainScreenInteractive(gridSize, onGridChange = onGridSizeChange, gotoSolution = {
                onGameVisibilityChange(false)
            })
        }

        AnimatedVisibility(!gameVisible, exit = fadeOut(), enter = fadeIn()) {
            MainScreen(gridSize, onGridChange = onGridSizeChange) {
                onGameVisibilityChange(true)
            }
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "NQueens") {
        App()
    }
}
