异常捕获类如下：这个是捕获运行时异常的
//如何把一个异常变成运行时异常抛出? catch (Exception e) {  throw new RuntimeException(e); }就可以了

/** 
* @author Stay 
*      在Application中统一捕获异常，保存到文件中下次再打开时上传 
*/
public class CrashHandler implements UncaughtExceptionHandler {   
    /** 是否开启日志输出,在Debug状态下开启,  
     * 在Release状态下关闭以提示程序性能  
     * */  
    public static final boolean DEBUG = true;   
    /** 系统默认的UncaughtException处理类 */  
    private Thread.UncaughtExceptionHandler mDefaultHandler;   
    /** CrashHandler实例 */  
    private static CrashHandler INSTANCE;   
    /** 程序的Context对象 */  
//    private Context mContext;   
    /** 保证只有一个CrashHandler实例 */  
    private CrashHandler() {}   
    /** 获取CrashHandler实例 ,单例模式*/  
    public static CrashHandler getInstance() {   
        if (INSTANCE == null) {   
            INSTANCE = new CrashHandler();   
        }   
        return INSTANCE;   
    }   
    
    /**  
     * 初始化,注册Context对象,  
     * 获取系统默认的UncaughtException处理器,  
     * 设置该CrashHandler为程序的默认处理器  
     *   
     * @param ctx  
     */  
    public void init(Context ctx) {   
//        mContext = ctx;   
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();   
        Thread.setDefaultUncaughtExceptionHandler(this);   
    }   
    
    /**  
     * 当UncaughtException发生时会转入该函数来处理  
     */  
    @Override  
    public void uncaughtException(Thread thread, Throwable ex) {   
        if (!handleException(ex) && mDefaultHandler != null) {   
            //如果用户没有处理则让系统默认的异常处理器来处理   
            mDefaultHandler.uncaughtException(thread, ex);   
        } else {  //如果自己处理了异常，则不会弹出系统自带错误对话框，则需要手动退出app 
		
            //比如:这里可以自定义一个新线程弹出一个对画框,友好提示下.
			new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					new AlertDialog.Builder(mContext).setTitle("提示").setCancelable(false)
							.setMessage("程序崩溃了...").setNeutralButton("我知道了", new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									System.exit(0);//让这个对话框关闭.关清理内存.
								}
							})
							.create().show();
					Looper.loop();
				}
			}.start();
			//别忘了手动退出,自已杀死自已的线程
            android.os.Process.killProcess(android.os.Process.myPid());   
            System.exit(10);   
        }   
    }   
    
    /**  
     * 自定义错误处理,收集错误信息  
     * 发送错误报告等操作均在此完成.  
     * 开发者可以根据自己的情况来自定义异常处理逻辑  
     * @return  
     * true代表处理该异常，不再向上抛异常， 
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理， 
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出 
     */  
    private boolean handleException(final Throwable ex) {   
        if (ex == null) {   
            return false;   
        }   
//        final String msg = ex.getLocalizedMessage();   
        final StackTraceElement[] stack = ex.getStackTrace(); 
        final String message = ex.getMessage(); 
        //使用Toast来显示异常信息  ,并把异常保存在SD卡中,再上传至服务器. 
        new Thread() {   
            @Override  
            public void run() {   
		//Looper用于封装了android线程中的消息循环，默认情况下一个线程是不存在消息循环（message loop）的，
		//需要调用Looper.prepare()来给线程创建一个消息循环，调用Looper.loop()来使消息循环起作用，从消息队列里取消息，处理消息。
		
                Looper.prepare();   
//   当然也可以弹一个Toast提示:Toast.makeText(mContext, "程序出错啦:" + message, Toast.LENGTH_LONG).show();  
 
//       保存异常到SD卡中:可以只创建一个文件，以后全部往里面append然后发送，这样就会有重复的信息，个人不推荐 
                String fileName = "crash-" + System.currentTimeMillis()  + ".log";   
                File file = new File(Environment.getExternalStorageDirectory(), fileName); 
                try { 
                    FileOutputStream fos = new FileOutputStream(file,true); 
                    fos.write(message.getBytes()); 
                    for (int i = 0; i < stack.length; i++) { 
                        fos.write(stack.toString().getBytes()); 
                    } 
                    fos.flush(); 
                    fos.close(); 
                } catch (Exception e) { 
                } 
				//注：写在Looper.loop()之后的代码不会被立即执行，当调用后mHandler.getLooper().quit()后，loop才会中止，
				//其后的代码才能得以运行。Looper对象通过MessageQueue来存放消息和事件。一个线程只能有一个Looper，对应一个MessageQueue
                Looper.loop();   
            }   
    
        }.start();   
        return false;   
    }   
    
    // TODO 使用HTTP Post 发送错误报告到服务器  这里不再赘述 
//    private void postReport(File file) {   
//      在上传的时候还可以将该app的version，该手机的机型等信息一并发送的服务器， 
//      Android的兼容性众所周知，所以可能错误不是每个手机都会报错，还是有针对性的去debug比较好 
//    }   
} 



2.在Application onCreate时就注册ExceptionHandler，此后只要程序在抛异常后就能捕获到。
//因这程序一旦运行,首先就会执行这个类的onCreate方法,这个方法优先级是最高的.
public class App extends Application{ 
        @Override  
        public void onCreate() {   
            super.onCreate();   
            CrashHandler crashHandler = CrashHandler.getInstance();   
            //注册crashHandler   
            crashHandler.init(getApplicationContext());   
        }   
} 
3.在Activity中定义异常全局捕获.
public class LogActivity extends Activity { 
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main); 
        try { 
            //try可能会出异常的代码
        } catch (Exception e) { 
		//如果想要将log信息保存起来，则抛出runtime异常， 
          //注意:这里必须抛RuntimeException，如果catch后不throw就默认是自己处理了，ExceptionHandler不会捕获异常了。	
            throw new RuntimeException(e); //这样就让自定义的handler来捕获，统一将文件保存起来上传 
        } 
    } 
} 

