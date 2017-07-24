将图片边界不可调节变为可调节 
private Bitmap copressImage(String imgPath){ 
	    File picture = new File(imgPath); 
	    Options bitmapFactoryOptions = new BitmapFactory.Options(); 
	    //下面这个设置是将图片边界不可调节变为可调节 
	    bitmapFactoryOptions.inJustDecodeBounds = true; 
	    bitmapFactoryOptions.inSampleSize = 2; 
	    int outWidth  = bitmapFactoryOptions.outWidth; 
	    int outHeight = bitmapFactoryOptions.outHeight; 
	    Bitmap bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), 
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
         
        /* 一定要重写的方法getView,传并几View对象 */ 
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
/*上面两种方式第一种直接使用边界压缩，第二种在使用边界压缩的情况下间接的使用了软引用来避免OOM，但大家都知道，这些函数在完成decode后，
最终都是通过java层的createBitmap来完成的，需要消耗更多内存如果图片多且大，这种方式还是会引用OOM异常的，不着急，有的是办法解决，
继续看，以下方式也大有妙用的：*/
图片缩放的两种方式:
1.方式一:
 InputStream is = this.getResources().openRawResource(R.drawable.pic1); 
     BitmapFactory.Options options=new BitmapFactory.Options(); 
     options.inJustDecodeBounds = false; 
     options.inSampleSize = 10;   //width，hight设为原来的十分一 
     Bitmap btp =BitmapFactory.decodeStream(is,null,options); 
if(!bmp.isRecycle() ){ 
         bmp.recycle()   //回收图片所占的内存 
         system.gc()  //提醒系统及时回收 
} 
上面代码与下面代码大家可分开使用，也可有效缓解内存问题哦...吼吼...
 2.方式二以最省内存的方式读取本地资源的图片
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
