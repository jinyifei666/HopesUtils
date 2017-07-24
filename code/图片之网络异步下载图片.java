   /**
     * 网络异步下载图片
     */
    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private static final int IO_BUFFER_SIZE= 4 * 1024;
        private String url;
        //这里定义一个WeakReference,这样到时候就不用再创建对象了,直接用ImageView view = imageViewReference.get()得到一个ImageView
        //然后.setImageBitmap(bitmap);给image设置图片,这样方便,且不会影响内存回收.
        private final WeakReference<ImageView> imageViewReference;
        public ImageDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            
        }
        //后台方法执行完后运行
        @Override
        protected void onPostExecute(Bitmap result) {
        	Message msg=Message.obtain();
        	msg.obj=result;
        	//这里就可以用handler把对象发过去....
        }
        /**
         * 后台自运行的方法
         */
      @Override
        protected Bitmap doInBackground(String... params) {
            final AndroidHttpClient client =AndroidHttpClient.newInstance("Android");
            url = params[0];
            final HttpGet getRequest = new HttpGet(url);
            try {
                HttpResponse response =client.execute(getRequest);
                final int statusCode =response.getStatusLine().getStatusCode();
                if (statusCode !=HttpStatus.SC_OK) {
                    Log.w("LOG", "从" +url + "中下载图片时出错!,错误码:" + statusCode);
                    return null;
                }
                final HttpEntity entity =response.getEntity();
                if (entity != null) {
                    InputStream inputStream =null;
                    OutputStream outputStream =null;
                    try {
                        inputStream =entity.getContent();
                        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                        outputStream = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
                        byte[] b=new byte[1024];
                        int i=-1;
                        while ((i=inputStream.read(b))!=-1) {
                        	outputStream.write(b);
						}
                        outputStream.flush();
                        final byte[] data =dataStream.toByteArray();
                        final Bitmap bitmap =BitmapFactory.decodeByteArray(data, 0, data.length);
                        return bitmap;
                    } finally {
                        if (inputStream !=null) {
                           inputStream.close();
                        }
                        if (outputStream !=null) {
                           outputStream.close();
                        }
                       entity.consumeContent();
                    }
                }
            } catch (IOException e) {
                getRequest.abort();
                Log.w("LOG", "I/O errorwhile retrieving bitmap from " + url, e);
            } catch (IllegalStateException e) {
                getRequest.abort();
                Log.w("LOG", "Incorrect URL:" + url);
            } catch (Exception e) {
                getRequest.abort();
                Log.w("LOG", "Error whileretrieving bitmap from " + url, e);
            } finally {
                if (client != null) {
                    client.close();
                }
            }
            return null;
        }
    }
	
	外部调用:
	String url="http://....";
	ImageView imageView=new ImageView(context);
	new ImageDownloaderTask(imageView).execute(url);
	在handler中接收.