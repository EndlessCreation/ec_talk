package kr.re.ec.talk;

/**
 * @ClassName LogUtil
 * @Description printing log with Class and Method name.
 * @Author Taehee Kim (slhyvaa@nate.com)
 * @CreatedDate 130812
 * @ModifiedDate 9/19/2016
 */
public class LogUtil {

    /* vervose */
    public static void v(String tag, String log)
    {
        if(Constants.IS_DEBUG) {
            android.util.Log.v(tag, new Exception().getStackTrace()[1].getClassName() + "::"
                    + new Exception().getStackTrace()[1].getMethodName() + "():" + log); //한 수준 이전의 클래스명과 메소드명 호출
        }
    }

    /* debug */
    public static void d(String tag, String log)
    {
        if(Constants.IS_DEBUG) {
            android.util.Log.d(tag, new Exception().getStackTrace()[1].getClassName() + "::"
                    + new Exception().getStackTrace()[1].getMethodName() + "():" + log); //한 수준 이전의 클래스명과 메소드명 호출
        }
    }

    /* info */
    public static void i(String tag, String log)
    {
        if(Constants.IS_DEBUG) {
            android.util.Log.i(tag, new Exception().getStackTrace()[1].getClassName() + "::"
                    + new Exception().getStackTrace()[1].getMethodName() + "():" + log); //한 수준 이전의 클래스명과 메소드명 호출
        }
    }

    /* warn */
    public static void w(String tag, String log)
    {
        android.util.Log.w(tag, new Exception().getStackTrace()[1].getClassName() + "::"
                + new Exception().getStackTrace()[1].getMethodName() + "():" + log); //한 수준 이전의 클래스명과 메소드명 호출
    }

    /* error */
    public static void e(String tag, String log)
    {
        android.util.Log.e(tag, new Exception().getStackTrace()[1].getClassName() + "::"
                + new Exception().getStackTrace()[1].getMethodName() + "():" + log); //한 수준 이전의 클래스명과 메소드명 호출
    }
}
