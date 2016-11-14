package examples

import java.io.{File, PrintWriter}

import org.training.spark.utils.PropertyUtil

/**
  * Scala打开文件是利用Java对象和java.io.File，它们都可在Scala编程中用来读取和写入文件
  * Created by fuli.shen on 2016/11/13.
  */
object Test13_FileIO {

  def main(args: Array[String]) {

    //PrintWriter 写入文件
    val userDir: String = System.getProperty("user.dir")
    println(userDir)
    val configsFile: String = userDir + "/src/test/resources/test.txt"
    val file = new File(configsFile)
    val out = new PrintWriter(file)
    out.write("spark.app.name=SparkStreaming\n")
    out.write("spark.app.id=app-101\n")
    out.close
    //从屏幕读取一行-有时需要从屏幕上读取用户输入，然后进行某些进一步的处理
    print("Please enter your input : ")
    //val line = Console.readLine
    //println("Thanks, you just typed: " + line)

    //读取文件内容-从文件中读取是非常简单的。可以使用Scala的Source 类和它配套对象读取文件
    println("Following is the content read:")
    val properties: Map[String, String] = PropertyUtil.getProperties(configsFile)
    for ((k, v) <- properties) {
      println(k + "->" + v)
    }
  }
}

