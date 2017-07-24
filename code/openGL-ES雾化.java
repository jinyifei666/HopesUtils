// 雾化代码 一般把其入于Renderer的onSurfaceCreated方法中.
		public void getFog(GL10 gl) {

			float fogColor[] = { 0.5f, 0.5f, 0.5f, 1.0f }; // 雾颜色为灰白色
			int fogMode[] = { GL10.GL_EXP, GL10.GL_EXP2, GL10.GL_LINEAR };

			gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // 雾化效果明显
			gl.glFogx(GL10.GL_FOG_MODE, fogMode[2]); // 设置雾模式
			gl.glFogfv(GL10.GL_FOG_COLOR, fogColor, 0); // 雾颜色
			// 更好的外观，需求大的雾(反之为gl.glHint(GL10.GL_FOG_HINT, GL10.GL_FASTEST);)
			gl.glHint(GL10.GL_FOG_HINT, GL10.GL_NICEST);
			// 雾密度在模式为LINEAR下无效
			// gl.glFogf(GL10.GL_FOG_DENSITY, 1f);
			gl.glFogf(GL10.GL_FOG_START, 0.5f); // 开始位置
			gl.glFogf(GL10.GL_FOG_END, -0.5f); // 结束位置
			gl.glEnable(GL10.GL_FOG); // 启用雾
		}