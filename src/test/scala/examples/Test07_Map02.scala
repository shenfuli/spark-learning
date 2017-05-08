package examples

/**
  * 合并两个Map集合对象,将两个对应KEY的对应的VALUE累加,解决方案：
  * ( map1 /: map2 ) { case (map, (k,v)) => map + ( k -> (v + map.getOrElse(k, 0)) ) }
  * Map的折叠函数是依次传入Map的键值对。所以操作函数希望传入的操作数可以是（K,V）形式。。于是用case表达式：(map, (k,v))
  * Created by fuli.shen on 2016/11/13.
  * Reference:
  * http://www.cnblogs.com/tugeler/p/5134862.html
  * http://www.runoob.com/scala/scala-maps.html
  */
object Test07_Map02 {

  def main(args: Array[String]) {


    println("-------------------------------案例1:根据指定的两个map进行合并---------------------------------------")
    val a = Map("G01" -> 1, "G02" -> 10)
    val b = Map("G02" -> 5, "G03" -> 2)

    val c = (a /: b) (
      (map, kv) => {
        map + (kv._1 -> (kv._2 + map.getOrElse(kv._1, 0)))
      }
    )
    println(c) //Map(G01 -> 1, G02 -> 15, G03 -> 2) , 发现会对相同KEY的Value进行相加

    println("-------------------------------案例2：可以使用 ++ 运算符或 Map.++() 方法来连接两个 Map，Map 合并时会移除重复的 key-------")
    //  ++ 作为运算符
    val merge1AB = a ++ b
    println("merge1AB:" + merge1AB)//mergeAB:Map(G01 -> 1, G02 -> 5, G03 -> 2) , 发现不会对相同KEY的Value进行相加
    //  .++() 方法   def ++(xs: Map[(A, B)]): Map[A, B]    返回一个新的 Map，新的 Map xs 组成
    val merge2AB = a.++(b)
    println("merge2AB:" + merge2AB)//merge2AB:Map(G01 -> 1, G02 -> 5, G03 -> 2) , 发现不会对相同KEY的Value进行相加

    println("-------------------------------案例3：根据动态Array中的数据进行合并---------------------------------------")
    val wordCountMapArrayRDD: Array[Map[String, Int]] = Array(Map("福田" -> 9, "卡车" -> 2, "奖品" -> 10), Map("通道" -> 4, "奖品" -> 3, "卡车" -> 18))

    var wordCountMapCompactRDD: Map[String, Int] = Map()
    wordCountMapArrayRDD.map(wordCountMap => {
      wordCountMapCompactRDD = (wordCountMapCompactRDD /: wordCountMap) (
        (map, kv) => {
          map + (kv._1 -> (kv._2 + map.getOrElse(kv._1, 0)))
        }
      )
    })
    wordCountMapCompactRDD.map(println) /*
                    (福田,9)
                    (卡车,20)
                    (奖品,13)
                    (通道,4)

                     结果： 发现会对相同KEY的Value进行相加
                    */
  }
}

