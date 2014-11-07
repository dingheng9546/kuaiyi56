package com.release.kuaiyi56;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.testcontact.SideBar;
import com.yicheng.kuaiyi56.R;

public class ContactsFragment extends Fragment {
	private ExpandableListView expListView_01 = null;
	private ImageButton btn_return = null;
	private TextView tv_top_title = null;

	// 小组图片
	private int[] groupPhotos = new int[] { R.drawable.wei, R.drawable.shu,
			R.drawable.wu };
	// 小组名称
	private String[] groupNames = new String[] { "魏", "蜀", "吴" };
	// 小组成员
	private String[][] groupPeoples = new String[][] {
			{ "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
			{ "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
			{ "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" } };
	// 小组成员图片
	private int[][] groupPeoplePhotos = new int[][] {
			{ R.drawable.xiahoudun, R.drawable.zhenji, R.drawable.xuchu,
					R.drawable.guojia, R.drawable.simayi, R.drawable.yangxiu },
			{ R.drawable.machao, R.drawable.zhangfei, R.drawable.liubei,
					R.drawable.zhugeliang, R.drawable.huangyueying,
					R.drawable.zhaoyun },
			{ R.drawable.lvmeng, R.drawable.luxun, R.drawable.sunquan,
					R.drawable.zhouyu, R.drawable.sunshangxiang } };
	
	
	Context mContext = null;
	private ListView lvContact;
	private SideBar indexBar;
	private WindowManager mWindowManager;
	private TextView mDialogText;
	private static String[] nicks = { "asd", "�����" };
	static ArrayList<String> nicks_list = new ArrayList<String>();

	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };

	/** ��ϵ����ʾ���� **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** �绰���� **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** ͷ��ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** ��ϵ�˵�ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	/** ��ϵ������ **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** ��ϵ��ͷ�� **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	/** ��ϵ��ͷ�� **/
	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();

	ListView mListView = null;
	
	private List<Map<String, String>> contacts = null;

//	private SpeechSynthesizer speechSynthesizer;

	public List<Map<String, String>> cleanContacts(
			List<Map<String, String>> contacts) {

		List listtemp = new ArrayList();

		for (int i = 0; i < contacts.size(); i++) {

			Map<String, String> value = contacts.get(i);
			String number = value.get("number");
			if (listtemp.contains(number)) {

			} else {
				contacts.remove(i);
			}

		}

		return contacts;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_contact, null);
		expListView_01 = (ExpandableListView) v.findViewById(R.id.list);
		final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
            //设置组视图的图片
            int[] logos = new int[] { R.drawable.wei, R.drawable.shu,R.drawable.wu};
            //设置组视图的显示文字
            private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
            //子视图显示文字
            private String[][] generals = new String[][] {
                    { "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
                    { "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
                    { "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" }

            };
            //子视图图片
            public int[][] generallogos = new int[][] {
                    { R.drawable.xiahoudun, R.drawable.zhenji,
                            R.drawable.xuchu, R.drawable.guojia,
                            R.drawable.simayi, R.drawable.yangxiu },
                    { R.drawable.machao, R.drawable.zhangfei,
                            R.drawable.liubei, R.drawable.zhugeliang,
                            R.drawable.huangyueying, R.drawable.zhaoyun },
                    { R.drawable.lvmeng, R.drawable.luxun, R.drawable.sunquan,
                            R.drawable.zhouyu, R.drawable.sunshangxiang } };
            
            //自己定义一个获得文字信息的方法
            TextView getTextView() {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, 64);
                TextView textView = new TextView(
                		getActivity());
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setPadding(36, 0, 0, 0);
                textView.setTextSize(20);
                textView.setTextColor(Color.BLACK);
                return textView;
            }

            
            //重写ExpandableListAdapter中的各个方法
            @Override
            public int getGroupCount() {
                // TODO Auto-generated method stub
                return generalsTypes.length;
            }

            @Override
            public Object getGroup(int groupPosition) {
                // TODO Auto-generated method stub
                return generalsTypes[groupPosition];
            }

            @Override
            public long getGroupId(int groupPosition) {
                // TODO Auto-generated method stub
                return groupPosition;
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                // TODO Auto-generated method stub
                return generals[groupPosition].length;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return generals[groupPosition][childPosition];
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                // TODO Auto-generated method stub
                return childPosition;
            }

            @Override
            public boolean hasStableIds() {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                    View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                LinearLayout ll = new LinearLayout(
                		getActivity());
                ll.setOrientation(0);
                ImageView logo = new ImageView(getActivity());
                logo.setImageResource(logos[groupPosition]);
                logo.setPadding(50, 0, 0, 0);
                ll.addView(logo);
                TextView textView = getTextView();
                textView.setTextColor(Color.BLACK);
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);

                return ll;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition,
                    boolean isLastChild, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                LinearLayout ll = new LinearLayout(
                		getActivity());
                ll.setOrientation(0);
                ImageView generallogo = new ImageView(
                		getActivity());
                generallogo
                        .setImageResource(generallogos[groupPosition][childPosition]);
                ll.addView(generallogo);
                TextView textView = getTextView();
                textView.setText(getChild(groupPosition, childPosition)
                        .toString());
                ll.addView(textView);
                return ll;
            }

            @Override
            public boolean isChildSelectable(int groupPosition,
                    int childPosition) {
                // TODO Auto-generated method stub
                return true;
            }

        };

        ExpandableListView expandableListView = (ExpandableListView) v.findViewById(R.id.list);
        expandableListView.setAdapter(adapter);
		return v;
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
