package com.release.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.release.app.Application;
import com.release.bean.Car;
import com.release.service.GsonTools;
import com.release.service.HttpUtils;
import com.yicheng.kuaiyi56.R;


public class LbeShowCars extends Activity {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	private MyOverlay mOverlay = null;
	private PopupOverlay pop = null;
	private ArrayList<OverlayItem> mItems = null;
	private TextView popupText = null;
	private View viewCache = null;
	private View popupInfo = null;
	private View popupLeft = null;
	private View popupRight = null;
	private Button button = null;
	private MapView.LayoutParams layoutParam = null;
	private OverlayItem mCurItem = null;
	private Application mApplication;

	/**
	 * overlay 位置坐标
	 */
	double mLon1 = 116.400244;
	double mLat1 = 39.963175;
	double mLon2 = 116.369199;
	double mLat2 = 39.942821;
	double mLon3 = 116.425541;
	double mLat3 = 39.939723;
	double mLon4 = 116.401394;
	double mLat4 = 39.906965;

	MapView mMapView1 = null;
	Context c1 = null;

	List<Map<String, Object>> cars = null;

	GsonTools gson = new GsonTools();
	private Car car;

	public class MyOverlay extends ItemizedOverlay {

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}

		@Override
		public boolean onTap(int index) {
			// OverlayItem item = getItem(index);
			// mCurItem = item;
			// if (index == 3) {
			// button.setText("这是一个系统控件");
			// GeoPoint pt = new GeoPoint((int) (mLat4 * 1E6),
			// (int) (mLon4 * 1E6));
			// // 弹出自定义View
			// pop.showPopup(button, pt, 32);
			// } else {
			// popupText.setText(getItem(index).getTitle());
			// Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupLeft),
			// BMapUtil.getBitmapFromView(popupInfo),
			// BMapUtil.getBitmapFromView(popupRight) };
			// pop.showPopup(bitMaps, item.getPoint(), 32);
			// }
			return true;
		}

		@Override
		public boolean onTap(GeoPoint pt, MapView mMapView) {
			if (pop != null) {
				pop.hidePop();
				mMapView.removeView(button);
			}
			return false;
		}

	}

	public void clearOverlay(View view, MapView mMapView) {
		mOverlay.removeAll();
		if (pop != null) {
			pop.hidePop();
		}
		mMapView.removeView(button);
		mMapView.refresh();
	}

	public void getCarsXY(Context c, MapView mMapView) {
		mMapView1 = mMapView;
		c1 = c;

		new Thread() {
			public void run() {
				String path = "";
				path = mApplication.Host+"/get_cars_xy/";

				String jsonString = HttpUtils.getJsonContent(path);
				cars = GsonTools.changeGsonToListMaps(jsonString);

				if (cars == null) {
					// 请求失败，或者网络没开
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("lontitude", 9999.0);
					map.put("latitude", 9999.0);
					map.put("level", 0.0);
					list.add(map);
					jsonString = GsonTools.createGsonString(list);
					Log.i("jsonString", jsonString);
					cars = GsonTools.changeGsonToListMaps(jsonString);
					Log.i("carssssss", "aa");
				}

				// for (Map<String, Object> car : cars) {
				// Log.i("carssssss", car.get("x").toString());
				// }
				handler.sendEmptyMessage(0);

			}
		}.start();

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {// handler接收到消息后就会执行此方法
			initOverlay(c1, mMapView1);
		}
	};

	public void initOverlay(Context c, final MapView mMapView) {
		/**
		 * 创建自定义overlay
		 */

		// getCarsXY();
		// mMapView.getOverlays().clear();

		mOverlay = new MyOverlay(c.getResources().getDrawable(
				R.drawable.nav_turn_via_1), mMapView);

		for (Map<String, Object> car : cars) {
			Double latitude = Double
					.parseDouble(car.get("latitude").toString());
			Double lontitude = Double.parseDouble(car.get("lontitude")
					.toString());
			GeoPoint p = new GeoPoint((int) (latitude * 1E6),
					(int) (lontitude * 1E6));
			OverlayItem item = new OverlayItem(p, "司机", "");
			item.setMarker(c.getResources()
					.getDrawable(R.drawable.icon_gcoding));
			mOverlay.addItem(item);
		}

		// /**
		// * 准备overlay 数据
		// */
		// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		// OverlayItem item1 = new OverlayItem(p1, "覆盖物1", "");
		// /**
		// * 设置overlay图标，如不设置，则使用创建ItemizedOverlay时的默认图标.
		// */
		// item1.setMarker(c.getResources().getDrawable(R.drawable.icon_gcoding));
		//
		// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		// OverlayItem item2 = new OverlayItem(p2, "覆盖物2", "");
		// item2.setMarker(c.getResources().getDrawable(R.drawable.icon_gcoding));
		//
		// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
		// OverlayItem item3 = new OverlayItem(p3, "覆盖物3", "");
		// item3.setMarker(c.getResources().getDrawable(R.drawable.icon_gcoding));
		//
		// GeoPoint p4 = new GeoPoint((int) (mLat4 * 1E6), (int) (mLon4 * 1E6));
		// OverlayItem item4 = new OverlayItem(p4, "覆盖物4", "");
		// item4.setMarker(c.getResources().getDrawable(R.drawable.icon_gcoding));
		// /**
		// * 将item 添加到overlay中 注意： 同一个itme只能add一次
		// */
		// mOverlay.addItem(item1);
		// mOverlay.addItem(item2);
		// mOverlay.addItem(item3);
		// mOverlay.addItem(item4);
		/**
		 * 保存所有item，以便overlay在reset后重新添加
		 */
		mItems = new ArrayList<OverlayItem>();
		mItems.addAll(mOverlay.getAllItem());

		/**
		 * 将overlay 添加至MapView中
		 */
		mMapView.getOverlays().add(mOverlay);

		/**
		 * 刷新地图
		 */

		mMapView.refresh();

	}

}
