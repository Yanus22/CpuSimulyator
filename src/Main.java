import java.io.BufferedReader;
import java.io.FileReader;

public  class Main{
    public static void main(String[] args)    {
        Cpu cpu = new Cpu("/home/yanus/IdeaProjects/CpuSimulyator/src/Asembler");
        Register register1 = new Register("r1",7);
        Register register2 = new Register("r2",6);
        cpu.mapForRegister.put("r1",register1);
        cpu.mapForRegister.put("r2",register2);
        cpu.Cu.Alu.Xor("r1","14");
        System.out.println(cpu.mapForRegister.get("r1").getValue());



    }
}
/* label , arithmetic,mov jump=!= >=,<=  ,fetch  ,increment,decrement,print,,r1-r10 register,sub,div ,and or*/