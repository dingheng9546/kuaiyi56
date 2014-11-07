package com.release.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.tencent.stat.StatService;

public class lbs extends Activity {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	public List now_lbe(final Context c) {
		final List list = new ArrayList();
		mLocationClient = new LocationClient(c); // ÉùÃ÷LocationClientÀà
		mLocationClient.registerLocationListener(myListener); // ×¢²á¼àÌýº¯Êý

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);// ÉèÖÃ¶¨Î»Ä£Ê½
		option.setCoorType("bd09ll");// ·µ»ØµÄ¶¨Î»½á¹ûÊÇ°Ù¶È¾­Î³¶È£¬Ä¬ÈÏÖµgcj02
		// option.setOpenGps(true);
		option.setScanSpan(500000);// ÉèÖÃ·¢Æð¶¨Î»ÇëÇóµÄ¼ä¸ôÊ±¼äÎª5000ms
		option.setIsNeedAddress(true);// ·µ»ØµÄ¶¨Î»½á¹û°üº¬µØÖ·ÐÅÏ¢
		option.setNeedDeviceDirect(true);// ·µ»ØµÄ¶¨Î»½á¹û°üº¬ÊÖ»ú»úÍ·µÄ·½Ïò
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.requestLocation();
		} else
			Log.i("loc", "locClient is null or not started");

		mLocationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location == null) {
					return;
				}
				int errorcode;
				errorcode = location.getLocType();
				switch (errorcode) {
				case 61:
					Toast.makeText(c, "GPS定位成功", Toast.LENGTH_SHORT).show();

					break;
				case 62:
					Toast.makeText(c, "定位失败", Toast.LENGTH_SHORT).show();
					break;
				case 63:
					Toast.makeText(c, "网络异常", Toast.LENGTH_SHORT).show();
					break;
				case 65:
					// 定位缓存
					Toast.makeText(c, "定位成功", Toast.LENGTH_SHORT).show();
					break;
				case 161:
					Toast.makeText(c, "网络定位成功", Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(c, "服务端定位失败", Toast.LENGTH_SHORT).show();
					break;
				}
				String str = location.getLatitude() + ","
						+ location.getLongitude();
				// 将经纬度存入本地
				SharedPreferences sharedPreferences = c.getSharedPreferences(
						"Location", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putFloat("Latitude", (float) location.getLatitude());
				editor.putFloat("Longitude", (float) location.getLongitude());
				// 提交修改
				editor.commit();
				list.add((int) (location.getLatitude() * 1E6));
				list.add((int) (location.getLongitude() * 1E6));

				Log.i("cccccc", list.toString());

			};
		});
		return list;
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
	    	 if (mLocationClient != null)
	    		 mLocationClient.stop();
	        super.onDestroy();
	     
	    }

}
