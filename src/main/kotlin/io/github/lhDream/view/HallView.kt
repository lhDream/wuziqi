package io.github.lhDream.view

import javafx.scene.Parent
import javafx.scene.layout.AnchorPane
import tornadofx.*

class HallView: View() {

    override val root = anchorpane{
        prefWidth = 900.0
        prefHeight = 800.0

        stackpane {
            AnchorPane.setLeftAnchor(this,0.0)
            AnchorPane.setRightAnchor(this,0.0)
            form {
                field("用户名称:") {
                    textfield()
                }
            }
        }

        tableview<String> {

        }


    }

}