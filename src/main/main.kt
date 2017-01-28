import java.util.*

/**
 * Simulation of an online learning.
 * Reactive paradigm seems to be a good candidate for this approach.
 */
fun main(args: Array<String>) {
    // number of the input data to generate
    val maxNum = 50
    // number of features that each input data contains
    val numFeatures = 5
    // generating data
    val generator = Random()
    // generate a vector of weights that should be then found
    val hiddenWeights = DoubleArray(numFeatures, {i -> 5.0 * i + 1})
    // generate tuples of input data: each tuple contains numFeatures elements
    val quantities = (1..maxNum).map{ (1..numFeatures).map{generator.nextInt(20)}.toTypedArray()}
    // generate list of input data to be fed to the learner algorithm
    val items = quantities.map { quantity ->
        Cost(quantity, quantity.mapIndexed { i, q -> hiddenWeights[i] * q }.sum())
    }

    val learner = Learner(numFeatures, 0.001)
    items.forEach { learner.learn(it) }

    learner.done()
    println(learner.getWeights().joinToString { it.toString() })
}

