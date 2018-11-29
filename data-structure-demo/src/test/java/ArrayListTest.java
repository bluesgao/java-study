import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<String>(16);

        System.out.println("add before strings.size: "+strings.size());

        for (int i=0; i<16; i++){
            strings.add("haha"+i);
        }
        System.out.println("add after strings.size: "+strings.size());
        strings.add(3, "haha999");


        System.out.println(strings.toString());
    }
}
