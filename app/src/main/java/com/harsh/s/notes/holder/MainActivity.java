package com.harsh.s.notes.holder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

	private static final int PERMISSON_REQ_CODE = 1;
	private Timer _timer = new Timer();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	
	private TimerTask timer;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
//		initializeLogic();
		initPermmisiion();
	}

	private void initPermmisiion() {
		if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
				|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSON_REQ_CODE);
		}else{
			initializeLogic();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode==PERMISSON_REQ_CODE){
			initPermmisiion();
		}
	}

	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);

		getWindow().setStatusBarColor(0xFFE6E6E6);
		_RoundedImage(imageview1, getDp(8), "icon_small");
		_setNavigationBarColor("#E6E6E6");

	}
	
	private void initializeLogic() {

		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						startActivity(new Intent(MainActivity.this,HomeActivity.class));
						timer.cancel();
						finish();
					}
				});
			}
		};
		_timer.schedule(timer, (int)(3000));
	}
	public int getDp(int px){
		    return( ((int)getResources().getDisplayMetrics().density) * px );
	}
	
	public void _RoundedImage(final ImageView _view, final double _radius, final String _image) {
		
		Glide.with(getApplicationContext())
		.load(getApplicationContext().getResources().getIdentifier(_image, "drawable", this.getPackageName()))
		.transform(new com.bumptech.glide.load.resource.bitmap.RoundedCorners((int)_radius))
		.into(_view);
	}
	
	
	public void _setNavigationBarColor(final String _color) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { 
					getWindow().setNavigationBarColor(Color.parseColor(_color));
		}
		
	}

}
