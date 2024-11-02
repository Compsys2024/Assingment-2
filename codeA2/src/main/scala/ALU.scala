import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    //Define the module interface here (inputs/outputs)
    val control = Input(UInt(4.W))
    val Operand1 = Input(UInt(32.W))
    val Operand2 = Input(UInt(32.W))
    val ALUresult = Output(UInt(32.W))
    val COMPresult = Output(Bool())
  })

  switch(io.control){
    is (1.U){ //Add
      io.ALUresult := io.Operand1 + io.Operand2
    }
    is (2.U){ //Multiply
      io.ALUresult := io.Operand1 * io.Operand2
    }
    is (3.U){ //Add immediate
      io.ALUresult := io.Operand1 + io.Operand2
    }
    is (4.U){ //Subtract immediate
      io.ALUresult := io.Operand1 - io.Operand2
    }
    is (5.U){ //Load data
      io.ALUresult := io.Operand1
    }
    is (6.U){ // Load immediate
      io.ALUresult := io.Operand1
    }
    is (7.U){ //Store data
      io.ALUresult := io.Operand1
    }
    is (8.U){ // Jump if equal
      when(io.Operand1 === io.Operand2){
        io.COMPresult := 1.B
      }.otherwise {
        io.COMPresult := 0.B
      }
    }
    is (9.U){ //Jump
      io.COMPresult := 1.B
    }
  }
}