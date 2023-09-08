import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;

class Cpu {
    //private  char [] arrForSyntaxeror = {'!','@','#','$','%','^','&','*','(',')','_','-','=','q',};
    public Map<Integer, String> mapForLine = new HashMap<>();
    public Map<String, Integer> mapForLabel = new HashMap<>();
    public Map<String, Integer> mapForRegister = new HashMap<>();
    public Map<Integer, String> mapForLineClean = new HashMap<>();


    Cpu(String path) {
        mapForRegister.put("r0", null);
        mapForRegister.put("r1", null);
        mapForRegister.put("r2", null);
        mapForRegister.put("r3", null);
        mapForRegister.put("r4", null);
        mapForRegister.put("r5", null);
        mapForRegister.put("r6", null);
        mapForRegister.put("r7", null);
        mapForRegister.put("r8", null);
        mapForRegister.put("r9", null);
        mapForRegister.put("r10", null);
        ReadFromFile(path);
        if(SyntaxAnalys()){
            System.out.println("sax lava");
        }
        else {
            System.out.println("syntax eroor");
        }


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

    private String RemoveWhiteSpace(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    private boolean IsLabelOrNO(String str) {

        boolean result = false;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ';' && i != 0) {
                result = true;
            }
        }
        return result;
    }

    private void Label() {
        mapForLine.forEach((indexLine, line) -> {

            if (IsLabelOrNO(line)) {

                String lineResult;
                lineResult = line.substring(0, line.indexOf(';'));
                mapForLabel.put(lineResult, indexLine);
            }
        });
    }


    public boolean SyntaxAnalys() {
       boolean flag = true;

        for (Map.Entry<Integer, String> entry : mapForLineClean.entrySet()) {
            Integer index = entry.getKey();
            String line = entry.getValue();
            String lineForInstruction = line.substring(0,line.indexOf('r'));

            if (In3CharInstruction(lineForInstruction)) {
                System.out.println("this is line for intstruction  " + lineForInstruction);/////
                String lineForFirstRefister = line.substring(3, 5);

                if (FirstOperandIsRegisterOrNO(lineForFirstRefister)) {
                    System.out.println("this is line for register   " + lineForFirstRefister);/////
                    String lineFor2Operand = line.substring(6);

                    if (line.charAt(5) == ',') {
                        if (operandTwoIsNumberOrNo(lineFor2Operand) || operandTwoIsNumberOrNo(lineFor2Operand)){
                            System.out.println("line for  2 opernd    " + lineFor2Operand);
                            flag = true;
                        }

                        else return false;////// erkrodoperad if
                    } else return false;// storaket if
                } else return false;// arajin operand if
            } else if (In4CharInstruction(lineForInstruction)) {
                String lineWithLine = mapForLine.get(index);
                lineWithLine = lineWithLine.substring(lineWithLine.indexOf(';'));
                if (lineWithLine.charAt(0) > 96 && lineWithLine.charAt(0) < 123) {
                    flag = true;
                }
                else  return false;
            }
           else  return false;
        }
  return flag;
    }


    private boolean FirstOperandIsRegisterOrNO(String line) {
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

    private boolean operandTwoIsRegisterOrNo(String line) {
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

    private boolean operandTwoIsNumberOrNo(String line) {

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

    private boolean In3CharInstruction(String line) {
        if (line.equals("mov") || line.equals("add") || line.equals("sub") || line.equals("mul") || line.equals("div") || line.equals("xor") || line.equals("orr")
                || line.equals("and") || line.equals("jpm") || line.equals("cmp") || line.equals("inc") || line.equals("dec")) {
            return true;
        }

        return false;
    }

    private boolean In4CharInstruction(String line) {
        if (line.equals("jmpe") || line.equals("jmpl") || line.equals("jmpg")) {
            return true;
        }
        return false;

    }
    public void Run(){
        return RunHlepr
    }



 /*  public void TestMapLine() {

        mapForLine.forEach((index, line) -> {
            System.out.println(line + " " + index);
        });

    }

    public void TestMapLabel() {

        mapForLabel.forEach((line, index) -> {
            System.out.println(line + " "  + index);
        });
    }

    public void TestMapLineCLean() {

        mapForLineClean.forEach((index, line) -> {
            System.out.println(line + " "  + index);
        });

    }*/

}

