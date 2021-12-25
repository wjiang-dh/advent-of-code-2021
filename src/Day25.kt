fun main() {
    fun part1(input: List<String>): Int {
        var board = input.map{line -> line.map { it }.toTypedArray() }.toTypedArray()

        var count = 0
        var moved = true
        while(moved){
            count++
            moved = false
            var newBoard = board.map{line -> line.map { it }.toTypedArray()}.toTypedArray()

            for (i in board.indices){
                for (j in board[i].indices){
                    if (j != board[i].size-1 && board[i][j] == '>' && board[i][j+1] == '.') {
                        newBoard[i][j+1] = '>'
                        newBoard[i][j] = '.'
                        moved = true
                    }
                    else if (j == board[i].size-1 && board[i][j] == '>' && board[i][0] == '.') {
                        newBoard[i][0] = '>'
                        newBoard[i][j] = '.'
                        moved = true
                    }
                }
            }
            board = newBoard
            newBoard = board.map{line -> line.map { it }.toTypedArray()}.toTypedArray()
            for (i in board.indices){
                for (j in board[i].indices){
                    if (i != board.size-1 && board[i][j] == 'v' && board[i+1][j] == '.') {
                        newBoard[i+1][j] = 'v'
                        newBoard[i][j] = '.'
                        moved = true
                    }
                    else if (i == board.size-1 && board[i][j] == 'v' && board[0][j] == '.') {
                        newBoard[0][j] = 'v'
                        newBoard[i][j] = '.'
                        moved = true
                    }
                }
            }
            board = newBoard
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day25_test")
    check(part1(testInput) == 58)

    val input = readInput("Day25")
    println(part1(input))
}
