package com.release.kuaiyi56;
import com.baidu.mapapi.BMapManager;
import com.yicheng.kuaiyi56.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MenuFragment extends Fragment {
	private RelativeLayout item_wallet,item_score_mall,item_transaction,item_about;
	private LinearLayout item_propose;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.publish_homepage_menu, container,false);
		
		return v;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
        item_wallet=(RelativeLayout)getActivity().findViewById(R.id.item_wallet);
		item_score_mall=(RelativeLayout)getActivity().findViewById(R.id.item_score_mall);
		item_transaction=(RelativeLayout)getActivity().findViewById(R.id.item_transaction);
		item_propose=(LinearLayout)getActivity().findViewById(R.id.item_propose);
		item_about=(RelativeLayout)getActivity().findViewById(R.id.item_about);
		item_wallet.setOnClickListener(confirmclickListener);
		item_score_mall.setOnClickListener(confirmclickListener);
		item_transaction.setOnClickListener(confirmclickListener);
		item_propose.setOnClickListener(confirmclickListener);
		item_about.setOnClickListener(confirmclickListener);
    }  
	
	private OnClickListener confirmclickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(v.getId()==R.id.item_wallet){
				Intent intent = new Intent();
//				intent.setClass(getActivity(), WalletActivity.class);
				startActivity(intent);
				
			}else if(v.getId()==R.id.item_score_mall){
				Intent intent = new Intent();
//				intent.setClass(getActivity(), ForTransactionActivity.class);
				startActivity(intent);
				
			}else if(v.getId()==R.id.item_transaction){
				Intent intent = new Intent();
//				intent.setClass(getActivity(), ForTransactionActivity.class);
				startActivity(intent);
			}else if(v.getId()==R.id.item_propose){
				Intent intent = new Intent();
//				intent.setClass(getActivity(), FeedBack.class);
				startActivity(intent);
				
			}else if(v.getId()==R.id.item_about){
				Intent intent = new Intent();
//				intent.setClass(getActivity(), AboutusActivity.class);
				startActivity(intent);
				
			}
			
		}
	};
	

}
