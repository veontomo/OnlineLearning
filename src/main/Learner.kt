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
    private var weights = DoubleArray(size, { i -> Random().nextDouble() })

    private val subject = PublishSubject.create<Array<Int>>()

    val receiver: Observable<Double> = subject
            .doOnNext { updateWeights(it) }
            .map { it -> targetValue(it) }

    private fun targetValue(input: Array<Int>): Double {
        return input.last() - input.slice(1..(size - 2)).mapIndexed { i, it -> it * weights[i] }.sum()
    }

    init {
        receiver.subscribe { it -> println("target value: $it") }

    }

    private fun updateWeights(input: Array<Int>) {
        val diff = input.last() + input.slice(1..(size - 2)).mapIndexed { i, it -> it * weights[i] }.sum()
        weights = weights.mapIndexed { i, it -> it - rate * diff * input[i] }.toDoubleArray()
    }

    fun learn(it: Array<Int>) {
        subject.onNext(it)
    }

    fun done() {
        print(weights.joinToString { it.toString() })
    }
}