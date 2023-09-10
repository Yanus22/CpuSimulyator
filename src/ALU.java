import java.util.Map;

public class ALU {

    public Map<Integer, String> mapForLine;
    public Map<String, Integer> mapForLabel;
    public Map<String, Register> mapForRegister;
    public Map<Integer, String> mapForLineClean;

    public ALU(Map<Integer, String> mapForLine, Map<String, Integer> mapForLabel, Map<String, Register> mapForRegister, Map<Integer, String> mapForLineClean) {
        this.mapForLabel = mapForLabel;
        this.mapForLineClean = mapForLineClean;
        this.mapForLine = mapForLine;
        this.mapForRegister = mapForRegister;
    }

    ALU() {
    }

    public void Mov(String register, String operand) {
        if (OperandIsRegisterOrNO(register) && operand.length() != 0) {
            if (OperandIsNumberOrNo(operand)) {
                int result = (int) Float.parseFloat(operand);
                Register register1 = mapForRegister.get(register);
                register1.setValue(result);
                mapForRegister.put(register, register1);
            } else if (OperandIsRegisterOrNO(register) && OperandIsRegisterOrNO(operand)) {
                int result = mapForRegister.get(operand).getValue();
                Register register1 = mapForRegister.get(register);
                register1.setValue(result);
                mapForRegister.put(register, register1);
            } else {
                throw new ILegealOperandException("problem second operand");
            }
        } else {
            throw new ILegealOperandException("movi mej xmdir ka arajin operand");
        }
    }

    public void Add(String register1, String operand) {
        AddSub(register1, operand, true);
    }

    public void Sub(String register1, String operand) {
        AddSub(register1, operand, false);
    }

    public void Mul(String register1, String operand) {
        MulDiv(register1, operand, true);
    }

    public void Div(String register1, String operand) {
        MulDiv(register1, operand, false);
    }

    public void And(String register, String operand) {
        AndOrrXor(register, operand, "and");
    }

    public void Orr(String register, String operand) {
        AndOrrXor(register, operand, "orr");
    }

    public void Xor(String register, String operand) {
        AndOrrXor(register, operand, "xor");
    }

    public void Increment(String register) {
        if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register) != null)) {
            int value = mapForRegister.get(register).getValue();
            value++;
            Register register1 = mapForRegister.get(register);
            register1.setValue(value);
            mapForRegister.put(register, register1);
        } else {
            throw new ILegealOperandException("problem for operand");
        }
    }

    public void Decrement(String register) {
        if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register) != null)) {
            int value = mapForRegister.get(register).getValue();
            value--;
            Register register1 = mapForRegister.get(register);
            register1.setValue(value);
            mapForRegister.put(register, register1);
        } else {
            throw new ILegealOperandException("problem for decrement ");
        }
    }

    public int Compear(String register, String operand) {
        int result = 0;
        if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register) != null)) {
            if (OperandIsRegisterOrNO(operand) && (mapForRegister.get(register) != null)) {
                result = mapForRegister.get(register).getValue() - mapForRegister.get(operand).getValue();
            } else if (OperandIsNumberOrNo(operand)) {
                result = mapForRegister.get(register).getValue()  - (int) Float.parseFloat(operand);

            }
            else{
                throw  new ILegealOperandException("problem for second  operand");
            }
        }
        else {
            throw  new ILegealOperandException("problem the first operand");
        }
        return  result;
    }

    public void Print(String register) {
        System.out.println(mapForRegister.get(register).getValue());
    }


    public void AndOrrXor(String register, String operand, String bitIntsruction) {
        if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register).getValue() != null)) {
            Register register1 = mapForRegister.get(register);
            int secondOperandValue = 0;
            if (OperandIsRegisterOrNO(operand) && (mapForRegister.get(register).getValue() != null)) {
                secondOperandValue = mapForRegister.get(operand).getValue();
            } else if (OperandIsNumberOrNo(operand)) {
                secondOperandValue = (int) Float.parseFloat(operand);
            } else {
                throw new ILegealOperandException("problem second operand");
            }
            int result = 0;
            if (bitIntsruction.equals("and")) {
                result = register1.getValue() & secondOperandValue;
            } else if (bitIntsruction.equals("orr")) {
                result = register1.getValue() | secondOperandValue;
            } else if (bitIntsruction.equals("xor")) {
                result = register1.getValue() ^ secondOperandValue;
            } else {
                throw new ILegealOperandException("problem forr instruction");
            }
            register1.setValue(result);
            mapForRegister.put(register, register1);
        }
    }


    private void MulDiv(String register, String operand, boolean mul) {
        if (mul) {
            if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register) != null)) {
                if (OperandIsRegisterOrNO(operand) && (mapForRegister.get(operand) != null)) {
                    int result = mapForRegister.get(register).getValue() * mapForRegister.get(operand).getValue();
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else if (OperandIsNumberOrNo(operand)) {
                    int result = mapForRegister.get(register).getValue() * (int) Float.parseFloat(operand);
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else {
                    throw new ILegealOperandException("exception second operand ");
                }
            } else {
                System.out.println(register);
                System.out.println(mapForRegister.get(register));
                throw new ILegealOperandException("exception first operand");
            }
        } else {
            if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register) != null)) {
                if (OperandIsRegisterOrNO(operand) && (mapForRegister.get(operand) != null && mapForRegister.get(operand).getValue() != 0)) {
                    int result = mapForRegister.get(register).getValue() / mapForRegister.get(operand).getValue();
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else if (OperandIsNumberOrNo(operand)) {
                    int operandValue = (int) Float.parseFloat(operand);
                    if (operandValue == 0) {
                        throw new ILegealOperandException("You dont can div for 0");
                    }
                    int result = mapForRegister.get(register).getValue() / operandValue;
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else {
                    throw new ILegealOperandException("exception second operand");
                }
            } else {
                throw new ILegealOperandException("exception first operand");
            }
        }

    }


    private int AddSub(String register, String operand, boolean plyus) {  // stugel elementneri chisht linel@  te che nuller chlinen
        int result = 0;
        if (plyus) {
            if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register).getValue() != null)) {
                if (OperandIsRegisterOrNO(operand) && (mapForRegister.get(operand) != null)) {
                    result = mapForRegister.get(register).getValue() + mapForRegister.get(operand).getValue();
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else if (OperandIsNumberOrNo(operand)) {
                    result = mapForRegister.get(register).getValue() + (int) Float.parseFloat(operand);
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else {
                    throw new ILegealOperandException("exception second operand ");
                }
            } else {
                throw new ILegealOperandException("exception first operand");
            }
        } else {
            if (OperandIsRegisterOrNO(register) && (mapForRegister.get(register) != null)) {
                if (OperandIsRegisterOrNO(operand) && (mapForRegister.get(operand) != null)) {
                    result = mapForRegister.get(register).getValue() - mapForRegister.get(operand).getValue();
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else if (OperandIsNumberOrNo(operand)) {
                    result = mapForRegister.get(register).getValue() - (int) Float.parseFloat(operand);
                    Register register1 = mapForRegister.get(register);
                    register1.setValue(result);
                    mapForRegister.put(register, register1);
                } else {
                    throw new ILegealOperandException("exception second operand ");
                }
            } else {
                throw new ILegealOperandException("exception first operand");
            }
        }
        return result;
    }
    static boolean OperandIsRegisterOrNO(String line) {
        if (line.length() == 2) {
            if (line.equals("r0") || line.equals("r1") || line.equals("r2") || line.equals("r3") ||
                    line.equals("r4") || line.equals("r5") || line.equals("r6") ||
                    line.equals("r7") || line.equals("r8") || line.equals("r9")) {

                return true;
            }
        } else if (line.length() == 3 && line.equals("r10")) {
            return true;
        }
        return false;
    }


    static boolean OperandIsNumberOrNo(String line) {

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

    boolean In3CharInstruction(String line) {
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
}

