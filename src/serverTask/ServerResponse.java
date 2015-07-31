package serverTask;
import org.json.JSONObject;

public interface ServerResponse {

	public void httpResponse(JSONObject jsonObject,String tag , int responceCode);
}
