import kotlin.math.absoluteValue

fun main() {
    fun part1(xrange: Pair<Int, Int>, yrange: Pair<Int, Int>): Int {

        var maxY = Int.MIN_VALUE

        // can only move forward
        for (velocityX in 0..xrange.second){
            // yrange.first is negative so we use absolute value to show the direction
            // if it has y velocity = -abs(yrange.first), it will drop to bottom line in 1s
            // if it has y velocity = abs(yrange.first+1), it will reach to the highest point, then drop back to the original y level with y velocity = -abs(yrange.first+1)
            // and drop to the bottom line in the next 1s with the velocity = -abs(yrange.first+1)-1 = -abs(yrange.first)
            for (velocityY in -yrange.first.absoluteValue..yrange.first.absoluteValue){
                var x = 0
                var y = 0
                var max = Int.MIN_VALUE
                var newVX = velocityX
                var newVY = velocityY

                while(true){
                    x += newVX
                    y += newVY
                    if (y < yrange.first) break

                    max = maxOf(max, y)
                    if (newVX > 0){
                        newVX--
                    }else if (newVX < 0){
                        newVX++
                    }
                    newVY--

                    if ((x in xrange.first..xrange.second) && (y in yrange.first..yrange.second)){
//                        println("Success: vx=$velocityX vy=$velocityY")
                        if (max > maxY) {
                            maxY = max
                        }
                        break
                    }
                }
            }
        }
        return maxY
    }

    fun part2(xrange: Pair<Int, Int>, yrange: Pair<Int, Int>): Int {
        var count = 0

        for (velocityX in 0..xrange.second){
            for (velocityY in -yrange.first.absoluteValue..yrange.first.absoluteValue){
                var x = 0
                var y = 0
                var newVX = velocityX
                var newVY = velocityY

                while(true){
                    x += newVX
                    y += newVY
                    if (y < yrange.first) break

                    if (newVX > 0){
                        newVX--
                    }else if (newVX < 0){
                        newVX++
                    }
                    newVY--

                    if ((x in xrange.first..xrange.second) && (y in yrange.first..yrange.second)){
//                        println("Success: vx=$velocityX vy=$velocityY")
                        count++
                        break
                    }
                }
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:

    // target area: x=34..67, y=-215..-186
    val x_test = Pair(20, 30)
    val y_test = Pair(-10, -5)
    check(part1(x_test, y_test) == 45)
    check(part2(x_test, y_test) == 112)

    // target area: x=34..67, y=-215..-186
    val x = Pair(34, 67)
    val y = Pair(-215, -186)
    println(part1(x, y))
    println(part2(x, y))
}
