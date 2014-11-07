package com.release.kuaiyi56;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.release.app.Application;
import com.release.bean.SessionPeople;
import com.release.service.AsyncHttpUtils;
import com.yicheng.kuaiyi56.R;

public class LoginActivity extends Activity {
	private EditText e_mobile_number, e_regist_verify;
	private TextView t_resend_verify_code,title;
	private ImageView back_button;
	private Button b_regist_next;
	private String session_id;
	private SessionPeople sp;
	private SharedPreferences shareP;
	private TimeCount time;
	private ProgressDialog pd; 
	private Application mApplication;
	private CheckBox regist_v1_agree;
	Gson gson=new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);
		initView();
		onChangeButton();
	}
	private void initView(){
		shareP= this.getSharedPreferences("userInfo", Context.MODE_MULTI_PROCESS);
		title=(TextView) findViewById(R.id.titlebarTV);
		title.setText("一键登录");
		regist_v1_agree=(CheckBox)findViewById(R.id.regist_v1_agree);
		back_button=(ImageView)findViewById(R.id.titlebarLeftButton);
		back_button.setOnClickListener(ClickListener);
		e_mobile_number = (EditText) findViewById(R.id.edittext_mobile_number);
		e_mobile_number.addTextChangedListener(textWatcher);
		e_regist_verify = (EditText) findViewById(R.id.regist_v1_verify);
		e_regist_verify.addTextChangedListener(textWatcher);
		b_regist_next = (Button) findViewById(R.id.regist_v1_next);
		t_resend_verify_code = (TextView) findViewById(R.id.resend_verify_code);
		e_mobile_number.setText(shareP.getString("phoneNum", ""));
		t_resend_verify_code.setOnClickListener(ClickListener);
		b_regist_next.setOnClickListener(ClickListener);
		b_regist_next.setClickable(false);
		time = new TimeCount(60000, 1000);
		regist_v1_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
            	onChangeButton();
            } 
        });
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
	private OnClickListener  ClickListener=new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(v.getId()==R.id.titlebarLeftButton){
				finish();
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
			}else if(v.getId()==R.id.regist_v1_next){
				try {
					String urlString = mApplication.Host+"/reg_two/?usertype=1&phone="
							+ java.net.URLEncoder.encode(e_mobile_number.getText().toString(), "utf-8")
							+"&vcode="+java.net.URLEncoder.encode(e_regist_verify.getText().toString(), "utf-8"); 
					AsyncHttpUtils.get(urlString, new AsyncHttpResponseHandler() {	
						public void onStart() {
							// TODO 自动生成的方法存根

							pd = ProgressDialog.show(LoginActivity.this, "等待提交", "加载中，请稍后……");  
						}
						public void onSuccess(int statusCode, String content) {
							// TODO 自动生成的方法存根
							Map<String, String> map =gson.fromJson(content, new TypeToken<Map<String, String>>() {}.getType()); 
							if(Integer.parseInt(map.get("code"))==1){
								Editor editor=shareP.edit();
								editor.putString("phoneNum", e_mobile_number.getText().toString());
								editor.putString("sessionId", map.get("sessionid"));
								editor.commit();
								finish();
								Intent intent=new Intent();
								intent.setClass(LoginActivity.this, MainActivity.class);
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
			}else if(v.getId()==R.id.resend_verify_code){
				if (isMobileNO(e_mobile_number.getText().toString())) {
					String urlString = "";
					try {
						urlString = mApplication.Host+"/reg_one/?phone="
								+ java.net.URLEncoder.encode(
										e_mobile_number.getText()
												.toString(), "utf-8");
						AsyncHttpUtils.get(urlString, new AsyncHttpResponseHandler() {	
							public void onStart() {
								// TODO 自动生成的方法存根
								pd = ProgressDialog.show(LoginActivity.this, "等待提交", "加载中，请稍后……");  
							}

							public void onSuccess(int statusCode, String content) {
								// TODO 自动生成的方法存根
								
								Map<String, String> map =gson.fromJson(content, new TypeToken<Map<String, String>>() {}.getType()); 
								Toast.makeText(getApplicationContext(),map.get("msg"),
									     Toast.LENGTH_SHORT).show();
								time.start();
								
							}
							public void onFailure(Throwable content) { // 失败，调用
								
							};
							
							public void onFinish() { // 完成后调用，失败，成功，都要掉
								pd.dismiss();
							};
						});
					} catch (UnsupportedEncodingException e) {
						// TODO 
						e.printStackTrace();
					}
					
				}
				else {Toast.makeText(getApplicationContext(), "请输入正确的11位手机号码",
					     Toast.LENGTH_SHORT).show();}
			}
		}
	};

	public static boolean isMobileNO(String mobiles) {
		String telRegex = "[1][3578]\\d{9}";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}
	
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			t_resend_verify_code.setText("重新验证");
			t_resend_verify_code.setClickable(true);
		}
		@Override
		public void onTick(long millisUntilFinished){
			t_resend_verify_code.setClickable(false);
			t_resend_verify_code.setText(millisUntilFinished /1000+"秒后重获");
		}

}
	
    private void onChangeButton(){
    	if(e_mobile_number.getText().toString().length()!=0&&e_regist_verify.getText().toString().length()!=0&&regist_v1_agree.isChecked()){
    		Drawable dr = this.getResources().getDrawable(R.drawable.btn_book_confirm_can);
    		b_regist_next.setBackgroundDrawable(dr);	
    		b_regist_next.setClickable(true);
    	}else{
    		Drawable dr = this.getResources().getDrawable(R.drawable.btn_book_confirm_cannot);
    		b_regist_next.setBackgroundDrawable(dr);	
    		b_regist_next.setClickable(false);
    		
    	}
    	
    };
    
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK){
//                System.out.println("user back down");
        	 Intent myIntent = new Intent();
             myIntent = new Intent(LoginActivity.this, MainActivity.class);
             startActivity(myIntent);
             this.finish();
        }                        
        return false;
} 
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
	        super.onDestroy();
	    }
}
