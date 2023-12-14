package io.github.lhDream.view

import tornadofx.*
import kotlin.system.exitProcess

/**
 * 标题界面
 */
class TitleView: View() {

    override val root = anchorpane{
        imageview("images/title.png"){
            fitWidth = 600.0
            fitHeight = 400.0
        }
        vbox {
            layoutX = 150.0
            layoutY = 500.0
            button("开始游戏") {
                action {
                    replaceWith<MainView>()
                }
            }
            button("退出") {
                action {
                    exitProcess(0)
                }
            }
        }
    }

}