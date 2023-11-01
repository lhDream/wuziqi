package io.github.lhDream.view.fragment

import io.github.lhDream.constant.chessboard
import io.github.lhDream.constant.piece
import io.github.lhDream.controller.GomokuController
import io.github.lhDream.view.VictoryView
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Paint
import javafx.scene.paint.Stop
import tornadofx.*

/**
 * 棋盘
 */
class ChessboardFragment: Fragment() {

    /**
     * 行数
     */
    val rows: Int by param()

    /**
     * 列数
     */
    val columns: Int by param()
    /**
     * 格子大小
     */
    val cellSize: Double by param()


    var enterAction:(PieceFragment)->Unit = { }

    private val gomokuController: GomokuController by inject()

    var x = 0
    var y = 0

    /**
     * 白子样式
     */
    val whitePiece = LinearGradient(
        0.0, 0.0, 1.0, 1.0, true, null,
        Stop(0.0, Color.WHITE),
        Stop(0.75, c("#aaa"))
    )

    /**
     * 黑子样式
     */
    val blackPiece = LinearGradient(
        0.0, 0.0, 1.0, 1.0, true, null,
        Stop(0.0, c("#ccc")),
        Stop(0.5, Color.BLACK)
    )
    /**
     * 临时棋子，标识即将落子的位置以及颜色
     */
    var hoverPiece: PieceFragment? = null
    /**
     * 棋子颜色缓存
     */
    var pieceColor: Paint = blackPiece


    override val root = anchorpane{
        addClass(chessboard)
        canvas {
            width = 700.0
            height = 700.0
            with(graphicsContext2D){
                stroke = Color.BLACK
                lineWidth = 2.0
                for(i in 0 until rows){
                    // 表格横线
                    strokeLine(0.0,i * cellSize,width,i * cellSize)
                }
                for(i in 0 until columns){
                    // 表格竖线
                    strokeLine(i * cellSize,0.0,i * cellSize,height)
                }
            }
        }

        var layoutX = 0.0
        var layoutY = 0.0


        setOnMouseMoved {
            x = if(it.x % cellSize > cellSize / 2){
                (it.x / cellSize + 1).toInt()
            }else{
                (it.x / cellSize).toInt()
            }
            y = if(it.y % cellSize > cellSize / 2){
                (it.y / cellSize + 1).toInt()
            }else{
                (it.y / cellSize).toInt()
            }
            layoutX = x * cellSize
            layoutY = y * cellSize
            // 移除旧的标识子
            hoverPiece?.let { it.removeFromParent() }
            if(!gomokuController.checkChessMoves(x,y)){
                // 添加新的标识子
                add(PieceFragment::class, mapOf(PieceFragment::color to pieceColor)){
                    hoverPiece = this
                    this.root.opacity = 0.6
                    this.root.layoutX = layoutX - this.pieceSize / 2
                    this.root.layoutY = layoutY - this.pieceSize / 2
                }
            }

        }

        setOnMouseExited {
            hoverPiece?.let { it.removeFromParent() }
            hoverPiece = null
        }

        setOnMouseClicked {
            if(!gomokuController.checkChessMoves(x,y)){
                // 落子
                add(PieceFragment::class, mapOf(PieceFragment::color to pieceColor)){
                    this.root.layoutX = layoutX - this.pieceSize / 2
                    this.root.layoutY = layoutY - this.pieceSize / 2
                    // 落子逻辑处理
                    val res = gomokuController.downPiece(x,y,this)
                    if(res){
                        enterAction(this)
                    }
                }
                // 标识下一子的颜色
                pieceColor = if(pieceColor == blackPiece){
                    whitePiece
                }else{
                    blackPiece
                }
            }
        }
    }

    fun action(action: (PieceFragment)->Unit){
        this.enterAction = action
    }

    /**
     * 重置棋盘
     */
    fun reset(){
        gomokuController.reset()
        root.children.removeIf { it.hasClass(piece) }
        pieceColor = blackPiece
    }

}