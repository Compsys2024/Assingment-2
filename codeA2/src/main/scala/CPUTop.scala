import chisel3._
import chisel3.util._

class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  //Connecting the modules
  programCounter.io.run := io.run
  programMemory.io.address := programCounter.io.programCounter
  //Noget instruktions halløj her
  //Adress ind i ProgramArray burde give instruktionen but how to do this????

  //Just creating all possibilites, this will throw no errors as the instruction is always 32 bits.
  //The control unit will make sure the correct things are used?
  val Immediate = programMemory.io.instructionRead(11, 0)

  //Giving the control unit the opcode, so it is ready for use.
  controlUnit.io.instruction := programMemory.io.instructionRead
  registerFile.io.writeEnable := controlUnit.io.RegWrite

  //Loading registers
  registerFile.io.readReg1 := programMemory.io.instructionRead(27,20)
  registerFile.io.readReg2 := programMemory.io.instructionRead(19,12)
  registerFile.io.Reg1 := registerFile.io.readReg1
  registerFile.io.Reg2 := registerFile.io.readReg2

  ///ALU!
  //Not sure if a multiplexer is needed for op2, as operand 2 is always selected for both R and I-type instructions
  alu.io.Operand1 := RegisterFile.io.Reg1
  alu.io.Operand2 := RegisterFile.io.Reg2
  alu.io.control := controlUnit.io.instruction(31,28)
  val result := Mux(controlUnit.io.Branch,alu.io.ALUresult, alu.io.COMPresult)

  //Storing data
  when (controlUnit.io.MemWrite) {
    dataMemory.io.address := result
    dataMemory.io.dataWrite := registerFile.io.Reg2
  }

  //Writing to a register if selecting
  when (registerFile.io.writeEnable){
    registerFile.io.writeReg := programMemory.io.instructionRead(11, 4)
    registerFile.io.writeData := Mux(Mux2, result, dataMemory.io.dataRead)
    registerFile.io.writeData := registerFile.io.writeReg
  }

  //Branching
  when (controlUnit.io.Branch && result){
    programCounter.io.programCounter := programMemory.io.instructionRead(27,0) //Target instruction
  }





  ////////////////////////////////////////////
  //Continue here with your connections
  ////////////////////////////////////////////

  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}