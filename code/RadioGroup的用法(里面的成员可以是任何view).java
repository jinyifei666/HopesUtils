 
 //这里ViewGroup有向个子view就向RadioGroup添加多少个RadioButton
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < myScrollView.getChildCount(); i++) {
        	RadioButton rbtn = new RadioButton(this);
        	radioGroup.addView(rbtn);//radioGroup并不一定要添加RadioButton,这里只要是view都可以!这个可以作为一个小框架.
        	if(i==0){//且默认认第0个为选上.
        		rbtn.setChecked(true);
        	}
        	rbtn.setTag(i);//给每个RadioButton一个int型标记
        	//监听
        	rbtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						myScrollView.moveToDest((Integer) buttonView.getTag());//RadioButton改变时,滑动到对应的view
					}
				}
			});
			
		}
		//可以通过这个方法获得里面的的子view并作相应的操作.
		((RadioButton)radioGroup.getChildAt(destId)).setChecked(true);