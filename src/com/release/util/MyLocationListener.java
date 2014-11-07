package com.release.util;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener {

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation) {
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
		}
		Log.i("", sb.toString());

		// shareLocation = getSharedPreferences("Location",
		// Context.MODE_MULTI_PROCESS);
		// Editor editor = shareLocation.edit();
		// editor.putInt("Latitude", (int) (location.getLatitude() * 1E6));
		// editor.putInt("Longitude", (int) (location.getLongitude() * 1E6));
		//
		// editor.commit();
		
		// MapController mMapController = mMapView.getController();
		// // µÃµ½mMapViewµÄ¿ØÖÆÈ¨,¿ÉÒÔÓÃËü¿ØÖÆºÍÇý¶¯Æ½ÒÆºÍËõ·Å
		// GeoPoint point = new GeoPoint((int) (location.getLatitude() *
		// 1E6),
		// (int) (location.getLongitude() * 1E6));
		//
		// // ÓÃ¸ø¶¨µÄ¾­Î³¶È¹¹ÔìÒ»¸öGeoPoint£¬µ¥Î»ÊÇÎ¢¶È (¶È * 1E6)
		// mMapController.setCenter(point);// ÉèÖÃµØÍ¼ÖÐÐÄµã
		// mMapController.setZoom(20);// ÉèÖÃµØÍ¼zoom¼¶±ð
		//
		// MyLocationOverlay myLocationOverlay = new MyLocationOverlay(
		// mMapView);
		// LocationData locData = new LocationData();
		// //
		// ÊÖ¶¯½«Î»ÖÃÔ´ÖÃÎªÌì°²ÃÅ£¬ÔÚÊµ¼ÊÓ¦ÓÃÖÐ£¬ÇëÊ¹ÓÃ°Ù¶È¶¨Î»SDK»ñÈ¡Î»ÖÃÐÅÏ¢£¬ÒªÔÚSDKÖÐÏÔÊ¾Ò»¸öÎ»ÖÃ£¬ÐèÒªÊ¹ÓÃ°Ù¶È¾­Î³¶È×ø±ê£¨bd09ll£©
		// locData.latitude = location.getLatitude();
		// locData.longitude = location.getLongitude();
		// locData.direction = 2.0f;
		// myLocationOverlay.setData(locData);
		// mMapView.getOverlays().clear();
		// mMapView.getOverlays().add(myLocationOverlay);
		// mMapView.refresh();
		// mMapView.getController().animateTo(
		// new GeoPoint((int) (locData.latitude * 1e6),
		// (int) (locData.longitude * 1e6)));
	}

}
