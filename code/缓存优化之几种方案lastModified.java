
Android的apk可以说是作为小型应用，其中99%的应用并不是需要实时更新的，而且诟病于蜗牛般的移动网速，与服务器的数据交互是能少则少，这样用户体验才更好，这也是我们有时舍弃webview而采用json传输数据的原因之一。 
采用缓存，可以进一步大大缓解数据交互的压力，特此，我们简略列举一下缓存管理的适用环境：
1. 提供网络服务的应用
2. 数据更新不需要实时更新，但是哪怕是3-5分钟的延迟也是可以采用缓存机制。 
3. 缓存的过期时间是可以接受的(不会因为缓存带来的好处，导致某些数据因为更新不及时而影响产品的形象等)
带来的好处：
1. 服务器的压力大大减小
2. 客户端的响应速度大大变快(用户体验)
3. 客户端的数据加载出错情况大大较少，大大提高了应有的稳定性(用户体验)
4. 一定程度上可以支持离线浏览(或者说为离线浏览提供了技术支持)

一、缓存管理的方法
这里的缓存管理的原理很简：通过时间的设置来判断是否读取缓存还是重新下载。
里面会有一些细节的处理，后面会详细阐述。
基于这个原理，目前鄙人见过的两种比较常见的缓存管理方法是:数据库法和文件法。

二、数据库法缓存管理
这种方法是在下载完数据文件后，把文件的相关信息如url，路经，下载时间，过期时间等存放到数据库，下次下载的时候根据url先从数据库中查询，如果查询到当前时间并未过期，就根据路径读取本地文件，从而实现缓存的效果。
从实现上我们可以看到这种方法可以灵活存放文件的属性，进而提供了很大的扩展性，可以为其它的功能提供一定的支持；
从操作上需要创建数据库，每次查询数据库，如果过期还需要更新数据库，清理缓存的时候还需要删除数据库数据，稍显麻烦，而数据库操作不当又容易出现一系列的性能，ANR问题，实现的时候要谨慎，具体作的话，但也只是增加一个工具类或方法的事情。
还有一个问题，缓存的数据库是存放在/data/data/<package>/databases/目录下，是占用内存空间的，如果缓存累计，容易浪费内存，需要及时清理缓存。
当然这种方法从目前一些应用的实用上看，我没有发现什么问题。
本文我侧重强调第二种方法，第一种方法的实现，就此掠过。 

三、文件法缓存管理
这种方法，使用File.lastModified()方法得到文件的最后修改时间，与当前时间判断是否过期，从而实现缓存效果。
实现上只能使用这一个属性，没有为其它的功能提供技术支持的可能。
操作上倒是简单，比较时间即可。本身处理也不容易带来其它问题，代价低廉。

四、文件法缓存管理的两点说明
1. 不同类型的文件的缓存时间不一样。
笼统的说，不变文件的缓存时间是永久，变化文件的缓存时间是最大忍受不变时间。
说白点，图片文件内容是不变的，直到清理，我们是可以永远读取缓存的。
配置文件内容是可能更新的，需要设置一个可接受的缓存时间。
2. 不同环境下的缓存时间标准不一样。
无网络环境下，我们只能读取缓存文件，哪怕缓存早就过期。
WiFi网络环境下，缓存时间可以设置短一点，一是网速较快，而是流量不要钱。
移动数据流量环境下，缓存时间可以设置长一点，节省流量，就是节省金钱，而且用户体验也更好。
举个例子吧，最近本人在做的一个应用在wifi环境下的缓存时间设置为5分钟，移动数据流量下的缓存时间设置为1小时。
这个时间根据自己的实际情况来设置：数据的更新频率，数据的重要性等。

五、何时刷新
开发者一方面希望尽量读取缓存，用户一方面希望实时刷新，但是响应速度越快越好，流量消耗越少越好，是一个矛盾。
其实何时刷新我也不知道，这里我提供两点建议：
1. 数据的最长多长时间不变，对应用无大的影响。
比如，你的数据更新时间为1天，则缓存时间设置为4~8小时比较合适，一天他总会看到更新，如果你觉得你是资讯类应用，再减少，2~4小时，如果你觉得数据比较重要或者比较受欢迎，用户会经常把玩，再减少，1~2小时，依次类推。
为了保险起见，你可能需要毫无理由的再次缩减一下。
2. 提供刷新按钮。
上面说的保险起见不一定保险，最保险的方法使在相关界面提供一个刷新按钮，为缓存，为加载失败提供一次重新来过的机会，有了这个刷新按钮，我们的心也才真的放下来。

六、文件缓存法的具体实现
针对配置文件的缓存，新建了一个类ConfigCache:
 
public class ConfigCache {
    private static final String TAG = ConfigCache.class.getName();
 
    public static final int CONFIG_CACHE_MOBILE_TIMEOUT  = 3600000;  //1 hour
    public static final int CONFIG_CACHE_WIFI_TIMEOUT    = 300000;   //5 minute
 
    public static String getUrlCache(String url) {
        if (url == null) {
            return null;
        }
 
        String result = null;
        File file = new File(AppApplication.mSdcardDataDir + "/" + getCacheDecodeString(url));
        if (file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            Log.d(TAG, file.getAbsolutePath() + " expiredTime:" + expiredTime/60000 + "min");
            //1. in case the system time is incorrect (the time is turn back long ago)
            //2. when the network is invalid, you can only read the cache
            if (AppApplication.mNetWorkState != NetworkUtils.NETWORN_NONE && expiredTime < 0) {
                return null;
            }
            if(AppApplication.mNetWorkState == NetworkUtils.NETWORN_WIFI
                   && expiredTime > CONFIG_CACHE_WIFI_TIMEOUT) {
                return null;
            } else if (AppApplication.mNetWorkState == NetworkUtils.NETWORN_MOBILE
                   && expiredTime > CONFIG_CACHE_MOBILE_TIMEOUT) {
                return null;
            }
            try {
                result = FileUtils.readTextFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
 
    public static void setUrlCache(String data, String url) {
        File file = new File(AppApplication.mSdcardDataDir + "/" + getCacheDecodeString(url));
        try {
            //创建缓存数据到磁盘，就是创建文件
            FileUtils.writeTextFile(file, data);
        } catch (IOException e) {
            Log.d(TAG, "write " + file.getAbsolutePath() + " data failed!");
            e.printStackTrace();
        }
    }
 
    public static String getCacheDecodeString(String url) {
        //1. 处理特殊字符
        //2. 去除后缀名带来的文件浏览器的视图凌乱(特别是图片更需要如此类似处理，否则有的手机打开图库，全是我们的缓存图片)
        if (url != null) {
            return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
        return null;
    }
}
2.从实现上我们全面考虑了几个细节，注释已经说明，不再赘述。
      然后我们调用方法如下：
void getConfig(){
        //首先尝试读取缓存
        String cacheConfigString = ConfigCache.getUrlCache(CONFIG_URL);
        //根据结果判定是读取缓存，还是重新读取
        if (cacheConfigString != null) {
            showConfig(cacheConfigString);
        } else {
            //如果缓存结果是空，说明需要重新加载
            //缓存为空的原因可能是1.无缓存;2. 缓存过期;3.读取缓存出错
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(CONFIG_URL, new AsyncHttpResponseHandler(){
 
                @Override
                public void onSuccess(String result){
                    //成功下载，则保存到本地作为后面缓存文件
                    ConfigCache.setUrlCache(result,  CONFIG_URL);
                    //后面可以是UI更新，仅供参考
                    showConfig(result);
                }
 
                @Override
                public void onFailure(Throwable arg0) {
                    //根据失败原因，考虑是显示加载失败，还是再读取缓存
                }
 
            });
        }
    }
	　　这样配置文件既能有效缓存，又能及时更新了，同时支持离线浏览。

七、小结
     智能手机的缓存管理应用非常的普遍和需要，是提高用户体验的有效手段之一。
     当然，缓存管理一些内容没有细说，如图片缓存，缓存清理等，这些处理起来比较简单。 
	 