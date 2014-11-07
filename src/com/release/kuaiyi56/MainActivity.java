package com.release.kuaiyi56;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TabHost;
import android.widget.Toast;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;
import com.release.util.lbs;

import com.yicheng.kuaiyi56.R;
import com.igexin.sdk.PushManager;

public class MainActivity extends SlidingFragmentActivity {
	private SharedPreferences shareP;
	int people;
    lbs ls = new lbs();
    TabHost mTabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		shareP= this.getSharedPreferences("userInfo", Context.MODE_MULTI_PROCESS);
		Editor editor=shareP.edit();
		super.onCreate(savedInstanceState);
		PushManager.getInstance().initialize(this.getApplicationContext());
		ls.now_lbe(getApplicationContext());
//		BaiduPushUtil.initBaiduPush(getBaseContext());
		initSlidingMenu(savedInstanceState);
	}
	
	private void initSlidingMenu(Bundle savedInstanceState){
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);     
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);	
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
		setContentView(R.layout.content_frame);
		if(shareP.getString("phoneNum", "")!=""&&shareP.getString("sessionId", "")!=""){
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MapviewFragment()).commit();	
		}else{
			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new NoLoginViewActivity()).commit();		
		}
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();
		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_two, new ContactsFragment()).commit();
	}
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			toggle();
//			return true;
//		case R.id.main:
////			Log.i("intent",people);
//			return true;
//		}		
//		return super.onOptionsItemSelected(item);
//	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK){
//                System.out.println("user back down");
        	showContent();
        	exitBy2Click(); 
                
        }                        
        return false;
} 
  private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // 准备退出  
	        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();  
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出  
	            }  
	        }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	  
	    } else {  
	        finish();  
	        System.exit(0);  
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
