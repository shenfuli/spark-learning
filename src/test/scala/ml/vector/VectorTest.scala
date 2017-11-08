package ml.vector

import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg.Vectors
/**
  * Vector向量
  * Created by fuli.shen on 2017/9/7.
  */
object VectorTest {

  def main(args: Array[String]): Unit = {

    // DenseVector and SpareVector

    // Creates a dense vector from a double array
    val denseVector = Vectors.dense(1, 0, 0, 0, 0, 0, 3)
    println(denseVector)//返回结果是Vector 类型的数据： [1.0,0.0,0.0,0.0,0.0,0.0,3.0]
    // Creates a sparse vector providing its index array and value array.
    val sparseVector = Vectors.sparse(7, Array(0, 6), Array(1, 3))
    println(sparseVector)// (7,[0,6],[1.0,3.0])
    // Creates a sparse vector using unordered (index, value) pairs.
    val sparseVector2 = Vectors.sparse(7, Seq((0, 1.0), (6, 3.0)))
    println(sparseVector2)//(7,[0,6],[1.0,3.0])
    // convert spare vector into dense vector
    val denseVector1 = sparseVector2.toDense
    println(denseVector1) //[1.0,0.0,0.0,0.0,0.0,0.0,3.0]

    // LabledPoint Vector
    // create a labeled point with a positive label and a dense feature vector
    val labeledPoint1 = LabeledPoint(1.0, Vectors.dense(1, 0, 0, 0, 0, 0, 3))
    println(labeledPoint1)//(1.0,[1.0,0.0,0.0,0.0,0.0,0.0,3.0])
    // create a labeled point with a negative label and a spare feature vector
    val labeledPoint2 = LabeledPoint(0.0, Vectors.sparse(7, Array(0, 6), Array(1, 3)))
    println(labeledPoint2)//(0.0,(7,[0,6],[1.0,3.0]))
  }
}
