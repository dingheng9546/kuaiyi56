package com.release.kuaiyi56;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.release.util.LbeShow;
import com.release.util.LbeShowCars;
import com.yicheng.kuaiyi56.R;

public class mapview extends Fragment {

	private MapController mMapController = null;
	private TextView reserve, reserve2;
	private LinearLayout ly;
	private TimeCount time;
	private ImageButton onkeyPublishBtn;
	BMapManager mBMapMan = null;
	SharedPreferences shareP = null;
	static MapView mMapView = null;

	static mapview newInstance() {
		mapview f = new mapview();
		return f;
	}

	LbeShowCars lbe = new LbeShowCars();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		shareP = getActivity().getSharedPreferences("Location",
				Context.MODE_MULTI_PROCESS);
		float Latitude = shareP.getFloat("Latitude", (float) 0.0);
		float Longitude = shareP.getFloat("Longitude", (float) 0.0);
		Log.i("Latitude", String.valueOf(Latitude));
		Log.i("Longitude", String.valueOf(Longitude));
		View v = inflater.inflate(R.layout.activity_main1, container, false);
		mMapView = (MapView) v.findViewById(R.id.bmapsView);
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(13);
		mMapView.getController().enableClick(true);
		mMapView.setBuiltInZoomControls(true);
	
		GeoPoint point = new GeoPoint((int) (Latitude * 1E6),
				(int) (Longitude * 1E6));
		mMapController.setCenter(point);
		mMapView.refresh();
		reserve = (TextView) v.findViewById(R.id.reserve_now);
		reserve2 = (TextView) v.findViewById(R.id.reserve_now2);
		onkeyPublishBtn = (ImageButton) v.findViewById(R.id.onkeyPublishBtn);
		LbeShow lbe = new LbeShow();
		lbe.now_lbe(getActivity(), mMapView);
		onkeyPublishBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存�?
				Intent intent = new Intent();
//				intent.setClass(getActivity(), BookListHistory.class);
				startActivity(intent);

			}

		});
		time = new TimeCount(60000, 1000);
		Button button = (Button) v.findViewById(R.id.more);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存�?
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});

		ImageButton imgbutton = (ImageButton) v.findViewById(R.id.request);
		imgbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存�?
				// lbe.now_lbe(getActivity(), mMapView);
				// lbe.getCarsXY(getActivity(), mMapView);
				LbeShow lbe = new LbeShow();
				lbe.now_lbe(getActivity(), mMapView);

			}
		});

		Button button2 = (Button) v.findViewById(R.id.back);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存�?
				((MainActivity) getActivity()).getSlidingMenu()
						.showSecondaryMenu();

			}
		});
		ly = (LinearLayout) v.findViewById(R.id.publishReserveBtn);
		ly.setFocusable(true);
		ly.setFocusableInTouchMode(true);
		ly.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存�?
				Intent intent = new Intent();
//				intent.setClass(getActivity(), editinfo.class);
				startActivityForResult(intent, 1);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			Bundle b = data.getExtras();
			String str = b.getString("city");
			Log.i("ggg", b.getString("city"));
			reserve2.setText("正在发给" + str + "�?");
			time.start();
			// reserve2.setText("");
		}
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为�?�时�?,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触�?
			reserve.setText("立即发布");
			ly.setClickable(true);
			reserve2.setText("");
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			ly.setClickable(false);
			reserve.setText("等待" + millisUntilFinished / 1000 + "�?");
		}

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
		// if (mLocClient != null)
		// mLocClient.stop();
		mMapView.destroy();
		super.onDestroy();
	}

}
