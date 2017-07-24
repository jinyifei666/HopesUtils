1.Location服务之LocationManager

// 上次介绍了位置服务中的Geocoder，这次就来介绍一下LocationManager。LocationManager系统服务是位置服务的核心组件，
// 它提供了一系列方法来处理与位置相关的问题，包括查询上一个已知位置、注册和注销来自某个LocationProvider的周期性的
// 位置更新、注册和注销接近某个坐标时对一个已定义的Intent的触发等。今天我们就一起探讨一下LocationManager的简单应用。
// 在进入正题之前，朋友们需要了解与LocationManager相关的两个知识点：
1.provider：LocationManager获取位置信息的途径，常用的有两种：GPS和NETWORK。GPS定位更精确，缺点是只能在户外使用，耗电严重，
// 并且返回用户位置信息的速度远不能满足用户需求。NETWORK通过基站和Wi-Fi信号来获取位置信息，室内室外均可用，速度更快，耗电更少。
// 为了获取用户位置信息，我们可以使用其中一个，也可以同时使用两个。
2.LocationListener：位置监听器接口，定义了常见的provider状态变化和位置的变化的方法，我们需要实现此接口，完成自己的处理逻辑，
// 然后让LocationManager注册此监听器，完成对各种状态的监听。


public class MainActivity extends MapActivity {
	
	private MapView mapView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.getController().setZoom(17);
        
        final LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        //获取缓存中的位置信息
        Location location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
        	markCurrLocation(location);
        }
        
        final MyLocationListener listener = new MyLocationListener();
        //注册位置更新监听(最小时间间隔为5秒,最小距离间隔为5米)
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, listener);
        
        Button removeUpdates = (Button) findViewById(R.id.removeUpdates);
        removeUpdates.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//停止监听
				locMgr.removeUpdates(listener);
			}
		});
    }
    
    /**
     * 标记当前位置
     * @param location
     */
    private void markCurrLocation(Location location) {
    	mapView.removeAllViews();	//清除地图上所有标记视图
    	GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
		mapView.getController().animateTo(point);
		final MapView.LayoutParams params = new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, point, LayoutParams.BOTTOM_CENTER);
		final ImageView marker = new ImageView(MainActivity.this);
		marker.setImageResource(R.drawable.marker);
		
		marker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "hello, location manager!", Toast.LENGTH_SHORT).show();
			}
		});
		mapView.addView(marker, params);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	/**
	 * 获取手机最后一次更新到的位置.
	 * @return
	 */
	public String getLastLocation(){
		return sp.getString("lastlocation", "");
	}
	//位置监听器
	private final class MyLocationListener implements LocationListener {
		//当位置发生改变的时候调用.
		@Override
		public void onLocationChanged(Location location) {
			String longitude = "j: "+ location.getLongitude();
			String latitude = "w: "+ location.getLatitude();
			String dx = "dx: "+location.getAccuracy();
			//三个加上为地址
			Editor editor = sp.edit();
			editor.putString("lastlocation", latitude+longitude+dx);
			markCurrLocation(location);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			//Provider状态在可用、暂不可用、无服务三个状态之间直接切换时触发此函数
		}

		@Override
		public void onProviderEnabled(String provider) {
			//Provider被enable时触发此函数,比如GPS被打开
		}

		@Override
		public void onProviderDisabled(String provider) {
			//Provider被disable时触发此函数,比如GPS被关闭
		}
		
	}
}