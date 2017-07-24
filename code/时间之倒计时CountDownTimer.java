/* 定义一个倒计时的内部类 */
		class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
			}

			@Override
			public void onFinish() {// 计时完毕时触发
				//limitlefttimeTV.setText("已下架");
				//limitlefttimeTV.setClickable(true);//设置可以点击
			}

			@Override
			public void onTick(long millisUntilFinished) {// 计时过程显示
				// limitlefttimeTV.setClickable(false);
				// limitlefttimeTV.setText(MyDateUtils.format(millisUntilFinished));
				// handler.sendEmptyMessage(0);
				// System.out.println(MyDateUtils.format(millisUntilFinished));
				
			}
		}
		cancel(),方法表示取消定时任务
		start()表示开始任务