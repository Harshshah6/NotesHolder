package com.harsh.s.notes.holder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private static final int EDITOR_CODE = 9019;

    private FloatingActionButton _fab;
    public static String SEC_ANDROID_ID = "";

    private ArrayList<HashMap<String, Object>> data_map = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> tasks_map = new ArrayList<>();

    private LinearLayout head;
    private LinearLayout rootBg;
    private LinearLayout bottom;
    private ImageView menu;
    private TextView textview1;
    private ImageView imageview1;
    private LinearLayout linear1;
    private RecyclerView recyclerview1;
    private RecyclerView recyclerview_tasks;
    private TextView textview6;
    private LinearLayout chips_bg;
    private LinearLayout linear2;
    private ImageView imageview3;
    private LinearLayout home_bt;
    private LinearLayout cloud_bt;
    private TextView textview4;
    private TextView textview5;
    private ImageView imageview2;
    private TextView textview2;

    private Intent editorIntent = new Intent();
    private SharedPreferences data;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.home);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        _fab = findViewById(R.id._fab);

        head = findViewById(R.id.head);
        rootBg = findViewById(R.id.rootBg);
        bottom = findViewById(R.id.bottom);
        menu = findViewById(R.id.menu);
        textview1 = findViewById(R.id.textview1);
        imageview1 = findViewById(R.id.imageview1);
        linear1 = findViewById(R.id.linear1);
        recyclerview1 = findViewById(R.id.recyclerview1);
        recyclerview_tasks = findViewById(R.id.recyclerview_tasks);
        textview6 = findViewById(R.id.textview6);
        chips_bg = findViewById(R.id.chips_bg);
        linear2 = findViewById(R.id.linear2);
        imageview3 = findViewById(R.id.imageview3);
        home_bt = findViewById(R.id.home_bt);
        cloud_bt = findViewById(R.id.cloud_bt);
        textview4 = findViewById(R.id.textview4);
        textview5 = findViewById(R.id.textview5);
        imageview2 = findViewById(R.id.imageview2);
        textview2 = findViewById(R.id.textview2);
        data = getSharedPreferences("data", Activity.MODE_PRIVATE);

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                _startEditor(true);
            }
        });


        cloud_bt.setOnClickListener(view -> {
            home_bt.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b) {
                    this.setCornerRadius(a);
                    this.setColor(b);
                    return this;
                }
            }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (100)), 0xFFF2F5FB));
            cloud_bt.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b) {
                    this.setCornerRadius(a);
                    this.setColor(b);
                    return this;
                }
            }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (100)), 0xFFFFFFFF));
            textview2.setText("Tasks");
            recyclerview1.setVisibility(View.GONE);
            recyclerview_tasks.setVisibility(View.VISIBLE);
        });

        home_bt.setOnClickListener(view -> {
            home_bt.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b) {
                    this.setCornerRadius(a);
                    this.setColor(b);
                    return this;
                }
            }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (100)), 0xFFFFFFFF));
            cloud_bt.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b) {
                    this.setCornerRadius(a);
                    this.setColor(b);
                    return this;
                }
            }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (100)), 0xFFF2F5FB));
            textview2.setText("Notes");
            recyclerview_tasks.setVisibility(View.GONE);
            recyclerview1.setVisibility(View.VISIBLE);
        });

        imageview3.setOnClickListener(view -> {
            final View pWindow = getLayoutInflater().inflate(R.layout.home_sort_cus, null);
            final PopupWindow popupWindow = new PopupWindow(pWindow, -2,-2,true);
            {
                //views init & click events
                final LinearLayout bg = pWindow.findViewById(R.id.bg);
                final TextView b1 = pWindow.findViewById(R.id.textView),b2 = pWindow.findViewById(R.id.textView2),b3 = pWindow.findViewById(R.id.textView3);
                bg.setBackground(new GradientDrawable(){
                    public GradientDrawable getIns(){
                        setCornerRadius(getResources().getDisplayMetrics().density * 8);
                        setColor(0xFFFFFFFF);
                        setStroke(2, 0xFFE0E0E0);
                        return this;
                    }
                }.getIns());
                bg.setElevation(10f);
                _ripple(b1,0xFFFFFFFF,0xFFF2F5FB, 0);
                _ripple(b2,0xFFFFFFFF,0xFFF2F5FB, 0);
                _ripple(b3,0xFFFFFFFF,0xFFF2F5FB, 0);

                if(data.getString("UI_LISTMODE","").toString().equals("ListView Mode")){
                    b3.setText("Straggered List");
                }else{
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    b3.setText("ListView Mode");
                }

                View.OnClickListener o = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                };
                b1.setOnClickListener(o);
                b2.setOnClickListener(o);

                b3.setOnClickListener(view1 -> {
                    if(b3.getText().toString().equals("Straggered List")) {
                        recyclerview1.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        data.edit().putString("UI_LISTMODE", "Straggered List").commit();
                    }else {
                        recyclerview1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        data.edit().putString("UI_LISTMODE","ListView Mode").commit();
                    }
                    popupWindow.dismiss();
                });

            }

            popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            popupWindow.setElevation(10f);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.showAsDropDown(imageview3, -5, 0);


        });

    }

    void _ripple(View v, int backgroundColor, int rippleColor, int cornerRadius){
        GradientDrawable g = new GradientDrawable();
        g.setColor(backgroundColor);
        g.setCornerRadius((float)cornerRadius);

        RippleDrawable r = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}},new int[]{rippleColor}),g, null);
        v.setBackground(r);
        v.setClickable(true);

    }

    private void initializeLogic() {
        _fab.setBackgroundTintList(ColorStateList.valueOf(0xFFF2F5FB));

        recyclerview1.setVerticalScrollBarEnabled(false);
        recyclerview1.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        getWindow().setStatusBarColor(0xFFFFFFFF);
        getWindow().setNavigationBarColor(0xFFF2F5FB);
        chips_bg.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int a, int b) {
                this.setCornerRadius(a);
                this.setColor(b);
                return this;
            }
        }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (100)), 0xFFF2F5FB));
        home_bt.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int a, int b) {
                this.setCornerRadius(a);
                this.setColor(b);
                return this;
            }
        }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (100)), 0xFFFFFFFF));
        cloud_bt.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int a, int b) {
                this.setCornerRadius(a);
                this.setColor(b);
                return this;
            }
        }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (100)), 0xFFF2F5FB));
        textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf"), Typeface.NORMAL);
        textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf"), Typeface.NORMAL);
        textview4.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_medium.ttf"), Typeface.NORMAL);
        textview5.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_medium.ttf"), Typeface.NORMAL);
        textview6.setVisibility(View.GONE);
        recyclerview_tasks.setHasFixedSize(true);
        recyclerview_tasks.setLayoutManager(new LinearLayoutManager(this));

        if(data.getString("UI_LISTMODE","").toString().equals("ListView Mode")){
            recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        }else{
            recyclerview1.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }

        _refresh();

        //imageview1.setColorFilter(ThemeUtil.imgColor);
        SEC_ANDROID_ID = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onStart() {
        super.onStart();
        _refresh();
    }

    public void _ThemeInit() {
        //_imageview.setColorFilter(ThemeUtil.imageColor, android.graphics.PorterDuff.Mode.MULTIPLY);
        initT(getWindow().getDecorView().getRootView());
    }

    void initT(final View v) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View tv = (View) vg.getChildAt(i);
                initT(tv);
            }
        } else {
            if (v instanceof TextView) {
                TextView ftv = (TextView) v;
                if (ftv.getId() == R.id.textview2) {
                    ftv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf"), Typeface.NORMAL);
                    return;
                }
                if (ftv.getId() == R.id.textview1) {
                    ftv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf"), Typeface.NORMAL);
                } else {
                    ftv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_medium.ttf"), Typeface.NORMAL);
                }
            } else {

            }
        }
    }


    public void _startEditor(final boolean _isActionNew) {
        if (_isActionNew) {
            editorIntent.putExtra("ACTION", "new");
        } else {
            editorIntent.putExtra("ACTION", "editor");
        }
        if (recyclerview1.getVisibility() == View.VISIBLE){
            editorIntent.setClass(getApplicationContext(), EditorActivity.class);
            startActivityForResult(editorIntent, EDITOR_CODE);
        }else{
            editorNote();
        }

    }

    void editorNote(){
        BottomSheetDialog b = new BottomSheetDialog(this);
        b.setCancelable(true);
        final View tac = getLayoutInflater().inflate(R.layout.tasks_add_cus,null);
        b.setContentView(tac);

        {
            LinearLayout bg = tac.findViewById(R.id.bg);
            EditText ed = tac.findViewById(R.id.note);
            Button bt = tac.findViewById(R.id.bt);
            //ImageView pickdate = tac.findViewById(R.id.pickdate);

            tac.setBackground(new GradientDrawable(){
                public GradientDrawable getIns(){
                    setCornerRadii(new float[]{
                            12,12, //tl
                            12,12, //tr
                            0,0, //bl
                            0,0  //br
                    });
                    setColor(0xFFFFFFFF);
                    return this;
                }
            }.getIns());
            _ripple(bt, 0xFF000000, 0xFFE0E0E0, (int)getResources().getDisplayMetrics().density*12);


            bt.setOnClickListener(view ->{

                ArrayList<HashMap<String, Object>> obj = new Gson().fromJson(data.getString("dataTask",""),new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType() );
                if(!ed.getText().toString().isEmpty()){
                    HashMap<String,Object> t = new HashMap<>();
                    t.put("task",ed.getText().toString());
                    t.put("isTaskDone", "false");
                    obj.add(0,t);
                    data.edit().putString("dataTask", new Gson().toJson(obj)).commit();
                    _refresh();
                }else{
                    showMessage("Empty Text cannot be added to task.");
                }

                b.dismiss();
            });

        }
        b.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITOR_CODE) {
            _refresh();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        _refresh();
    }

    public void _refresh() {

        if (data.contains("data")) {
            if (data.getString("data", "").equals("[{}]")) {
                textview6.setVisibility(View.VISIBLE);
            } else {
                data_map = new Gson().fromJson(data.getString("data", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                recyclerview1.setAdapter(new Recyclerview1Adapter(data_map));
                if (data_map.size() == 0) {
                    textview6.setVisibility(View.VISIBLE);
                }
            }
        } else {
            data.edit().putString("data", "[{}]").commit();
            textview6.setVisibility(View.VISIBLE);
        }


        //tasks-data
        if (data.contains("dataTask")) {
            if (data.getString("dataTask", "").equals("[{}]")) {

            } else {
                tasks_map = new Gson().fromJson(data.getString("dataTask", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                recyclerview_tasks.setAdapter(new Recyclerview_tasksAdapter(tasks_map, HomeActivity.this));
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeHelper(new Recyclerview_tasksAdapter(tasks_map, HomeActivity.this)));
                itemTouchHelper.attachToRecyclerView(recyclerview_tasks);
            }
        } else {
            data.edit().putString("dataTask", "[{}]").commit();
        }

    }

    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {

        ArrayList<HashMap<String, Object>> _data;

        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.home_list_item, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
            View _view = _holder.itemView;

            final LinearLayout linear1 = _view.findViewById(R.id.linear1);
            final TextView textview1 = _view.findViewById(R.id.textview1);
            final TextView textview2 = _view.findViewById(R.id.textview2);

            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _view.setLayoutParams(_lp);
            linear1.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int a, int b, int c, int d) {
                    this.setCornerRadius(a);
                    this.setStroke(b, c);
                    this.setColor(d);
                    return this;
                }
            }.getIns((int) ThemeUtil.getDip(getApplicationContext(), (int) (8)), (int) ThemeUtil.getDip(getApplicationContext(), (int) (1)), 0xFFE0E0E0, 0xFFFFFFFF));
            textview1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_medium.ttf"), Typeface.NORMAL);
            textview2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf"), Typeface.NORMAL);
            textview1.setText(_data.get((int) _position).get("title").toString());
            textview2.setText(_data.get((int) _position).get("desc").toString());
            linear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View _view) {
                    editorIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    editorIntent.putExtra("title", _data.get((int) _position).get("title").toString());
                    editorIntent.putExtra("desc", _data.get((int) _position).get("desc").toString());
                    editorIntent.putExtra("theme", _data.get((int) _position).get("theme").toString());
                    editorIntent.putExtra("created_on", _data.get((int) _position).get("created_on").toString());
                    editorIntent.putExtra("updated_on", _data.get((int) _position).get("updated_on").toString());
                    editorIntent.putExtra("position", String.valueOf((long) (_position)));
                    _startEditor(false);
                }
            });
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }

    public class Recyclerview_tasksAdapter extends RecyclerView.Adapter<Recyclerview_tasksAdapter.ViewHolder> {

        ArrayList<HashMap<String, Object>> _data;
        Context mContext;

        public Recyclerview_tasksAdapter(ArrayList<HashMap<String, Object>> _data,Context c) {
            this._data = _data;
            mContext = c;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(R.layout.tasks_cus, null);


            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            View _view = holder.itemView;
            CheckBox chkbox = _view.findViewById(R.id.checkbox1);

            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _view.setLayoutParams(lp);

            chkbox.setText(_data.get(position).get("task").toString());
            chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    HashMap<String, Object> d = _data.get(position);
                    d.put("isTaskDone", (b ? "true" : "false"));
                    tasks_map.set(position, d);
                    data.edit().putString("dataTask", new Gson().toJson(tasks_map)).commit();

                    if (b)
                        chkbox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    else
                        chkbox.setPaintFlags(chkbox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            });

            if (Boolean.valueOf(_data.get(position).get("isTaskDone").toString())) {
                chkbox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                chkbox.setChecked(true);
            } else {
                chkbox.setPaintFlags(chkbox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                chkbox.setChecked(false);
            }

        }

        public void delete(int pos){
            tasks_map.remove(pos);
            data.edit().putString("dataTask", new Gson().toJson(tasks_map)).commit();
            _refresh();
        }

        public void refresh(){
            _refresh();
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public Context getContext() {
            return mContext;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
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
                _result.add((double) _arr.keyAt(_iIdx));
        }
        return _result;
    }

}
