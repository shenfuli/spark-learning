package examples

/**
  * Scala集合为相同类型的配对的不同元素的集合。换句话说，集合是不包含重复元素的集合。
  * 有两种集合，不可改变的和可变的。可变和不可变的对象之间的区别在于，当一个对象是不可变的，对象本身不能被改变。
  * 默认情况下，Scala中使用不可变的集。如果想使用可变集，必须明确地导入scala.collection.mutable.Set类。
  * 如果想在同一个同时使用可变和不可变的集合，那么可以继续参考不变的集合，但可以参考可变设为mutable.Set
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_Set {

  def main(args: Array[String]) {
    // Empty set of integer type
    var emptySet: Set[Int] = Set()
    // Set of integer type
    var set: Set[Int] = Set(1, 3, 5, 7,7)
    set = set.+(10,11)
    println(set) //Set(5, 10, 1, 7, 3, 11)

    var mutableSet = scala.collection.mutable.Set(1, 3, 5, 7,7)
    mutableSet = mutableSet.+(20,21)
    println(mutableSet)//Set(1, 5, 20, 3, 21, 7)
  }
}
