package com.yjw.thread.async;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.yjw.util.UpdateUtil;

public class UpdateThread extends Thread{
	private Context context;
	private String updateURL;
	public UpdateThread(Context context,String updateURL){
		this.context=context;
		this.updateURL=updateURL;
		this.start();
	}
	
	@Override
	public void run(){
		File file =UpdateUtil.downLoadFile(context, updateURL);
		Log.e("OpenFile", file.getName());
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		((Activity)context).startActivity(intent);
	}

}
