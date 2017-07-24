//应用之清理SD卡,原理就是把一些SD卡是必须或常见的文件或文件夹存于数据库中,再遍历整个sd卡目录,
//去查看上面的每一个文件夹或文件是否存于数据库中,存在不删除除,不存在提示用户删除.
public class CleanSDActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setTextSize(30);
		tv.setText("SD卡清理");
		setContentView(tv);
		File file = Environment.getExternalStorageDirectory();
		File[] files = file.listFiles();
		
		for(File f: files){
			if(f.isDirectory()){
				String dirname = f.getName();
				//查询数据库 查询这个dirname是否在数据库里面存在.
				//提示用户删除sd卡上的文件.
			}
		}
	}
	public void deleteFile(File file){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f : files){
				deleteFile(f);
			}
		}else{
			file.delete();
		}
	}
}