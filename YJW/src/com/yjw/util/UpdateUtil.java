package com.yjw.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.yjw.activity.UpdateActivity;

public class UpdateUtil extends UpdateActivity{
/*
	
	public static void CheckUpdate(final Context context) {
		Log.d("CheckUpdate",
				"Begin at" + new Date(System.currentTimeMillis()).toString());
		Handler handler = new Handler() {
			@Override
			public void handleMessage(final Message msg) {
				UpdateStyle style = UpdateStyle.values()[msg.what];
				switch (style) {
				case ForceUpdate:
					Uri uri = Uri.parse("");
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					context.startActivity(intent);
					break;
				case OptionalUpdate:
					// show the dialog whether should update
					// Toast.makeText(context,
					// "有新版本：" + ((Version) msg.obj).UpdateURL,
					// Toast.LENGTH_SHORT).show();
				//	final String updateURL = "http://"	+ ((Version) msg.obj).UpdateURL;
					//Log.d(updateURL, updateURL);
					new AlertDialog.Builder(context)
							.setTitle("YON")
							.setMessage("程序有新版本是否需要下载？")
							.setNegativeButton("No",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									})
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
									//		new UpdateThread(context,updateURL);
											/*File file = downLoadFile(context,
													updateURL);
											Log.e("OpenFile", file.getName());
											Intent intent = new Intent();
											intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											intent.setAction(android.content.Intent.ACTION_VIEW);
											intent.setDataAndType(
													Uri.fromFile(file),
													"application/vnd.android.package-archive");
											((Activity) context)
													.startActivity(intent);*
											
										}

									}).show();
					break;
				case NoUpdate:
					Log.d("CheckUpdate", "The version is up to date!");
					break;
				case UpdateError:
					Toast.makeText(context, "获取新版本错误。", Toast.LENGTH_SHORT)
							.show();
					break;
				}
				Log.d("CheckUpdate",
						"End at "
								+ new Date(System.currentTimeMillis())
										.toString());
			}
		};
		CheckVersionThread cvt = new CheckVersionThread();
		cvt.setHandler(handler);
		cvt.setContext(context);
		cvt.start();
	}

	public static int CompareVersion(Context context, Version versionInfo) {
		UpdateStyle style;
		try {
			String currentVersion = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
			//if (currentVersion != null)
				if (currentVersion.compareTo(versionInfo.MinimalVersionNumber) < 0) {
					style = UpdateStyle.ForceUpdate;
				} else if (currentVersion.compareTo(versionInfo.VersionNumber) < 0) {
					style = UpdateStyle.OptionalUpdate;
				} else {
					style = UpdateStyle.NoUpdate;
				}
			//else
				//style = Enum.UpdateStyle.NoUpdate;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			style = UpdateStyle.UpdateError;
		}
		return style.ordinal();
	}

	*/
	@SuppressLint("ParserError")
	public static File downLoadFile(Context context, String httpUrl) {
		// TODO Auto-generated method stub
		Log.d("Download File!", "Begin!");
		Intent in=new Intent();
		in.setClass(context, UpdateActivity.class);
		context.startActivity(in);
		final String fileName = "YJW.apk";
		File tmpFile = new File("//sdcard//YJW//");
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		final File file = new File("//sdcard//YJW//" + fileName);
		try {
			URL url = new URL(httpUrl);
			try {
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				InputStream is = conn.getInputStream();
				filesize = conn.getContentLength();//根据响应获取文件大小
			    if (filesize <= 0) throw new RuntimeException("无法获知文件大小 ");
			    if (is == null) throw new RuntimeException("stream is null");
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buf = new byte[256];
			   // sendMsg(0);
				conn.connect();
				double count = 0;
				if (conn.getResponseCode() >= 400) {
					Toast.makeText(context, "连接超时", Toast.LENGTH_SHORT).show();
					Log.i("time", "time exceed");
				} else {
					while (count <= 100) {
						if (is != null) {
							int numRead = is.read(buf);
							if (numRead <= 0) {
								break;
							} else {
								fos.write(buf, 0, numRead);
								downLoadFileSize += numRead;
						      //  sendMsg(1);//更新进度条
							}
						} else {
							// sendMsg(2);//通知下载完成
							break;
						}
					}
				}
				isend=true;
				conn.disconnect();
				fos.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("Download File!", "End!");
		return file;
	}
}
