package io.github.lhDream

import io.github.lhDream.view.MainView
import tornadofx.App
import tornadofx.launch

fun main() {
    launch<MainApp>()
}

class MainApp: App(MainView::class)