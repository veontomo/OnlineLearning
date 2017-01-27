import rx.Observable
import rx.subjects.PublishSubject

/**
 * Simulation of an online learning.
 * Reactive paradigm seems to be a good candidate for this approach.
 */
fun main(args: Array<String>) {

    val learner = Learner(3, 0.2)
    // input data.
    // Each array element a four-element array corresponding to a single input data.
    // First three elements are the feature vector components, the fourth - the correct value.
    val data = arrayOf(arrayOf(1, 2, 0, 50), arrayOf(0, 2, 4, 30), arrayOf(3, 2, 1, 100))
    data.forEach { learner.learn(it) }

    learner.done();

//    receiver.subscribe{println("length: $it")}
//
//    subject.onNext("AAAA")
//    subject.onNext("BBB")
//    subject.onNext("CCC")
//    subject.onNext("DDD")

}

fun learn(it: String?): Int = it?.length ?: 0

fun elaborator(str: String) {
    print(str)
}
