fun main() {

    fun recordCoordinates(input: List<String>): Array<ArrayList<Int>>{
        val xs : ArrayList<Int> = ArrayList()
        val ys : ArrayList<Int> = ArrayList()
        for (line in input){
            val points = line.split(" -> ")
            val x1 = points[0].split(",")[0].toInt()
            val y1 = points[0].split(",")[1].toInt()
            val x2 = points[1].split(",")[0].toInt()
            val y2 = points[1].split(",")[1].toInt()

            xs.add(x1)
            xs.add(x2)
            ys.add(y1)
            ys.add(y2)
        }
        return arrayOf(xs, ys)
    }

    fun countDots(board: Array<Array<Int>>): Int{
        var count = 0
        for (array in board){
            count += array.count{it >= 2}
        }
        return count
    }

    fun part1(input: List<String>): Int {
        // the x's an y's
        val (xs, ys) = recordCoordinates(input)

        // determine the size of the board and create the board
        val maxX = xs.maxOrNull()
        val maxY = ys.maxOrNull()
        val board = Array(maxX!!+1) { Array(maxY!!+1) {0} }

        // draw lines
        for (index in 0 until xs.size step 2){
            var (x1, x2, y1, y2) = listOf(xs[index], xs[index+1], ys[index], ys[index+1])

            //draw vertical or horizontal lines
            if (x1 == x2 || y1 == y2){
                if (x1 > x2) x1 = x2.also { x2 = x1 }
                if (y1 > y2) y1 = y2.also { y2 = y1 }
                for (x in x1..x2){
                    for (y in y1..y2){
                        board[x][y]++
                    }
                }
            }
        }
        return countDots(board)
    }

    fun part2(input: List<String>): Int {
        // the x's an y's
        val (xs, ys) = recordCoordinates(input)

        // determine the size of the board and create the board
        val maxX = xs.maxOrNull()
        val maxY = ys.maxOrNull()
        val board = Array(maxX!!+1) { Array(maxY!!+1) {0} }

        // draw lines
        for (index in 0 until xs.size step 2){
            var (x1, x2, y1, y2) = listOf(xs[index], xs[index+1], ys[index], ys[index+1])

            //draw vertical or horizontal lines
            if (x1 == x2 || y1 == y2){
                if (x1 > x2) x1 = x2.also { x2 = x1 }
                if (y1 > y2) y1 = y2.also { y2 = y1 }
                for (x in x1..x2){
                    for (y in y1..y2){
                        board[x][y]++
                    }
                }
            }
            // draw diagonal lines
            else {
                // diagonal lines from upper left to lower right
                if ((x1 < x2 && y1 < y2) || (x1 > x2 && y1 > y2)){
                    if (x1 > x2) x1 = x2.also { x2 = x1 }
                    if (y1 > y2) y1 = y2.also { y2 = y1 }
                    for (i in 0..x2-x1) {
                        val x = x1 + i
                        val y = y1 + i
                        board[x][y]++
                    }
                }
                // diagonal lines from lower left to upper right
                else {
                    if (x1 > x2) x1 = x2.also { x2 = x1 }
                    if (y1 > y2) y1 = y2.also { y2 = y1 }
                        for (i in 0..x2-x1){
                            val x = x1 + i
                            val y = y2 - i
                            board[x][y]++
                    }
                }
            }
        }
        return countDots(board)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
