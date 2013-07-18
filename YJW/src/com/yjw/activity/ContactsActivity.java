package com.yjw.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.yjw.R;
import com.yjw.bean.UserBean;
import com.yjw.ctrl.YJWControler;
import com.yjw.thread.delayed.ShowMessageThread;
import com.yjw.util.G;
import com.yjw.widget.ContactListView;

public class ContactsActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {

	private ContactListView listview;
	private SimpleAdapter adapter;
	private Button back_button;
	private Button forward_button;
	private int group;
	static private List<Boolean> selected = new ArrayList<Boolean>();
	// List<UserBean> objects;
	int type;

	private Set<String> cellphones = new HashSet<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		YJWControler.getInstance().setActivity(this);
		setContentView(R.layout.contacts);
		group = getIntent().getIntExtra("group", 0);
		init();
		Log.d("ContactsEnter",
				selected.toString() + " " + listview.getChildCount());

	}

	@Override
	protected void onResume() {
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}

	@Override
	protected void init() {
		super.init();

		adapter = new SimpleAdapter(this, getUser(), R.layout.contact_item,
				new String[] { "name", "cellphone", "selected" }, new int[] {
						R.id.tv_name, R.id.tv_phone, R.id.checkbox });
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private List<Map<String, Object>> getUser() {
		synchronized (G.cellphones) {
			G.cellphones.clear();
		}
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		ContentResolver content = getContentResolver();
		Cursor cur = content.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		int i = 0;
		while (cur.moveToNext()) {
			int idcol = cur.getColumnIndex(ContactsContract.Contacts._ID);
			String id = cur.getString(idcol);
			Cursor num = content.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ id, null, null);

			while (num.moveToNext()) {
				int numcol = num
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
				String phone = num.getString(numcol);
				phone = phone.replace(" ", "");
				phone = phone.replace("-", "");
				phone = phone.replace("+86", "");
				synchronized (G.cellphones) {
					G.cellphones.add(phone);
				}
				UserBean bean = G.getUser(phone);
				Map<String, Object> map = new HashMap<String, Object>();
				if (bean == null) {
					map.put("cellphone", phone);
					if (i < selected.size() && selected.get(i)) {
						map.put("selected", true);
						cellphones.add(phone);
					}
					ret.add(map);
				} else {
					map.put("name", bean.getName());
					map.put("cellphone", phone);
					if (i < selected.size() && selected.get(i)) {
						map.put("selected", true);
						cellphones.add(phone);
					}
					ret.add(map);
				}
				++i;
			}
			num.close();
		}
		cur.close();
		return ret;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_right: {
			Intent intent = new Intent();
			String[] strArray = new String[cellphones.size()];
			cellphones.toArray(strArray);
			intent.putExtra("phones", strArray);
			intent.putExtra("group", group);
			Log.i("ContactsActivity", strArray.toString());
			setResult(RESULT_OK, intent);
			this.finish();
		}
			break;
		case R.id.bt_left: {
			selected.clear();
			for (int i = 0; i < listview.getChildCount(); ++i) {
				View cv = (View) listview.getChildAt(i);
				CheckBox cb = (CheckBox) cv.findViewById(R.id.checkbox);
				selected.add(cb.isChecked());
			}
			setResult(RESULT_CANCELED, null);
			Log.d("ContactsLeft", selected.toString());
			this.finish();
		}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
		CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox);
		checkBox.toggle();
		if (checkBox.isChecked()) {
			cellphones.add(((TextView) v.findViewById(R.id.tv_phone)).getText()
					.toString());
		} else {
			cellphones.remove(((TextView) v.findViewById(R.id.tv_phone))
					.getText().toString());
		}
	}

	@Override
	protected void initViews() {
		listview = (ContactListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);
		// init buttons
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		forward_button = (Button) findViewById(R.id.bt_right);
		forward_button.setOnClickListener(this);
	}

}
