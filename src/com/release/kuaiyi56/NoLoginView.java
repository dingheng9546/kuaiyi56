package com.release.kuaiyi56;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.yicheng.kuaiyi56.R;

public class NoLoginView extends Fragment {
	private MapController mMapController = null;
	BMapManager mBMapMan = null;
	static MapView mMapView = null;

	static mapview newInstance() {
		mapview f = new mapview();
		return f;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nologin, container, false);
		mMapView = (MapView) v.findViewById(R.id.bmapsView2);
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);
		mMapView.refresh();
		TextView button2 = (TextView) v.findViewById(R.id.back2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				getActivity().finish();
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			}
		});
		LinearLayout ly = (LinearLayout) v
				.findViewById(R.id.publishReserveBtn2);
		ly.setFocusable(true);
		ly.setFocusableInTouchMode(true);
		ly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivity(intent);
			}
		});
		return v;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getActivity().getApplication());
		mBMapMan.init(null);
	}

	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		mMapView.destroy();
		super.onDestroy();
	}
}
