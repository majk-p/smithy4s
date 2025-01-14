package smithy4s.example

import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.EnumTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ClosedInt(_value: String, _name: String, _intValue: Int, _hints: Hints) extends Enumeration.Value {
  override type EnumType = ClosedInt
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = _hints
  override def enumeration: Enumeration[EnumType] = ClosedInt
  @inline final def widen: ClosedInt = this
}
object ClosedInt extends Enumeration[ClosedInt] with ShapeTag.Companion[ClosedInt] {
  val id: ShapeId = ShapeId("smithy4s.example", "ClosedInt")

  val hints: Hints = Hints.empty

  case object FOO extends ClosedInt("FOO", "FOO", 0, Hints.empty) {
    override val hints: Hints = Hints(alloy.proto.ProtoIndex(0)).lazily
  }
  case object BAR extends ClosedInt("BAR", "BAR", 1, Hints.empty) {
    override val hints: Hints = Hints(alloy.proto.ProtoIndex(1)).lazily
  }

  val values: List[ClosedInt] = List(
    FOO,
    BAR,
  )
  val tag: EnumTag[ClosedInt] = EnumTag.ClosedIntEnum
  implicit val schema: Schema[ClosedInt] = enumeration(tag, values).withId(id).addHints(hints)
}
