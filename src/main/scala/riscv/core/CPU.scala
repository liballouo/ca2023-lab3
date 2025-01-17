package riscv.core

import chisel3._
import riscv.core.fivestage_final.{ CPU => FiveStageCPUFinal }
import riscv.core.fivestage_forward.{ CPU => FiveStageCPUForward }
import riscv.core.fivestage_stall.{ CPU => FiveStageCPUStall }
import riscv.core.threestage.{ CPU => ThreeStageCPU }
import riscv.ImplementationType

class CPU(val implementation: Int = ImplementationType.FiveStageFinal) extends Module {
  val io = IO(new CPUBundle)
  implementation match {
    case ImplementationType.ThreeStage =>
      val cpu = Module(new ThreeStageCPU)
      cpu.io <> io
    case ImplementationType.FiveStageStall =>
      val cpu = Module(new FiveStageCPUStall)
      cpu.io <> io
    case ImplementationType.FiveStageForward =>
      val cpu = Module(new FiveStageCPUForward)
      cpu.io <> io
    case _ =>
      val cpu = Module(new FiveStageCPUFinal)
      cpu.io <> io
  }
}
