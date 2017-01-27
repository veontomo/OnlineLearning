import java.util.*

/**
 * Simulation of an online learning.
 * Reactive paradigm seems to be a good candidate for this approach.
 */
fun main(args: Array<String>) {
    val maxNum = 100
    val generator = Random()
    val prices = arrayOf(10.0, 5.0, 8.0)
    val quantities = (1..maxNum).map{ arrayOf(generator.nextInt(20), generator.nextInt(20), generator.nextInt(20))}
    val items = quantities.map { quantity ->
        Cost(quantity, quantity.mapIndexed { i, q -> prices[i] * q }.sum())
    }

    val learner = Learner(3, 0.1)
    items.forEach { learner.learn(it) }

    learner.done()
    println(learner.getWeights().joinToString { it.toString() })
}

