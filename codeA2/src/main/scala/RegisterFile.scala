import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val readReg1 = Input(UInt(4.W))
    val readReg2 = Input(UInt(4.W))
    val writeReg = Input(UInt(4.W))
    val writeData = Input(UInt(32.W))
    val writeEnable = Input(Bool())
    val Reg1 = Output(UInt(32.W))
    val Reg2 = Output(UInt(32.W))
  })

  val registerFile = Reg(Vec(16, UInt(32.W)))

  io.Reg1 := registerFile(io.readReg1)
  io.Reg2 := registerFile(io.readReg2)

  when (io.writeEnable) {
    registerFile(io.writeReg) := io.writeData
  }




}