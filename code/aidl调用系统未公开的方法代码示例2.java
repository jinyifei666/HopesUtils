//通过Search工具找到layout,再找到id,通过id找到java代码.再在代码看源码具体实现.再通过返射和aidl调用系统的方法.
public class DemoActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	public void click(View view){
    	PackageManager pm = getPackageManager();
    	
    	try {
			Method method = PackageManager.class.getMethod("deleteApplicationCacheFiles",
					new Class[] { String.class
				,IPackageDataObserver.class
			});
			
			method.invoke(pm, new Object[]{"cn.itcast.cache",new MyObserver()});
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	private class MyObserver implements IPackageDataObserver{
		@Override
		public IBinder asBinder() {
			return null;
		}
		//方法执行成功后执行的方法
		@Override
		public void onRemoveCompleted(String packageName, boolean succeeded)
				throws RemoteException {
			System.out.println("==="+succeeded);
		}
	}
}