package com.yjw.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.app.yjw.R;
import com.yjw.bean.AccountBean;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;
import com.yjw.ctrl.YJWControler;
import com.yjw.database.DBProxy;
import com.yjw.database.DBStatic;
import com.yjw.thread.async.LoginThread;
import com.yjw.thread.delayed.ShowMessageThread;
import com.yjw.util.Util;
import com.yjw.util.YJWMessage;

public class YJWActivity extends BaseActivity implements OnClickListener {
	public static ShowMessageThread BackgroundThread;
	public static SQLiteDatabase database;
	public static UserBean user = null;

	private static String password;
	private static BaseActivity instance = null;

	public static BaseActivity getInstance() {
		return instance;
	}

	public static <T> T getInstance(Class<T> cls) {
		return (T) instance;
	}

	static Callback callback = new Callback() {
		public boolean handleMessage(Message msg) {
			switch (YJWMessage.values()[msg.what]) {
			case LOGIN_SUCCESS: {
				Toast.makeText((YJWActivity) getInstance(), "登录成功",
						Toast.LENGTH_SHORT).show();
				DBProxy.clearAccountTable();
				UserBean bean = (UserBean) msg.obj;
				user = bean;
				AccountBean ab = bean.to(AccountBean.class);
				ab.setPassword(password);
				password = null;
				DBProxy.insertNewAccount(ab);
				// Util.startNewActivity(getInstance(), MainPageActivity.class,
				// true);
				Util.startNewActivity(getInstance(), TestActivity.class, true);
			}
				break;
			default:
				Toast.makeText(getInstance(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			return false;
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 检测网络连接用，部分手机不能自动连接网络
		/*
		 * if(!check_NetworkInfo()){ startActivity(new
		 * Intent(Settings.ACTION_WIRELESS_SETTINGS)); }
		 */
		user = null;
		database = this.openOrCreateDatabase("YJW.db", MODE_PRIVATE, null);
		dropTables();
		initTables();
		// SmsManager.getDefault().sendTextMessage("13816955910", null, "这是短信",
		// null, null);
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.main);
		init();
		BackgroundThread = YJWControler.Start(ShowMessageThread.class, null);
		YJWControler.registerCallback(YJWMessage.LOGIN_SUCCESS, callback);
		if (check_database()) {
			YJWControler.Start(LoginThread.class, user.to(AccountBean.class));
		}
		// 初始化后台消息提示

	}

	@Override
	protected void onResume() {
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}

	private boolean check_NetworkInfo() {
		ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		// mobile 3G Data Network
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		// wifi
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();

		// 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
		if (mobile == State.CONNECTED || mobile == State.CONNECTING)
			return true;
		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			return true;

		// startActivity(new
		// Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面
		// startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		// //进入手机中的wifi网络设置界面
		return false;
	}

	private boolean check_database() {
		Cursor cur = database.query(DBStatic.AccountTableName, null, null,
				null, null, null, null);
		if (cur != null && 0 != cur.getCount()) {
			cur.moveToFirst();
			YJWActivity.user = new UserBean();
			YJWActivity.user.setCellphone(cur.getString(0));
			YJWActivity.user.setPassword(cur.getString(1));
			password = cur.getString(1);
			Log.i("YJWActivity",
					"tel:" + user.getCellphone() + ",pw:" + user.getPassword());
		} else {
			// do something
			Toast.makeText(this, "没有账户", Toast.LENGTH_SHORT).show();
			cur.close();
			return false;
		}
		cur.close();
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.rigister_button) {
			Util.startNewActivity(this, RegisterPageActivity.class, true);
		} else if (v.getId() == R.id.login_button) {
			Util.startNewActivity(this, LoginPageActivity.class, true);
		}
	}

	private void initTables() {
		database.execSQL(DBStatic.CreateTableByBean(AccountBean.class,
				DBStatic.AccountTableName));
		database.execSQL(DBStatic.CreateTableByBean(DealBean.class,
				DBStatic.DealTableName));
		database.execSQL(DBStatic.CreateTableByBean(UserBean.class,
				DBStatic.UserTableName));
		database.execSQL(DBStatic.CreateTableByBean(TransBean.class,
				DBStatic.TransTableName));
		database.execSQL(DBStatic.CreateImageTable);
		database.execSQL(DBStatic.CreateMessageTable);
	}

	private void dropTables() {
		// database.execSQL("DROP TABLE IF EXISTS "+DBStatic.AccountTableName);
		database.execSQL("DROP TABLE IF EXISTS " + DBStatic.MessageTableName);
		database.execSQL("DROP TABLE IF EXISTS " + DBStatic.DealTableName);
		database.execSQL("DROP TABLE IF EXISTS " + DBStatic.UserTableName);
		database.execSQL("DROP TABLE IF EXISTS " + DBStatic.TransTableName);
		database.execSQL("DROP TABLE IF EXISTS " + DBStatic.ImageTableName);

	}

	@Override
	protected void initViews() {
		Button register_button = (Button) findViewById(R.id.rigister_button);
		register_button.setOnClickListener(this);
		Button login_button = (Button) findViewById(R.id.login_button);
		login_button.setOnClickListener(this);
	}

}