fun main() {
    fun part1(player1: Int, player2: Int): Int {
        var position1 = player1
        var position2 = player2
        var score1 = 0
        var score2 = 0
        var dice = 0
        var player1Turn = true

        while(true){
            dice++
            if (player1Turn){
                position1 += dice % 100
            } else {
                position2 += dice % 100
            }
            if (dice % 3 == 0){
                if (player1Turn){
                    score1 += if (position1 % 10 == 0) 10 else position1 % 10
//                    println("Turn $dice : Player1 move to $position1 has $score1")
                } else {
                    score2 += if (position2 % 10 == 0) 10 else position2 % 10
//                    println("Turn $dice : Player2 move to $position2 has $score2")
                }
                player1Turn = !player1Turn
                if (score1 >= 1000 || score2 >= 1000){
                    break
                }
            }
        }
//        println("Player 1 score: $score1 , player2 score: $score2")
        return dice * minOf(score1, score2)
    }

    fun gameTurn(position1: Int, position2: Int, score1: Int, score2: Int, memory: HashMap<String, Pair<Long, Long>>): Pair<Long, Long> {
        if (score1 >= 21) return Pair(1, 0)
        if (score2 >= 21) return Pair(0, 1)

        if ("$position1 $position2 $score1 $score2" in memory.keys){
            return memory["$position1 $position2 $score1 $score2"]!!
        }

        var win1 = (0).toLong()
        var win2 = (0).toLong()
        for (dice1 in listOf(1 ,2, 3)) {
            for (dice2 in listOf(1, 2, 3)){
                for (dice3 in listOf(1, 2, 3)){
                    var newP1 = (position1 + dice1 + dice2 + dice3) % 10
                    if (newP1 == 0) newP1 = 10
                    val s1 = score1 + newP1

                    // change player
                    val result = gameTurn(position2, newP1, score2, s1, memory)
                    memory["$position2 $newP1 $score2 $s1"] = result
                    win2 += result.first
                    win1 += result.second
                }
            }
        }
        return Pair(win1, win2)
    }


    // grabbed some help form https://github.com/jonathanpaulson/AdventOfCode/blob/master/2021/21.py
    fun part2(player1: Int, player2: Int): Long {

        val memory : HashMap<String, Pair<Long, Long>> = hashMapOf()

        val result = gameTurn(player1, player2, 0, 0, memory)

        return if (result.first > result.second) result.first else result.second
    }

    // test if implementation meets criteria from the description, like:
    // Player 1 starting position: 4
    // Player 2 starting position: 8
    check(part1(4, 8) == 739785)
    check(part2(4, 8) == 444356092776315)

    // Player 1 starting position: 8
    // Player 2 starting position: 6
    println(part1(8, 6))
    println(part2(8, 6))
}
