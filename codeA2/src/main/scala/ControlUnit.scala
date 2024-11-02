import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val instruction = Input(UInt(32.W))

    val RegWrite = Output(Bool())
    val Mux1 = Output(Bool())
    val Mux2 = Output(Bool())
    val MemWrite = Output(Bool())
    val ALUop = Output(UInt(4.W))
    val Branch = Output(Bool())
  })

  val opcode = io.instruction >> 28.U

  switch(opcode){
    is (1.U){ //add
      io.RegWrite := 1.B
      io.Mux1 := 1.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 0.B
    }
    is (2.U){ //mult
      io.RegWrite := 1.B
      io.Mux1 := 1.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 0.B
    }
    is (3.U){ //ADDI
      io.RegWrite := 1.B
      io.Mux1 := 0.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 0.B
    }
    is (4.U){ //subi
      io.RegWrite := 1.B
      io.Mux1 := 1.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 0.B
    }
    is (5.U){ //LD (double check)
      io.RegWrite := 1.B
      io.Mux1 := 1.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 1.B
      io.Branch := 0.B
    }
    is (6.U){ //LI
      io.RegWrite := 1.B
      io.Mux1 := 0.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 0.B

    }
    is (7.U){ //SD
      io.RegWrite := 0.B
      io.Mux1 := 1.B
      io.MemWrite := 1.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 0.B
    }
    is (8.U){ //JBEQ
      io.RegWrite := 0.B
      io.Mux1 := 1.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 1.B

    }
    is (9.U){ //JB
      io.RegWrite := 0.B
      io.Mux1 := 1.B
      io.MemWrite := 0.B
      io.ALUop := opcode
      io.Mux2 := 0.B
      io.Branch := 1.B
    }
  }
}