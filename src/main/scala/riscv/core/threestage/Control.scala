package riscv.core.threestage

import chisel3._

class Control extends Module {
  val io = IO(new Bundle {
    val JumpFlag = Input(Bool())
    val Flush    = Output(Bool())
  })
  io.Flush := io.JumpFlag
}
