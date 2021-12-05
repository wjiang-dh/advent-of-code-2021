import java.lang.Exception

class Board(private val rows: List<List<String>>, private val draws: List<String>){

    private val columns = transpose(rows)
    val bingoIndex = findBingoIndex() ?: Int.MAX_VALUE
    val bingoNumber = draws[bingoIndex].toInt()
    val bingoScore = calculateScore()

    private fun findBingoIndex(): Int? {
        val indexesOfAllLines: MutableList<Int> = mutableListOf()
        for (line in rows + columns){
            var largestIndexInLine = -1
            for (number in line){
                // there is a number that can never be drawn, so this row/column can never bingo
                if (!draws.contains(number)){
                    largestIndexInLine = Int.MAX_VALUE
                    break
                }
                if (draws.indexOf(number) > largestIndexInLine){
                    largestIndexInLine = draws.indexOf(number)
                }
            }
            indexesOfAllLines.add(largestIndexInLine)
        }
        return indexesOfAllLines.minOrNull()
    }

    private fun calculateScore(): Int {
        var score = 0
        for (row in rows){
            for (number in row){
                if (!draws.contains(number) || draws.indexOf(number) > bingoIndex){
                    score += number.toInt()
                }
            }
        }
        return score
    }
}

fun processInput(input: List<String>): List<Board>{
    val draws = input[0].split(",")

    val data: MutableList<List<String>> = mutableListOf()
    val boards: MutableList<Board> = mutableListOf()

    // process readings and construct boards
    for (index in 2 until input.size){
        if (input[index].isNotEmpty()) {
            val pattern = """\W+""".toRegex()
            val line = pattern.split(input[index]).filter { it.isNotBlank() }
            data.add(line)
        } else {
            boards.add(Board(data, draws))
            data.removeAll(data)
        }
    }
    // add the last board
    boards.add(Board(data, draws))

    return boards
}

fun transpose(table: List<List<String>>): List<List<String>> {
    val ret: MutableList<List<String>> = ArrayList()
    for (i in 0 until table[0].size) {
        val col: MutableList<String> = ArrayList()
        for (row in table) {
            col.add(row[i])
        }
        ret.add(col)
    }
    return ret
}

fun main() {

    fun part1(input: List<String>): Int {

        val boards = processInput(input)

        // find the board that first bingo
        var smallestBingoIndexes = Int.MAX_VALUE
        var winnerBoard: Board? = null
        for (board in boards){
            if (board.bingoIndex < smallestBingoIndexes){
                smallestBingoIndexes = board.bingoIndex
                winnerBoard = board
            }
        }
        if (winnerBoard == null){
            throw Exception("There is no winner!")
        }
        return winnerBoard.bingoScore * winnerBoard.bingoNumber
    }


    fun part2(input: List<String>): Int {

        val boards = processInput(input)

        // find the board that last bingo
        var largestBingoIndexes = Int.MIN_VALUE
        var loserBoard: Board? = null
        for (board in boards){
            if (board.bingoIndex > largestBingoIndexes){
                largestBingoIndexes = board.bingoIndex
                loserBoard = board
            }
        }
        if (loserBoard == null){
            throw Exception("There is no loser!")
        }
        return loserBoard.bingoScore * loserBoard.bingoNumber
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}