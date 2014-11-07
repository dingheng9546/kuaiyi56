package com.release.kuaiyi56;

//import com.way.weather.SampleListFragment.SampleItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.release.app.Application;
import com.release.bean.PublishInfo;
import com.release.service.AsyncHttpUtils;
import com.release.service.HttpUtils;
import com.tencent.stat.StatService;
import com.yicheng.kuaiyi56.R;


public class BookListHistoryActivity extends Activity {
	private List<PublishInfo> mPublishInfos;
//	private BookHistoryDB mBookHistoryDB;
//	Button cancel,editor;
	private SharedPreferences shareP;
	private ProgressDialog pd;  
    private ListView listview; 
    Map<String,String> map=new HashMap<String,String>();
    ArrayList<Map<String,String>> booklist=new ArrayList<Map<String,String>>();
    private Application mApplication;
    private Button update;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		Log.i("cgm5", "cgm");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_history);
		shareP= this.getSharedPreferences("userInfo", Context.MODE_MULTI_PROCESS);
		update=(Button)findViewById(R.id.titlebarRightButton);
		update.setOnClickListener(clickListener);
		initDB();
	}
	
	private void initDB(){
		try {
			
			String urlString = mApplication.Host+"/get_myall_publishinfo/?phone="
					+ java.net.URLEncoder.encode(shareP.getString("phoneNum", "").toString(), "utf-8")
					+"&sessionid="+java.net.URLEncoder.encode(shareP.getString("sessionId", "").toString(), "utf-8"); 
			AsyncHttpUtils.get(urlString, new AsyncHttpResponseHandler() {	
				public void onStart() {
					// TODO 自动生成的方法存根

					pd = ProgressDialog.show(BookListHistoryActivity.this, "等待提交", "加载中，请稍后……");  
				}
				public void onSuccess(int statusCode, String content) {
					// TODO 自动生成的方法存根
					Log.i("content", content);
					Toast.makeText(getApplicationContext(), content,
						     Toast.LENGTH_SHORT).show();
//					Map<String, String> map =gson.fromJson(content, new TypeToken<Map<String, String>>() {}.getType()); 
//					if(Integer.parseInt(map.get("code"))==1){
//						
//					}else{
//						Toast.makeText(getApplicationContext(), map.get("msg"),
//							     Toast.LENGTH_SHORT).show();
//					}
				
				}
				public void onFailure(Throwable content) { // 失败，调用
					Toast.makeText(getApplicationContext(), "请求失败，请检查网络后尝试！",
						     Toast.LENGTH_SHORT).show();
				};
				
				public void onFinish() { // 完成后调用，失败，成功，都要掉
					pd.dismiss();
				};
			});
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}
	

	protected ArrayList<Map<String,String>> converCursorToList(Cursor cursor){
		ArrayList<Map<String,String>> result=new ArrayList<Map<String,String>>();
		while(cursor.moveToNext()){
			Map<String,String> map=new HashMap<String,String>();
			map.put("mTime", cursor.getString(0));
			map.put("fromCity", cursor.getString(1));
			map.put("toCity", cursor.getString(2));
			map.put("mheavay", cursor.getString(3));
			map.put("status", cursor.getString(4));
			map.put("sid", cursor.getString(5));
			map.put("truck_type", cursor.getString(6));
			map.put("total_pay", cursor.getString(7));
			map.put("charge_pay", cursor.getString(8));
			map.put("phone_num", cursor.getString(9));
			result.add(map);
		}
		return result;
	}
	
	
	private class SampleItem {
		public String tag,secline,status,phone_num;
		public SampleItem(String tag,String secline,String status,String phone_num) {
			this.secline=secline;
			this.tag = tag; 
			this.status = status;
			this.phone_num = phone_num;

		}
	}
	public class SampleAdapter extends ArrayAdapter<SampleItem> {
		public SampleAdapter(Context context) {
			super(context, 0);
		}
		 @Override
	    public long getItemId(int position) {
	        return position;
	    }
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, null);
			}
			Log.i("kkk", getItem(position).phone_num);
			TextView headline=(TextView)convertView.findViewById(R.id.book_city_text);
			headline.setText(getItem(position).tag+"    "+getItem(position).status);
			TextView secline=(TextView)convertView.findViewById(R.id.book_from_text);
			secline.setText(getItem(position).secline);
			Button cancel=(Button)convertView.findViewById(R.id.one);
			Button editor=(Button)convertView.findViewById(R.id.two);
			Button confirm=(Button)convertView.findViewById(R.id.three);
			Button call=(Button)convertView.findViewById(R.id.four);
			cancel.setOnClickListener(new lvButtonListener(position));
			editor.setOnClickListener(new lvButtonListener(position));
			call.setOnClickListener(new lvButtonListener(position));
			confirm.setOnClickListener(new lvButtonListener(position));
			Log.i("status", getItem(position).status);
			if(Integer.parseInt(getItem(position).status)==0){
				cancel.setVisibility(View.VISIBLE);
				confirm.setVisibility(View.GONE);
				editor.setVisibility(View.GONE);
				call.setVisibility(View.GONE);
			}else if(Integer.parseInt(getItem(position).status)==2||Integer.parseInt(getItem(position).status)==4){
				editor.setVisibility(View.VISIBLE);
				call.setVisibility(View.GONE);
				cancel.setVisibility(View.GONE);
				confirm.setVisibility(View.GONE);
			}else if(Integer.parseInt(getItem(position).status)==3){
				confirm.setVisibility(View.VISIBLE);
				call.setVisibility(View.VISIBLE);
				editor.setVisibility(View.GONE);
				cancel.setVisibility(View.GONE);
			}else if(Integer.parseInt(getItem(position).status)==1){
				cancel.setVisibility(View.VISIBLE);
				call.setVisibility(View.VISIBLE);
				confirm.setVisibility(View.GONE);
				editor.setVisibility(View.GONE);
			}
			
			return convertView;
		}
	}
	
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Log.i("aa", "bb");
			
		}
	};
	
	class lvButtonListener implements OnClickListener {
        private int position;
        lvButtonListener(int pos) {
            position = pos;
        }
        @Override
        public void onClick(View v) {
            int vid=v.getId();
            if(vid==R.id.two){
            	 Map<String,String> c=booklist.get(position);
            	 Intent intent=new Intent(BookListHistoryActivity.this,EditinfoActivity2.class);
            	 finish();
		            for(String key:c.keySet()){
		            	intent.putExtra(key, c.get(key));
		            }
		            startActivity(intent);
            }
            else if(vid==R.id.one){
            
            }else if(vid==R.id.three){
            	
            }else if(vid==R.id.four){
            	Map<String,String> ph=booklist.get(position);
            	if(ph.get("phone_num")!=null){
            		String phone=ph.get("phone_num");
            	}
       	
            }
           
        }
    }
	

		 @Override
		    public void onPause() {
		        super.onPause();
		        StatService.onPause(this);
		    }

		    @Override
		    public void onResume() {
		        super.onResume();
		        StatService.onResume(this);


		    }
		    @Override
		    public void onDestroy() {
		        super.onDestroy();
		    }
}
