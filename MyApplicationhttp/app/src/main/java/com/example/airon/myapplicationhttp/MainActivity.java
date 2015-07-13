package com.example.airon.myapplicationhttp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

public class MainActivity extends Activity {

    private EditText et_name, et_pass;
    private TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.et_name);
        et_pass = (EditText) findViewById(R.id.et_pass);
        tv_result = (TextView) findViewById(R.id.tv_result);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = et_name.getText().toString();// 用户名
                String userPass = et_pass.getText().toString();// 用户密码
                //判断用户名和密码是否为空
                if (TextUtils.isEmpty(userName.trim())
                        || TextUtils.isEmpty(userPass.trim())) {
                    Toast.makeText(v.getContext(), "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    // 发送请求给服务器
                    //调用：loginByAsyncHttpClientPost(userName, userPass);
                    loginByAsyncHttpClientGet(userName, userPass);
                }
            }
        });
    }
    /**
     * 点击按钮控件触发的事件
     * @param v
     */
    public void login(View v) {
        //获取控件的id
        int id = v.getId();
        //根据id判断进行相应的处理
        switch (id) {
            case R.id.btn_login:
                // 获取控件的文本内容
                String userName = et_name.getText().toString();// 用户名
                String userPass = et_pass.getText().toString();// 用户密码
                //判断用户名和密码是否为空
                if (TextUtils.isEmpty(userName.trim())
                        || TextUtils.isEmpty(userPass.trim())) {
                    Toast.makeText(this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    // 发送请求给服务器
                    //调用：loginByAsyncHttpClientPost(userName, userPass);
                    loginByAsyncHttpClientGet(userName, userPass);
                }
                break;
        }
    }

    /**
     * 采用AsyncHttpClient的Post方式进行实现
     * @param userName
     * @param userPass
     */
    public void loginByAsyncHttpClientPost(String userName, String userPass) {
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        String url = "http://172.16.237.200:8080/video/login.do"; // 定义请求的地址
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        params.put("username", userName); // 设置请求的参数名和参数值
        params.put("userpass", userPass);// 设置请求的参数名和参数
        // 执行post方法
        client.post(url, params, new AsyncHttpResponseHandler() {
            /**
             * 成功处理的方法
             * statusCode:响应的状态码; headers:相应的头信息 比如 响应的时间，响应的服务器 ;
             * responseBody:响应内容的字节
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                if (statusCode == 200) {
                    tv_result.setText(new String(responseBody)); // 设置显示的文本
                }
            }

            /**
             * 失败处理的方法
             * error：响应失败的错误信息封装到这个异常对象中
             */
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                error.printStackTrace();// 把错误信息打印出轨迹来
            }
        });
    }

    /**
     * 采用AsyncHttpClient的Get方式进行实现
     * @param userName
     * @param userPass
     */
    public void loginByAsyncHttpClientGet(String userName, String userPass) {
        // 创建异步的客户端对象
        AsyncHttpClient client = new AsyncHttpClient();
        // 请求的地址
        //String url = "http://172.16.237.200:8080/video/login.do";
        //String url = "http://mail.163.com";
        String url = "http://www.weather.com.cn/data/sk/101010100.html";
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        params.put("username", userName); // 设置请求的参数名和参数值
        params.put("userpass", userPass);// 设置请求的参数名和参数

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.w("JsonHttpResponseHandler", "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                System.out.println("json-1-" + response);
                tv_result.setText(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                System.out.println("json-2-" + response);
                Log.w("JsonHttpResponseHandler", "onSuccess(int, Header[], JSONArray) was not overriden, but callback was received");
            }
        });
        // 发送get请求的时候 url地址 相应参数,匿名回调对象
/*        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                // 成功处理的方法
                System.out
                        .println("statusCode-------------------" + statusCode);
                // 遍历头信息
                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    System.out.println("header------------Name:"
                            + header.getName() + ",--Value:"
                            + header.getValue());
                }
                // 设置控件内容
                tv_result.setText(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                // 失败处理的方法
                error.printStackTrace();
            }
        });*/
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
            startActivity(new Intent(this, MainActivityPic.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
