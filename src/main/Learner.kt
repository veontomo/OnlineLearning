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

    val receiver: Observable<Double> = subject
            .doOnNext { updateWeights(it) }
            .map { it -> deviation(it) }

    private fun deviation(input: Cost): Double {
        val diff = input.cost - scalarProduct(input.quantity, weights)
        return diff * diff / 2
    }

    init {
        receiver.subscribe { it -> println("deviation: $it") }

    }

    private fun updateWeights(input: Cost) {

        val diff = input.cost - scalarProduct(input.quantity, weights)
        weights = weights.mapIndexed { i, it -> it - rate * diff * input.quantity[i] }.toDoubleArray()
    }

    fun learn(it: Cost) {
        subject.onNext(it)
    }

    fun done() {
        println("Done")
    }

    fun getWeights(): DoubleArray {
        return weights.map { it }.toDoubleArray()
    }

    fun scalarProduct(a1: Array<Int>, a2: DoubleArray): Double {
        return a1.mapIndexed { index, value -> a2[index] * value }.sum()
    }
}