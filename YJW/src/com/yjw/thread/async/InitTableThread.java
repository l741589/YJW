package com.yjw.thread.async;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import com.yjw.bean.UserBean;
import com.yjw.ctrl.YJWControler;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.G;
import com.yjw.util.YJWMessage;

public class InitTableThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void run() {
		Log.i("InitTable","Start InitTable");
		ContentResolver content = YJWControler.getInstance().getActivity().getContentResolver();
		Cursor cur=content.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while(cur.moveToNext()){
			int namecol=cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			String name=cur.getString(namecol);
			int idcol=cur.getColumnIndex(ContactsContract.Contacts._ID);
			String id=cur.getString(idcol);
			Cursor num=content.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+id, null, null); 
			while(num.moveToNext()){
				int numcol=num.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
				String phone=num.getString(numcol);
				phone=phone.replace(" ", "");
				phone=phone.replace("-", "");
				phone=phone.replace("+86", "");
				synchronized (G.cellphones) {
					if (!G.cellphones.contains(phone))
						G.cellphones.add(phone);
				}
				
				UserBean bean=new UserBean();
				bean.setName(name);
				bean.setCellphone(phone);
				G.addUser(bean);
			}
			num.close();
		}
		cur.close();
		msg=Message.obtain();
		msg.what=YJWMessage.INIT_TABLE_DONE.ordinal();
		Log.i("InitTable","InitTable Done");
		sendMessage();
	}
	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

}
