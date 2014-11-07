package com.release.kuaiyi56;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.release.app.Application;
import com.release.bean.PublishInfo;
import com.release.db.DBManager;
import com.release.service.AsyncHttpUtils;
import com.yicheng.kuaiyi56.R;

public class EditinfoActivity2 extends Activity {
	private TextView tValid,tFromcity,tTocity,tTrucktype,tTrucklengh,tGoodstype,title;
	private EditText mPrice,mCharge,tWeight;
	private RelativeLayout rValid, rFromcity,rTocity,rWeight,rTrucktype,rTrucklengh,rGoodstype;
	private Button book_confirm;
	private ImageView back_button;
	private int i=1,j=0;
	private DBManager dbm;
	private ProgressDialog pd;
	private Application mApplication;
	private SQLiteDatabase db;
	SampleAdapter adapter;
	private SharedPreferences shareP;
	private PublishInfo pi;
	private GridView gridview1 = null;
	private GridView gridview2 = null;
	private GridView gridview_trucktype=null;
	private GridView gridview_trucklength=null;
	private GridView gridview_goods_type=null;
	private String province=null;
	private String city=null;
	private String district=null;
	private String fromcode=null;
	private String tocode=null;
	private String weight,trucktype,trucklength1,trucklength2,goodstype,totalpay,chargpay;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editinfo);
		adapter = new SampleAdapter(this);
		shareP= this.getSharedPreferences("userInfo", Context.MODE_MULTI_PROCESS);
		initView();
		initGridview1();
		onChangeButton();
	}
	
	private void initView(){
		title=(TextView) findViewById(R.id.titlebarTV);
		title.setText("立即发布");
		gridview1 = (GridView) findViewById(R.id.list_city1);
		gridview2= (GridView) findViewById(R.id.list_city2);
		gridview_trucktype=(GridView) findViewById(R.id.truck_type_choose);
		gridview_trucklength=(GridView) findViewById(R.id.truck_length_choose);
		gridview_goods_type=(GridView) findViewById(R.id.goods_type_choose);
		back_button=(ImageView)findViewById(R.id.titlebarLeftButton);
		tValid=(TextView)findViewById(R.id.book_time_text);
		rValid=(RelativeLayout)findViewById(R.id.book_time);
		tFromcity=(TextView)findViewById(R.id.book_city_text);
		rFromcity=(RelativeLayout)findViewById(R.id.book_city);
		tTocity=(TextView)findViewById(R.id.book_to_text);
		rTocity=(RelativeLayout)findViewById(R.id.book_to);
		tWeight=(EditText)findViewById(R.id.book_Weight_text);
		rWeight=(RelativeLayout)findViewById(R.id.book_Weight);
		tTrucktype=(TextView)findViewById(R.id.book_truck_text);
		rTrucktype=(RelativeLayout)findViewById(R.id.book_truck);
		tTrucklengh=(TextView)findViewById(R.id.book_length_text);
		rTrucklengh=(RelativeLayout)findViewById(R.id.book_length);
		tGoodstype=(TextView)findViewById(R.id.book_type_text);
		rGoodstype=(RelativeLayout)findViewById(R.id.book_type);
		mPrice=(EditText)findViewById(R.id.book_price_text);
		mCharge=(EditText)findViewById(R.id.book_service_charge_text);
		book_confirm=(Button)findViewById(R.id.book_confirm);
		book_confirm.setOnClickListener(ClickListener);
		rValid.setOnClickListener(ClickListener);
		rFromcity.setOnClickListener(ClickListener);
		rTocity.setOnClickListener(ClickListener);
		rTrucktype.setOnClickListener(ClickListener);
		rTrucklengh.setOnClickListener(ClickListener);
		rGoodstype.setOnClickListener(ClickListener);
		mPrice.addTextChangedListener(textWatcher);
		mCharge.addTextChangedListener(textWatcher);
		tWeight.addTextChangedListener(textWatcher);

	}
	private OnClickListener  ClickListener=new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(v.getId()==R.id.book_time){
				if(i==3){
					i-=2;}
				else{
					i+=1;}
				String adddate="有效期"+String.valueOf(i)+"天";
				tValid.setText(adddate);
			}else if(v.getId()==R.id.book_city){
				
				if(gridview1.getVisibility()==View.VISIBLE){
					gridview1.setVisibility(View.GONE);
				}else{
					initGridview1();
					gridview1.setVisibility(View.VISIBLE);
					gridview2.setVisibility(View.GONE);
					gridview_trucktype.setVisibility(View.GONE);
					gridview_trucklength.setVisibility(View.GONE);
					gridview_goods_type.setVisibility(View.GONE);
				}
			}else if(v.getId()==R.id.book_to){
				
				if(gridview2.getVisibility()==View.VISIBLE){
					gridview2.setVisibility(View.GONE);
				}else{
					initGridview1();
					gridview2.setVisibility(View.VISIBLE);
					gridview1.setVisibility(View.GONE);
					gridview_trucktype.setVisibility(View.GONE);
					gridview_trucklength.setVisibility(View.GONE);
					gridview_goods_type.setVisibility(View.GONE);
				}
			}else if(v.getId()==R.id.book_truck){

				if(gridview_trucktype.getVisibility()==View.VISIBLE){
					gridview_trucktype.setVisibility(View.GONE);
				}else{
					initGridViewTruckType();
					gridview_trucktype.setVisibility(View.VISIBLE);
					gridview1.setVisibility(View.GONE);
					gridview2.setVisibility(View.GONE);
					gridview_trucklength.setVisibility(View.GONE);
					gridview_goods_type.setVisibility(View.GONE);
				}
			}else if(v.getId()==R.id.book_length){
				if(gridview_trucklength.getVisibility()==View.VISIBLE){
					gridview_trucklength.setVisibility(View.GONE);
				}else{
					initGridViewTruckLength();
					gridview_trucklength.setVisibility(View.VISIBLE);
					gridview1.setVisibility(View.GONE);
					gridview2.setVisibility(View.GONE);
					gridview_trucktype.setVisibility(View.GONE);
					gridview_goods_type.setVisibility(View.GONE);
				}
			}else if(v.getId()==R.id.book_type){
				if(gridview_goods_type.getVisibility()==View.VISIBLE){
					gridview_goods_type.setVisibility(View.GONE);
				}else{
					initGridViewGoodsType();
					gridview_goods_type.setVisibility(View.VISIBLE);
					gridview1.setVisibility(View.GONE);
					gridview2.setVisibility(View.GONE);
					gridview_trucktype.setVisibility(View.GONE);
					gridview_trucklength.setVisibility(View.GONE);
				}
			}else if(v.getId()==R.id.book_confirm){
				try {
					final Gson gson=new Gson();
					weight=tWeight.getText().toString();
					totalpay=mPrice.getText().toString();
					chargpay=mCharge.getText().toString();
					pi=new PublishInfo();
					pi.setmTime(i);
					pi.setFromCity(fromcode);
					pi.setToCity(tocode);
					pi.setMheavy(weight);
					pi.setTruck_type(trucktype);
					pi.setGoodstype(goodstype);
					pi.setTotal_pay(totalpay);
					pi.setCharge_pay(chargpay);
					pi.setTrucklength1(trucklength1);
					pi.setTrucklength2(trucklength2);
					
					pi.setPhoneNum(shareP.getString("phoneNum", ""));
					pi.setSession_id(shareP.getString("sessionId", ""));

					String urlString =  mApplication.Host+"/save_publishinfo/?content="+java.net.URLEncoder.encode(gson.toJson(pi),   "utf-8");
					AsyncHttpUtils.get(urlString, new AsyncHttpResponseHandler() {	
						public void onStart() {
							// TODO 自动生成的方法存根
							Log.i("urlstring", "bbb");
							pd = ProgressDialog.show(EditinfoActivity2.this, "等待提交", "加载中，请稍后……");  
						}
						public void onSuccess(int statusCode, String content) {
							// TODO 自动生成的方法存根
							Map<String, String> map =gson.fromJson(content, new TypeToken<Map<String, String>>() {}.getType()); 
							if(Integer.parseInt(map.get("code"))==1){
//								Editor editor=shareP.edit();
//								editor.putString("phoneNum", e_mobile_number.getText().toString());
//								editor.putString("sessionId", map.get("sessionid"));
//								editor.commit();
//								finish();
								Intent intent=new Intent();
								intent.setClass(EditinfoActivity2.this, MainActivity.class);
								startActivity(intent); 
							}else{
								Toast.makeText(getApplicationContext(), map.get("msg"),
									     Toast.LENGTH_SHORT).show();
							}
						
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
		}
	};
	
    public void initGridview1(){
		dbm = new DBManager(this);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	adapter.clear();
	 	try {    
	        String sql = "select * from province";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        Log.i("dd",code);
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        adapter.add(new SampleItem(name,code));
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        adapter.add(new SampleItem(name,code));
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	gridview1.setAdapter(adapter);
	 	gridview1.setOnItemClickListener(new GridviewOnClickListener1());
	 	gridview2.setAdapter(adapter);
	 	gridview2.setOnItemClickListener(new GridviewOnClickListener4());
	}
    public void initGridView2(String pcode){
		dbm = new DBManager(this);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
	 	adapter.clear();
	 	try {    
	        String sql = "select * from city where pcode='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        adapter.add(new SampleItem(name,code));
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        adapter.add(new SampleItem(name,code));
	        list.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	gridview1.invalidate();
	 	gridview1.setOnItemClickListener(new GridviewOnClickListener2());
	 	gridview2.invalidate();
	 	gridview2.setOnItemClickListener(new GridviewOnClickListener5());

	}
    public void initGridView3(String pcode){
		dbm = new DBManager(this);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	List<MyListItem> list = new ArrayList<MyListItem>();
	 	adapter.clear();
	 	try {    
	        String sql = "select * from district where pcode='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        adapter.add(new SampleItem(name,code));
		        list.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        adapter.add(new SampleItem(name,code));
	        list.add(myListItem);
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
	 	gridview1.invalidate();
	 	gridview1.setOnItemClickListener(new GridviewOnClickListener3());
	 	gridview2.invalidate();
	 	gridview2.setOnItemClickListener(new GridviewOnClickListener6());
	}
    private void initGridViewTruckType(){
    	adapter.clear();
    	adapter.add(new SampleItem("平板","0"));
    	adapter.add(new SampleItem("高栏","1"));
    	adapter.add(new SampleItem("箱式","2"));
    	adapter.add(new SampleItem("高低板","3"));
    	adapter.add(new SampleItem("保温冷藏","4"));
    	adapter.add(new SampleItem("危险品","5"));
    	gridview_trucktype.setAdapter(adapter);
    	gridview_trucktype.setOnItemClickListener(new GridviewTruckTypeOnClickListener());
    	
    }
    
    private void initGridViewTruckLength(){
    	adapter.clear();
    	adapter.add(new SampleItem("4.2米","0"));
    	adapter.add(new SampleItem("5米","1"));
    	adapter.add(new SampleItem("6.2米","2"));
    	adapter.add(new SampleItem("6.8米","3"));
    	adapter.add(new SampleItem("7.2米","4"));
    	adapter.add(new SampleItem("7.7米","5"));
    	adapter.add(new SampleItem("7.8米","6"));
    	adapter.add(new SampleItem("8.2米","7"));
    	adapter.add(new SampleItem("8.7米","8"));
    	adapter.add(new SampleItem("9.6米","9"));
    	adapter.add(new SampleItem("12.5米","10"));
    	adapter.add(new SampleItem("13米","11"));
    	adapter.add(new SampleItem("16米","12"));
    	adapter.add(new SampleItem("17.5米","13"));
    	gridview_trucklength.setAdapter(adapter);
    	gridview_trucklength.setOnItemClickListener(new GridviewTruckLengthOnClickListener());
    	
    }
    
    private void initGridViewGoodsType(){
    	adapter.clear();
    	adapter.add(new SampleItem("重货","0"));
    	adapter.add(new SampleItem("泡货","1"));
    	adapter.add(new SampleItem("重泡货","2"));
    	gridview_goods_type.setAdapter(adapter);
    	gridview_goods_type.setOnItemClickListener(new GridviewGoodsTypeOnClickListener());
    }

	class GridviewOnClickListener1 implements OnItemClickListener{
		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			province=((SampleItem) adapterView.getItemAtPosition(position)).name;
			String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
			fromcode=pcode;
			tFromcity.setText(province);
			initGridView2(pcode);
			onChangeButton();
		}		
	}
	class GridviewOnClickListener2 implements OnItemClickListener{
		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			city=((SampleItem) adapterView.getItemAtPosition(position)).name;
			String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
			fromcode=pcode;
			tFromcity.setText(province+" "+city);
			initGridView3(pcode);
			onChangeButton();
		}		
	}
	
	class GridviewOnClickListener3 implements OnItemClickListener{
		
		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			district=((SampleItem) adapterView.getItemAtPosition(position)).name;
			String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
			fromcode=pcode;
			tFromcity.setText(province+" "+city+" "+district);
			gridview1.setVisibility(View.GONE);
			onChangeButton();
		}		
	}
class GridviewOnClickListener4 implements OnItemClickListener{
		
		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			province=((SampleItem) adapterView.getItemAtPosition(position)).name;
			String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
			tocode=pcode;
			tTocity.setText(province);
			initGridView2(pcode);
			onChangeButton();
			
		}		
	}
class GridviewOnClickListener5 implements OnItemClickListener{
	
	
	public void onNothingSelected(AdapterView<?> adapterView) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		city=((SampleItem) adapterView.getItemAtPosition(position)).name;
		String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
		tocode=pcode;
		tTocity.setText(province+" "+city);
		initGridView3(pcode);
		onChangeButton();
	}		
}
class GridviewOnClickListener6 implements OnItemClickListener{
	
	public void onNothingSelected(AdapterView<?> adapterView) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		district=((SampleItem) adapterView.getItemAtPosition(position)).name;
		String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
		tocode=pcode;
		tTocity.setText(province+" "+city+" "+district);
		gridview2.setVisibility(View.GONE);
		onChangeButton();
	}		
}

class GridviewTruckTypeOnClickListener implements OnItemClickListener{
	
	public void onNothingSelected(AdapterView<?> adapterView) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		district=((SampleItem) adapterView.getItemAtPosition(position)).name;
		String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
		trucktype=pcode;
		tTrucktype.setText(district);
		gridview_trucktype.setVisibility(View.GONE);
		onChangeButton();
	}		
}

class GridviewTruckLengthOnClickListener implements OnItemClickListener{
	
	public void onNothingSelected(AdapterView<?> adapterView) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(j==2){
			tTrucklengh.setText(null);
			j=0;
			trucklength1=null;
			trucklength2=null;
		}
		district=((SampleItem) adapterView.getItemAtPosition(position)).name;
		String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
		if(tTrucklengh.getText().length()==0){
			j+=1;
			trucklength1=pcode;
			tTrucklengh.setText(district+"->");
		}else{
			j+=1;
			String b=(String) tTrucklengh.getText();
			trucklength2=pcode;
			tTrucklengh.setText(b+district);
			gridview_trucklength.setVisibility(View.GONE);
		}
		onChangeButton();
		
	}		
}

class GridviewGoodsTypeOnClickListener implements OnItemClickListener{
	
	public void onNothingSelected(AdapterView<?> adapterView) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		district=((SampleItem) adapterView.getItemAtPosition(position)).name;
		String pcode =((SampleItem) adapterView.getItemAtPosition(position)).pcode;
		goodstype=pcode;
		tGoodstype.setText(district);
		gridview_goods_type.setVisibility(View.GONE);
		onChangeButton();
	}		
}

private TextWatcher textWatcher=new TextWatcher(){

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		onChangeButton();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		onChangeButton();
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		onChangeButton();
	}
	
};
private void onChangeButton(){
	if(tFromcity.getText()!=""&&tTocity.getText()!=""
			&&tTrucktype.getText()!=""
			&&tTrucklengh.getText()!=""
			&&tGoodstype.getText()!=""
			&&mPrice.getText().toString().length()!=0
			&&tWeight.getText().toString().length()!=0){
		Drawable dr = this.getResources().getDrawable(R.drawable.btn_book_confirm_can);
		book_confirm.setBackgroundDrawable(dr);	
		book_confirm.setClickable(true);
	}else{
		Drawable dr = this.getResources().getDrawable(R.drawable.btn_book_confirm_cannot);
		book_confirm.setBackgroundDrawable(dr);	
		book_confirm.setClickable(false);
		
	}
	
};

    private class SampleItem {
		private String name;
		private String pcode;

		public SampleItem(String name,String pcode) {
			this.name = name; 
			this.pcode = pcode;
		}
	}
    private class MyListItem {
    	private String name;
    	private String pcode;
    	public String getName(){
    		return name;
    	}
    	public String getPcode(){
    		return pcode;
    	}
    	public void setName(String name){
    		this.name=name;
    	}
    	public void setPcode(String pcode){
    		this.pcode=pcode;
    	}
    }

    public class SampleAdapter extends ArrayAdapter<SampleItem> {
		public SampleAdapter(Context context) {
			super(context, 0);
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.textViewName);
			title.setText(getItem(position).name);
			TextView pcode = (TextView) convertView.findViewById(R.id.textViewPcode);
			pcode.setText(getItem(position).pcode);
			return convertView;
		}
	}
}
