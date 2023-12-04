import java.util.*

// 棋盘大小
val BOARD_SIZE = 15

// 评估函数（简化版本，根据棋局评估得分）
// 五子棋中针对当前局面的评估函数
fun evaluate(board: Array<Array<Int>>, player: Int): Int {
    val opponent = if (player == 2) 1 else 2
    var score = 0

    // 检查每个位置的行、列、对角线是否存在连续的棋子
    for (i in 0 until BOARD_SIZE) {
        for (j in 0 until BOARD_SIZE) {
            // 对于每个空位置
            // 检查水平、垂直和对角线上的棋子情况
            val directions = arrayOf(
                intArrayOf(1, 0), intArrayOf(0, 1),
                intArrayOf(1, 1), intArrayOf(1, -1)
            )

            for (dir in directions) {
                var countPlayer = 0
                var countOpponent = 0
                var association = 0
                for (k in 0 until 6) {
                    val r = i + k * dir[0]
                    val c = j + k * dir[1]
                    if (r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE) {
//                        if (board[r][c] == player) {
//                            countPlayer++
//                        } else if (board[r][c] == opponent) {
//                            countOpponent++
//                        }
                        association = association * 10 + board[r][c]
                    } else {
                        break
                    }
                }
                score += when(association){
                    21 -> 1
                    12 -> 1
                    211 -> 2
                    2111 -> 10
                    211112 -> 1000

                    22 -> 1
                    222 -> 5
                    2222 -> 20

                    else -> 0
                }
//                if (countPlayer == 1 && countOpponent == 0) score += 1
//                if (countPlayer == 1 && countOpponent == 1) score += 10
//                if (countPlayer == 1 && countOpponent == 2) score += 100
//                if (countPlayer == 1 && countOpponent == 3) score += 2000
////                    if (countPlayer == 1 && countOpponent == 4) score += 2000
//
//                if (countPlayer == 2 && countOpponent == 0) score += 2
//                if (countPlayer == 2 && countOpponent == 1) score += 2
//                if (countPlayer == 2 && countOpponent == 2) score += 2
//                if (countPlayer == 2 && countOpponent == 3) score += 2
//                if (countPlayer == 2 && countOpponent == 4) score += 10000
//
//                if (countPlayer == 3 && countOpponent == 0) score += 20
//                if (countPlayer == 3 && countOpponent == 1) score += 20
//                if (countPlayer == 3 && countOpponent == 2) score += 20
//
//                if (countPlayer == 4 && countOpponent == 0) score += 100
//                if (countPlayer == 4 && countOpponent == 1) score += 50
//                if (countPlayer == 5) score += 9999999
            }
//            if (board[i][j] == ' ') {
//            }
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
                val score = minimax(board, depth - 1, player)
//                val score = evaluate(board,player)
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
                board[i][j] = player
                val score = minimax(board, 2, player) // 这里设定最大搜索深度为3
                board[i][j] = 0

                if (score > bestScore) {
                    bestScore = score
                    bestMove = Pair(i, j)
                }
            }
        }
    }

    return bestMove
}

// 主函数
fun main() {
    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { 0 } }
    // 模拟当前棋盘状态
    val scan = Scanner(System.`in`)
    while (true){
        val p = scan.nextLine().split(",")
        board[p[0].toInt()][p[1].toInt()] = 1
        // ...
        val (x,y) = findBestMove(board,2)
        board[x][y] = 2
        println("Best Move: ${x}, $y")
        for (i in board.indices){
            println(board[i].contentToString())
        }
    }
}
