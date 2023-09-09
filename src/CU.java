import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class CU {
    public Map<Integer, String> mapForLine;
    public Map<String, Integer> mapForLabel;
    public Map<String, Register> mapForRegister;
    public Map<Integer, String> mapForLineClean;
    ALU Alu;
    CompilerSyntax Compiler;


    public CU(Map<Integer, String> mapForLine, Map<String, Integer> mapForLabel, Map<String, Register> mapForRegister, Map<Integer, String> mapForLineClean) {
        this.mapForLabel = mapForLabel;
        this.mapForLineClean = mapForLineClean;
        this.mapForLine = mapForLine;
        this.mapForRegister = mapForRegister;
        Alu = new ALU(mapForLine, mapForLabel, mapForRegister, mapForLineClean);
        Compiler = new CompilerSyntax(mapForLine, mapForLabel, mapForRegister, mapForLineClean);

    }


    public void Instruction(String line) {
        String instruction3Char = line.substring(0, 3);
        String intruction4Char = line.substring(0, 4);
        if (CompilerSyntax.In3CharInstruction(instruction3Char) && !(instruction3Char.equals("inc") || instruction3Char.equals("dec")))
        {
            String operand1 = line.substring(3, line.indexOf(','));
            String operand2 = line.substring(line.indexOf(',') + 1);
            switch (instruction3Char){
                case "mov": Alu.Mov(operand1,operand2);
                break;
                case "add": Alu.Add(operand1,operand2);
                break;
                case "sub": Alu.Sub(operand1,operand2);
                break;
                case  "mul":Alu.Mul(operand1,operand2);
                break;
                case  "div": Alu.Div(operand1,operand2);
            }
        }
    }


}
