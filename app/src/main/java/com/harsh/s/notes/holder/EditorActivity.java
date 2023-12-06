package com.harsh.s.notes.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class EditorActivity extends AppCompatActivity {
	
	private String new_date = "";
	private String ms = "";
	private HashMap<String, Object> map_saver = new HashMap<>();
	private boolean sav = false;
	
	private ArrayList<HashMap<String, Object>> map_data = new ArrayList<>();
	
	private LinearLayout linear1;
	private ScrollView vscroll1;
	private ImageView imageview1;
	private LinearLayout linear3;
	private ImageView imageview3;
	private ImageView imageview4;
	private LinearLayout linear2;
	private EditText edittext1;
	private TextView textview1;
	private EditText edittext2;
	
	private Calendar c = Calendar.getInstance();
	private SharedPreferences data;
	private Calendar c1 = Calendar.getInstance();
	private AlertDialog.Builder dialog;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.editor);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		vscroll1 = findViewById(R.id.vscroll1);
		imageview1 = findViewById(R.id.imageview1);
		linear3 = findViewById(R.id.linear3);
		imageview3 = findViewById(R.id.imageview3);
		imageview4 = findViewById(R.id.imageview4);
		linear2 = findViewById(R.id.linear2);
		edittext1 = findViewById(R.id.edittext1);
		textview1 = findViewById(R.id.textview1);
		edittext2 = findViewById(R.id.edittext2);
		data = getSharedPreferences("data", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_saveAndFinish();
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				View ssV = getLayoutInflater().inflate(R.layout.editor_menu_cus, null);
				final PopupWindow ss = new PopupWindow(ssV, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
				final LinearLayout ssbg = ssV.findViewById(R.id.bg);
				final TextView ssb1 = ssV.findViewById(R.id.b1);
				final TextView ssb2 = ssV.findViewById(R.id.b2);
				final TextView ssb3 = ssV.findViewById(R.id.b3);
				final TextView ssb4 = ssV.findViewById(R.id.b4);
				final TextView ssb5 = ssV.findViewById(R.id.b5);
				ssbg.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)ThemeUtil.getDip(getApplicationContext(), (int)(8)), 0xFFF2F5FB));
				_ripple(ssb1, 0x33E0E0E0, 0xFFF2F5FB, 0);
				_ripple(ssb2, 0x33E0E0E0, 0xFFF2F5FB, 0);
				_ripple(ssb3, 0x33E0E0E0, 0xFFF2F5FB, 0);
				_ripple(ssb4, 0x33E0E0E0, 0xFFF2F5FB, 0);
				_ripple(ssb5, 0x33E0E0E0, 0xFFF2F5FB, 0);
				ssb4.setVisibility(View.GONE);
				if (getIntent().getStringExtra("ACTION").equals("new")) {
					ssb3.setVisibility(View.GONE);
				}
				ss.setAnimationStyle(android.R.style.Animation_Dialog);
				ss.showAsDropDown(imageview4, 0,0);
				ss.setBackgroundDrawable(new BitmapDrawable());
				ssb1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						//save
						sav = true;
						_saveAndFinish();
						ss.dismiss();
					}
				});
				ssb2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						//share
						ss.dismiss();
					}
				});
				ssb3.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						//delete
						dialog.setTitle("Are you sure?");
						dialog.setMessage("Are you sure you want to delete this note? this can't be undone!");
						dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								map_data = new Gson().fromJson(data.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
								map_data.remove((int)(Double.parseDouble(getIntent().getStringExtra("position"))));
								data.edit().putString("data", new Gson().toJson(map_data)).commit();
								finish();
							}
						});
						dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						dialog.create().show();
						ss.dismiss();
					}
				});
				ssb4.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						//upload to cloud
						ss.dismiss();
					}
				});
			}
		});
		
		edittext2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				c1.setTimeInMillis((long)(Double.parseDouble(new_date)));
				textview1.setText(new SimpleDateFormat("hh:mm aa").format(c1.getTime()).concat(" | ".concat(String.valueOf((long)(_charSeq.length())))));
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});

		imageview3.setOnClickListener(view -> {
			FileUtil.writeFile(FileUtil.getPackageDataDir(this).replace("/files","")+"/cache/myFile.txt", edittext1.getText().toString()+"\n"+edittext2.getText().toString());
			edittext2.setText(FileUtil.getPackageDataDir(this).replace("/files","")+"/cache/myFile.txt");
		});

	}
	
	private void initializeLogic() {
		vscroll1.setVerticalScrollBarEnabled(false);
		vscroll1.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		getWindow().setStatusBarColor(0xFFF2F5FB);
		getWindow().setNavigationBarColor(0xFFFFFFFF);
		if (getIntent().getStringExtra("ACTION").equals("new")) {
			new_date = String.valueOf((long)(c.getTimeInMillis()));
			textview1.setText(new SimpleDateFormat("HH:mm aa").format(c.getTime()).concat(" | ".concat(String.valueOf((long)(edittext2.getText().toString().length())))));
		}
		else {
			new_date = getIntent().getStringExtra("updated_on");
			edittext1.setText(getIntent().getStringExtra("title"));
			edittext2.setText(getIntent().getStringExtra("desc"));
			c1.setTimeInMillis((long)(Double.parseDouble(new_date)));
			textview1.setText(new SimpleDateFormat("hh:mm aa").format(c1.getTime()).concat(" | ".concat(String.valueOf((long)(edittext2.getText().toString().length())))));
		}
	}
	
	@Override
	public void onBackPressed() {
		_saveAndFinish();
	}
	public void _saveAndFinish() {
		View view = getCurrentFocus();
		if(view!=null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		if (!(edittext1.getText().toString().isEmpty() || edittext2.getText().toString().isEmpty())) {
			ms = String.valueOf((long)(c.getTimeInMillis()));
			if (getIntent().getStringExtra("ACTION").equals("new")) {
				map_saver = new HashMap<>();
				map_saver.put("title", edittext1.getText().toString().trim());
				map_saver.put("desc", edittext2.getText().toString().trim());
				map_saver.put("created_on", ms);
				map_saver.put("updated_on", ms);
				map_saver.put("theme", "default");
				if (data.contains("data")) {
					if (data.getString("data", "").equals("[{}]")) {
						map_data.add(map_saver);
						data.edit().putString("data", new Gson().toJson(map_data)).commit();
					}
					else {
						map_data = new Gson().fromJson(data.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						map_data.add(map_saver);
						data.edit().putString("data", new Gson().toJson(map_data)).commit();
					}
				}
				else {
					data.edit().putString("data", "[{}]").commit();
				}
			}
			else {
				map_data = new Gson().fromJson(data.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				map_saver = new HashMap<>();
				map_saver.put("title", edittext1.getText().toString().trim());
				map_saver.put("desc", edittext2.getText().toString().trim());
				map_saver.put("created_on", getIntent().getStringExtra("created_on"));
				map_saver.put("updated_on", ms);
				map_saver.put("theme", "default");
				map_data.set((int)(Double.parseDouble(getIntent().getStringExtra("position"))), map_saver);
				data.edit().putString("data", new Gson().toJson(map_data)).commit();
			}
		}
		else {
			if (sav) {
				sav = false;
			}
			else {
				setResult(100);
				finish();
			}
		}
		if (sav) {
			sav = false;
		}
		else {
			setResult(100);
			finish();
		}
	}
	
	
	public void _ripple(final View _v, final int _rippleColor, final int _backgroundColor, final double _radius) {
		int rippleColor = _rippleColor;
		int backgroundColor = _backgroundColor;
		int radius = (int) _radius;
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(backgroundColor);
		gd.setCornerRadius(radius);
		
		RippleDrawable rd = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}},new int[]{rippleColor}),gd,null);
		
		_v.setBackground(rd);
		_v.setClickable(true);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
