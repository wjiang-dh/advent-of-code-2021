fun main() {

    fun calculateScore(board: List<List<String>>, draws: List<String>, step: Int): Int {
        var score = 0
        for (row in board){
            for (number in row){
                if (!draws.contains(number) || draws.indexOf(number) > step){
                    score += number.toInt()
                }
            }
        }
        return score
    }

    fun calculateSteps(boards: List<List<String>>, draws: List<String>): List<Int> {
        val steps: MutableList<Int> = mutableListOf()
        for (i in boards.indices){
            var largest = -1
            for (number in boards[i]){
                if (draws.indexOf(number) > largest){
                    largest = draws.indexOf(number)
                }
            }
            steps.add(largest)
        }
        return steps
    }

    fun part1(input: List<String>): Int {
        val draws = input[0].split(",")

        val rows : MutableList<List<String>> = mutableListOf()
        val columns : MutableList<List<String>> = mutableListOf()

        for (index in 1 until input.size){
            if (input[index].isNotEmpty()) {
                val pattern = """\W+""".toRegex()
                val row = pattern.split(input[index]).filter { it.isNotBlank() }
                rows.add(row)
            }
        }

        for (index in 0 until rows.size step 5){
            val transpose = Array(5) { Array(5) {""} }
            for (i in 0..4) {
                for (j in 0..4) {
                    transpose[j][i] = rows[index+i][j]
                }
            }
            for (array in transpose){
                columns.add(array.toList())
            }
        }

        val total = rows + columns
        val steps = calculateSteps(total, draws)
        val smallestStep = steps.minOrNull()

        var score = 0
        var lastStep = 0
        if (smallestStep != null){
            val index = steps.indexOf(smallestStep)
            val boardStartLine = (index).floorDiv(5)*5
            val board = total.slice(boardStartLine..boardStartLine+4)
            score = calculateScore(board, draws, smallestStep)

            lastStep = draws[smallestStep].toInt()
        }

        return score*lastStep
    }


    fun part2(input: List<String>): Int {

        val draws = input[0].split(",")

        val rows : MutableList<List<String>> = mutableListOf()
        val columns : MutableList<List<String>> = mutableListOf()

        for (index in 1 until input.size){
            if (input[index].isNotEmpty()) {
                val pattern = """\W+""".toRegex()
                val row = pattern.split(input[index]).filter { it.isNotBlank() }
                rows.add(row)
            }
        }

        for (index in 0 until rows.size step 5){
            val transpose = Array(5) { Array(5) {""} }
            for (i in 0..4) {
                for (j in 0..4) {
                    transpose[j][i] = rows[index+i][j]
                }
            }
            for (array in transpose){
                columns.add(array.toList())
            }
        }

        val rowSteps = calculateSteps(rows, draws)
        val columnSteps = calculateSteps(columns, draws)

        val stepsToBingo : MutableList<Int> = mutableListOf()

        for (index in rowSteps.indices step 5){
            val boardStep =
                rowSteps.slice(index..index+4).minOrNull()
                    ?.let { columnSteps.slice(index..index+4).minOrNull()?.let { it1 -> minOf(it, it1) } }
            if (boardStep != null) {
                stepsToBingo.add(boardStep)
            }
        }

        val maxStepsToBingo = stepsToBingo.maxOrNull()
        val maxBoardIndex = stepsToBingo.indexOf(maxStepsToBingo)

        val boardStartLine = maxBoardIndex*5
        val board = rows.slice(boardStartLine..boardStartLine+4)
        val score = calculateScore(board, draws, maxStepsToBingo!!)
        val lastStep = draws[maxStepsToBingo].toInt()

        return score*lastStep
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}