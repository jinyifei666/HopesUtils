//Android HttpClient基本使用方法
httpclientgetpost 
这里只介绍如何使用HttpClient发起GET或者POST请求

1.GET方式
//先将参数放入List，再对参数进行URL编码
List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
params.add(new BasicNameValuePair("param1", "中国"));
params.add(new BasicNameValuePair("param2", "value2"));

//对参数编码
String param = URLEncodedUtils.format(params, "UTF-8");

//baseUrl			
String baseUrl = "http://ubs.free4lab.com/php/method.php";

	//将URL与参数拼接
	HttpGet getMethod = new HttpGet(baseUrl + "?" + param);
	HttpParams params = new BasicHttpParams();// 
	HttpConnectionParams.setConnectionTimeout(params, 8000);   //连接超时
	HttpConnectionParams.setSoTimeout(params, 5000);   //响应超时
	getMethod.setParams(params);//设制参数
	getMethod.setHeaders(headers);//可以设请求头
	
HttpClient httpClient = new DefaultHttpClient();

try {
    HttpResponse response = httpClient.execute(getMethod); //发起GET请求

    Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码
    Log.i(TAG, "result = " + EntityUtils.toString(response.getEntity(), "utf-8"));//获取服务器响应内容
} catch (ClientProtocolException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
} catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
2.POST方式
public  static BasicHeader[] headers = new BasicHeader[10];
	static {
		headers[0] = new BasicHeader("Appkey", "12343");
		headers[1] = new BasicHeader("Udid", "");//手机串号
		headers[2] = new BasicHeader("Os", "android");//
		headers[3] = new BasicHeader("Osversion", "");//
	}
//和GET方式一样，先将参数放入List
params = new LinkedList<BasicNameValuePair>();
params.add(new BasicNameValuePair("param1", "Post方法"));
params.add(new BasicNameValuePair("param2", "第二个参数"));
			
try {
    HttpPost postMethod = new HttpPost(baseUrl);
	HttpParams params = new BasicHttpParams();// 
	HttpConnectionParams.setConnectionTimeout(params, 8000);   //连接超时
	HttpConnectionParams.setSoTimeout(params, 5000);   //响应超时
	postMethod.setParams(params);//设制参数
	postMethod.setHeaders(headers);//可以设请求头
    postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中
				
    HttpResponse response = httpClient.execute(postMethod); //执行POST方法
    Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码
    Log.i(TAG, "result = " + EntityUtils.toString(response.getEntity(), "utf-8")); //获取响应内容
				
} catch (UnsupportedEncodingException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
} catch (ClientProtocolException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
} catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}