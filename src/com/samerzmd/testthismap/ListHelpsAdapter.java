package com.samerzmd.testthismap;

import java.util.ArrayList;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import serverReqeusts.Help;
import serverReqeusts.Result;
import serverReqeusts.ServerTransaction;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ListHelpsAdapter extends BaseAdapter {
	ArrayList<Help> helps;
	Help tempHelp;
	private LayoutInflater inflater = null;

	public ListHelpsAdapter(Context context, ArrayList<Help> helps) {
		this.helps = helps;
		inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		return helps.size();
	}

	@Override
	public Object getItem(int Helpid) {
		// TODO Auto-generated method stub
		return helps.get(Helpid);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public static class ViewHolder {

		public TextView title;
		public TextView details;
		public TextView whoAnswerd;
		public Button approveButton;
		public Button deleteThisHelp;

	}

	@Override
	public View getView(final int position, View row, ViewGroup arg2) {
		View vi = row;
		ViewHolder holder;
		if (row == null) {

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.submittedhelprow, arg2, false);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.submittedHelpTitle);
			holder.details = (TextView) vi
					.findViewById(R.id.submittedHelpDetails);
			holder.whoAnswerd = (TextView) vi
					.findViewById(R.id.submittedHelpWhoAns);
			holder.approveButton = (Button) vi.findViewById(R.id.approveButton);
			holder.deleteThisHelp = (Button) vi
					.findViewById(R.id.deleteThisHelp);

			/************ Set holder with LayoutInflater ************/
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		if (helps.size() <= 0) {
			holder.title.setText("No Data");

		} else {
			/***** Get each Model object from Arraylist ********/
			tempHelp = null;
			tempHelp = (Help) helps.get(position);

			/************ Set Model values in Holder elements ***********/

			holder.title.setText(tempHelp.title);
			holder.details.setText(tempHelp.details);
			if (tempHelp.whoAnswersId != 0) {
				holder.whoAnswerd
						.setText(String.valueOf(tempHelp.whoAnswersId));
				holder.approveButton.setClickable(true);

				holder.approveButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						ServerTransaction s = new ServerTransaction();
						s.acceptHelp(inflater.getContext(),
								helps.get(position).id,
								new AjaxCallback<Result>() {
									@Override
									public void callback(String url,
											Result object, AjaxStatus status) {
										Toast.makeText(
												inflater.getContext(),
												"you have approved user:"
														+ helps.get(position).whoAnswersId,
												Toast.LENGTH_SHORT).show();
									}
								});
					}
				});
				
				holder.deleteThisHelp.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						ServerTransaction s = new ServerTransaction();
						s.deleteHelp(inflater.getContext(),
								helps.get(position).id,
								new AjaxCallback<Result>() {
							
								});
						
					}
				});

			} else {
				holder.whoAnswerd.setText("no answer yet");
			}

			/******** Set Item Click Listner for LayoutInflater for each row *******/

		}
		return vi;
	}

}
