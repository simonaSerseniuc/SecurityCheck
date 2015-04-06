package android.com.phonegap.plugins.securityCheck;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecurityCheck extends CordovaPlugin{

	public SecurityCheck(){
	}
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
		boolean result = isDeviceSecured();
		return result;
	}
	
	private boolean isDeviceSecured()
	{
		String LOCKSCREEN_UTILS = "com.android.internal.widget.LockPatternUtils";
		try
		{ 
			Class<?> lockUtilsClass = Class.forName(LOCKSCREEN_UTILS);
			// "this" is a Context, in my case an Activity
			Object lockUtils = lockUtilsClass.getConstructor(Context.class).newInstance(this);

			Method method = lockUtilsClass.getMethod("getActivePasswordQuality");

			int lockProtectionLevel = (Integer)method.invoke(lockUtils); // Thank you esme_louise for the cast hint

			if(lockProtectionLevel >= DevicePolicyManager.PASSWORD_QUALITY_NUMERIC)
			{
				return true;
			}
		}
		catch (Exception e)
		{
			Log.e("reflectInternalUtils", "ex:"+e);
		}

		return false;
	}
}