import rx.Observable
import rx.subjects.PublishSubject
import java.util.*

/**
 * Recalculate the weights every time new input is received.
 * @param size dimension of the feature vector
 * @param rate learning rate parameter
 */
class Learner(val size: Int, val rate: Double) {
    // list of weight
    val generator = Random()
    private var weights = DoubleArray(size, { i -> 10 * generator.nextDouble() })

    private val subject = PublishSubject.create<Cost>()

    val receiver: Observable<DoubleArray> = subject
            .map { it -> newWeights(it) }

    init {
        println("initial weights: ${weights.joinToString()}")
        receiver.subscribe { it -> updateWeight(it) }

    }

    private fun updateWeight(it: DoubleArray) {
        weights = it

    }

    private fun newWeights(input: Cost): DoubleArray {
        val prod = scalarProduct(input.quantity, weights)
        val diff = input.cost - prod
//        println("(${input.quantity.joinToString()} )*(${weights.joinToString()}) = $prod")
//        println("cost = ${input.cost}, diff = $diff")
        return weights.mapIndexed { i, it -> it + rate * diff * input.quantity[i] }.toDoubleArray()
    }

    fun learn(it: Cost) {
        subject.onNext(it)
    }

    fun done() {
        println("Done")
//        println("weights: ${weights.joinToString()}")
    }

    fun getWeights(): DoubleArray {
        return weights.map { it }.toDoubleArray()
    }

    fun scalarProduct(a1: Array<Int>, a2: DoubleArray): Double {
        return a1.mapIndexed { index, value -> a2[index] * value }.sum()
    }
}