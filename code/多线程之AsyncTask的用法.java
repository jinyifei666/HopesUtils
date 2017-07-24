/*
AsyncTask的用法

      在开发Android应用时必须遵守单线程模型的原则： Android UI操作并不是线程安全的并且这些操作必须在UI线程中执行。在单线程模型中始终要记住两条法则： 
1. 不要阻塞UI线程 
2. 确保只在UI线程中访问Android UI工具包 
      当一个程序第一次启动时，Android会同时启动一个对应的主线程(Main Thread)，主线程主要负责处理与UI相关的事件，如：用户的按键事件，用户接触屏幕的事件以及屏幕绘图事件，并把相关的事件分发到对应的组件进行处理。所以主线程通常又被叫做UI线程。 
      比如说从网上获取一个网页，在一个TextView中将其源代码显示出来，这种涉及到网络操作的程序一般都是需要开一个线程完成网络访问，但是在获得页面源码后，是不能直接在网络操作线程中调用TextView.setText()的.因为其他线程中是不能直接访问主UI线程成员 。

android提供了几种在其他线程中访问UI线程的方法。 
Activity.runOnUiThread( Runnable ) 
View.post( Runnable ) 
View.postDelayed( Runnable, long ) 
Hanlder 
这些类或方法同样会使你的代码很复杂很难理解。然而当你需要实现一些很复杂的操作并需要频繁地更新UI时这会变得更糟糕。 

     为了解决这个问题，Android 1.5提供了一个工具类：AsyncTask，它使创建需要与用户界面交互的长时间运行的任务变得更简单。相对来说AsyncTask更轻量级一些，适用于简单的异步处理，不需要借助线程和Handler即可实现。 
AsyncTask是抽象类.AsyncTask定义了三种泛型类型 Params，Progress和Result。 
　　Params 启动任务执行的输入参数，比如HTTP请求的URL。 
　　Progress 后台任务执行的百分比。 
　　Result 后台执行任务最终返回的结果，比如String。 

     AsyncTask的执行分为四个步骤，每一步都对应一个回调方法，这些方法不应该由应用程序调用，开发者需要做的就是实现这些方法。 
　　1) 子类化AsyncTask 
　　2) 实现AsyncTask中定义的下面一个或几个方法 
　　   onPreExecute(), 该方法将在执行实际的后台操作前被UI thread调用。可以在该方法中做一些准备工作，如在界面上显示一个进度条。 
　　  doInBackground(Params...), 将在onPreExecute 方法执行后马上执行，该方法运行在后台线程中。这里将主要负责执行那些很耗时的后台计算工作。可以调用 publishProgress方法来更新实时的任务进度。该方法是抽象方法，子类必须实现。 
　　  onProgressUpdate(Progress...),在publishProgress方法被调用后，UI thread将调用这个方法从而在界面上展示任务的进展情况，例如通过一个进度条进行展示。 
　　  onPostExecute(Result), 在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用，后台的计算结果将通过该方法传递到UI thread. 

为了正确的使用AsyncTask类，以下是几条必须遵守的准则： 
　　1) Task的实例必须在UI thread中创建 
　　2) execute方法必须在UI thread中调用 
　　3) 不要手动的调用onPreExecute(), onPostExecute(Result)，doInBackground(Params...), onProgressUpdate(Progress...)这几个方法 
　　4) 该task只能被执行一次，否则多次调用时将会出现异常 
      doInBackground方法和onPostExecute的参数必须对应，这两个参数在AsyncTask声明的泛型参数列表中指定，第一个为doInBackground接受的参数，第二个为显示进度的参数，第第三个为doInBackground返回和onPostExecute传入的参数。


从网上获取一个网页，在一个TextView中将其源代码显示出来
*/

package test.list;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NetworkActivity extends Activity{
    private TextView message;
    private Button open;
    private EditText url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.network);
       message= (TextView) findViewById(R.id.message);
       url= (EditText) findViewById(R.id.url);
       open= (Button) findViewById(R.id.open);
       open.setOnClickListener(new View.OnClickListener() {
           public void onClick(View arg0) {
              connect();
           }
       });

    }

    private void connect() {
        PageTask task = new PageTask(this);
        task.execute(url.getText().toString());
    }


    class PageTask extends AsyncTask<String, Integer, String> {
        // 可变长的输入参数，与AsyncTask.exucute()对应
        ProgressDialog pdialog;
        public PageTask(Context context){
            pdialog = new ProgressDialog(context, 0);   
            pdialog.setButton("cancel", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int i) {
              dialog.cancel();
             }
            });
            pdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
             public void onCancel(DialogInterface dialog) {
              finish();
             }
            });
            pdialog.setCancelable(true);
            pdialog.setMax(100);
            pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pdialog.show();


        }
        @Override
        protected String doInBackground(String... params) {

            try{

               HttpClient client = new DefaultHttpClient();
               // params[0]代表连接的url
               HttpGet get = new HttpGet(params[0]);
               HttpResponse response = client.execute(get);
               HttpEntity entity = response.getEntity();
               long length = entity.getContentLength();
               InputStream is = entity.getContent();
               String s = null;
               if(is != null) {
                   ByteArrayOutputStream baos = new ByteArrayOutputStream();

                   byte[] buf = new byte[128];

                   int ch = -1;

                   int count = 0;

                   while((ch = is.read(buf)) != -1) {

                      baos.write(buf, 0, ch);

                      count += ch;

                      if(length > 0) {
                          // 如果知道响应的长度，调用publishProgress（）更新进度
                          publishProgress((int) ((count / (float) length) * 100));
                      }

                      // 让线程休眠100ms
                      Thread.sleep(100);
                   }
                   s = new String(baos.toByteArray());              }
               // 返回结果
               return s;
            } catch(Exception e) {
               e.printStackTrace();

            }

            return null;

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            // 返回HTML页面的内容
            message.setText(result);
            pdialog.dismiss(); 
        }

        @Override
        protected void onPreExecute() {
            // 任务启动，可以在这里显示一个对话框，这里简单处理
            message.setText(R.string.task_started);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // 更新进度
              System.out.println(""+values[0]);
              message.setText(""+values[0]);
              pdialog.setProgress(values[0]);
        }

     }

}

//最后需要说明AsyncTask不能完全取代线程，在一些逻辑较为复杂或者需要在后台反复执行的逻辑就可能需要线程来实现了。