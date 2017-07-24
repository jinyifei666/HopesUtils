
/*内存保存：
在内存中保存的话，只能保存一定的量，而不能一直往里面放，需要设置数据的过期时间、LRU等算法。这里有一个方法是把常用的数据放到一个缓存中（A），不常用的放到另外一个缓存中（B）。当要获取数据时先从A中去获取，如果A中不存在那么再去B中获取。B中的数据主要是A中LRU出来的数据，这里的内存回收主要针对B内存，从而保持A中的数据可以有效的被命中。*/
private static final int HARD_CACHE_CAPACITY = 30;//定义map集合存的图片数量
1.先定义A缓存：
private final HashMap<String, Bitmap>mHardBitmapCache = new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY/ 2, 0.75f, true) {
        @Override
        protected booleanremoveEldestEntry(LinkedHashMap.Entry<String, Bitmap> eldest) {
            if (size() >HARD_CACHE_CAPACITY) {
               //当map的size大于30时，把最近不常用的key放到mSoftBitmapCache中，从而保证mHardBitmapCache的效率
               mSoftBitmapCache.put(eldest.getKey(), newSoftReference<Bitmap>(eldest.getValue()));
                return true;
            } else
                return false;
        }
    };
2.再定于B缓存：
   /**
     *当mHardBitmapCache的key大于30的时候，会根据LRU算法把最近没有被使用的key放入到这个缓存中。
     *Bitmap使用了SoftReference，当内存空间不足时，此cache中的bitmap会被垃圾回收掉
     */
    private final staticConcurrentHashMap<String, SoftReference<Bitmap>> mSoftBitmapCache =new ConcurrentHashMap<String,SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2);

3.从缓存中获取数据：
/**
     * 从缓存中获取图片
     */
    private Bitmap getBitmapFromCache(String url) {
        // 先从mHardBitmapCache缓存中获取
        synchronized (mHardBitmapCache) {
            final Bitmap bitmap =mHardBitmapCache.get(url);
            if (bitmap != null) {
                //如果找到的话，把元素移到linkedhashmap的最前面，从而保证在LRU算法中是最后被删除
                mHardBitmapCache.remove(url);
                mHardBitmapCache.put(url,bitmap);
                return bitmap;
            }
        }
        //如果mHardBitmapCache中找不到，到mSoftBitmapCache中找
        SoftReference<Bitmap>bitmapReference = mSoftBitmapCache.get(url);
        if (bitmapReference != null) {
            final Bitmap bitmap =bitmapReference.get();
            if (bitmap != null) {
                return bitmap;
            } else {
                mSoftBitmapCache.remove(url);
            }
        }
        return null;
    }