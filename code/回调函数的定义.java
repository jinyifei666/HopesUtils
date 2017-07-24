//回调函数:就是把程序运行中产生的数据用接口的方式提交出去,具体数据用来作什么操作由调用者实现.
public class SmsUtils {
	private Context context;

	public SmsUtils(Context context) {
		this.context = context;
	}
	/**
	 *定义一个接口 ,把ui里需要的数据定义为接口里方法的参数
	 *在把接口的对象作为工具类的参数,并里工具类里调用两个接口方法,把工具类里的数据当方法的参数.
	 *使用者可以作为progressbar,也可以作其它用,这样藕合性就低了,复用性就高了.
	 * @author fada
	 *
	 */
	public interface BackUpProcessListener{
		void beforeBackup(int max);
		void onProcessUpdate(int process);
	}
	
	//提供给外部用的方法
	public void backUpSms(BackUpProcessListener listener) throws Exception{
		Uri uri = Uri.parse("content://sms/");
		XmlSerializer  serializer = Xml.newSerializer();
		serializer.setOutput(os, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "smss");
		Cursor cursor = context.getContentResolver().query(uri, new String[]{"address","date","type","body"} , null, null, null);
		//这是关健,把数据当参数,传给了抽象方法,这样调用者就能拿到数据了
		listener.beforeBackup(cursor.getCount());
		int total = 0;
		while(cursor.moveToNext()){
			//这个方法的具体代码略.......
			total++;
			//同样这里把数据当抽象方法参数
			listener.onProcessUpdate(total);
		}
		os.flush();
		os.close();
		
	}
	//外部具本实现,下表的举例是把数据给了进度条用.
	//用一个异步类实现功能.
	public void back(View v) {
		new AsyncTask<Void, Integer, Boolean>() {
			// 任务执行前
			@Override
			protected void onPreExecute() {
				pd = new ProgressDialog(ReadSmsActivity.this);
				pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pd.setTitle("提示:");
				pd.setMessage("正在备份短信....");
				pd.show();
				super.onPreExecute();
			}

			// 任务执行中
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
				//这里定义了具体实现.
					dao.backUpSms(new BackUpProcessListener() {
						@Override
						public void onProcessUpdate(int process) {
							pd.setProgress(process);
						}

						@Override
						public void beforeBackup(int max) {
							pd.setMax(max);
						}
					});
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}

			// 任务执行后
			@Override
			protected void onPostExecute(Boolean result) {
				pd.dismiss();
				if (result) {
					Toast.makeText(getApplicationContext(), "备份成功", 0).show();
				} else {
					Toast.makeText(getApplicationContext(), "备份失败", 0).show();
				}
				super.onPostExecute(result);
			}

			// 执行进度
			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

		}.execute();
	}