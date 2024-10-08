const val SIZE = 8
const val whitecell = '□'
const val blackcell = '■'
const val blackb = 'b'
const val whitew = 'w'
const val blackking = 'B'
const val whiteking = 'W'

val board = Array(SIZE) { Array(SIZE) { whitecell } }
var currentPlayer = whitew

fun main() {
    setupBoard()
    while (true) {
        printBoard()
        println("Ход игрока ${if (currentPlayer == whitew) "белых" else "черных"}")
        val input = readLine()
        if (input != null && processMove(input)) {
            if (checkEndGame()) {
                println("Победа ${if (currentPlayer == whitew) "черные" else "белые"}!")
                break
            }
            currentPlayer = if (currentPlayer == whitew) blackb else whitew
        } else {
            println("Неверный ход")
        }
    }
}

fun setupBoard() {
    for (row in 0 until SIZE) {
        for (col in 0 until SIZE) {
            if ((row + col) % 2 == 1) {
                if (row < 3) {
                    board[row][col] = blackb
                } else if (row > 4) {
                    board[row][col] = whitew
                } else {
                    board[row][col] = blackcell
                }
            } else {
                board[row][col] = whitecell
            }
        }
    }
}

fun printBoard() {
    println("  A  B  C  D  E  F  G  H")
    for (row in 0 until SIZE) {
        print("${row + 1} ")
        for (col in 0 until SIZE) {
            print("${board[row][col]}  ")
        }
        println()
    }
}

fun processMove(move: String): Boolean {
    if (!move.matches(Regex("[A-H][1-8]-[A-H][1-8]"))) return false

    val (from, to) = move.split("-").map { it.trim() }
    val fromCol = from[0] - 'A'
    val fromRow = from[1] - '1'
    val toCol = to[0] - 'A'
    val toRow = to[1] - '1'

    if (!isValidMove(fromRow, fromCol, toRow, toCol)) return false

    board[toRow][toCol] = board[fromRow][fromCol]
    board[fromRow][fromCol] = if ((fromRow + fromCol) % 2 == 1) blackcell else whitecell

    if (Math.abs(fromRow - toRow) == 2) {
        val jumpedRow = (fromRow + toRow) / 2
        val jumpedCol = (fromCol + toCol) / 2
        board[jumpedRow][jumpedCol] = if ((jumpedRow + jumpedCol) % 2 == 0) whitecell else blackcell
    }

    if (toRow == 0 && currentPlayer == whitew) {
        board[toRow][toCol] = whiteking
    } else if (toRow == SIZE - 1 && currentPlayer == blackb) {
        board[toRow][toCol] = blackking
    }

    return true
}

fun isValidMove(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean {
    if (toRow !in 0 until SIZE || toCol !in 0 until SIZE) return false

    if (board[toRow][toCol] != whitecell && board[toRow][toCol] != blackcell) {
        return false
    }

    val rowDiff = toRow - fromRow
    val colDiff = toCol - fromCol

    if (Math.abs(colDiff) != Math.abs(rowDiff) || Math.abs(rowDiff) > 2) return false

    val playerPiece = board[fromRow][fromCol]
    if ((currentPlayer == whitew && (playerPiece != whitew && playerPiece != whiteking)) ||
        (currentPlayer == blackb && (playerPiece != blackb && playerPiece != blackking))) return false

    if (Math.abs(rowDiff) == 2) {
        val jumpedRow = (fromRow + toRow) / 2
        val jumpedCol = (fromCol + toCol) / 2
        val jumpedPiece = board[jumpedRow][jumpedCol]

        if ((currentPlayer == whitew && (jumpedPiece == blackb  jumpedPiece == blackking)) 
            (currentPlayer == blackb && (jumpedPiece == whitew || jumpedPiece == whiteking))) {
            return true
        }
        return false
    }

    return true
}

fun checkEndGame(): Boolean {
    val whitePieces = board.flatten().count { it == whitew || it == whiteking }
    val blackPieces = board.flatten().count { it == blackb || it == blackking }

    return whitePieces == 0 || blackPieces == 0
}