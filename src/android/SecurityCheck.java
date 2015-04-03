import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
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
		Class<?> clazz = Class.forName("com.android.internal.widget.LockPatternUtils");
        Constructor<?> constructor = clazz.getConstructor(Context.class);
        constructor.setAccessible(true);
        Object utils = constructor.newInstance(context);
        Method method = clazz.getMethod("isSecure");
        return (Boolean)method.invoke(utils);
	}
	
}