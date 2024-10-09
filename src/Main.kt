fun main() {
    printCheckersBoard()
}

fun printCheckersBoard() {
    val whiteChecker = '⛀'  // белая шашка
    val blackChecker = '⛁'  // черная шашка
    val emptyCell = ' '      // пустая ячейка

    // создание доски
    val board = Array(8) { Array(8) { emptyCell } }

    // расстановка шашек
    for (row in 0..2) {
        for (col in 0..7) {
            if ((row + col) % 2 == 1) {
                board[row][col] = blackChecker
            }
        }
    }

    for (row in 5..7) {
        for (col in 0..7) {
            if ((row + col) % 2 == 1) {
                board[row][col] = whiteChecker
            }
        }
    }

    // печать заголовка
    println("  A  B  C   D  E   F  G  H") ////

    for (row in 0 until 8) { //печать доски
        print("${8 - row} ")
        for (col in 0 until 8) {
            // ровная доска
            print(String.format("%-3s", board[row][col]))
        }
        println(" ${8 - row}")
    }
    println("  A  B  C   D  E   F  G  H")
}