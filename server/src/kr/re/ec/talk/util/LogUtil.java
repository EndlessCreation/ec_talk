package kr.re.ec.talk.util;

import kr.re.ec.talk.common.Constants;

/**
 * Class LogUtil.
 * print to log using Class and Method name.
 * @author Kim Taehee (slhyvaa@nate.com)
 * @since 130812
 * @modified 140514 (for java)
 */
public class LogUtil {
	private static final String TAG = "User Logged";

	private LogUtil() {} //cannot create instance

	/* vervose */
	public static void v(String log)
	{
		if(Constants.DEBUG) {
			System.out.println(TAG + "(V):" + new Exception().getStackTrace()[1].getClassName() + "::" 
					+ new Exception().getStackTrace()[1].getMethodName() + "():" + log); //get class and method name
		}
	}

	/* debug */
	public static void d(String log)
	{
		if(Constants.DEBUG) {
			System.out.println(TAG + "(D):" + new Exception().getStackTrace()[1].getClassName() + "::" 
					+ new Exception().getStackTrace()[1].getMethodName() + "():" + log); //get class and method name
		}
	}

	/* info */
	public static void i(String log)
	{
		if(Constants.DEBUG) {
			System.out.println(TAG + "(I):" + new Exception().getStackTrace()[1].getClassName() + "::" 
					+ new Exception().getStackTrace()[1].getMethodName() + "():" + log); //get class and method name
		}
	}

	/* warn */
	public static void w(String log)
	{
		System.out.println(TAG + "(W):" + new Exception().getStackTrace()[1].getClassName() + "::" 
				+ new Exception().getStackTrace()[1].getMethodName() + "():" + log); //get class and method name
	}

	/* error */
	public static void e(String log)
	{
		System.out.println(TAG + "(E):" + new Exception().getStackTrace()[1].getClassName() + "::" 
				+ new Exception().getStackTrace()[1].getMethodName() + "():" + log); //get class and method name
		//		System.out.println(TAG + "(E):" + new Exception().getStackTrace()[2].getClassName() + "::" 
		//				+ new Exception().getStackTrace()[2].getMethodName() + "():"); 
		//		System.out.println(TAG + "(E):" + new Exception().getStackTrace()[3].getClassName() + "::" 
		//				+ new Exception().getStackTrace()[3].getMethodName() + "():"); 
	}
}
