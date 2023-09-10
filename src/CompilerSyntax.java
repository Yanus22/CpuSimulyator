import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CompilerSyntax {
    private Map<Integer, String> mapForLine;
    private Map<String, Integer> mapForLabel;
    private Map<String, Register> mapForRegister;
    private Map<Integer, String> mapForLineClean;

    public CompilerSyntax(Map<Integer, String> mapForLine, Map<String, Integer> mapForLabel, Map<String, Register> mapForRegister, Map<Integer, String> mapForLineClean, String path) {
        this.mapForLabel = mapForLabel;
        this.mapForLineClean = mapForLineClean;
        this.mapForLine = mapForLine;
        this.mapForRegister = mapForRegister;
        ReadFromFile(path);
    }

    public boolean SyntaxAnalys() {
        boolean flag = true;

        for (Map.Entry<Integer, String> entry : mapForLineClean.entrySet()) {
            Integer index = entry.getKey();
            String line = entry.getValue();
            if(line.startsWith("inc") || line.startsWith("dec")){
                continue;
            }
            String lineForInstruction = line.substring(0, 3);
            String lineForInstructionJumps = line.substring(0, 4);
            if (In3CharInstruction(lineForInstruction)) {
                String lineForFirstRegister = line.substring(3, line.indexOf(','));
                if (OperandIsRegisterOrNO(lineForFirstRegister)) {
                    String lineFor2Operand = line.substring(line.indexOf(',') + 1);
                    if (line.charAt(5) == ',' || (line.charAt(5) == '0' && line.charAt(6) == ',')) {
                        if (OperandIsNumberOrNo(lineFor2Operand) || OperandIsRegisterOrNO(lineFor2Operand)) {
                            flag = true;
                        } else return false;////// erkrodoperad if
                    } else return false;// storaket if
                } else return false;// arajin operand if
            } else if (In4CharInstruction(lineForInstructionJumps)) {
                String lineWithLine = mapForLine.get(index);
                lineWithLine = lineWithLine.substring(lineWithLine.indexOf(';') + 1);
                if (lineWithLine.charAt(0) > 96 && lineWithLine.charAt(0) < 123) {
                    flag = true;
                } else return false;
            } else if (line.startsWith("print")) {
                if (OperandIsRegisterOrNO(line.substring(line.indexOf('t') + 1))) {
                    flag = true;
                }
            } else return false;
        }
        return flag;
    }


    private boolean OperandIsRegisterOrNO(String line) {
        if (line.length() == 2) {
            if (line.equals("r0") || line.equals("r1") || line.equals("r2") || line.equals("r3") ||
                    line.equals("r4") || line.equals("r5") || line.equals("r6") ||
                    line.equals("r7") || line.equals("r8") || line.equals("r9") ||
                    line.equals("r10")) {
                return true;
            }
        } else if (line.length() == 3 && line.equals("r10")) {
            return true;
        }
        return false;
    }


    private boolean OperandIsNumberOrNo(String line) {

        for (int i = 0; i < line.length(); i++) {
            int chr = line.charAt(i);
            if (chr == 46) {
                continue;
            } else if (chr < 48 || chr > 57) {
                return false;
            } // ascii hamapatasxcan tara kam tiv
        }
        return true;
    }

    static boolean In3CharInstruction(String line) {
        if (line.equals("mov") || line.equals("add") || line.equals("sub") || line.equals("mul") || line.equals("div") || line.equals("xor") || line.equals("orr")
                || line.equals("and") || line.equals("jpm") || line.equals("cmp") || line.equals("inc") || line.equals("dec")) {
            return true;
        }

        return false;
    }

    static boolean In4CharInstruction(String line) {
        if (line.equals("jmpe") || line.equals("jmpl") || line.equals("jmpg")) {
            return true;
        }
        return false;

    }

    public void ReadFromFile(String path) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            int lineIndex = 0;
            while ((line = reader.readLine()) != null) {
                mapForLine.put(lineIndex, RemoveWhiteSpace(line));
                ++lineIndex;
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("kardalu xndir");
        }
        Label();
        mapForLine.forEach((index, line) -> {
            if (IsLabelOrNO(line)) {
                mapForLineClean.put(index, line.substring(line.indexOf(';') + 1));
            } else {
                mapForLineClean.put(index, line);
            }
        });
    }

    public String RemoveWhiteSpace(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    static boolean IsLabelOrNO(String str) {

        boolean result = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ';' && i != 0) {
                result = true;
            }
        }
        return result;
    }

    public void Label() {
        mapForLine.forEach((indexLine, line) -> {

            if (IsLabelOrNO(line)) {

                String lineResult;
                lineResult = line.substring(0, line.indexOf(';'));
                mapForLabel.put(lineResult, indexLine);
            }
        });
    }


}
