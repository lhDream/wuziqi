package io.github.lhDream.view

import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.system.exitProcess

/**
 * 标题界面
 */
class TitleView: View() {

    override val root = anchorpane{
        style {
            backgroundColor = multi(Color.WHITE)
        }
        prefWidth = 900.0
        prefHeight = 800.0
        stackpane {
            AnchorPane.setLeftAnchor(this,0.0)
            AnchorPane.setRightAnchor(this,0.0)
            imageview("/img/logo.png"){
                fitWidth = 600.0
                fitHeight = 400.0
            }
        }
        vbox {
            spacing = 10.0
            layoutX = 420.0
            layoutY = 500.0
            button("开始游戏") {
                action {
                    replaceWith<MainView>()
                }
            }
//            button("联机对战") {
//
//            }
            button("退出") {
                action {
                    exitProcess(0)
                }
            }
        }
    }

}