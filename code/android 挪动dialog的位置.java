Window mWindow = dialog.getWindow();  
WindowManager.LayoutParams lp = mWindow.getAttributes();  
lp.x = 10;   //新位置X坐标  
lp.y = -100; //新位置Y坐标  
dialog.onWindowAttributesChanged(lp);