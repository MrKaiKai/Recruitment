package com.example.a25494.mydear;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView Expresstype,Expresstype_num,E_Show ;
    private AutoCompleteTextView Expresstype_v ,Expresstype_num_v;
    private static final int GET = 1;
    public OkHttpClient client = new OkHttpClient();
    private String str = null,Str_show = null;
    private ArrayAdapter<String> adapter;

    public  int flag = 0;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET:
                    String_msg((String) msg.obj);

                    E_Show.setText(Str_show);
                    break;
                case 2:

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Expresstype = findViewById(R.id.E_type);
        Expresstype_v = findViewById(R.id.E_v);
        Expresstype_num = findViewById(R.id.ExpressNum);
        Expresstype_num_v = findViewById(R.id.ExpressNum_v);
        E_Show = findViewById(R.id.E_Show);
        E_Show.setMovementMethod(ScrollingMovementMethod.getInstance());
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getDataSource());
        Expresstype_v.setAdapter(adapter);

        findViewById(R.id.E_ok).setOnClickListener(listener_Dear);
        }

        private  void GetData(){
        new Thread(){
            @Override
            public void run() {
                super.run();

                Log.e("TAG", "GetData"+ "running");

                    try {
                        String result = get(str);
                        Log.e("发送请求1",str);
                        Log.e("TAG", result);

                        Message msg = Message.obtain();
                        msg.what = GET;
                        msg.obj = result;
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }.start();
        }

    public List<String> getDataSource() {
        List<String> list = new ArrayList<String>();
        list.add("申通");
        list.add("EMS");
        list.add("顺丰");
        list.add("圆通");
        list.add("中通");
        list.add("韵达");
        list.add("天天");
        list.add("汇通");
        list.add("全峰");
        list.add("德邦");
        list.add("宅急送");
        return list;
    }

    private View.OnClickListener listener_Dear =new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            String str_type = null;
            String text = Expresstype_v.getText().toString();
            String num = Expresstype_num_v.getText().toString();
            switch(v.getId())
            {
                case R.id.E_ok:

                    switch(text) {
                        case "申通快递":
                        case "申通":
                            str_type = "shentong";
                            break;
                        case "EMS":
                            str_type = "ems";
                            break;
                        case "顺丰":
                        case "顺丰快递":
                            str_type = "shunfeng";
                            break;
                        case "圆通":
                            str_type = "yuantong";
                            break;
                        case "中通":
                            str_type = "zhongtong";
                            break;
                        case "韵达":
                            str_type = "yunda";
                            break;
                        case "天天":
                            str_type = "tiantian";
                            break;
                        case "汇通":
                            str_type = "huitongkuaidi";
                            break;
                        case "全峰":
                            str_type = "quanfengkuaidi";
                            break;
                        case "德邦":
                            str_type = "debangwuliu";
                            break;
                        case "宅急送":
                            str_type = "zhaijisong";
                            break;

                        default:
                            break;
                    }

                    str = String.format("http://www.kuaidi100.com/query?type=%s&postid=%s",str_type,num);

                    Log.e("URL1", str);

                    GetData();

                    break;

                default:
                    break;
            }
        }
    };

    public  void String_msg(String str_v)
    {
        if (str_v != null){
            try{
                JSONObject jsonObject = new JSONObject(str_v);
                String data = jsonObject.optString("data");
//                this.Str_show = jsonObject.optString("data");
                JSONArray jsonArray = new JSONArray(data);
                this.Str_show = "";
                for(int i = 0;i < jsonArray.length();i++)
                {
                    JSONObject item_Object = (JSONObject) jsonArray.get(i);
                    this.Str_show += String.format("<%d> %s %s\r\n\r\n",i,item_Object.optString("ftime"),item_Object.optString("context"));
                }

                Log.e("Str_show:", Str_show);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 请求网络
     */
    private  String get(String url) throws IOException{
        Log.e("URL",url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.e("URL1",url);
        Response response = client.newCall(request).execute();
        Log.e("Response",response.toString());
        return response.body().string();
    }

}
