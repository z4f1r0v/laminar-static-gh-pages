import com.raquo.laminar.api.L._
import org.scalajs.dom

import scala.util.Random

case class Rectangle(width: Double, height: Double, color: Color)

case class Color(r: Int, g: Int, b: Int) {
  override def toString() = s"rgb($r, $g, $b)"
}

object Frontend {

  val myApp = {

    def $randomRectangle: EventStream[Rectangle] =
      EventStream.periodic(1000).mapTo(Rectangle.random)

    div(
      display.flex,
      span(
        opacity(0.7),
        "You rectangle is: ",
        marginRight("8px")
      ),
        RectangleView($randomRectangle),
      marginRight("8px"),
        RectangleView($randomRectangle),
      marginRight("8px"),
      RectangleView($randomRectangle),
    )
  }

  def RectangleView($rectangle: EventStream[Rectangle]): Div =
    div(
      width <-- $rectangle.map(r => s"${r.width}px"),
      height <-- $rectangle.map(r => s"${r.height}px"),
      borderRadius("2px"),
      background <-- $rectangle.map(_.color.toString())
    )

  def main(args: Array[String]): Unit = {
    documentEvents.onDomContentLoaded.foreach { _ =>
      render(dom.document.getElementById("appContainer"), myApp)
    }(unsafeWindowOwner)
  }
}

object Rectangle {
  val empty = Rectangle(0.0, 0.0, Color.random)

  def random = {
    Rectangle(Random.nextDouble() * 30, Random.nextDouble() * 30, Color.random)
  }
}

object Color {
  def random: Color = Color(Random.nextInt, Random.nextInt, Random.nextInt)
}
