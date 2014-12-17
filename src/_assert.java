/**
 * Created by Nirmal on 18/12/2014.
 */
public class _assert {

    public static void isTrue(boolean a){
        if (!a) System.out.println(
                  "_assert:\n"
                + "expected true, found false"
        );
        else System.out.println("_assert: passed all");
    }
    public static void isFalse(boolean a){
        if (a) System.out.println(
                "_assert:\n"
                        + "expected false, found true"
        );
        else System.out.println("_assert: passed all");
    }
//    public static void isEqual(){
//        if (a) System.out.println(
//                "_assert:\n"
//                        + "expected false, found true"
//        );
//        else System.out.println("_assert: passed all");
//    }
}
