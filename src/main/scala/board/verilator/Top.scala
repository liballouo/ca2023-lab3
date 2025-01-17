package board.verilator

import chisel3._
import chisel3.stage.ChiselGeneratorAnnotation
import chisel3.stage.ChiselStage
import riscv.core.CPU
import riscv.core.CPUBundle
import riscv.ImplementationType

class Top extends Module {
  val io = IO(new CPUBundle)

  val cpu = Module(new CPU(implementation = ImplementationType.ThreeStage))

  io.device_select          := 0.U
  cpu.io.debug_read_address := io.debug_read_address
  io.debug_read_data        := cpu.io.debug_read_data

  io.memory_bundle <> cpu.io.memory_bundle
  io.instruction_address := cpu.io.instruction_address
  cpu.io.instruction     := io.instruction

  cpu.io.interrupt_flag    := io.interrupt_flag
  cpu.io.instruction_valid := io.instruction_valid
}

object VerilogGenerator extends App {
  (new ChiselStage)
    .execute(Array("-X", "verilog", "-td", "verilog/verilator"), Seq(ChiselGeneratorAnnotation(() => new Top())))
}
