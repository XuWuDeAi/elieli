package entity;

import org.json.JSONObject;

public class JsonRespBean<T> {
	// public String reason = "ok";
	// public T data;
	//
	// public String getReason() {
	// return reason;
	// }
	//
	// public JsonRespBean(String reason, T data) {
	// super();
	// this.reason = reason;
	// this.data = data;
	// }
	//
	public static String success(Object data) {
		JSONObject req = new JSONObject();
		req.put("reason", "ok");
		req.put("data", data);
		return req.toString();

	}

	public static String erro(Exception e) {
		JSONObject req = new JSONObject();
		req.put("reason", e.getMessage());
		return req.toString();
	}

	// public void setReason(String reason) {
	// this.reason = reason;
	// }
	//
	// public T getData() {
	// return data;
	// }
	//
	// public void setData(T data) {
	// this.data = data;
	// }

}
