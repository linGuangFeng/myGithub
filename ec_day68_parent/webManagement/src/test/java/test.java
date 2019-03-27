import org.junit.Test;

public class test {


    @Test
    public void test(){
        Long a = 127L;
        Long b = 127L;
        Long c = 128L;
        Long d = 128L;
        Long e = -128L;
        Long f = -128L;
        Long g = -129L;
        Long h = -129L;
        System.out.println(a==b);
        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(g==h);
    }
}
