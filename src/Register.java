import java.net.Inet4Address;

public  class Register {
    String name ;
    Integer value;
    public Register(String name,Integer value){
        this.name = name;
        this.value = value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public Integer getValue (){
        return value;
    }
    public String getName(){
        return name;
    }
}
