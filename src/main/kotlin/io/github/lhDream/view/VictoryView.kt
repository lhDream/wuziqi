package io.github.lhDream.view

import tornadofx.*

/**
 * 胜利提示窗口
 */
class VictoryView: View() {

    var enterAction:()->Unit = { }

    override val root = vbox{
        label("胜利")
        button("确定") {
            action {
                enterAction()
            }
        }
    }

}