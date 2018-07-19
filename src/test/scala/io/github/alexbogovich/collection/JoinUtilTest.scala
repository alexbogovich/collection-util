package io.github.alexbogovich.collection

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class JoinUtilTest extends FlatSpec with Matchers {
  "JoinUtil join 3 list" should "return cross join" in {
    val join = JoinUtil.crossJoin(
      List(
        List(3, "b"),
        List(1, 8),
        List(0, "f", 4.3)
      )
    )
    val expected = List(
      List(3, 1, 0),
      List(3, 1, "f"),
      List(3, 1, 4.3),
      List(3, 8, 0),
      List(3, 8, "f"),
      List(3, 8, 4.3),
      List("b", 1, 0),
      List("b", 1, "f"),
      List("b", 1, 4.3),
      List("b", 8, 0),
      List("b", 8, "f"),
      List("b", 8, 4.3))
    assertResult(expected)(join)
  }

  "JoinUtil join 0 list" should "return cross join" in {
    val join = JoinUtil.crossJoin(List(List()))
    val expected = List()
    assertResult(expected)(join)
  }
}
