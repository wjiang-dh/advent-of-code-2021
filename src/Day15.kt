import java.util.PriorityQueue

data class Point(val i: Int, val j: Int, val risk: Int)

fun dijkstra(pq: PriorityQueue<Point>, board: Array<Array<Int>>, minRisks: Array<Array<Int>>): Int{
    while (true){
        val (i, j, risk) = pq.remove()
        if (i == board.size-1 && j == board[0].size-1) return risk
        if (i != 0 && (board[i - 1][j] + risk) < minRisks[i - 1][j]) {
            minRisks[i - 1][j] = board[i - 1][j] + risk
            pq.add(Point(i - 1, j, board[i - 1][j] + risk))
        }
        if (i != board.size-1 && (board[i + 1][j] + risk) < minRisks[i + 1][j]) {
            minRisks[i + 1][j] = board[i + 1][j] + risk
            pq.add(Point(i + 1, j, board[i + 1][j] + risk))
        }
        if (j != 0 && (board[i][j-1] + risk) < minRisks[i][j-1]) {
            minRisks[i][j-1] = board[i][j-1] + risk
            pq.add(Point(i, j - 1, board[i][j - 1] + risk))
        }
        if (j != board[0].size-1 && (board[i][j+1] + risk) < minRisks[i][j+1]) {
            minRisks[i][j+1] = board[i][j+1] + risk
            pq.add(Point(i, j + 1, board[i][j + 1] + risk))
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {

        val board = input.map{i -> i.map { it.digitToInt() }.toTypedArray()}.toTypedArray()
        val m = board.size
        val n = board[0].size
        val minRisks = Array(m){Array(n){Int.MAX_VALUE} }
        val pq = PriorityQueue<Point>(compareBy{ it.risk })
        pq.add(Point(0, 0, 0))

        return dijkstra(pq, board, minRisks)
    }

    fun part2(input: List<String>): Int {

        val board = input.map{i -> i.map { it.digitToInt() }.toTypedArray()}.toTypedArray()
        val m = board.size * 5
        val n = board[0].size * 5
        val newBoard = Array(m){Array(n){0} }

        for (i in newBoard.indices) for (j in newBoard[i].indices){
            val add = i/board.size + j/board[0].size
            val value = board[i%board.size][j%board[0].size] + add
            newBoard[i][j] = if (value % 9 == 0) value else value % 9
        }

        val minRisks = Array(m){Array(n){Int.MAX_VALUE} }
        val pq = PriorityQueue<Point>(compareBy{ it.risk })
        pq.add(Point(0, 0, 0))

        return dijkstra(pq, newBoard, minRisks)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
