 一般我们大家在遇到内存问题的时候常用的方式网上也有相关资料，大体如下几种：
   一：在内存引用上做些处理，常用的有软引用、强化引用、弱引用
   二：在内存中加载图片时直接在内存中做处理，如：边界压缩
   三：动态回收内存
   四：优化Dalvik虚拟机的堆内存分配
   五：自定义堆内存大小
   可是真的有这么简单吗，就用以上方式就能解决OOM了？不是的，继续来看...
   下面小马就照着上面的次序来整理下解决的几种方式，数字序号与上面对应：
   1：软引用(SoftReference)、虚引用(PhantomRefrence)、弱引用(WeakReference),这三个类是对heap中java对象的应用，通过这个三个类可以和gc做简单的交互，除了这三个以外还有一个是最常用的强引用
    1.1：强引用，例如下面代码：
Object o=new Object();       
Object o1=o;   
     上面代码中第一句是在heap堆中创建新的Object对象通过o引用这个对象，第二句是通过o建立o1到new Object()这个heap堆中的对象的引用，这两个引用都是强引用.只要存在对heap中对象的引用，gc就不会收集该对象.如果通过如下代码：
o=null;       
o1=null 
heap中对象有强可及对象、软可及对象、弱可及对象、虚可及对象和不可到达对象。应用的强弱顺序是强、软、弱、和虚。对于对象是属于哪种可及的对象，由他的最强的引用决定。如下:
String abc=new String("abc");  //1       
SoftReference<String> abcSoftRef=new SoftReference<String>(abc);  //2       
WeakReference<String> abcWeakRef = new WeakReference<String>(abc); //3       
abc=null; //4       
abcSoftRef.clear();//5    
上面的代码中：
    第一行在heap对中创建内容为“abc”的对象，并建立abc到该对象的强引用,该对象是强可及的。第二行和第三行分别建立对heap中对象的软引用和弱引用，此时heap中的对象仍是强可及的。第四行之后heap中对象不再是强可及的，变成软可及的。同样第五行执行之后变成弱可及的。
        1.2:软引用
               软引用是主要用于内存敏感的高速缓存。在jvm报告内存不足之前会清除所有的软引用，这样以来gc就有可能收集软可及的对象，可能解决内存吃紧问题，避免内存溢出。什么时候会被收集取决于gc的算法和gc运行时可用内存的大小。当gc决定要收集软引用是执行以下过程,以上面的abcSoftRef为例：
 
    1 首先将abcSoftRef的referent设置为null，不再引用heap中的new String("abc")对象。
    2 将heap中的new String("abc")对象设置为可结束的(finalizable)。
    3 当heap中的new String("abc")对象的finalize()方法被运行而且该对象占用的内存被释放， abcSoftRef被添加到它的ReferenceQueue中。
   注:对ReferenceQueue软引用和弱引用可以有可无，但是虚引用必须有，参见：
Reference(T paramT, ReferenceQueue<? super T>paramReferenceQueue)  
         被 Soft Reference 指到的对象，即使没有任何 Direct Reference，也不会被清除。一直要到 JVM 内存不足且 没有 Direct Reference 时才会清除，SoftReference 是用来设计 object-cache 之用的。如此一来 SoftReference 不但可以把对象 cache 起来，也不会造成内存不足的错误 （OutOfMemoryError）。我觉得 Soft Reference 也适合拿来实作 pooling 的技巧。 
 A obj = new A();    
Refenrence sr = new SoftReference(obj);    
   
//引用时    
if(sr!=null){    
    obj = sr.get();    
}else{    
    obj = new A();    
    sr = new SoftReference(obj);    
}    
    1.3:弱引用
                当gc碰到弱可及对象，并释放abcWeakRef的引用，收集该对象。但是gc可能需要对此运用才能找到该弱可及对象。通过如下代码可以了明了的看出它的作用：
String abc=new String("abc");       
WeakReference<String> abcWeakRef = new WeakReference<String>(abc);       
abc=null;       
System.out.println("before gc: "+abcWeakRef.get());       
System.gc();       
System.out.println("after gc: "+abcWeakRef.get());    
运行结果:    
before gc: abc    
after gc: null   
     gc收集弱可及对象的执行过程和软可及一样，只是gc不会根据内存情况来决定是不是收集该对象。如果你希望能随时取得某对象的信息，但又不想影响此对象的垃圾收集，那么你应该用 Weak Reference 来记住此对象，而不是用一般的 reference。   
 
A obj = new A();    
   
    WeakReference wr = new WeakReference(obj);    
   
    obj = null;    
   
    //等待一段时间，obj对象就会被垃圾回收   
　　...    
   
　　if (wr.get()==null) {    
　　System.out.println("obj 已经被清除了 ");    
　　} else {    
　　System.out.println("obj 尚未被清除，其信息是 "+obj.toString());   
　　}   
　　...   
}   
 
    在此例中，透过 get() 可以取得此 Reference 的所指到的对象，如果返回值为 null 的话，代表此对象已经被清除。这类的技巧，在设计 Optimizer 或 Debugger 这类的程序时常会用到，因为这类程序需要取得某对象的信息，但是不可以 影响此对象的垃圾收集。
 
     1.4:虚引用
 
     就是没有的意思，建立虚引用之后通过get方法返回结果始终为null,通过源代码你会发现,虚引用通向会把引用的对象写进referent,只是get方法返回结果为null.先看一下和gc交互的过程在说一下他的作用. 
      1.4.1 不把referent设置为null, 直接把heap中的new String("abc")对象设置为可结束的(finalizable).
      1.4.2 与软引用和弱引用不同, 先把PhantomRefrence对象添加到它的ReferenceQueue中.然后在释放虚可及的对象. 
   你会发现在收集heap中的new String("abc")对象之前,你就可以做一些其他的事情.通过以下代码可以了解他的作用.
 
import java.lang.ref.PhantomReference;       
import java.lang.ref.Reference;       
import java.lang.ref.ReferenceQueue;       
import java.lang.reflect.Field;       
      
public class Test {       
    public static boolean isRun = true;       
      
    public static void main(String[] args) throws Exception {       
        String abc = new String("abc");       
        System.out.println(abc.getClass() + "@" + abc.hashCode());       
        final ReferenceQueue referenceQueue = new ReferenceQueue<String>();       
        new Thread() {       
            public void run() {       
                while (isRun) {       
                    Object o = referenceQueue.poll();       
                    if (o != null) {       
                        try {       
                            Field rereferent = Reference.class      
                                    .getDeclaredField("referent");       
                            rereferent.setAccessible(true);       
                            Object result = rereferent.get(o);       
                            System.out.println("gc will collect:"      
                                    + result.getClass() + "@"      
                                    + result.hashCode());       
                        } catch (Exception e) {       
      
                            e.printStackTrace();       
                        }       
                    }       
                }       
            }       
        }.start();       
        PhantomReference<String> abcWeakRef = new PhantomReference<String>(abc,       
                referenceQueue);       
        abc = null;       
        Thread.currentThread().sleep(3000);       
        System.gc();       
        Thread.currentThread().sleep(3000);       
        isRun = false;       
    }       
      
} 
 
 
结果为
class java.lang.String@96354   
gc will collect:class java.lang.String@96354  好了，关于引用就讲到这，下面看2
 
   2:在内存中压缩小马做了下测试，对于少量不太大的图片这种方式可行，但太多而又大的图片小马用个笨的方式就是，先在内存中压缩，再用软引用避免OOM，两种方式代码如下，大家可参考下：
     方式一代码如下：
@SuppressWarnings("unused") 
private Bitmap copressImage(String imgPath){ 
    File picture = new File(imgPath); 
    Options bitmapFactoryOptions = new BitmapFactory.Options(); 
    //下面这个设置是将图片边界不可调节变为可调节 
    bitmapFactoryOptions.inJustDecodeBounds = true; 
    bitmapFactoryOptions.inSampleSize = 2; 
    int outWidth  = bitmapFactoryOptions.outWidth; 
    int outHeight = bitmapFactoryOptions.outHeight; 
    bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), 
         bitmapFactoryOptions); 
    float imagew = 150; 
    float imageh = 150; 
    int yRatio = (int) Math.ceil(bitmapFactoryOptions.outHeight 
            / imageh); 
    int xRatio = (int) Math 
            .ceil(bitmapFactoryOptions.outWidth / imagew); 
    if (yRatio > 1 || xRatio > 1) { 
        if (yRatio > xRatio) { 
            bitmapFactoryOptions.inSampleSize = yRatio; 
        } else { 
            bitmapFactoryOptions.inSampleSize = xRatio; 
        } 
 
    }  
    bitmapFactoryOptions.inJustDecodeBounds = false; 
    bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), 
            bitmapFactoryOptions); 
    if(bmap != null){                
        //ivwCouponImage.setImageBitmap(bmap); 
        return bmap; 
    } 
    return null; 
} 
    2.方式二代码如下:

/**   
* @Title: PhotoScanActivity.java 
* @Description: 照片预览控制类 
* @author XiaoMa   
*/ 
public class PhotoScanActivity extends Activity { 
    private Gallery gallery ; 
    private List<String> ImageList; 
    private List<String> it ; 
    private ImageAdapter adapter ;  
    private String path ; 
    private String shopType; 
    private HashMap<String, SoftReference<Bitmap>> imageCache = null; 
    private Bitmap bitmap = null; 
    private SoftReference<Bitmap> srf = null; 
     
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
        WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        setContentView(R.layout.photoscan); 
        Intent intent = this.getIntent(); 
        if(intent != null){ 
            if(intent.getBundleExtra("bundle") != null){ 
                Bundle bundle = intent.getBundleExtra("bundle"); 
                path = bundle.getString("path"); 
                shopType = bundle.getString("shopType"); 
            } 
        } 
        init(); 
    } 
     
    private void init(){ 
        imageCache = new HashMap<String, SoftReference<Bitmap>>(); 
         gallery = (Gallery)findViewById(R.id.gallery); 
         ImageList = getSD(); 
         if(ImageList.size() == 0){ 
            Toast.makeText(getApplicationContext(), "无照片，请返回拍照后再使用预览", Toast.LENGTH_SHORT).show(); 
            return ; 
         } 
         adapter = new ImageAdapter(this, ImageList); 
         gallery.setAdapter(adapter); 
         gallery.setOnItemLongClickListener(longlistener); 
    } 
 
     
    /** 
     * Gallery长按事件操作实现 
     */ 
    private OnItemLongClickListener longlistener = new OnItemLongClickListener() { 
 
        @Override 
        public boolean onItemLongClick(AdapterView<?> parent, View view, 
                final int position, long id) { 
            //此处添加长按事件删除照片实现 
            AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoScanActivity.this); 
            dialog.setIcon(R.drawable.warn); 
            dialog.setTitle("删除提示"); 
            dialog.setMessage("你确定要删除这张照片吗？"); 
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    File file = new File(it.get(position)); 
                    boolean isSuccess; 
                    if(file.exists()){ 
                        isSuccess = file.delete(); 
                        if(isSuccess){ 
                            ImageList.remove(position); 
                            adapter.notifyDataSetChanged(); 
                            //gallery.setAdapter(adapter); 
                            if(ImageList.size() == 0){ 
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.phoSizeZero), Toast.LENGTH_SHORT).show(); 
                            } 
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.phoDelSuccess), Toast.LENGTH_SHORT).show(); 
                        } 
                    } 
                } 
            }); 
            dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    dialog.dismiss(); 
                } 
            }); 
            dialog.create().show(); 
            return false; 
        } 
    }; 
     
    /** 
     * 获取SD卡上的所有图片文件 
     * @return 
     */ 
    private List<String> getSD() { 
        /* 设定目前所在路径 */ 
        File fileK ; 
        it = new ArrayList<String>(); 
        if("newadd".equals(shopType)){  
             //如果是从查看本人新增列表项或商户列表项进来时 
            fileK = new File(ApplicationData.TEMP); 
        }else{ 
            //此时为纯粹新增 
            fileK = new File(path); 
        } 
        File[] files = fileK.listFiles(); 
        if(files != null && files.length>0){ 
            for(File f : files ){ 
                if(getImageFile(f.getName())){ 
                    it.add(f.getPath()); 
                     
                     
                    Options bitmapFactoryOptions = new BitmapFactory.Options(); 
                    
                    //下面这个设置是将图片边界不可调节变为可调节 
                    bitmapFactoryOptions.inJustDecodeBounds = true; 
                    bitmapFactoryOptions.inSampleSize = 5; 
                    int outWidth  = bitmapFactoryOptions.outWidth; 
                    int outHeight = bitmapFactoryOptions.outHeight; 
                    float imagew = 150; 
                    float imageh = 150; 
                    int yRatio = (int) Math.ceil(bitmapFactoryOptions.outHeight 
                            / imageh); 
                    int xRatio = (int) Math 
                            .ceil(bitmapFactoryOptions.outWidth / imagew); 
                    if (yRatio > 1 || xRatio > 1) { 
                        if (yRatio > xRatio) { 
                            bitmapFactoryOptions.inSampleSize = yRatio; 
                        } else { 
                            bitmapFactoryOptions.inSampleSize = xRatio; 
                        } 
 
                    }  
                    bitmapFactoryOptions.inJustDecodeBounds = false; 
                     
                    bitmap = BitmapFactory.decodeFile(f.getPath(), 
                            bitmapFactoryOptions); 
                     
                    //bitmap = BitmapFactory.decodeFile(f.getPath());  
                    srf = new SoftReference<Bitmap>(bitmap); 
                    imageCache.put(f.getName(), srf); 
                } 
            } 
        } 
        return it; 
    } 
     
    /** 
     * 获取图片文件方法的具体实现  
     * @param fName 
     * @return 
     */ 
    private boolean getImageFile(String fName) { 
        boolean re; 
 
        /* 取得扩展名 */ 
        String end = fName 
                .substring(fName.lastIndexOf(".") + 1, fName.length()) 
                .toLowerCase(); 
 
        /* 按扩展名的类型决定MimeType */ 
        if (end.equals("jpg") || end.equals("gif") || end.equals("png") 
                || end.equals("jpeg") || end.equals("bmp")) { 
            re = true; 
        } else { 
            re = false; 
        } 
        return re; 
    } 
     
    public class ImageAdapter extends BaseAdapter{ 
        /* 声明变量 */ 
        int mGalleryItemBackground; 
        private Context mContext; 
        private List<String> lis; 
         
        /* ImageAdapter的构造符 */ 
        public ImageAdapter(Context c, List<String> li) { 
            mContext = c; 
            lis = li; 
            TypedArray a = obtainStyledAttributes(R.styleable.Gallery); 
            mGalleryItemBackground = a.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0); 
            a.recycle(); 
        } 
 
        /* 几定要重写的方法getCount,传回图片数目 */ 
        public int getCount() { 
            return lis.size(); 
        } 
 
        /* 一定要重写的方法getItem,传回position */ 
        public Object getItem(int position) { 
            return lis.get(position); 
        } 
 
        /* 一定要重写的方法getItemId,传并position */ 
        public long getItemId(int position) { 
            return position; 
        } 
         
        /* 几定要重写的方法getView,传并几View对象 */ 
        public View getView(int position, View convertView, ViewGroup parent) { 
            System.out.println("lis:"+lis); 
            File file = new File(it.get(position)); 
            SoftReference<Bitmap> srf = imageCache.get(file.getName()); 
            Bitmap bit = srf.get(); 
            ImageView i = new ImageView(mContext); 
            i.setImageBitmap(bit); 
            i.setScaleType(ImageView.ScaleType.FIT_XY); 
            i.setLayoutParams( new Gallery.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, 
                    WindowManager.LayoutParams.WRAP_CONTENT)); 
            return i; 
        } 
    } 
} 
    上面两种方式第一种直接使用边界压缩，第二种在使用边界压缩的情况下间接的使用了软引用来避免OOM，但大家都知道，这些函数在完成decode后，最终都是通过java层的createBitmap来完成的，需要消耗更多内存,如果图片多且大，这种方式还是会引用OOM异常的，不着急，有的是办法解决，继续看，以下方式也大有妙用的：
1. InputStream is = this.getResources().openRawResource(R.drawable.pic1); 
     BitmapFactory.Options options=new BitmapFactory.Options(); 
     options.inJustDecodeBounds = false; 
     options.inSampleSize = 10;   //width，hight设为原来的十分一 
     Bitmap btp =BitmapFactory.decodeStream(is,null,options); 
2. if(!bmp.isRecycle() ){ 
         bmp.recycle()   //回收图片所占的内存 
         system.gc()  //提醒系统及时回收 
} 
上面代码与下面代码大家可分开使用，也可有效缓解内存问题哦...吼吼...
 
    /** 这个地方大家别搞混了，为了方便小马把两个贴一起了，使用的时候记得分开使用
     * 以最省内存的方式读取本地资源的图片 
     */   
    public static Bitmap readBitMap(Context context, int resId){   
        BitmapFactory.Options opt = new BitmapFactory.Options();   
        opt.inPreferredConfig = Bitmap.Config.RGB_565;    
       opt.inPurgeable = true;   
       opt.inInputShareable = true;   
          //获取资源图片   
       InputStream is = context.getResources().openRawResource(resId);   
           return BitmapFactory.decodeStream(is,null,opt);   
   } 
3.大家可以选择在合适的地方使用以下代码动态并自行显式调用GC来回收内存：
if(bitmapObject.isRecycled()==false) //如果没有回收   
         bitmapObject.recycle();    
4.这个就好玩了，优化Dalvik虚拟机的堆内存分配，听着很强大，来看下具体是怎么一回事
对于Android平台来说，其托管层使用的Dalvik JavaVM从目前的表现来看还有很多地方可以优化处理，比如我们在开发一些大型游戏或耗资源的应用中可能考虑手动干涉GC处理，使用 dalvik.system.VMRuntime类提供的setTargetHeapUtilization方法可以增强程序堆内存的处理效率。当然具体原理我们可以参考开源工程，这里我们仅说下使用方法: 代码如下：
private final static floatTARGET_HEAP_UTILIZATION = 0.75f;  
在程序onCreate时就可以调用 
VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION); 
即可 
5.自定义我们的应用需要多大的内存，这个好暴力哇，强行设置最小内存大小，代码如下：
private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ; 
 //设置最小heap内存为6MB大小 
VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE); 
6.直接退出应用虚拟机回到桌面
protected void onDestroy() {

// TODO Auto-generated method stub

super.onDestroy();

// release application's RAM

Intent intent = new Intent(Intent.ACTION_MAIN);

intent.addCategory(Intent.CATEGORY_HOME);

intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

this.startActivity(intent);

System.exit(0);

}