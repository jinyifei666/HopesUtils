//AsyncTask异步任务的用法 异步任务内部是用handler的消息传递机制实现的,
//也就是在把要准备工作执行下,再执行,执行后发message给handler再执行之后的方法.
//注意:
1.任务实例必须创建在UI线程
2.execute(Params...)必须在UI线程上调用
3.不要手动调用onPreExecute(), onPostExecute(Result), doInBackground(Params...), onProgressUpdate(Progress...)
4.这个任务只执行一次（如果执行第二次将会抛出异常）
//Handler+Thread适合进行大框架的异步处理，而asyncTask适用于小型简单的异步处理。

//需要三个参数,如果都不需要,则用Void表示
//第一个参数:是doInBackground的参数其实就是execute(fos)执行方法的参数,
//第二个参数:是doInBackground方法中调用publishProgress(这里可以是多个参数也可以传一个)传递数据(如:发布进度)给onProgressUpdate方法用
//第三个参数:是doInBackground执行后的返回值(如果需要的话),给onPostExecute方法用的.
private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	//后台线程执行前的操作,比如开一个进度条之类的
	@Override
	protected void onPreExecute() {
		.......
		super.onPreExecute();
	}
	//新线程执行的方法
     protected Long doInBackground(URL... urls) {
         int count = urls.length;
         long totalSize = 0;
         for (int i = 0; i < count; i++) {
             totalSize += Downloader.downloadFile(urls[i]);
             publishProgress((int) ((i / (float) count) * 100));//把执行进度发布给onProgressUpdate
			 publishProgress(i,i+1);//可以发布多个数据
         }
         return totalSize;
     }
	//接收进度:可变参数
     protected void onProgressUpdate(Integer... progress) {
		 //如果是多个进度数据,那么可以这样选择:
		 if(progress.length==1){
			setProgressPercent(progress[0]);
		 }else if(progress.length==2){
			 int i=progress[0];
			 int ii=progress[1];
		 }
        
     }
	//doInBackground执行完后执行的方法,如果doInBackground有返回值,那么接收返回值数据,另外做一些扫尾的工作,如关闭进度提示之类.等.
     protected void onPostExecute(Long result) {
         showDialog("Downloaded " + result + " bytes");
     }
 }
 //记住,定义好后记得执行execute()!!
  new DownloadFilesTask().execute(url1, url2, url3);