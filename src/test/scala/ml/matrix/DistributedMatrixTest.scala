package ml.matrix
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD

/**
  * Created by fuli.shen on 2017/9/10.
  */
object DistributedMatrixTest {

  def main(args: Array[String]): Unit = {

    //A RowMatrix is a row-oriented distributed matrix without meaningful row indices, backed by an RDD of its rows,, where each row is a local vector.
    val rows: RDD[Vector] = null;//... // an RDD of local vectors
    // Create a RowMatrix from an RDD[Vector].
    val mat: RowMatrix = new RowMatrix(rows)

    // Get its size.
    val m = mat.numRows()
    val n = mat.numCols()

    // QR decomposition
    val qrResult = mat.tallSkinnyQR(true)
  }
}
