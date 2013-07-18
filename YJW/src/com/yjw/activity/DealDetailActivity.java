package com.yjw.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.ImageSpan;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.app.yjw.R;
import com.yjw.bean.DataBean;
import com.yjw.bean.DealBean;
import com.yjw.bean.ImageBean;
import com.yjw.ctrl.ControlCode;
import com.yjw.ctrl.YJWControler;
import com.yjw.thread.async.GetImageThread;
import com.yjw.thread.delayed.BufferBigObjectThread;
import com.yjw.util.BigObject;
import com.yjw.util.G;
import com.yjw.util.YJWMessage;

public class DealDetailActivity extends BaseActivity {

	private TextView tv;

	private static BaseActivity instance = null;

	public static BaseActivity getInstance() {
		return instance;
	}

	public static <T> T getInstance(Class<T> cls) {
		return (T) instance;
	}

	private int id;
	private Editable editable;
	private ImageSpan[] spans;

	static Callback callback = new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (YJWMessage.values()[msg.what]) {
			case GET_IMAGE_SUCCESS: {
				ImageBean ib = ((ImageBean) msg.obj);
				DataBean db = ib.getData();
				BufferBigObjectThread.add(db.getId(), db.getLength(),
						ib.getId(), ib.getDid());
			}
				break;
			case GET_BIGOBJ_SUCCESS: {
				BigObject obj = (BigObject) msg.obj;
				byte[] data = obj.getData();
				int id = (Integer) obj.getExtra(0);
				G.addImage(id, data);
				msg.arg2 = ControlCode.C_REFRESH.ordinal();
			}
				break;
			}

			// getInstance(DealDetailActivity.class).tv.setText((Editable)msg.obj,BufferType.EDITABLE);
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		instance = this;
		init();
		YJWControler.registerCallback(YJWMessage.GET_IMAGE_SUCCESS, callback);
		YJWControler.registerCallback(YJWMessage.GET_BIGOBJ_SUCCESS, callback);
		id = getIntent().getIntExtra("id", -1);
		if (id == -1)
			return;
		DealBean bean = G.getDeal(id);
		if (bean == null)
			return;
		String s = bean.getContent();
		tv.setText(s, BufferType.EDITABLE);
		editable = tv.getEditableText();
		refresh(null);
	}

	@Override
	protected void initViews() {
		tv = (TextView) findViewById(R.id.textView1);
	}

	private boolean exists(int l, int r) {
		for (ImageSpan span : spans) {
			if (editable.getSpanStart(span) == l
					&& editable.getSpanEnd(span) == r)
				return true;
		}
		return false;
	}

	@Override
	public void refresh(Message msg) {
		editable = tv.getEditableText();
		spans = editable.getSpans(0, editable.length(), ImageSpan.class);
		if (editable == null)
			return;
		Pattern pat = Pattern.compile("\\[i\\d+\\]");
		Matcher match = pat.matcher(editable.toString());
		while (match.find()) {
			int l = match.start();
			int r = match.end();
			if (exists(l, r))
				continue;
			String sub = match.group();
			sub = sub.substring(2, sub.length() - 1);
			id = Integer.valueOf(sub);
			Drawable d = G.getImage(id);
			if (d == null) {
				if (msg == null)
					YJWControler.Start(GetImageThread.class, id);
			} else {
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
				editable.setSpan(span, l, r, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		super.refresh(msg);
	}
}
