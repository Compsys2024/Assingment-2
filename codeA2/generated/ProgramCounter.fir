;buildInfoPackage: chisel3, version: 3.3.2, scalaVersion: 2.12.11, sbtVersion: 1.3.10
circuit ProgramCounter : 
  module ProgramCounter : 
    input clock : Clock
    input reset : UInt<1>
    output io : {flip stop : UInt<1>, flip jump : UInt<1>, flip run : UInt<1>, flip programCounterJump : UInt<16>, programCounter : UInt<16>}
    
    reg countReg : UInt<16>, clock with : (reset => (reset, UInt<16>("h00"))) @[ProgramCounter.scala 13:25]
    io.programCounter <= UInt<1>("h00") @[ProgramCounter.scala 15:21]
    node _T = lt(io.programCounter, UInt<16>("h0ffff")) @[ProgramCounter.scala 18:27]
    when _T : @[ProgramCounter.scala 18:37]
      node _T_1 = eq(io.run, UInt<1>("h00")) @[ProgramCounter.scala 19:11]
      node _T_2 = or(_T_1, io.stop) @[ProgramCounter.scala 19:19]
      when _T_2 : @[ProgramCounter.scala 19:30]
        countReg <= countReg @[ProgramCounter.scala 20:16]
        skip @[ProgramCounter.scala 19:30]
      else : @[ProgramCounter.scala 22:47]
        node _T_3 = eq(io.stop, UInt<1>("h00")) @[ProgramCounter.scala 22:27]
        node _T_4 = and(io.run, _T_3) @[ProgramCounter.scala 22:25]
        node _T_5 = eq(io.jump, UInt<1>("h00")) @[ProgramCounter.scala 22:38]
        node _T_6 = and(_T_4, _T_5) @[ProgramCounter.scala 22:36]
        when _T_6 : @[ProgramCounter.scala 22:47]
          node _T_7 = add(countReg, UInt<1>("h01")) @[ProgramCounter.scala 23:28]
          node _T_8 = tail(_T_7, 1) @[ProgramCounter.scala 23:28]
          countReg <= _T_8 @[ProgramCounter.scala 23:16]
          skip @[ProgramCounter.scala 22:47]
        else : @[ProgramCounter.scala 25:47]
          node _T_9 = eq(io.stop, UInt<1>("h00")) @[ProgramCounter.scala 25:27]
          node _T_10 = and(io.run, _T_9) @[ProgramCounter.scala 25:25]
          node _T_11 = and(_T_10, io.jump) @[ProgramCounter.scala 25:36]
          when _T_11 : @[ProgramCounter.scala 25:47]
            countReg <= io.programCounterJump @[ProgramCounter.scala 26:16]
            skip @[ProgramCounter.scala 25:47]
      skip @[ProgramCounter.scala 18:37]
    else : @[ProgramCounter.scala 29:15]
      countReg <= countReg @[ProgramCounter.scala 31:14]
      skip @[ProgramCounter.scala 29:15]
    io.programCounter <= countReg @[ProgramCounter.scala 33:21]
    
