//挪动对话框位置
Window mWindow = dialog.getWindow();  
WindowManager.LayoutParams lp = mWindow.getAttributes();  
lp.x = 10;   //新位置X坐标  
lp.y = -100; //新位置Y坐标  
dialog.onWindowAttributesChanged(lp);

//控制对话框位置
 window =dialog.getWindow();// 　　　得到对话框的窗口．  
      WindowManager.LayoutParams wl = window.getAttributes();  
       wl.x = x;//这两句设置了对话框的位置．0为中间  
       wl.y =y;  
       wl.width =w;  
       wl.height =h;  
       wl.alpha =0.6f;// 这句设置了对话框的透明度   
