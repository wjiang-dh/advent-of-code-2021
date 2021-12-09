fun main() {
    fun helper(board: ArrayList<IntArray>, i: Int, j: Int, size: MutableList<Int>, visited: Array<Array<Boolean>>, dirs: Array<Array<Int>>){
        if (i < 0 || i >= board.size || j < 0 || j >= board[0].size
            || board[i][j] == 9 || visited[i][j]) return
        visited[i][j] = true
        size.add(1)
        for (dir in dirs){
            val x = i + dir[0]
            val y = j + dir[1]
            helper(board, x, y, size, visited, dirs)
        }
    }

    fun part1(input: List<String>): Int {
        val board: ArrayList<IntArray> = arrayListOf()
        for (line in input){
            board.add(line.map{it.toString().toInt()}.toIntArray())
        }
        val m = board.size
        val n = board[0].size

        var sum = 0
        for (i in 0 until m){
            for (j in 0 until n){
                if (i != 0 && board[i-1][j] <= board[i][j]) continue
                if (i != m-1 && board[i+1][j] <= board[i][j]) continue
                if (j != 0 && board[i][j-1] <= board[i][j]) continue
                if (j != n-1 && board[i][j+1] <= board[i][j]) continue
                sum += board[i][j]+1
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

        val dirs: Array<Array<Int>> = arrayOf(arrayOf(1,0), arrayOf(0,1), arrayOf(-1,0), arrayOf(0, -1))
        val visited: Array<Array<Boolean>> = Array(m){Array(n){false} }
        var size: MutableList<Int> = ArrayList()
        val sizes: MutableList<Int> = ArrayList()
        for (i in 0 until m){
            for (j in 0 until n){
                helper(board, i, j, size, visited, dirs)
                if (size.sum() != 0) sizes.add(size.sum())
                size = ArrayList()
            }
        }
        sizes.sortDescending()
        return sizes[0]*sizes[1]*sizes[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
