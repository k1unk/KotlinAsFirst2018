package lesson8.task3

import lesson8.task2.Square
import lesson8.task2.square
import java.util.*

class Graph {
    private data class Vertex(val name: String) {
        val neighbors = mutableSetOf<Vertex>()
    }

    private val vertices = mutableMapOf<String, Vertex>()

    private operator fun get(name: String) = vertices[name] ?: throw IllegalArgumentException()

    fun addVertex(name: String) {
        vertices[name] = Vertex(name)
    }

    private fun connect(first: Vertex, second: Vertex) {
        first.neighbors.add(second)
        second.neighbors.add(first)
    }

    fun connect(first: String, second: String) = connect(this[first], this[second])

    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в ширину
     */
    fun bfs(start: String, finish: String) = bfs(this[start], this[finish])

    private fun bfs(start: Vertex, finish: Vertex): Int {
        val queue = ArrayDeque<Vertex>()
        queue.add(start)
        val visited = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) return distance
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited.put(neighbor, distance + 1)
                queue.add(neighbor)
            }
        }
        return -1
    }

    fun bfsForKnightTraectory(start: String, finish: String) = bfsForKnightTraectory(this[start], this[finish])

    private fun bfsForKnightTraectory(start: Vertex, finish: Vertex): List<Square> {
        val queue = ArrayDeque<Vertex>()
        var firstList = listOf<Graph.Vertex>()
        var secondList = listOf<Graph.Vertex>()
        queue.add(start)
        var fullDistance = 0
        val visited = mutableMapOf(start to 0)

        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val distance = visited[next]!!
            if (next == finish) break
            for (neighbor in next.neighbors) {
                if (neighbor in visited) continue
                visited.put(neighbor, distance + 1)
                firstList += Vertex(neighbor.name)
                secondList += Vertex(next.name)
                queue.add(neighbor)
                fullDistance = distance + 1
            }
        }

        var re = listOf<Square>()
        var f1 = finish
        var f2 = f1
        for (i in 0 until fullDistance) {
            for (yi in firstList.size - 1 downTo 0) {
                f1 = f2
                if (firstList[yi] == f1) {
                    re += square(secondList[yi].name)
                    f1 = secondList[yi]
                }
                f2 = f1
            }
        }

        var result = listOf<Square>()
        for (i in re.size - 1 downTo 0) {
            result += re[i]
        }
        result += square(finish.name)
        return result
    }


    /**
     * Пример
     *
     * По двум вершинам рассчитать расстояние между ними = число дуг на самом коротком пути между ними.
     * Вернуть -1, если пути между вершинами не существует.
     *
     * Используется поиск в глубину
     */
    fun dfs(start: String, finish: String): Int = dfs(this[start], this[finish], setOf()) ?: -1

    private fun dfs(start: Vertex, finish: Vertex, visited: Set<Vertex>): Int? =
            if (start == finish) 0
            else {
                val min = start.neighbors
                        .filter { it !in visited }
                        .mapNotNull { dfs(it, finish, visited + start) }
                        .min()
                if (min == null) null else min + 1
            }
}
