info
1.ActivityInfo 
ActivityInfo extends ComponentInfo extends PackageItemInfo:表示这个对象封装了minifest信息.
name属性表示:每一个条目的"android:name" 属性.


2.ApplicationInfo
public class ApplicationInfo extends PackageItemInfo implements Parcelable {

    public String taskAffinity;

    public String permission; //权限
   
    public String processName; //进程名

    public String className; //类名

    public int descriptionRes;    //

    public int theme; //样式
  
    public String manageSpaceActivityName;    
}
    public String backupAgentName;
ApplicationInfo  applicationInfo =	pm.getApplicationInfo(packName, 0);
	applicationInfo.loadIcon(pm);
	applicationInfo.loadLabel(pm).toString();
