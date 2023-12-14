package io.github.lhDream.view

import tornadofx.*
import kotlin.system.exitProcess

/**
 * 标题界面
 */
class TitleView: View() {

    override val root = anchorpane{
        prefWidth = 900.0
        prefHeight = 600.0
        imageview("images/title.png"){
            fitWidth = 600.0
            fitHeight = 400.0
        }
        vbox {
            spacing = 20.0
            layoutX = 150.0
            layoutY = 500.0
            button("单人模式") {
                action {
                    replaceWith<MainView>()
                }
            }
            button("双人模式") {
                action {
                    replaceWith<MainView>()
                }
            }
            button("本地联机") {
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