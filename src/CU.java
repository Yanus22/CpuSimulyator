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


    public CU(Map<Integer, String> mapForLine, Map<String, Integer> mapForLabel, Map<String, Register> mapForRegister, Map<Integer, String> mapForLineClean, String path) {
        this.mapForLabel = mapForLabel;
        this.mapForLineClean = mapForLineClean;
        this.mapForLine = mapForLine;
        this.mapForRegister = mapForRegister;
        Alu = new ALU(mapForLine, mapForLabel, mapForRegister, mapForLineClean);
        Compiler = new CompilerSyntax(mapForLine, mapForLabel, mapForRegister, mapForLineClean, path);
    }

    public void Instruction(String line, int index) {
        if(line == null){
            return;
        }
        int indexNext = index + 1;
        String lineNext = mapForLineClean.get(indexNext);
        String instruction3Char = line.substring(0, 3);
        String intruction4Char = line.substring(0, 4);
        String instructionPrint = line.substring(0, 5);
        if (CompilerSyntax.In3CharInstruction(instruction3Char) && !(instruction3Char.equals("inc") || instruction3Char.equals("dec"))) {
            String operand1 = line.substring(3, line.indexOf(','));
            String operand2 = line.substring(line.indexOf(',') + 1);

            switch (instruction3Char) {
                case "mov":
                    Alu.Mov(operand1, operand2);
                    break;
                case "add":
                    Alu.Add(operand1, operand2);
                    break;
                case "sub":
                    Alu.Sub(operand1, operand2);
                    break;
                case "mul":
                    Alu.Mul(operand1, operand2);
                    break;
                case "div":
                    Alu.Div(operand1, operand2);
                    break;
                case "and":
                    Alu.And(operand1, operand2);
                    break;
                case "xor":
                    Alu.Xor(operand1, operand2);
                    break;
                case "orr":
                    Alu.Orr(operand1, operand2);
                    break;
            }
        } else if (instruction3Char.equals("inc")) {
            Alu.Increment(line.substring(3));
        } else if (instruction3Char.equals("dec")) {
            Alu.Decrement(line.substring(3));
        } else if (instructionPrint.equals("print")) {
            Alu.Print(line.substring(line.indexOf('t') + 1));
        } else if (intruction4Char.equals("jmpe")) {
            String label = line.substring(line.indexOf('e') + 1);
            if (mapForLabel.get(label) != null) {
                String prevLine = mapForLineClean.get(index - 1);
                if (prevLine.startsWith("cmp")) {
                    String register = prevLine.substring(prevLine.indexOf('p') + 1, prevLine.indexOf(','));
                    String operand = prevLine.substring(prevLine.indexOf(',') + 1);
                    int resultCmp = Alu.Compear(register, operand);
                    if (resultCmp == 0) {
                        indexNext = mapForLabel.get(label);
                        lineNext = mapForLineClean.get(indexNext);
                        Instruction(lineNext, indexNext);
                        return;
                    } else {
                        Instruction(lineNext, indexNext);
                    }
                } else {
                    Instruction(lineNext, indexNext);
                }
            } else {
                throw new ILegealOperandException("label not found");
            }
        } //
        Instruction(lineNext,indexNext);

    }
}




