import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public  class Main{
    public static void main(String[] args)
    {
        Cpu cpu = new Cpu("/home/yanus/IdeaProjects/CpuSimulyator/src/Asembler");
        cpu.Run();
    }
}
