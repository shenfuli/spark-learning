package examples

/**
  * 合并两个Map集合对象,将两个对应KEY的对应的VALUE累加,解决方案：
  * ( map1 /: map2 ) { case (map, (k,v)) => map + ( k -> (v + map.getOrElse(k, 0)) ) }
  * Map的折叠函数是依次传入Map的键值对。所以操作函数希望传入的操作数可以是（K,V）形式。。于是用case表达式：(map, (k,v))
  * Created by fuli.shen on 2016/11/13.
  * Reference:http://www.cnblogs.com/tugeler/p/5134862.html
  */
object Test07_Map02 {

  def main(args: Array[String]) {


    val a = Map("G01" -> 1, "G02" -> 10)
    val b = Map("G02" -> 5, "G03" -> 2)

    val c = (a /: b) (
      (map, kv) => {
        map + (kv._1 -> (kv._2 + map.getOrElse(kv._1, 0)))
      }
    )
    println(c) //Map(G01 -> 1, G02 -> 15, G03 -> 2)
  }
}

