package com.example.airon.myapplicationhttp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivityPic extends Activity {

    private EditText edittext;
    private Button button1;
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pic);

        imageview = (ImageView)findViewById(R.id.iv);
        edittext = (EditText)findViewById(R.id.et);
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlpath1 = edittext.getText().toString();
                if (TextUtils.isEmpty(urlpath1.trim()))
                    urlpath1 = "http://img.zcool.cn/community/05195d554af45e00000115a8973e3f.jpg";
                urlpath1="http://b.hiphotos.baidu.com/image/pic/item/060828381f30e924596b913a4e086e061c95f7ea.jpg";
                loginByAsyncHttpClientGet(urlpath1);
            }
        });
    }

    public void loginByAsyncHttpClientGet(String url) {
        // 创建异步的客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        // 请求的地址

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                // 成功处理的方法
                System.out
                        .println("statusCode-------------------" + statusCode);
                // 遍历头信息
                if(statusCode==200){
                    BitmapFactory factory = new BitmapFactory();
                    Bitmap bitmap=factory.decodeByteArray(responseBody, 0, responseBody.length);
                    imageview.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                // 失败处理的方法
                error.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
