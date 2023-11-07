package io.github.lhDream.controller

import io.github.lhDream.view.fragment.PieceFragment
import tornadofx.Controller

/**
 * 五子棋逻辑处理类
 */
class GomokuController: Controller() {
    /**
     * 行数
     */
    val rows = 15

    /**
     * 列数
     */
    val columns = 15
    /**
     * 格子大小
     */
    val cellSize = 50.0
    /**
     * 当前玩家
     */
    var player = Player.BLACK

    /**
     * 已下棋子缓存
     */
    val pieceList = Array(rows) { Array<PieceFragment?>(columns) { null } }

    /**
     * 第几手
     */
    var whichMoveNum = 0

    /**
     * 落子
     */
    fun downPiece(x: Int,y: Int,piece: PieceFragment): Boolean{
        if(!this.checkChessMoves(x,y)){
            pieceList[x][y] = piece
            whichMoveNum++
            player = if(player == Player.BLACK) Player.WHITE else Player.BLACK
        }
        return check(x, y)
    }

    /**
     * 判断该位置是否已落子
     */
    fun checkChessMoves(x: Int,y: Int): Boolean{
        return pieceList[x][y] != null
    }

    /**
     * 重开棋局
     */
    fun reset(){
        whichMoveNum = 0
        for (row in 0 until rows){
            for (column in 0 until columns){
                pieceList[row][column] = null
            }
        }
    }

    /**
     * 计算输赢
     */
    private fun check(x: Int,y: Int): Boolean{
        return checkHorizontal(x, y) || checkVertical(x, y) || checkLeftOblique(x, y) || checkRightOblique(x, y)
    }

    /**
     * 水平方向校验
     */
    private fun checkHorizontal(x: Int,y: Int): Boolean{
        var count = 1
        // 左侧
        for (i in 0 until x){
            if(pieceList[x - i][y]?.color == pieceList[x-i-1][y]?.color){
                count++
            }else{
                break
            }
        }
        // 右侧
        for (i in x until pieceList.size-1){
            if(pieceList[i][y]?.color == pieceList[i+1][y]?.color){
                count++
            }else{
                break
            }
        }
        return count >= 5
    }

    /**
     * 垂直方向校验
     */
    private fun checkVertical(x: Int,y: Int): Boolean{
        var count = 1
        // 上方
        for (i in 0 until y){
            if(pieceList[x][y-i]?.color == pieceList[x][y-i-1]?.color){
                count++
            }else{
                break
            }
        }
        // 下方
        for (i in y until pieceList.size-1){
            if(pieceList[x][i]?.color == pieceList[x][i+1]?.color){
                count++
            }else{
                break
            }
        }
        return count >= 5
    }

    /**
     * 左斜方向
     */
    private fun checkLeftOblique(x: Int,y: Int): Boolean{
        var count = 1
        val maxTem = x.coerceAtMost((columns - y - 1))
        val minTem = (rows - x - 1).coerceAtMost(y)
        // 右上方
        for (i in 0 until minTem){
            if(pieceList[x+i][y-i]?.color == pieceList[x+i+1][y-i-1]?.color){
                count++
            }else{
                break
            }
        }
        // 左下方
        for (i in 0 until maxTem){
            if(pieceList[x-i][y+i]?.color == pieceList[x-i-1][y+i+1]?.color){
                count++
            }else{
                break
            }
        }
        return count >= 5
    }

    /**
     * 右斜方向
     */
    private fun checkRightOblique(x: Int,y: Int): Boolean{
        var count = 1
        val maxTem = (rows - x  - 1).coerceAtMost((columns - y  - 1))
        val minTem = x.coerceAtMost(y)
        // 左上方
        for (i in 0 until minTem){
            if(pieceList[x-i][y-i]?.color == pieceList[x-i-1][y-i-1]?.color){
                count++
            }else{
                break
            }
        }
        // 右下方
        for (i in 0 until maxTem){
            if(pieceList[x+i][y+i]?.color == pieceList[x+i+1][y+i+1]?.color){
                count++
            }else{
                break
            }
        }
        return count >= 5
    }

}

enum class Player{
    WHITE,BLACK
}