package com.kimersoft.pointofsaleterminal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.smartdevice.aidl.ICallBack;
import com.kimersoft.pointofsaleterminal.util.ExecutorFactory;

public class SerialPortActivity extends BaseActivity {
	private static final String TAG = "SerialPortActivity";
	private EditText revText;
	static String text;
	private EditText editText_send;
	private Button btn_send;
	CheckBox checkbox_rec, checkbox_send;
	TextView textViewInfo, textViewCount;
	static int countRev = 0, countSend = 0;
	private boolean runFlag = true;
	
	ICallBack.Stub mCallback = new ICallBack.Stub() {
		
		@Override
		public void onReturnValue(byte[] buffer, int size) throws RemoteException {
			countRev += size;
			if (checkbox_rec.isChecked()) {
				text += byteToString(buffer, size);
			} else {
				text += new String(buffer, 0, size);
			}
			revText.post(new Runnable() {
				@Override
				public void run() {
					revText.setText(text);
					revText.setSelection(revText.length());
					textViewCount.setText("R:" + countRev + ",S:"
							+ countSend);
					if (text.length() > 1000) {
						text = "";
					}
				}
			});
			
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serialport);

		initView();
		initData();
		
		btn_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					byte[] buffer = null;
					if (checkbox_send.isChecked()) {
						buffer = hexStringToBytes(editText_send.getText().toString());
						mIzkcService.sendRAWData("SerialPort", buffer);
						countSend += buffer.length;
						textViewCount.setText("R:" + countRev + ",S:" + countSend);
					} else {
						mIzkcService.sendRAWData("SerialPort",editText_send.getText().toString().getBytes());
					}
				} catch (RemoteException e) {
					
					e.printStackTrace();
				}
			}
		});
		
		btn_send.setEnabled(false);

		ExecutorFactory.executeThread(new Runnable() {
			
			@Override
			public void run() {
				while(runFlag){
					if(bindSuccessFlag){
						//注册回调接口
						try {
							mIzkcService.registerCallBack("SerialPort",mCallback);
							//关闭线程
							runFlag = false;
							mHandler.sendEmptyMessage(0);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							runFlag = false;
							e.printStackTrace();
						}
					}
				}
				
			}
		});
	}
	
	Handler mHandler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				btn_send.setEnabled(true);				
				break;

			default:
				break;
			}
			return false;
		}
	});

	private void initData() {
		countRev = 0;
		countSend = 0;
		text = "";
		setEditTextReadOnly(revText);
		checkbox_rec.setChecked(true);
		checkbox_send.setChecked(true);
	}

	private void initView() {
		textViewInfo = (TextView) findViewById(R.id.textViewInfo);
		revText = (EditText) findViewById(R.id.tvRev);
		checkbox_rec = (CheckBox) findViewById(R.id.checkBox_rec);
		checkbox_send = (CheckBox) findViewById(R.id.checkBox_send);
		textViewCount = (TextView) findViewById(R.id.textViewCount);
		editText_send = (EditText) findViewById(R.id.editText_send);
		btn_send = (Button) findViewById(R.id.btn_send);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		try {
			mIzkcService.unregisterCallBack("SerialPort",mCallback);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static String byteToString(byte[] b, int size) {
		byte high, low;
		byte maskHigh = (byte) 0xf0;
		byte maskLow = 0x0f;

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < size; i++) {
			high = (byte) ((b[i] & maskHigh) >> 4);
			low = (byte) (b[i] & maskLow);
			buf.append(findHex(high));
			buf.append(findHex(low));
			buf.append(" ");
		}

		return buf.toString();
	}

	private static char findHex(byte b) {
		int t = new Byte(b).intValue();
		t = t < 0 ? t + 16 : t;

		if ((0 <= t) && (t <= 9)) {
			return (char) (t + '0');
		}

		return (char) (t - 10 + 'A');
	}

	public static byte[] hexStringToBytes(String hexString) {
		hexString = hexString.toLowerCase();
		String[] hexStrings = hexString.split(" ");
		byte[] bytes = new byte[hexStrings.length];
		for (int i = 0; i < hexStrings.length; i++) {
			char[] hexChars = hexStrings[i].toCharArray();
			bytes[i] = (byte) (charToByte(hexChars[0]) << 4 | charToByte(hexChars[1]));
		}
		return bytes ;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789abcdef".indexOf(c);
	}

	public static void setEditTextReadOnly(EditText view) {
		if (view instanceof EditText) {
			view.setCursorVisible(false);
			view.setFocusable(false);
			view.setFocusableInTouchMode(false);
		}
	}

	public static char intTochar(int backnum) {
		char strChar = (char) backnum;
		return strChar;
	}
}
