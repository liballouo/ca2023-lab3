package riscv.core.fivestage_final

import chisel3._
import riscv.Parameters

object ForwardingType {
  val NoForward      = 0.U(2.W)
  val ForwardFromMEM = 1.U(2.W)
  val ForwardFromWB  = 2.U(2.W)
}

class Forwarding extends Module {
  val io = IO(new Bundle() {
    val rs1_id               = Input(UInt(Parameters.PhysicalRegisterAddrWidth)) // id.io.regs_reg1_read_address             //
    val rs2_id               = Input(UInt(Parameters.PhysicalRegisterAddrWidth)) // id.io.regs_reg2_read_address             //
    val rs1_ex               = Input(UInt(Parameters.PhysicalRegisterAddrWidth)) // id2ex.io.output_regs_reg1_read_address
    val rs2_ex               = Input(UInt(Parameters.PhysicalRegisterAddrWidth)) // id2ex.io.output_regs_reg2_read_address
    val rd_mem               = Input(UInt(Parameters.PhysicalRegisterAddrWidth)) // ex2mem.io.output_regs_write_address
    val reg_write_enable_mem = Input(Bool())                                     // ex2mem.io.output_regs_write_enable
    val rd_wb                = Input(UInt(Parameters.PhysicalRegisterAddrWidth)) // mem2wb.io.output_regs_write_address
    val reg_write_enable_wb  = Input(Bool())                                     // mem2wb.io.output_regs_write_enable

    val reg1_forward_id = Output(UInt(2.W)) // id.io.reg1_forward                       //
    val reg2_forward_id = Output(UInt(2.W)) // id.io.reg2_forward                       //
    val reg1_forward_ex = Output(UInt(2.W)) // ex.io.reg1_forward
    val reg2_forward_ex = Output(UInt(2.W)) // ex.io.reg2_forward
  })

  // io.reg1_forward_ex
  when(io.reg_write_enable_mem && io.rs1_ex === io.rd_mem && io.rd_mem =/= 0.U) {
    io.reg1_forward_ex := ForwardingType.ForwardFromMEM
  }.elsewhen(io.reg_write_enable_wb && io.rs1_ex === io.rd_wb && io.rd_wb =/= 0.U) {
    io.reg1_forward_ex := ForwardingType.ForwardFromWB
  }.otherwise {
    io.reg1_forward_ex := ForwardingType.NoForward
  }
  // io.reg2_forward_ex
  when(io.reg_write_enable_mem && io.rs2_ex === io.rd_mem && io.rd_mem =/= 0.U) {
    io.reg2_forward_ex := ForwardingType.ForwardFromMEM
  }.elsewhen(io.reg_write_enable_wb && io.rs2_ex === io.rd_wb && io.rd_wb =/= 0.U) {
    io.reg2_forward_ex := ForwardingType.ForwardFromWB
  }.otherwise {
    io.reg2_forward_ex := ForwardingType.NoForward
  }
  // io.reg1_forward_id
  when(io.reg_write_enable_mem && io.rs1_id === io.rd_mem && io.rd_mem =/= 0.U) {
    io.reg1_forward_id := ForwardingType.ForwardFromMEM
  }.elsewhen(io.reg_write_enable_wb && io.rs1_id === io.rd_wb && io.rd_wb =/= 0.U) {
    io.reg1_forward_id := ForwardingType.ForwardFromWB
  }.otherwise {
    io.reg1_forward_id := ForwardingType.NoForward
  }
  // io.reg2_forward_id
  when(io.reg_write_enable_mem && io.rs2_id === io.rd_mem && io.rd_mem =/= 0.U) {
    io.reg2_forward_id := ForwardingType.ForwardFromMEM
  }.elsewhen(io.reg_write_enable_wb && io.rs2_id === io.rd_wb && io.rd_wb =/= 0.U) {
    io.reg2_forward_id := ForwardingType.ForwardFromWB
  }.otherwise {
    io.reg2_forward_id := ForwardingType.NoForward
  }
}
