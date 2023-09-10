
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
    public Map<String, Register> mapForRegister = new HashMap<>();
    public Map<Integer, String> mapForLineClean = new HashMap<>();

    public CU Cu;
    public CompilerSyntax Compiler;


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
        mapForRegister.put("r10", new Register("r10", null));
        Cu = new CU(mapForLine, mapForLabel, mapForRegister, mapForLineClean, path);
        Compiler = new CompilerSyntax(mapForLine, mapForLabel, mapForRegister, mapForLineClean, path);
        if(Compiler.SyntaxAnalys()){
            System.out.println("code can run");
        }
    }

    public void Run() {
         int index =  0;
         String lineStart = mapForLineClean.get(0);
         if(index  == mapForLineClean.size()-2){
                    return;
    }
         else {
             Cu.Instruction(lineStart,index);
         }
    }

}






