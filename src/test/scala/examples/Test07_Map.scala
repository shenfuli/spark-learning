package examples

/**
  * Scala中的映射是键/值对的集合。任何值可以根据它的键进行检索。键是在映射唯一的，但值不一定是唯一的。
  * 映射也被称为哈希表。有两种类型的映射，不可变以及可变的。可变和不可变的对象之间的区别在于，当一个对象是不可变的，对象本身不能被改变。
  * 默认情况下，Scala中使用不可变的映射。如果想使用可变集，必须明确地导入scala.collection.mutable.Map类。如果想在同一个同时使用可变和不可变的映射，
  * 那么可以继续参考不可变的映射作为映射，但可以参考可变集合为mutable.Map
  *
  * Created by fuli.shen on 2016/11/13.
  */
object Test07_Map {

  def main(args: Array[String]) {
    // Empty hash table whose keys are strings and values are integers:
    var az: Map[Char, Int] = Map()
    // A map with keys and values.
    val colors = Map("red" -> "#FF0000", "azure" -> "#F0FFFF")

    //在定义空映射，类型注释是必要的，因为系统需要指定一个具体的类型变量。如果我们要一个键值对添加到映射，我们可以使用运算符+
    az += ('A' -> 0)
    az += ('B' -> 1)
    az += ('C' -> 2)
    az += ('D' -> 3)

    //映射的基本操作
    println("Keys in az : " + az.keys) //Keys in az : Set(A, B, C, D)
    println("Values in az : " + az.values) //Values in az : MapLike(0, 1, 2, 3)
    println("Check if az is empty : " + az.isEmpty) //Check if az is empty : false
    println(az.get('A').get)//0
    println(az.get('E'))//None
    println(az.getOrElse('E',-1))//-1
    //打印映射的键和值
    az.keys.foreach(key => {
      print(key + "->" + az(key) + " ")//A->0 B->1 C->2 D->3
    })
    println
    for((key,value)<-az){
      print(key + "->" +value + " ")//A->0 B->1 C->2 D->3
    }
  }
}

