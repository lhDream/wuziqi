package io.github.lhDream.view.fragment

import io.github.lhDream.constant.piece
import javafx.scene.Parent
import javafx.scene.layout.Background
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.ArcType
import tornadofx.*

/**
 * 棋子
 */
class PieceFragment: Fragment() {
    /**
     * 棋子颜色
     */
    val color: Paint by param()

    /**
     * 棋子大小
     */
    val pieceSize = 40.0

    /**
     * 棋子样式
     */
    override val root = anchorpane{
        addClass(piece)
        canvas {
            background = Background.EMPTY
            width = pieceSize + 1
            height = pieceSize + 1
            with(graphicsContext2D){
                fill = Color.rgb(0,0,0,0.3)
                fillArc(1.0,1.0,pieceSize,pieceSize,0.0,360.0,ArcType.OPEN)
                fill = color
                fillArc(0.0,0.0,pieceSize,pieceSize,0.0,360.0,ArcType.OPEN)
            }
        }
    }

}