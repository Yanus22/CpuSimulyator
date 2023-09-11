import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public  class CU {
    private Map<Integer, String> mapForLine;
    private Map<String, Integer> mapForLabel;
    private Map<String, Register> mapForRegister;
    private Map<Integer, String> mapForLineClean;
   private ALU Alu;
   private CompilerSyntax compiler;


    public CU ( Map<Integer, String> mapForLine ,  Map<String, Integer> mapForLabel,  Map<String, Register> mapForRegister,  Map<Integer, String> mapForLineClean,  String path ,CompilerSyntax compiler)
    {
        this.mapForLabel = mapForLabel;
        this.mapForLineClean = mapForLineClean;
        this.mapForLine = mapForLine;
        this.mapForRegister = mapForRegister;
        this.compiler = compiler;
        Alu = new ALU(mapForLine, mapForLabel, mapForRegister, mapForLineClean);
    }

    public void Instruction(String line, int index) {// amen angam  inq@ irana kanchum hamapatsxan toxov yev toxi indexov
        if ( line == null) {
            return;
        }
        int indexNext = index + 1;//verncuma hajord toxi index@
        String lineNext = mapForLineClean.get( indexNext );//maqur toxeri mapic vercnuma hajord  tox@
        String instruction3Char = line.substring( 0, 3);
        String intruction4Char = line.substring( 0, 4);
        String instructionPrint = line.substring( 0, 5);
        if (CompilerSyntax.In3CharInstruction(instruction3Char) && !(instruction3Char.equals("inc") || instruction3Char.equals("dec")))// iranc logikan urish ddzeva incrrementi u decrementi
        {
            String operand1 = line.substring ( 3, line.indexOf(','));//instruckcyayi arajin operand
            String operand2 = line.substring(line.indexOf (',') + 1);

            switch (instruction3Char) {
                case "mov":
                    Alu.Mov( operand1, operand2);
                    break;
                case "add":
                    Alu.Add( operand1, operand2);
                    break;
                case "sub":
                    Alu.Sub( operand1, operand2);
                    break;
                case "mul":
                    Alu.Mul( operand1, operand2);
                    break;
                case "div":
                    Alu.Div( operand1, operand2);
                    break;
                case "and":
                    Alu.And( operand1, operand2);
                    break;
                case "xor":
                    Alu.Xor( operand1, operand2);
                    break;
                case "orr":
                    Alu.Orr( operand1, operand2);
                    break;
            }
        }
        else if ( instruction3Char.equals("inc") )
        {
            Alu.Increment( line.substring( 3));
        }
        else if ( instruction3Char.equals("dec"))
        {
            Alu.Decrement( line.substring(3));
        }
        else if (instructionPrint.equals("print"))
        {
            Alu.Print(line.substring(line.indexOf('t') + 1));
        }
        else if (CompilerSyntax.In4CharInstruction( intruction4Char) ){ // es blockum stugum a  te vor jumpn a uzum , u ira naxord tox@ vercnuma tena cmpear ka te che   heto cmpeari ardyunq@ hamematum a paymanov jumpin tenuma yete ham@nkel en poxuma hajord  katarvox tox@ u     index@
            String prveLine = mapForLineClean.get( index-1 );
            if( prveLine.startsWith("cmp") )
            {
                String  register =  prveLine.substring( 3,prveLine.indexOf(','));
                String operand = prveLine.substring( prveLine.indexOf(',')+1);
                int result = Alu.Compear( register, operand);
                String  label = line.substring(4);//erkarutyun@ 4 a paymanov jmperi
                if( line.startsWith("jmpe") && ( result == 0) )
                {
                      indexNext =   mapForLabel.get( label);
                      lineNext = mapForLineClean.get( indexNext);
                }
                else if (line.startsWith("jmpl") &&  ( result < 0) )
                {
                    indexNext = mapForLabel.get( label);
                    lineNext = mapForLineClean.get( indexNext);
                }
                else if (line.startsWith( "jmpg" ) && ( result > 0) )
                {
                    indexNext = mapForLabel.get( label);
                    lineNext = mapForLineClean.get( indexNext);
                }
            }
        }
        else
        {
          throw new ILegealOperandException("i dont know excpetio");
        }
        if( indexNext == mapForLineClean.size() ) return;
        Instruction(lineNext,indexNext);//nuyn funkcyan kanchum a hamapatasxan toxov vodex ktorner@ bajanvuma  u alui hamapatasxan  instrukcyan kanchum
    }
    private  void InstructionForJumps ( String jumpIf,String linForNow,int indexForNow) // shtkumneri kariq uni es funkcyan yev chi ogtagorcvel
    {
        int indexNext = indexForNow +1;
        String lineNext  = mapForLineClean.get( indexForNow+1 );
        String label = linForNow.substring(linForNow.indexOf('e') + 1);
            if ( mapForLabel.get( label) != null )
            {
                String prevLine = mapForLineClean.get(indexForNow - 1);//naxord tox@ cmperi hamar
                if ( prevLine.startsWith("cmp") )
                {
                    String register = prevLine.substring(prevLine.indexOf('p') + 1, prevLine.indexOf(','));
                    String operand = prevLine.substring(prevLine.indexOf(',') + 1);
                    int resultCmp = Alu.Compear(register, operand);
                    if( jumpIf.equals("jmpe") && resultCmp == 0 )
                    {
                        indexNext = mapForLabel.get(label);
                        System.out.println(indexNext);
                        lineNext = mapForLineClean.get(indexNext);
                        System.out.println(lineNext);
                        Instruction(lineNext, indexNext);
                        return;
                    }
                    else if(jumpIf.equals("jmpl") && resultCmp < 0)
                    {
                        indexNext = mapForLabel.get(label);
                        lineNext = mapForLineClean.get(indexNext);
                        Instruction(lineNext, indexNext);

                    }
                    else if(jumpIf.equals("jmpg") && resultCmp > 0)
                    {
                        indexNext = mapForLabel.get(label);
                        lineNext = mapForLineClean.get(indexNext);
                        Instruction(lineNext, indexNext);
                    }
                }
            }
            else
            {
                throw new ILegealOperandException("label not found");
            }
    }
}




