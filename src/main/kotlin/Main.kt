// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.*
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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


        Box (modifier = Modifier.animateContentSize()){
            AnimatedVisibility(gameVisible,exit = fadeOut(),enter = fadeIn()) {

                MainScreenInteractive(gridSize,onGridChange = {
                    gridSize=it
                }) {
                    gameVisible = false
                }
            }

            AnimatedVisibility(!gameVisible,exit = fadeOut(),enter = fadeIn()) {
                MainScreen (gridSize,onGridChange = {
                    gridSize=it
                }){
                    gameVisible = true
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun main() = application {
    Window(onCloseRequest = ::exitApplication,title = "NQueens",) {
        App()
    }
}
