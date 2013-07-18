package com.yjw.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.yjw.R;
import com.yjw.bean.AddTransBean;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;
import com.yjw.ctrl.GetDealCtrl;
import com.yjw.ctrl.YJWControler;
import com.yjw.database.DBProxy;
import com.yjw.thread.async.AddTransThread;
import com.yjw.thread.async.ConfTransThread;
import com.yjw.thread.async.DelDealThread;
import com.yjw.thread.async.DelTransThread;
import com.yjw.thread.delayed.BufferThread;
import com.yjw.util.G;
import com.yjw.util.Util;
import com.yjw.util.YJWMessage;

public class TestActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private static GetDealCtrl getdealctrl;
	private int itemId;
	private String toCellphone;

	enum Mode {
		TRANS, DEAL, USER
	};

	private static Mode mode = Mode.USER;
	private static BaseActivity instance = null;

	public static BaseActivity getInstance() {
		return instance;
	}

	public static <T> T getInstance(Class<T> cls) {
		return (T) instance;
	}

	static private Callback refreshcallback = new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (getInstance() != YJWControler.getInstance().getActivity())
				return false;
			switch (mode) {
			case DEAL:
				getdealctrl.syncDeal(0);
				break;
			case TRANS:
				getdealctrl.syncTrans(0);
				break;
			// case
			// USER:getdealctrl.syncUser();break;//getdealctrl.syncTrans(0);break;
			}
			((TestActivity) getInstance()).refresh(null);
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// G.cellphones.clear();
		// G.transes.clear();
		// G.deals.clear();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		YJWControler.getInstance().setActivity(this);
		init();
		getdealctrl = new GetDealCtrl();
		switch (mode) {
		case DEAL:
			getdealctrl.syncDeal(0);
			break;
		case TRANS:
			getdealctrl.syncTrans(0);
			break;
		// case USER:getdealctrl.syncUser();break;
		}
		// YJWControler.registerCallback(YJWMessage.ADD_DEAL_FAILED,
		// refreshcallback);
		// YJWControler.registerCallback(YJWMessage.ADD_DEAL_SUCCESS,
		// refreshcallback);
		YJWControler.registerCallback(YJWMessage.DEL_DEAL_SUCCESS,
				refreshcallback);
		YJWControler.registerCallback(YJWMessage.ADD_TRANS_FAILED,
				refreshcallback);
		YJWControler.registerCallback(YJWMessage.ADD_TRANS_SUCCESS,
				refreshcallback);
		YJWControler.registerCallback(YJWMessage.DEL_TRANS_SUCCESS,
				refreshcallback);
		YJWControler.registerCallback(YJWMessage.CONF_TRANS_SUCCESS,
				refreshcallback);
		YJWControler.registerCallback(YJWMessage.SYNC_USER_SUCCESS,
				refreshcallback);
		YJWControler.registerCallback(YJWMessage.INIT_TABLE_DONE,
				refreshcallback);
	}

	List<String> getData() {
		switch (mode) {
		case DEAL:
			return getDeal();
		case TRANS:
			return getTrans();
		case USER:
			return getUser();
		}
		return null;
	}

	private List<String> getUser() {
		List<String> ret = new ArrayList<String>();
		synchronized (G.cellphones) {
			for (String phone : G.cellphones) {
				UserBean u = G.getUser(phone);
				if (u == null)
					ret.add(phone);
				else {
					String s = u.getName();
					if (u.getId() == null)
						s += " *";
					s += "\n";
					s += u.getCellphone();
					ret.add(s);
				}
			}
		}
		return ret;
	}

	private List<String> getDeal() {
		List<String> ret = new ArrayList<String>();
		synchronized (G.deals) {
			for (Integer i : G.deals) {
				String s = "";
				DealBean bean = G.getDeal(i);
				if (bean == null) {
					s = "d" + i;
					// BufferThread.addDeal(i);
				} else {
					s = bean.getTitle() + "\n" + bean.getContent();
				}
				ret.add(s);
			}
		}
		return ret;
	}

	private List<String> getTrans() {
		List<String> ret = new ArrayList<String>();
		synchronized (G.transes) {
			for (Integer key : G.transes) {
				String s = "";
				TransBean trans = G.getTrans(key);
				if (trans == null) {
					BufferThread.addTrans(key);
					s = "t" + key;
				} else {

					DealBean deal = G.getDeal(trans.getDeal_id());
					if (deal == null) {
						s += "d" + trans.getDeal_id();
						if (trans.getConfirmed() == 0)
							s += " 需要验证";
						s += "\n";
						// BufferThread.addDeal(trans.getDeal_id());
					} else {
						s += deal.getTitle() + " ";
						if (trans.getConfirmed() == 0)
							s += "需要验证 ";
						int id = deal.getOwner_id();
						UserBean user = G.getUser(id);
						if (user == null) {
							s += "o" + deal.getOwner_id() + "\n";
							// BufferThread.addUser(id);
						} else {
							s += user.getName() + "\n";
						}
					}
					int id = trans.getFrom_id();
					UserBean user = G.getUser(id);
					if (user == null) {
						s += "o" + trans.getFrom_id() + " ";
						// BufferThread.addUser(id);
					} else {
						s += user.getName() + " ";
					}

					id = trans.getTo_id();
					user = G.getUser(id);
					if (user == null) {
						s += "o" + trans.getTo_id() + " ";
						// BufferThread.addUser(id);
					} else {
						s += user.getName() + " ";
					}

				}
				ret.add(s);
			}
		}
		return ret;
	}

	@Override
	public void refresh(Message msg) {
		adapter.clear();
		List<String> ss = getData();
		for (String s : ss)
			adapter.add(s);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_insimg: {
			Util.startNewActivity(this, AddDealPageActivity.class, true);
		}
			break;
		case R.id.button2: {
			mode = Mode.TRANS;
			refresh(null);
			getdealctrl.syncTrans(0);
		}
			break;
		case R.id.button3: {
			mode = Mode.DEAL;
			refresh(null);
			getdealctrl.syncDeal(0);
		}
			break;
		case R.id.button4: {
			mode = Mode.USER;
			getdealctrl.syncUser();
			refresh(null);
		}
			break;
		case R.id.button5: {
			DBProxy.clearAccountTable();
			G.deals.clear();
			G.transes.clear();
			G.cellphones.clear();
		}
			break;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		switch (mode) {
		case TRANS: {
			TransBean tb;
			if ((tb = G.getTrans(G.transes.get(itemId))) != null) {
				if (tb.getConfirmed() == 0) {
					menu.add(0, -2, ContextMenu.NONE, "通过验证");
				}
			}
			menu.add(0, itemId, ContextMenu.NONE, "推荐");
			menu.add(0, -1, ContextMenu.NONE, "删除");
		}
			break;
		case DEAL: {
			menu.add(1, -2, ContextMenu.NONE, "详细");
			menu.add(1, itemId, ContextMenu.NONE, "推荐");
			menu.add(1, -1, ContextMenu.NONE, "删除");
		}
			break;
		case USER: {
			menu.add(2, 0, 0, "推荐");
		}
			break;
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int arg2, long id) {
		if (parent.getId() != R.id.listView1)
			return;
		if (id < 0)
			return;
		itemId = (int) id;
		if (mode == Mode.USER) {
			UserBean bean;
			synchronized (G.cellphones) {
				bean = G.getUser(G.cellphones.get((int) id));
				toCellphone = G.cellphones.get((int) id);
			}
			if (bean != null)
				return;
		}
		listView.showContextMenu();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View v, int arg2,
			long id) {
		if (parent.getId() != R.id.listView1)
			return true;
		if (id < 0)
			return true;
		itemId = (int) id;
		if (mode == Mode.USER) {
			UserBean bean;
			synchronized (G.cellphones) {
				bean = G.getUser(G.cellphones.get((int) id));
				toCellphone = G.cellphones.get((int) id);
			}
			if (bean == null)
				return false;
			return true;
		}
		return false;
	}

	private void AskCellphone(MenuItem item) {
		Intent intent = new Intent(this, ContactsActivity.class);
		Object bean;
		int id;
		if (item.getGroupId() == 0) {
			id = G.transes.get(item.getItemId());
			bean = G.getTrans(id);
		} else {
			id = G.deals.get(item.getItemId());
			bean = G.getDeal(id);
		}
		if (bean == null) {
			Toast.makeText(this, "数据加载中请稍候", Toast.LENGTH_SHORT).show();
		} else {
			intent.putExtra("group", item.getGroupId());
			startActivityForResult(intent, id);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (mode) {
		case TRANS:
			switch (item.getItemId()) {
			case -2: {
				ConfTransThread t = new ConfTransThread();
				YJWControler.Start(ConfTransThread.class,
						G.getTrans(G.transes.get(itemId)).getId());
			}
				break;
			case -1: {
				DelTransThread t = new DelTransThread();
				t.setId(G.transes.get(itemId));
				t.setHandler(YJWControler.getHandler());
				t.start();
			}
				break;
			default:
				AskCellphone(item);
				break;

			}
			break;
		case DEAL:
			switch (item.getItemId()) {
			case -2: {
				Intent intent = new Intent(this, DealDetailActivity.class);
				intent.putExtra("id", G.deals.get(itemId));
				startActivity(intent);
			}
				break;
			case -1: {
				DelDealThread t = new DelDealThread();
				t.setId(G.deals.get(itemId));
				t.setHandler(YJWControler.getHandler());
				t.start();
			}
				break;
			default:
				AskCellphone(item);
				break;

			}
			break;
		case USER:
			switch (item.getItemId()) {
			case 0: {
				// SmsManager mgr=;
				// PendingIntent itnt=PendingIntent.getBroadcast(context,
				// requestCode, intent, flags);
				SmsManager.getDefault().sendTextMessage(toCellphone, null,
						"这是短信", null, null);
				Log.d("SMS", "SMS Sent");
			}
				break;
			}
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		YJWControler.getInstance().setActivity(this);
		if (requestCode == RESULT_CANCELED)
			return;
		if (data == null)
			return;
		Bundle extra = data.getExtras();
		if (extra == null)
			return;
		String[] result = extra.getStringArray("phones");
		if (result == null || result.length == 0)
			return;
		int group = data.getIntExtra("group", 0);
		AddTransBean bean = new AddTransBean();
		bean.setFromid(YJWActivity.user.getId());
		if (group == 0) {
			bean.setTranses(new TransBean[] { G.getTrans(requestCode) });
		} else {
			bean.setDeals(new DealBean[] { G.getDeal(requestCode) });
		}
		List<UserBean> users = new ArrayList<UserBean>();
		for (String s : result) {
			UserBean user = new UserBean();
			user.setCellphone(s);
			users.add(user);
		}
		UserBean[] arUser = new UserBean[users.size()];
		users.toArray(arUser);
		bean.setUsers(arUser);
		YJWControler.Start(AddTransThread.class, bean);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void initViews() {
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData());
		listView.setAdapter(adapter);
		registerForContextMenu(listView);
		findViewById(R.id.btn_insimg).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(this);
	}
}
