package riscv.core

import chisel3._
import peripheral.RAMBundle
import riscv.Parameters

class CPUBundle extends Bundle {
  val instruction_address = Output(UInt(Parameters.AddrWidth))
  val instruction         = Input(UInt(Parameters.DataWidth))
  val instruction_valid   = Input(Bool())
  val memory_bundle       = Flipped(new RAMBundle)
  val device_select       = Output(UInt(Parameters.SlaveDeviceCountBits.W))
  val interrupt_flag      = Input(UInt(Parameters.InterruptFlagWidth))
  val debug_read_address  = Input(UInt(Parameters.PhysicalRegisterAddrWidth))
  val debug_read_data     = Output(UInt(Parameters.DataWidth))
}
