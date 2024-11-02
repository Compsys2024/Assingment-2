module ProgramCounter(
  input         clock,
  input         reset,
  input         io_stop,
  input         io_jump,
  input         io_run,
  input  [15:0] io_programCounterJump,
  output [15:0] io_programCounter
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_REG_INIT
  reg [15:0] countReg; // @[ProgramCounter.scala 13:25]
  wire  _T = io_programCounter < 16'hffff; // @[ProgramCounter.scala 18:27]
  wire  _T_1 = ~io_run; // @[ProgramCounter.scala 19:11]
  wire  _T_2 = _T_1 | io_stop; // @[ProgramCounter.scala 19:19]
  wire  _T_3 = ~io_stop; // @[ProgramCounter.scala 22:27]
  wire  _T_4 = io_run & _T_3; // @[ProgramCounter.scala 22:25]
  wire  _T_5 = ~io_jump; // @[ProgramCounter.scala 22:38]
  wire  _T_6 = _T_4 & _T_5; // @[ProgramCounter.scala 22:36]
  wire [15:0] _T_8 = countReg + 16'h1; // @[ProgramCounter.scala 23:28]
  wire  _T_11 = _T_4 & io_jump; // @[ProgramCounter.scala 25:36]
  assign io_programCounter = countReg; // @[ProgramCounter.scala 15:21 ProgramCounter.scala 33:21]
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  countReg = _RAND_0[15:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
  always @(posedge clock) begin
    if (reset) begin
      countReg <= 16'h0;
    end else if (_T) begin
      if (!(_T_2)) begin
        if (_T_6) begin
          countReg <= _T_8;
        end else if (_T_11) begin
          countReg <= io_programCounterJump;
        end
      end
    end
  end
endmodule
