package smithy4s.example

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.long
import smithy4s.schema.Schema.struct

final case class Longs(long: Long, slong: Long, ulong: Long, fixedLong: Long, fixedSlong: Long)

object Longs extends ShapeTag.Companion[Longs] {
  val id: ShapeId = ShapeId("smithy4s.example", "Longs")

  val hints: Hints = Hints(
    alloy.proto.ProtoEnabled(),
  ).lazily

  // constructor using the original order from the spec
  private def make(long: Long, slong: Long, ulong: Long, fixedLong: Long, fixedSlong: Long): Longs = Longs(long, slong, ulong, fixedLong, fixedSlong)

  implicit val schema: Schema[Longs] = struct(
    long.required[Longs]("long", _.long).addHints(alloy.proto.ProtoIndex(1)),
    long.required[Longs]("slong", _.slong).addHints(alloy.proto.ProtoNumType.SIGNED.widen, alloy.proto.ProtoIndex(2)),
    long.required[Longs]("ulong", _.ulong).addHints(alloy.proto.ProtoNumType.UNSIGNED.widen, alloy.proto.ProtoIndex(3)),
    long.required[Longs]("fixedLong", _.fixedLong).addHints(alloy.proto.ProtoNumType.FIXED.widen, alloy.proto.ProtoIndex(4)),
    long.required[Longs]("fixedSlong", _.fixedSlong).addHints(alloy.proto.ProtoNumType.FIXED_SIGNED.widen, alloy.proto.ProtoIndex(5)),
  )(make).withId(id).addHints(hints)
}
