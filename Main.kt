/*
 * Copyright (C) 2021 Vaticle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.vaticle.compose.perf

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() {
    application {
        Window(title = "Boxes", onCloseRequest = { exitApplication() }, state = rememberWindowState(WindowPlacement.Maximized)) {
            val boxes = rememberBoxes()
            Canvas(Modifier.fillMaxSize()) {
                boxes.forEach {
                    drawRect(Color.Red, Offset(it.x, it.y), Size(18f, 6f))
                }
            }
            LaunchedEffect(Unit) {
                while (true) {
                    withFrameNanos { boxes.forEach { box -> box.x++ } }
                }
            }
        }
    }
}

@Composable
fun rememberBoxes(): List<Block> {
    val boxes = mutableListOf<Block>()
    for (i in 0 until 100) {
        for (j in 0 until 20) { boxes += Block(x = (j * 20).toFloat(), y = (i * 8).toFloat()) }
    }
    return remember { boxes }
}

class Block(x: Float, y: Float) {
    var x by mutableStateOf(x)
    var y by mutableStateOf(y)
}
