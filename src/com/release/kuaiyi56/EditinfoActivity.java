package com.release.kuaiyi56;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.release.app.Application;
//import com.way.bean.City;
//import com.way.bean.PublishInfo;
//import com.way.db.DBHelper;
//import com.way.popwindow.SelectNumPopWindow;
//import com.way.popwindow.SelectPicPopupWindow;
//import com.way.popwindow.SelectTypePopWindow;
//import com.way.service.HttpUtils;
import com.yicheng.kuaiyi56.R;


public class EditinfoActivity extends Activity {
	private TextView mTimeDisplay, mtextView,mcity,tcity,mtextWeight,mtextTruck;
	private EditText mprice,mcharge;
	private RelativeLayout mPickTime,mPickStartCity,mPickToCity,mBookWeight,mBookTruck;
//	private SelectPicPopupWindow menuWindow;
//	private SelectNumPopWindow numWindow;
//	private SelectTypePopWindow typeWindow;
	private Button button1,book_confirm;
	private SharedPreferences shareP;
	private ProgressDialog pd;  
	private SQLiteDatabase db;
	private Application mApplication;
	private int mHour;
	private String mMinute;
	private int mYear;
	private int mMonth;
	private String mDay;
//	private City mNewIntentCity;
//	private PublishInfo pi;
	static final int TIME_DIALOG_ID = 0;
	static final int DAY_DIALOG_ID=1;
	static final int NUM_DIALOG_ID=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_editinfo);
		
//		Intent intent=this.getIntent();
//		
//		String un=intent.getStringExtra("sid");
//		
//		String fromCity=intent.getStringExtra("fromCity");
//		
//		String toCity=intent.getStringExtra("toCity");
//		
//		String mheavay=intent.getStringExtra("mheavay");
//		
//		String truck_type=intent.getStringExtra("truck_type");
//		
//		String total_pay=intent.getStringExtra("total_pay");
//		
//		String charge_pay=intent.getStringExtra("charge_pay");
//	
//		shareP= this.getSharedPreferences("userInfo", Context.MODE_MULTI_PROCESS);
//		
//		mprice=(EditText)findViewById(R.id.book_price_text);
//		
//		if(total_pay!=null&&total_pay!=""){
//			mprice.setText(total_pay);
//		}
//
//		mtextView = (TextView) findViewById(R.id.book_time_text);
//		
//		mcharge=(EditText)findViewById(R.id.book_service_charge_text);
//		if(charge_pay!=null){
//			mcharge.setText(charge_pay);
//		}
//		
//		book_confirm=(Button)findViewById(R.id.book_confirm);
//
//		mtextView.setText("����");
//		
//		mcity=(TextView)findViewById(R.id.book_city_text);
//		
//		if(fromCity!=null){
//			mcity.setText(fromCity);
//		}
//		
//		mtextTruck=(TextView)findViewById(R.id.book_truck_text);
//		
//		if(truck_type==null){
//			mtextTruck.setText("��ͨ");
//		}else{
//			mtextTruck.setText(truck_type);
//		}
//
//		
//		tcity=(TextView) findViewById(R.id.book_to_text);
//		
//		if(toCity!=null){
//			tcity.setText(toCity);
//		}
//		
//		mtextWeight=(TextView)findViewById(R.id.book_Weight_text);
//		
//		if(mheavay!=null){
//			mtextWeight.setText(mheavay);
//		}
//
//		mPickTime = (RelativeLayout) findViewById(R.id.book_time);
//		
//		mPickStartCity =(RelativeLayout) findViewById(R.id.book_city);
//		
//		mPickToCity =(RelativeLayout) findViewById(R.id.book_to);
//		
//		mBookWeight =(RelativeLayout) findViewById(R.id.book_Weight);
//		
//		mBookTruck=(RelativeLayout) findViewById(R.id.book_truck);
//
//		mPickTime.setOnClickListener(clickListener);
//		
//		mBookWeight.setOnClickListener(numClickListener);
//		
//		mPickStartCity.setOnClickListener(cityclickListener);
//		
//		mPickToCity.setOnClickListener(citytoclickListener);
//		
//		mBookTruck.setOnClickListener(truckclickListener);
//		
//		book_confirm.setOnClickListener(confirmclickListener);
		
//		book_confirm.setClickable(false);
//		
//		mprice.addTextChangedListener(new TextWatcher(){
//	
//		
//			@Override
//			public void afterTextChanged(Editable s) {
//				onChangeButton();
//				
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				onChangeButton();
//				
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				onChangeButton();
//			}       
//		});
//		onChangeButton();

	}
//	private OnClickListener confirmclickListener = new OnClickListener(){
//
//		@Override
//		public void onClick(View v) {
//			// TODO �Զ����ɵķ������
//			pd = ProgressDialog.show(EditinfoActivity.this, "�ȴ��ύ", "�����У����Ժ󡭡�");  
//			new Thread() {
//				public void run() {
//					pi=new PublishInfo();
//					
//					int charge_pay=0;
//					if(mcharge.getText().toString().trim().length()!=0){
//						charge_pay=Integer.parseInt(mcharge.getText().toString());
//					}
//					pi.setCharge_pay(charge_pay);//�����
//					String fromCity;
//					fromCity=(String) mcity.getText();
//					pi.setFromCity(fromCity);//��������
//					String[] sourceStrArray=((String) mtextWeight.getText()).split("��");
//					pi.setMheavy(Integer.parseInt(sourceStrArray[0]));//����
//					
//					String mTime;
//					mTime=mtextView.getText().toString();
//					pi.setmTime(mTime);//����ʱ��
//					
//					String toCity;
//					toCity=tcity.getText().toString();
//					pi.setToCity(toCity);//Ŀ�����
//					
//					int total_pay=0;
//					if(mprice.getText().toString().trim().length()!=0){
//						total_pay=Integer.parseInt(mprice.getText().toString());
//					}
//					
//					pi.setTotal_pay(total_pay);//�ܼ�
//					
//					String truck_type;
//					truck_type=mtextTruck.getText().toString();
//					pi.setTruck_type(truck_type);//����
//					
//					pi.setPhoneNum(shareP.getString("phoneNum", ""));
//					pi.setSession_id(shareP.getString("sessionId", ""));
//
//					
//					
//					Gson gson=new Gson();
//					
//					String path="";
//					Log.i("host",  mApplication.Host);
//					try {
//			
//						path = mApplication.Host+"/save_publishinfo/?content="+java.net.URLEncoder.encode(gson.toJson(pi),   "utf-8");
//						Log.i("path", path);
////						path="http://127.0.0.1:4567/save_publishinfo/?username=&sessionid=&fromcity=&tocity=&weight=&cartype=&starttime=&totalprice=&chargeprice="
//					} catch (UnsupportedEncodingException e) {
//						// TODO �Զ����ɵ� catch ��
//						e.printStackTrace();
//					}
//					String jsonString = HttpUtils.getJsonContent(path);
//					Log.i("json", jsonString);
//
//					Map<String, String> map =gson.fromJson(jsonString, new TypeToken<Map<String, String>>() {}.getType()); 
//					ContentValues values = new ContentValues();
//					values.put("fromCity", pi.getFromCity());
//					values.put("toCity",pi.getToCity());
//					values.put("mheavay", pi.getMheavy());
//					values.put("truck_type", pi.getTruck_type());
//					values.put("total_pay", pi.getTotal_pay());
//					values.put("charge_pay", pi.getCharge_pay());
//					values.put("status", 0);
//					
//					values.put("mTime", map.get("createtime"));
//					values.put("sid", map.get("id"));
//					Log.i("id",  map.get("id"));
//					DBHelper dbHelper = new DBHelper(EditinfoActivity.this,"userInfo1");
//			        db = dbHelper.getWritableDatabase();
//			        db.insert("booklist", null, values);
//					Log.i("aaa", map.get("msg"));
//					Message msg = new Message();  
//					msg.arg1=1;
//					msg.obj=map.get("msg");
//					handler.sendMessage(msg);
//					if(Integer.parseInt(map.get("code"))==1){
//						startActivity("1",Integer.parseInt(map.get("waittime")));
//					}
//				}
//			}.start();
//		}
//		
//		
//	};
//	
//	private OnClickListener truckclickListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			typeWindow = new SelectTypePopWindow(EditinfoActivity.this,itemsOnClick);
//			//��ʾ����
//			typeWindow.showAtLocation(EditinfoActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
////			showDialog(TIME_DIALOG_ID);
//		}
//	};
//	  private OnClickListener  itemsOnClick = new OnClickListener(){
//
//			public void onClick(View v) {
//				typeWindow.dismiss();
//				button1=(Button)v.findViewById(v.getId());
//				mtextTruck.setText(button1.getText());
//				onChangeButton();
//			}
//	    	
//	    };
//	
//
//	private OnClickListener clickListener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			menuWindow = new SelectPicPopupWindow(EditinfoActivity.this,itemsChange,tclickListener);
//			//��ʾ����
//			menuWindow.showAtLocation(EditinfoActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
////			showDialog(TIME_DIALOG_ID);
//		}
//	};
//	private OnClickListener tclickListener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			mtextView.setText(new StringBuilder().append(mMinute)
//					.append(mHour)
//					.append("��")
//					);
//			menuWindow.dismiss();
//			onChangeButton();
//		}
//	};
//	
//	private OnClickListener numClickListener=new OnClickListener(){
//
//		@Override
//		public void onClick(View v) {
//			numWindow = new SelectNumPopWindow(EditinfoActivity.this,numItemsChange);
//			numWindow.showAtLocation(EditinfoActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//			
//		}
//		
//		
//	};
//	private OnTimeChangedListener  numItemsChange = new OnTimeChangedListener(){
//
//		@Override
//		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//			// TODO �Զ����ɵķ������
//			mtextWeight.setText(new StringBuilder().append(hourOfDay)
//					.append(Integer.toString(minute))
//					.append("��"));
//			onChangeButton();
//			
//		}
//		
//		
//	};
//	private void startActivity(String city,int wait_time) {
//		Intent i = new Intent();
//		i.putExtra("city", city);
//		i.putExtra("wai_time", wait_time);
//		setResult(RESULT_OK, i);
//		finish();
//	}
//	
//
//    private OnTimeChangedListener  itemsChange = new OnTimeChangedListener(){
//
//		@Override
//		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//
//			mHour=hourOfDay;
//	
//			Log.i("aaa",Integer.toString(hourOfDay));
//			switch (minute) {
//			case 0:
//				mMinute = "����";
//				break;
//			case 1:
//				mMinute = "����";
//				break;
//			case 2:
//				mMinute ="����";
//				break;
//			}
//
//			
//		}
//    };
//	
//	private OnClickListener cityclickListener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			Intent intent=new Intent();
//			intent.setClass(EditinfoActivity.this, SelectCtiyActivity.class);
//			startActivityForResult(intent,0);
//			onChangeButton();
//		}
//	};
//	
//	private OnClickListener citytoclickListener = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			Intent intent=new Intent();
//			intent.setClass(EditinfoActivity.this, SelectCtiyActivity.class);
//			startActivityForResult(intent,1);
//			
//		}
//	};
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (requestCode == 0 && resultCode == RESULT_OK) {
//			mNewIntentCity = (City) data.getSerializableExtra("city");
//			mcity.setText(mNewIntentCity.getCity());
//		}else if(requestCode == 1 && resultCode == RESULT_OK){
//			mNewIntentCity = (City) data.getSerializableExtra("city");
//			tcity.setText(mNewIntentCity.getCity());	
//		}
//		onChangeButton();
//	}
//
//	
//
//	private String pad(int c)
//
//	{
//
//		// TODO Auto-generated method stub
//
//		if (c >= 10)
//
//		{
//
//			return String.valueOf(c);
//
//		} else
//
//		{
//
//			return "0" + String.valueOf(c);
//
//		}
//
//	}
//
//	@Override
//	protected Dialog onCreateDialog(int id) {
//		Calendar calendar = Calendar.getInstance();  
//
//		switch (id) {
//
//		case TIME_DIALOG_ID:
//			return new DatePickerDialog(this, 
//                    dateListener, 
//                    calendar.get(Calendar.YEAR), 
//                    calendar.get(Calendar.MONTH), 
//                    calendar.get(Calendar.DAY_OF_MONTH));
//
//		}
//		
//		return null;
//
//	}
//	private DatePickerDialog.OnDateSetListener dateListener =  
//    new DatePickerDialog.OnDateSetListener() { 
//        @Override 
//        public void onDateSet(DatePicker datePicker,  
//                int year, int month, int dayOfMonth) { 
//        	mYear=year;
//        	mMonth=month+1;
//        } 
//    }; 
//    
//    private void onChangeButton(){
//    	if(mtextView.getText()!=""&&mcity.getText()!=""&&tcity.getText()!=""&&mtextWeight.getText()!=""&&mtextTruck.getText()!=""&&mprice.getText().toString().length()!=0){
//    		Drawable dr = this.getResources().getDrawable(R.drawable.btn_book_confirm_can);
//    		book_confirm.setBackgroundDrawable(dr);	
//    		book_confirm.setClickable(true);
//    	}else{
//    		Drawable dr = this.getResources().getDrawable(R.drawable.btn_book_confirm_cannot);
//    		book_confirm.setBackgroundDrawable(dr);	
//    		book_confirm.setClickable(false);
//    		
//    	}
//    	
//    };
//    Handler handler = new Handler() {  
//        @Override  
//        public void handleMessage(Message msg) {// handler���յ���Ϣ��ͻ�ִ�д˷���  
//        	pd.dismiss();
//        	if(msg.arg1==1){
//        	Toast.makeText(EditinfoActivity.this, "���ͳɹ�", Toast.LENGTH_SHORT).show();
//            // �ر�ProgressDialog  
//        	}
//        	else{
//        		Toast.makeText(EditinfoActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
//        	}
//        }  
//
//        
//    };  
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//    	 
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                 && event.getRepeatCount() == 0) {
//            //do something...
//        	
//        	Intent sendIntent = new Intent(EditinfoActivity.this, MainActivity.class);
//        	startActivity(sendIntent);
////        	finish();
//            return true;
//         }
//         return super.onKeyDown(keyCode, event);
//     }
    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onDestroy() {
    
        //editinfo.this.finish();
        super.onDestroy();
        if(db!=null && db.isOpen()){
        	db.close();
        }
        
    }


	


}
