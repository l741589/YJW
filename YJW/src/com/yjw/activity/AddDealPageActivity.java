package com.yjw.activity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.app.yjw.R;
import com.yjw.bean.AddImageBean;
import com.yjw.bean.ArrayBean;
import com.yjw.bean.DealBean;
import com.yjw.bean.ImageBean;
import com.yjw.ctrl.YJWControler;
import com.yjw.thread.async.AddDealThread;
import com.yjw.thread.async.ApplyImgIdThread;
import com.yjw.thread.delayed.BufferBigObjectThread;
import com.yjw.thread.delayed.ShowMessageThread;
import com.yjw.util.ImgTool;
import com.yjw.util.Util;
import com.yjw.util.YJWMessage;

@SuppressLint({ "HandlerLeak", "HandlerLeak" })
public class AddDealPageActivity extends BaseActivity implements
		OnClickListener {

	/**
	 * Members
	 */

	private Button back_button;
	private Button add_button;
	private Button ins_button;
	private EditText title_edittext;
	private DatePicker datepicker;
	private EditText refer_edittext;
	private EditText details_edittext;
	private static BaseActivity instance = null;

	public static BaseActivity getInstance() {
		return instance;
	}

	public static <T> T getInstance(Class<T> cls) {
		return (T) instance;
	}

	private List<byte[]> datalist = new ArrayList<byte[]>();

	// *无奈之策 第一部分
	private static List<SpanInfo> bak = new ArrayList<SpanInfo>();

	private static class SpanInfo {
		ImageSpan span;
		int l, r;
		int flag;
	}

	// */

	static private Callback callback = new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (YJWMessage.values()[msg.what]) {
			case ADD_DEAL_SUCCESS:
				break;
			case ADD_DEAL_FAILED:
				break;
			case APPLY_IMG_ID_SUCCESS: {
				List<ImageBean> list = (List<ImageBean>) msg.obj;
				AddDealPageActivity act = getInstance(AddDealPageActivity.class);
				Editable text = act.details_edittext.getText();
				ImageSpan[] spans = text.getSpans(0, text.length(),
						ImageSpan.class);
				if (list.size() != spans.length) {
					Toast.makeText(getInstance(), "添加业务失败", Toast.LENGTH_SHORT)
							.show();
					return false;
				}
				for (int i = 0; i < list.size(); ++i) {
					ImageSpan span = spans[i];
					int l = text.getSpanStart(span);
					int r = text.getSpanEnd(span);
					int id = list.get(i).getId();
					int did = list.get(i).getDid();
					String tar = "[i" + id + "]";
					text.replace(l, r, tar);
					text.setSpan(spans[i], l, l + tar.length(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					BufferBigObjectThread
							.add(did, act.datalist.get(i), id, did);
				}
				act.datalist.clear();
				DealBean bean = new DealBean();
				bean.setOwner_id(YJWActivity.user.getId());
				bean.setTitle(act.title_edittext.getText().toString());
				bean.setexpire_date(new Date(act.datepicker.getYear(),
						act.datepicker.getMonth(), act.datepicker
								.getDayOfMonth()));
				bean.setFee(Float.valueOf(act.refer_edittext.getText()
						.toString()));
				bean.setContent(text.toString());
				YJWControler.Start(AddDealThread.class, bean);
			}
				break;
			case APPLY_IMG_ID_FAILED: {
				AddDealPageActivity act = getInstance(AddDealPageActivity.class);
				act.datalist.clear();
			}
				break;
			}
			return false;
		}
	};

	static void handleMessage(Message msg) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_deal_page);
		instance = this;
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}

	private boolean checkAllInfoFilled() {
		return editTextFilled(title_edittext) && editTextFilled(refer_edittext)
		// && editTextFilled(commission_edittext)
				&& editTextFilled(details_edittext) // && phones != null
		// && phones.length != 0
		;
	}

	private boolean editTextFilled(EditText et) {
		return !et.getText().toString().equals("");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			// this.finish();
			Util.startNewActivity(this, TestActivity.class, true);
			break;
		case R.id.bt_right:
			if (checkAllInfoFilled()) {
				send();
			} else
				Toast.makeText(AddDealPageActivity.this, "信息不完整",
						Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_insimg: {

			// *谁TM能解释着bug是怎么回事，我只能用浪费更多时间的方法来解决这个问题
			// 无奈之策 第二部分
			Editable text = details_edittext.getText();
			ImageSpan[] spans = text
					.getSpans(0, text.length(), ImageSpan.class);
			bak.clear();
			for (ImageSpan span : spans) {
				SpanInfo t = new SpanInfo();
				t.span = span;
				t.l = text.getSpanStart(span);
				t.r = text.getSpanEnd(span);
				t.flag = text.getSpanFlags(span);
				bak.add(t);
			}
			Log.i("ImageSpan", "备份完成，ImageSpan数量:" + bak.size());
			// */
			Intent intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, 1);
		}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// *无奈之策 第三部分
		Editable text = details_edittext.getEditableText();
		for (SpanInfo t : bak) {
			if (text.getSpanStart(t) != -1) {
				Log.e("ImageSpan", "居然没出问题了！！");
			} else {
				text.setSpan(t.span, t.l, t.r, t.flag);
				Log.i("ImageSpan", "ImageSpan问题修复");
			}
		}
		if (bak.size() == 0)
			Log.i("ImageSpan", "Span哪去了？？");
		bak.clear();
		// */
		switch (resultCode) {
		case RESULT_OK:
			switch (requestCode) {
			case 1: {

				// Log.d("SpanCountResult",""+details_edittext.getText().getSpans(0,
				// details_edittext.length(), ImageSpan.class).length);
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();

				String img = "[图]";
				int s = details_edittext.getSelectionStart();
				int e = details_edittext.getSelectionEnd();

				text.replace(s, e, img);
				Drawable d = Drawable.createFromPath(picturePath);
				byte[] bs = ImgTool.getInstance().Drawable2Bytes(d);
				d = ImgTool.getInstance().Bytes2Drawable(bs);
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				ImageSpan span = new ImageSpan(d, picturePath,
						ImageSpan.ALIGN_BASELINE);
				text.setSpan(span, s, s + img.length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				// Log.d("Spans","Text:"+text);
				// Log.d("Spans","Size:"+text.getSpans(0, text.length(),
				// Object.class).length);
				// details_edittext.setText(text);
				// details_edittext.setSelection(s+img.length());

			}
				break;
			}
			break;
		case RESULT_CANCELED:
			break;
		default:
			Toast.makeText(this, "error on activity result", Toast.LENGTH_SHORT)
					.show();
			break;
		}
	}

	// @SuppressLint({ "Hansk", "HandlerLeak", "HandlerLeak", "HandlerLeak" })
	private void send() {
		Editable text = details_edittext.getText();
		ImageSpan[] spans = text.getSpans(0, text.length(), ImageSpan.class);
		AddImageBean[] beans = new AddImageBean[spans.length];
		if (datalist.size() != 0) {
			Toast.makeText(this, "上一个任务未完成部署,请稍候", Toast.LENGTH_SHORT).show();
			return;
		}
		for (ImageSpan span : spans) {
			datalist.add(ImgTool.getInstance().Drawable2Bytes(
					span.getDrawable()));
		}
		for (int i = 0; i < spans.length; ++i) {
			beans[i] = new AddImageBean();
			beans[i].setLength(datalist.get(i).length);
			beans[i].setMark("DEAL");
		}
		ArrayBean<AddImageBean> bean = new ArrayBean<AddImageBean>();
		bean.setData(beans);
		YJWControler.Start(ApplyImgIdThread.class, bean);
	}

	@Override
	protected void init() {
		super.init();
		YJWControler.registerCallback(YJWMessage.ADD_DEAL_SUCCESS, callback);
		YJWControler
				.registerCallback(YJWMessage.APPLY_IMG_ID_SUCCESS, callback);
		YJWControler.registerCallback(YJWMessage.APPLY_IMG_ID_FAILED, callback);
	}

	@Override
	protected void initViews() {
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		add_button = (Button) findViewById(R.id.bt_right);
		add_button.setOnClickListener(this);
		ins_button = (Button) findViewById(R.id.btn_insimg);
		ins_button.setOnClickListener(this);

		title_edittext = (EditText) findViewById(R.id.et_title);
		datepicker = (DatePicker) findViewById(R.id.dp_expire_date);
		refer_edittext = (EditText) findViewById(R.id.et_refer_fee);
		details_edittext = (EditText) findViewById(R.id.et_details);
		// confirm_checkbox = (CheckBox) findViewById(R.id.cb_confirm);
	}

}
