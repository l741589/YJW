package com.yjw.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.app.yjw.R;
import com.yjw.thread.delayed.ShowMessageThread;

public class UpdateActivity extends BaseActivity implements OnClickListener,
		Runnable {

	private Button back_button;
	// private TextView tview;
	private ProgressBar myBar;
	protected static int filesize;
	protected static int downLoadFileSize = 0;
	protected static boolean isend = false;

	/*
	 * private Handler handler = new Handler() {
	 * 
	 * @Override public void handleMessage(Message msg)
	 * {//定义一个Handler，用于处理下载线程与UI间通讯 tview.setText(msg.what+"%");
	 * super.handleMessage(msg); } };
	 */

	/** 启动等待界面 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		init();
		new Thread(this).start();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_update, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.bt_left) {
			/*
			 * Intent intent = new Intent(); intent.setClass(this,
			 * MainPageActivity.class); startActivity(intent);
			 */
			this.finish();
		}

	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		myBar = (ProgressBar) findViewById(R.id.myBar);
		// tview=(TextView)findViewById(R.id.tView);
		// tview.setText(20+"%");
	}

	/** 不用线程保护么 */
	@SuppressLint("ParserError")
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (!this.isend) {
			if (filesize == 0)
				continue;
			int result = (downLoadFileSize * 100 / filesize + 1);
			myBar.setProgress(result);
			// myBar.setTag(result);
			// sendMsg(result);
			// tview.setText(String.valueOf(result)+"%");
			// 13818554170
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * private void sendMsg(int flag) { Message msg = new Message(); msg.what =
	 * flag; handler.sendMessage(msg); }
	 */

}
