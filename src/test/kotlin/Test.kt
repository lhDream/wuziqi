// 棋盘大小
val BOARD_SIZE = 15

// 评估函数（简化版本，根据棋局评估得分）
// 五子棋中针对当前局面的评估函数
fun evaluate(board: Array<Array<Char>>, player: Char): Int {
    val opponent = if (player == 'X') 'O' else 'X'
    var score = 0

    // 检查每个位置的行、列、对角线是否存在连续的棋子
    for (i in 0 until BOARD_SIZE) {
        for (j in 0 until BOARD_SIZE) {
            // 对于每个空位置
            if (board[i][j] == ' ') {
                // 检查水平、垂直和对角线上的棋子情况
                val directions = arrayOf(
                    intArrayOf(1, 0), intArrayOf(0, 1),
                    intArrayOf(1, 1), intArrayOf(1, -1)
                )

                for (dir in directions) {
                    var countPlayer = 0
                    var countOpponent = 0
                    for (k in 1 until 6) {
                        val r = i + k * dir[0]
                        val c = j + k * dir[1]
                        if (r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE) {
                            if (board[r][c] == player) {
                                countPlayer++
                            } else if (board[r][c] == opponent) {
                                countOpponent++
                            }
                        } else {
                            break
                        }
                    }

                    if (countPlayer == 4) score += 100
                    if (countPlayer == 3 && countOpponent == 0) score += 5
                    if (countPlayer == 2 && countOpponent == 0) score += 2
                    if (countPlayer == 1 && countOpponent == 0) score += 1
                    //
                    if (countPlayer == 1 && countOpponent == 3) score += 90
                }
            }
        }
    }

    return score
}


// Minimax 算法
fun minimax(board: Array<Array<Char>>, depth: Int, player: Char): Int {
    if (depth == 0) {
        return evaluate(board,player)
    }
    val maximizingPlayer = player == 'X'
    var bestScore = if (maximizingPlayer) Int.MIN_VALUE else Int.MAX_VALUE

    for (i in 0 until BOARD_SIZE) {
        for (j in 0 until BOARD_SIZE) {
            if (board[i][j] == ' ') {
                board[i][j] = player
                val opponent = if (player == 'X') 'O' else 'X'
                val score = minimax(board, depth - 1, opponent)
                board[i][j] = ' '

                if (maximizingPlayer) {
                    bestScore = maxOf(bestScore, score)
                } else {
                    bestScore = minOf(bestScore, score)
                }
            }
        }
    }

    return bestScore
}

// 寻找最佳下棋位置
fun findBestMove(board: Array<Array<Char>>,player: Char): Pair<Int, Int> {
    var bestScore = Int.MIN_VALUE
    var bestMove = Pair(-1, -1)

    for (i in 0 until BOARD_SIZE) {
        for (j in 0 until BOARD_SIZE) {
            if (board[i][j] == ' ') {
                board[i][j] = player
                val score = minimax(board, 3, player) // 这里设定最大搜索深度为3
                board[i][j] = ' '

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
    val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { ' ' } }
    // 模拟当前棋盘状态
    // ...
    val bestMove = findBestMove(board,'X')
    println("Best Move: ${bestMove.first}, ${bestMove.second}")
}
