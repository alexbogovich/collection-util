package io.github.alexbogovich.collection

import java.util

import scala.collection.JavaConverters._

object JoinUtil {
  def crossJoin[T](list: Iterable[Iterable[T]]): Iterable[Iterable[T]] =
    list match {
      case xs :: Nil => xs map (Iterable(_))
      case x :: xs => for {
        i <- x
        j <- crossJoin(xs)
      } yield Iterable(i) ++ j
    }

  def crossJoin[T](list: util.List[util.List[T]]): util.Collection[util.Collection[T]] = {
    val iterable: Iterable[Iterable[T]] = collectionAsScalaIterableConverter(list).asScala
      .map(v => collectionAsScalaIterableConverter(v).asScala)
    asJavaCollectionConverter(crossJoin(iterable)
      .map(v => asJavaCollectionConverter(v).asJavaCollection))
      .asJavaCollection
  }
}
