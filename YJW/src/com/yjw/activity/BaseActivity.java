package com.yjw.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;

import com.yjw.ctrl.YJWControler;

public abstract class BaseActivity extends Activity {

	protected ProgressDialog loadingDialog;

	protected void showLoadingDialog() {
		if (loadingDialog == null) {
			// 设置正在处理窗口
			loadingDialog = new ProgressDialog(this);
			loadingDialog.setTitle("佣金王");
			loadingDialog.setMessage("正在加载，请稍后...");
			loadingDialog.setCancelable(true);
		} else {
			if (!loadingDialog.isShowing())
				loadingDialog.show();
		}
	}

	protected void hideLoadingDialog() {
		if (loadingDialog.isShowing())
			loadingDialog.dismiss();
	}

	protected abstract void initViews();

	public void refresh(Message msg) {
	};

	protected void init() {
		this.initViews();
		YJWControler.getInstance().setActivity(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		YJWControler.getInstance().setActivity(this);
		super.onResume();
	}

	@Override
	protected void onRestart() {
		YJWControler.getInstance().setActivity(this);
		super.onRestart();
	}
}
