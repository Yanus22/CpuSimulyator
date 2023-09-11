
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.Map;

class Cpu
{
    private Map<Integer, String> mapForLine = new HashMap<>();
    private Map<String, Integer> mapForLabel = new HashMap<>();

    private Map<String, Register> mapForRegister = new HashMap<>();

    private Map<Integer, String> mapForLineClean = new HashMap<>();


    private CU Cu;
    private CompilerSyntax Compiler;


    Cpu(String path) {
        mapForRegister.put("r0", new Register("r0", null));
        mapForRegister.put("r1", new Register("r1", null));
        mapForRegister.put("r2", new Register("r2", null));
        mapForRegister.put("r3", new Register("r3", null));
        mapForRegister.put("r4", new Register("r4", null));
        mapForRegister.put("r5", new Register("r5", null));
        mapForRegister.put("r6", new Register("r6", null));
        mapForRegister.put("r7", new Register("r7", null));
        mapForRegister.put("r8", new Register("r8", null));
        mapForRegister.put("r9", new Register("r9", null));
        mapForRegister.put("r10", new Register("r10", null));//cpun sarqeluc sax nullera lcum vor heto chisht hamematutyuner ani
        Compiler = new CompilerSyntax(mapForLine, mapForLabel, mapForRegister, mapForLineClean, path); //nuyn dzev compiler@ iran petqa  maper@
        Cu = new CU(mapForLine, mapForLabel, mapForRegister, mapForLineClean, path,Compiler);//  maperi referencner@ talisenk vor  CUnel ogtagorci pathnel vor CUn compile ani
        if ( Compiler.SyntaxAnalys () ) // stugel Syntax@ chishta te che  SyntaxAnalys@ exceptiona qcum sxal syntaxi jamanak
        {
            System.out.println("code can run");
        } else {
            throw new ILegealOperandException("Compile time eroor");
        }
    }

    public void Run()
    {
        int index =  0;//skselu index@ toxi
         String lineStart = mapForLineClean.get(0); //skselu tox@
        if(index == mapForLineClean.size()-1)
        {
            return;
        }
        else
        {
             Cu.Instruction(lineStart,index);
    }
    }
}






