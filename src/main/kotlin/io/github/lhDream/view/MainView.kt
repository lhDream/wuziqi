package io.github.lhDream.view

import io.github.lhDream.controller.GomokuController
import io.github.lhDream.view.fragment.ChessboardFragment
import io.github.lhDream.view.fragment.PieceFragment
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Paint
import javafx.scene.paint.Stop
import javafx.stage.StageStyle
import tornadofx.*

class MainView:View() {

    private val gomokuController: GomokuController by inject()

    init {
        title = "五子棋"
        primaryStage.isResizable = false
    }

    override val root = anchorpane{
        prefWidth = 900.0
        prefHeight = 800.0
        style {
            backgroundColor = multi(c("#ffddcd"))
        }
        val chessboardParam = mapOf(
            ChessboardFragment::rows to gomokuController.rows,
            ChessboardFragment::columns to gomokuController.columns,
            ChessboardFragment::cellSize to gomokuController.cellSize,
        )
        val chessboardFragment = find(ChessboardFragment::class,chessboardParam){
            this.root.layoutX = 50.0
            this.root.layoutY = 50.0
            action {
                // 游戏结束触发动作
                val victoryView = find<VictoryView>()
                victoryView.enterAction = {
                    // 重开处理逻辑
                    reset()
                    // 关闭弹窗
                    victoryView.close()
                }
                victoryView.openInternalWindow<VictoryView>(owner = this@anchorpane)
            }
        }
        add(chessboardFragment)
        button("重开"){
            this.layoutX = 820.0
            this.layoutY = 50.0
            action {
                chessboardFragment.reset()
            }
        }
    }

}