package com.release.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;



public class Application extends FrontiaApplication {
	private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
	public static String Host="http://www.kuaiyi56.com:4567";
//	public static String Host="http://10.5.16.200:4567";
	private static final int CITY_LIST_SCUESS = 0;
	private static final String FORMAT = "^[a-z,A-Z].*$";
	private static Application mApplication;

	private Map<String, Integer> mWeatherIcon;// 婢垛晜鐨甸崶鐐�?
	private Map<String, Integer> mWidgetWeatherIcon;// 閹绘帊娆㈡径鈺傜毜閸ョ偓鐖�
	
	private List<String> mSections;
	// 閺嶈宓佹＃鏍х摟濮ｅ秴鐡ㄩ弨鐐殶閹癸拷
	
	private List<Integer> mPositions;
	// 妫ｆ牕鐡уВ宥咁嚠鎼存梻娈戞担宥囩枂
	private Map<String, Integer> mIndexer;
	private boolean isCityListComplite;

	private LocationClient mLocationClient = null;

	public static int mNetWorkState;


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
}
