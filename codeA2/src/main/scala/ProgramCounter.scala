import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool())
    val jump = Input(Bool())
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))

  })
  val countReg = RegInit(0.U(16.W))

  io.programCounter := 0.U
  //Implement this module here (respect the provided interface, since it used by the tester)

  when (io.programCounter < 65535.U){
    when (!io.run | io.stop) {
      countReg := countReg

    } .elsewhen (io.run & !io.stop & !io.jump){
      countReg := countReg + 1.U

    } .elsewhen (io.run & !io.stop & io.jump) {
      countReg := io.programCounterJump
    }

  }.otherwise {
    println("Maximum count value reached!")
    countReg := countReg
  }
  io.programCounter := countReg

}