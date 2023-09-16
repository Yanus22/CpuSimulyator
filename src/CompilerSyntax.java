import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

 public  class CompilerSyntax    //SYtnax check class
 {
    private Map<Integer, String> mapForLine;
    private Map<String, Integer> mapForLabel;//map for label name and her line number
    private Map<String, Register> mapForRegister;//map for register
    private Map<Integer, String> mapForLineClean;//map for without label

     CompilerSyntax ( Map<Integer, String> mapForLine, Map<String, Integer> mapForLabel, Map<String, Register> mapForRegister, Map<Integer, String> mapForLineClean, String path) {
        this.mapForLabel = mapForLabel;
        this.mapForLineClean = mapForLineClean;
        this.mapForLine = mapForLine;
        this.mapForRegister = mapForRegister;
        ReadFromFile(path);  //read text
         Label();//which line is label push label name and her Number line

    }

    public boolean SyntaxAnalys()    //Syntax analays
    {
        boolean flag = true;

        for (Map.Entry<Integer, String> entry : mapForLineClean.entrySet()) {
            Integer index = entry.getKey();
            String line = entry.getValue();
           if ((line.startsWith("inc") || line.startsWith("dec")) && OperandIsRegisterOrNO(line.substring(line.indexOf('c')+1)) )
            {
                continue;
            }

            String lineForInstruction = line.substring(0, 3);
            String lineForInstructionJumps = line.substring(0, 4);
            if (In3CharInstruction(lineForInstruction))//chek instruction is  3 char or no
            {
                String lineForFirstRegister = line.substring(3, line.indexOf(','));
                if (OperandIsRegisterOrNO(lineForFirstRegister))//check operand 1 is rgister or no
                {
                    String lineFor2Operand = line.substring(line.indexOf(',') + 1);
                    if (line.charAt(5) == ',' || (line.charAt(5) == '0' && line.charAt(6) == ',')) // chek operand 2
                    {
                        if (OperandIsNumberOrNo(lineFor2Operand) || OperandIsRegisterOrNO(lineFor2Operand))
                        {
                            flag = true;
                        }
                        else return false;
                    }
                    else return false;// ','  ->    if
                }
                else return false;// first operand  -> if
            }
            else if (In4CharInstruction(lineForInstructionJumps))//chek instruction for jumps
            {
                String lineWithLine = mapForLine.get(index);
                lineWithLine = lineWithLine.substring(lineWithLine.indexOf(';') + 1);
                if (lineWithLine.charAt(0) > 96 && lineWithLine.charAt(0) < 123)
                {
                    flag = true;
                }
                else return false;
            }
            else if (line.startsWith("print"))
            {
                if (OperandIsRegisterOrNO(line.substring(line.indexOf('t') + 1)))
                {
                    flag = true;
                }
            }
            else return false;
        }
        return flag;
    }


    private boolean OperandIsRegisterOrNO(String line)// check line is register or no
    {
        if (line.length() == 2)
        {
            if (line.equals("r0") || line.equals("r1") || line.equals("r2") || line.equals("r3") ||
                    line.equals("r4") || line.equals("r5") || line.equals("r6") ||
                    line.equals("r7") || line.equals("r8") || line.equals("r9") ) {
                return true;
            }
        }
        else if (line.length() == 3 && line.equals("r10"))
        {
            return true;
        }
        return false;
    }


    private boolean OperandIsNumberOrNo(String line) {// check operand 2 is number or no

        for (int i = 0; i < line.length(); i++)
        {
            int chr = line.charAt(i);
            if (chr == 46) {//. for float
                continue;
            }
            else if (chr < 48 || chr > 57)
            {
                return false;
            } // asci number value
        }
        return true;
    }

    static boolean In3CharInstruction(String line)
    {
        if (line.equals("mov") || line.equals("add") || line.equals("sub") || line.equals("mul") || line.equals("div") || line.equals("xor") || line.equals("orr")
                || line.equals("and") || line.equals("jpm") || line.equals("cmp") || line.equals("inc") || line.equals("dec")) {
            return true;
        }

        return false;
    }

    static boolean In4CharInstruction(String line)//check instruction in 4 char
    {
        if (line.equals("jmpe") || line.equals("jmpl") || line.equals("jmpg")){
            return true;
        }
        return false;

    }

    public void ReadFromFile(String path)//read and push mapLine line and number  ,and  mapForLineClean  push cleand map
    {
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
        }
        catch (Exception e) {
            System.out.println("Java problem for read");
        }
        mapForLine.forEach((index, line) -> {
            if (IsLabelOrNO(line))
            {
                mapForLineClean.put(index, line.substring(line.indexOf(';') + 1));
            }
            else
            {
                mapForLineClean.put(index, line);
            }
        });

    }
    public String RemoveWhiteSpace(String str) { // whit space remove
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) != ' ')
            {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    static boolean IsLabelOrNO(String str) //check it label or no
    {
        boolean result = false;
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == ';' && i != 0)
            {
                result = true;
            }
        }
        return result;
    }

    public void Label() { //all labels add mapForLabel
        mapForLine.forEach((indexLine, line) -> {
            if (IsLabelOrNO(line))
            {
                String lineResult;
                lineResult = line.substring(0, line.indexOf(';'));
                mapForLabel.put(lineResult, indexLine);
            }
        });
    }


}
