fun main() {
    fun helper(board: ArrayList<IntArray>, i: Int, j: Int, count: Array<Int>, flashed: Array<Array<Boolean>>, dirs: Array<Array<Int>>){
        if (i < 0 || i >= board.size || j < 0 || j >= board[0].size || flashed[i][j]) return
        if (board[i][j] <= 8){
            board[i][j]++
            return
        }
        if (board[i][j] == 9){
            count[0]++
            board[i][j] = 0
            flashed[i][j] = true
            for (dir in dirs){
                val x = i + dir[0]
                val y = j + dir[1]
                helper(board, x, y, count, flashed, dirs)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val board: ArrayList<IntArray> = arrayListOf()
        for (line in input){
            board.add(line.map{it.toString().toInt()}.toIntArray())
        }
        val m = board.size
        val n = board[0].size

        val dirs: Array<Array<Int>> = arrayOf(arrayOf(1,0), arrayOf(0,1), arrayOf(-1,0), arrayOf(0, -1),
            arrayOf(-1, -1), arrayOf(1 ,1), arrayOf(-1, 1), arrayOf(1, -1))
        var sum = 0
        var count: Array<Int> = Array(1){0}
        for (step in 1..100){
            val flashed: Array<Array<Boolean>> = Array(m){Array(n){false} }
            for (i in 0 until m){
                for (j in 0 until n){
                    helper(board, i, j, count, flashed, dirs)
                    sum += count[0]
                    count = Array(1){0}
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {

        val board: ArrayList<IntArray> = arrayListOf()
        for (line in input){
            board.add(line.map{it.toString().toInt()}.toIntArray())
        }
        val m = board.size
        val n = board[0].size

        val dirs: Array<Array<Int>> = arrayOf(arrayOf(1,0), arrayOf(0,1), arrayOf(-1,0), arrayOf(0, -1),
            arrayOf(-1, -1), arrayOf(1 ,1), arrayOf(-1, 1), arrayOf(1, -1))
        var sum = 0
        var step = 0
        var count: Array<Int> = Array(1){0}
        while (true){
            val flashed: Array<Array<Boolean>> = Array(m){Array(n){false} }
            step++
            for (i in 0 until m){
                for (j in 0 until n){
                    helper(board, i, j, count, flashed, dirs)
                    sum += count[0]
                    count = Array(1){0}
                }
            }
            var stop = true
            for (array in flashed){
                if (false in array) {
                    stop = false
                    break
                }
            }
            if (stop) return step
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
