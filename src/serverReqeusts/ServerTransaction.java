package serverReqeusts;

import android.content.Context;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.Transformer;
import com.google.gson.Gson;

public class ServerTransaction {
	private String Base_Link = "http://mapz.eb2a.com/mapz";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <Y> void connect(Context context, String url, Class p,
			AjaxCallback<Y> callback) {
		AQuery query = new AQuery(context);

		AjaxCallback.setTransformer(new Transformer() {
			@Override
			public <T> T transform(String url, Class<T> arg1, String arg2,
					byte[] data, AjaxStatus arg4) {
				String s = new String(data);
				Gson g = new Gson();
				T a = g.fromJson(s, arg1);
				return a;
			}
		});
		query.ajax(url, p, callback);
	}

	private String makeUrl(String postFix) {
		if (Base_Link.endsWith("/")) {
			return Base_Link + postFix;
		} else {
			return Base_Link + "/" + postFix;
		}
	}

	public void getProfile(Context context, String email, String name,
			AjaxCallback<UserProfile> callback) {
		String url = this.makeUrl("profile.php?email=" + email.toString()
				+ "&name=" + name);
		this.<UserProfile> connect(context, url, UserProfile.class, callback);
	}

	public void getProfile(Context context, int id,
			AjaxCallback<UserProfile> callback) {
		String url = this.makeUrl("profile2.php?id=" + id);
		this.<UserProfile> connect(context, url, UserProfile.class, callback);
	}

	public void submitHelp(Context context, String title, String details,
			int whoReqId, double longitude, double latitude,
			AjaxCallback<Result> callback) {
		String url = this.makeUrl("new_help.php?title=" + title + "&details="
				+ details + "&whoRequestsId=" + whoReqId + "&longitude="
				+ longitude + "&latitude=" + latitude);
		this.<Result> connect(context, url, Result.class, callback);
	}

	public void getAllHelps(Context context, AjaxCallback<Help[]> callback) {
		String url = this.makeUrl("get_helps.php");
		this.<Help[]> connect(context, url, Help[].class, callback);
	}

	public void answerForHelp(Context context, Integer whoAnswersId,
			Integer helpId, AjaxCallback<Result> callback) {
		String url = this.makeUrl("answer_help.php?id=" + whoAnswersId
				+ "&helpId=" + helpId);
		this.<Result> connect(context, url, Result.class, callback);
	}

	public void acceptHelp(Context context, int helpId,
			AjaxCallback<Result> callback) {
		String url = this.makeUrl("accept_help.php?helpId=" + helpId);
		this.<Result> connect(context, url, Result.class, callback);
	}

	public void deleteHelp(Context context, int helpId,
			AjaxCallback<Result> callback) {
		String url = this.makeUrl("delete_help.php?helpId=" + helpId);
		this.<Result> connect(context, url, Result.class, callback);
	}

	public void getSpecificHelp(Context context, int helpId,
			AjaxCallback<Help> callback) {
		String url = this.makeUrl("get_specific_help.php?helpId=" + helpId);
		this.<Help> connect(context, url, Help.class, callback);
	}

	public void setBaseLink(String link) {
		Base_Link = link;
	}
}