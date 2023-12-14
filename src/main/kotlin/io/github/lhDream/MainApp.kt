package io.github.lhDream

import io.github.lhDream.view.TitleView
import tornadofx.App
import tornadofx.launch

/**
 * 主程序
 */
fun main() {
    launch<MainApp>()
}

/**
 * 主程序
 */
class MainApp: App(TitleView::class)