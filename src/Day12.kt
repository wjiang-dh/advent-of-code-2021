fun main() {
    fun findPath(graph: HashMap<String, ArrayList<String>>, currentPoint: String, count: Array<Int>, visited: ArrayList<String>){
        if (currentPoint == "end"){
            visited.add("end")
            count[0]++
            return
        }
        if (currentPoint[0].isLowerCase() && currentPoint in visited){
            return
        }
        visited.add(currentPoint)
        for (nextPoint in graph[currentPoint]!!){
            val visitedCopy : ArrayList<String> = ArrayList(visited)
            findPath(graph, nextPoint, count, visitedCopy)
        }
    }

    fun findPath(graph: HashMap<String, ArrayList<String>>, currentPoint: String, count: Array<Int>, visited: ArrayList<String>, visitedTwice: ArrayList<String>){
        if (currentPoint == "end"){
            visited.add("end")
            count[0]++
            return
        }
        if ((currentPoint == "start" && "start" in visited) // visited "start" twice
            || (currentPoint[0].isLowerCase() && currentPoint in visited && visitedTwice.isNotEmpty() && currentPoint !in visitedTwice) // small cave already visited once and cannot visit twice
            || (currentPoint[0].isLowerCase() && visited.count {it == currentPoint} == 2)// small cave that already visited twice
        ) {
            return
        }
        if (currentPoint[0].isLowerCase() && currentPoint in visited && visitedTwice.isEmpty() && currentPoint != "start"){
            visitedTwice.add(currentPoint)
        }
        visited.add(currentPoint)
        for (nextPoint in graph[currentPoint]!!){
            val visitedCopy : ArrayList<String> = ArrayList(visited)
            val visitedTwiceCopy : ArrayList<String> = ArrayList(visitedTwice)
            findPath(graph, nextPoint, count, visitedCopy, visitedTwiceCopy)
        }
    }

    fun part1(input: List<String>): Int {
        val graph : HashMap<String, ArrayList<String>> = hashMapOf()
        for (line in input){
            val path = line.split("-")
            if (graph.containsKey(path[0])){
                graph[path[0]]?.add(path[1])
            } else {
                graph[path[0]] = arrayListOf(path[1])
            }
            if (graph.containsKey(path[1])){
                graph[path[1]]?.add(path[0])
            } else {
                graph[path[1]] = arrayListOf(path[0])
            }
        }
        val visited : ArrayList<String> = arrayListOf()
        val count: Array<Int> = Array(1){0}

        findPath(graph, "start", count, visited)

        return count[0]
    }

    fun part2(input: List<String>): Int {
        val graph : HashMap<String, ArrayList<String>> = hashMapOf()
        for (line in input){
            val path = line.split("-")
            if (graph.containsKey(path[0])){
                graph[path[0]]?.add(path[1])
            } else {
                graph[path[0]] = arrayListOf(path[1])
            }
            if (graph.containsKey(path[1])){
                graph[path[1]]?.add(path[0])
            } else {
                graph[path[1]] = arrayListOf(path[0])
            }
        }
        println(graph.toString())
        val visited : ArrayList<String> = arrayListOf()
        val count: Array<Int> = Array(1){0}

        findPath(graph, "start", count, visited, arrayListOf())

        return count[0]
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day12_test1")
    check(part1(testInput1) == 10)
    check(part2(testInput1) == 36)

    val testInput2 = readInput("Day12_test2")
    check(part1(testInput2) == 19)

    val testInput3 = readInput("Day12_test3")
    check(part1(testInput3) == 226)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
