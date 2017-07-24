/*纹理的概念
纹理定义了物体表面的结构，如花纹，图案，皱纹等等。有了纹理，模型世界才会更加丰富多彩。如一个球形模型，我们给其映射足球的纹理，这就是一个足球，给其映射地球纹理，就是一个地球。另外，如果给一个四边形映射一个墙的纹理，这边是墙，否则，我们需要一块砖一块砖的构建在本节中，我们所指的是狭义的纹理: 图像纹理(对应的有函数纹理—用数学函数来定义的纹理)。
纹理实际上是一个二维数组，其元素是一些颜色值，每一元素称之为纹理像素(texel)。 纹理对象是一个内部数据类型，存储着纹理数据。你不能直接访问纹理对象，但是可以通过一个整数的ID 来作为其句柄跟踪之。通过此句柄，你可以作为当前使用的纹理(称之为纹理绑定)，也可以从内存中删除这个纹理对象，还可以为一的纹理赋值(将一些纹理数据加载到关联的纹理中，称之为指定纹理)。
通常一个纹理映射的步骤是:
1、创建纹理对象。就是获得一个新的纹理句柄 ID.
2、指定纹理。就是将数据赋值给 ID 的纹理对象，在这一步，图像数据正式加载到了 ID 的纹理对象中。
3、设定过滤器。定义了opengl现实图像的效果，如纹理放大时的马赛克消除。
4、绑定纹理对象。就是将 ID 的纹理作为下面操作的纹理。
5、纹理映射。将已绑定纹理的数据绘制到屏幕上去，在这一步，就能看到贴图的效果了。
*/

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GLSurfaceView gsv = (GLSurfaceView) findViewById(R.id.gsv);
        gsv.setRenderer(new MyRenderer());
       
        
    }

    class MyRenderer implements Renderer{

		private int image;
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			//声明透视修正模式为速度优先
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			//声明清除屏幕使用的颜色
			gl.glClearColor(0, 0, 0, 0);
			//设置着色模式（GL10.GL_SMOOTH:平滑；GL_FLAT:单调）
			gl.glShadeModel(GL10.GL_SMOOTH);
			//设置深度缓存
			gl.glClearDepthf(1.0f);
			//开启深度测试
			gl.glEnable(GL10.GL_DEPTH_TEST);
			//设置深度测试模式
			gl.glDepthFunc(GL10.GL_LEQUAL);
			image=initTex(gl,R.drawable.a);
			getFog(gl);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// 设置OpenGL场景(就是视口)大小为屏幕大小
			gl.glViewport(0, 0, width, height);
			// 设置当前矩阵为投影矩阵
			gl.glMatrixMode(GL10.GL_PROJECTION);
			// 重置当前矩阵
			gl.glLoadIdentity();
			// 设置平接头体大小
			gl.glFrustumf(-(float) width / height, (float) width / height
			, -1, 1, 1, 10);
			//设置模式视图矩阵(视点)1.眼球(相机)的坐标值.2.观察点,向哪个点观察,3:相机的向上的方向
			GLU.gluLookAt(gl, 0, 0, 2.8f, 0, 0, 0, 0, 1, 0);
			//设置当前矩阵为模型视图矩阵
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			// 重置模型观察矩阵
			gl.glLoadIdentity();
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			//清除颜色缓冲区和深度缓存区
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
			//重设模型观察矩阵
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			//将矩阵初始化
			gl.glLoadIdentity();
			// 数组开启
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//顶点数组
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//纹理数组
			
			//定义纹理数组,以左下角为0点
			float[] texArray = new float[] {
					0, 0,
					0, 1, 
					1f, 0,
					1f, 1 };
			FloatBuffer texBuffer = getFloatBufferByArray(texArray);
			
			//定义顶点数组,以左下角为0点
			float[] vertexArray2 = new float[] { 
					-1.5f, 1f, 0f,
					-1.5f, -1f, 0f, 
					1.5f,1f, 0f,
					1.5f, -1f, 0f, };
			
			FloatBuffer fbb = getFloatBufferByArray(vertexArray2);
			
			
			//启用纹理
			gl.glEnable(GL10.GL_TEXTURE_2D); // 启用2D纹理。
			gl.glActiveTexture(image);// 激活纹理
			gl.glBindTexture(GL10.GL_TEXTURE_2D, image); // 指明启用哪个纹理
			
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer); // 设置纹理坐标数组
			//指定顶点指针
			//2:指定几维点.(2维点或3维点),有几个数表示一个点
			//GL10.GL_FLOAT:数据类型.
			//0:跨度,指定指针是否连续遍历点
			//fbb:浮点缓冲区
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fbb);
			//绘制三角形
			//GL10.GL_TRIANGLES:每三个点绘制一个三角形
			//0:从第几个点开始绘制.点的索引值
			//3:绘制点的总数
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			// 数组关闭
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
		}
		/**
		 * 转换成浮点缓冲区数据
		 * @param float[]
		 * @return FloatBuffer
		 */
		public FloatBuffer getFloatBufferByArray(float[] vertexArray2) {
			//每个浮点数对应4个字节.
			ByteBuffer ibb = ByteBuffer.allocateDirect(vertexArray2.length * 4);
			//本地顺序.(底层的访问顺序)
			ibb.order(ByteOrder.nativeOrder());
			//转换成浮点缓冲区
			FloatBuffer fbb = ibb.asFloatBuffer();
			//放置浮点数据
			fbb.put(vertexArray2);
			//定位指针
			fbb.position(0);
			return fbb;
		}
		// 图片尺寸压缩
		private Bitmap formatBitmap(Bitmap bmp, int w, int h) {

			int imgw = bmp.getWidth();
			int imgh = bmp.getHeight();
			float scale_w = (float) w / imgw;
			float scale_h = (float) h / imgh;
			Matrix m = new Matrix();
			m.setScale(scale_w, scale_h);
			Bitmap newBitmap = Bitmap.createBitmap(bmp, 0, 0, imgw, imgh, m, true);
			bmp = newBitmap;
			return bmp;
		}
	//生成图片纹理  
		private int initTex(GL10 gl, int imgId) {
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgId);


			bitmap = formatBitmap(bitmap, 512, 512);

			IntBuffer buffer = IntBuffer.allocate(1);
			// 创建纹理
			gl.glGenTextures(1, buffer);
			int tex1 = buffer.get();
			// 设置要使用的纹理
			gl.glBindTexture(GL10.GL_TEXTURE_2D, tex1);
			// 为纹理设置线性滤波
			gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_LINEAR);
			gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
					GL10.GL_LINEAR);
			// 生成纹理
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			return tex1;
		}