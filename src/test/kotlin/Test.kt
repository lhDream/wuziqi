import java.util.*
import kotlin.math.abs

// 棋盘大小
const val BOARD_SIZE = 15

// 评估函数（简化版本，根据棋局评估得分）
// 五子棋中针对当前局面的评估函数
fun evaluate(board: Array<Array<Int>>, player: Int): Int {
    val opponent = if (player == 2) 1 else 2
    var score = 0

    // 检查每个位置的行、列、对角线是否存在连续的棋子
    for (i in 0 until BOARD_SIZE) {
        for (j in 0 until BOARD_SIZE) {
            // 检查水平、垂直和对角线上的棋子情况
            val directions = arrayOf(
                intArrayOf(1, 0), intArrayOf(1, -1),
                intArrayOf(0, -1), intArrayOf(-1, -1),
                intArrayOf(-1, 0), intArrayOf(-1, 1),
                intArrayOf(0, 1), intArrayOf(1, 1)
            )

            for (dir in directions) {
                var countPlayer = 0
                var countOpponent = 0
                var association = 0
                for (k in 0 until 6) {
                    val r = i + k * dir[0]
                    val c = j + k * dir[1]
                    if (r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE) {
                        if (board[r][c] == player) {
                            countPlayer++
                        } else if (board[r][c] == opponent) {
                            countOpponent++
                        }
                        association = association * 10 + board[r][c]
                    } else {
                        break
                    }
                }
                score += when(association){
                    22222 -> 50000 // 活五
                    22220, 22202, 22022, 20222 -> 10000 // 活四
                    122220, 122202, 122022, 120222, 102222, 22221 -> 5000 // 冲四
                    222, 2202, 2022, 2220 -> 2000 // 活三
                    12220, 12202, 12022, 10222, 2221 -> 1000 // 眠三
                    22, 202, 20 -> 500 // 活二
                    1220, 1202, 1020, 210, 2120 -> 200 // 眠二
                    else -> 0
                }
            }
        }
    }

    return score
}


// Minimax 算法
fun minimax(board: Array<Array<Int>>, depth: Int, player: Int): Int {
    if (depth == 0) {
        return evaluate(board,player)
    }
    var bestScore = 0
    val opponent = if (player == 2) 1 else 2
    for (i in 0 until BOARD_SIZE) {
        for (j in 0 until BOARD_SIZE) {
            if (board[i][j] == 0) {
                board[i][j] = opponent
                val score = minimax(board, depth - 1, opponent)
                board[i][j] = 0
                bestScore = maxOf(bestScore, score)
            }
        }
    }

    return bestScore
}

// 寻找最佳下棋位置
fun findBestMove(board: Array<Array<Int>>,player: Int): Pair<Int, Int> {
    var bestScore = Int.MIN_VALUE
    var bestMove = Pair(-1, -1)

    for (i in 0 until BOARD_SIZE) {
        for (j in 0 until BOARD_SIZE) {
            if (board[i][j] == 0) {
                board[i][j] = player // 模拟落子
                var score = minimax(board, 3, player) // 这里设定最大搜索深度为3
                score = score * major[i][j] + major[i][j]  // 位置价值
                board[i][j] = 0 // 撤销落子
                if (score > bestScore) {
                    bestScore = score // 更新最高分
                    bestMove = Pair(i, j) // 更新最佳落子位置
                }
            }
        }
    }

    return bestMove
}
// 初始化位置价值矩阵
fun initMatrix(matrix:Array<Array<Int>>){
    val centerX = matrix.size / 2
    val centerY = matrix.size / 2
    for (i in matrix.indices) {
        for (j in matrix.indices) {
            val distance = abs(i - centerX) + abs(j - centerY)
            matrix[i][j] = matrix.size - distance
        }
    }
}
// 位置价值矩阵
val major = Array(BOARD_SIZE) { Array(BOARD_SIZE) { 0 } }

// 主函数
fun main() {

    initMatrix(major)


    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { 0 } }
    // 模拟当前棋盘状态
    val scan = Scanner(System.`in`)
    while (true){
        // ...
        val (x,y) = findBestMove(board,2)
        board[x][y] = 2
        println("Best Move: ${x}, $y")
        for (i in board.indices){
            println(board[i].contentToString())
        }

        val p = scan.nextLine().split(",")
        board[p[0].toInt()][p[1].toInt()] = 1
    }
}

