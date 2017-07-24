
/* 实现文本复制功能 
*/ 
public static void copy(String content, Context context) { 
// 得到剪贴板管理器 
	ClipboardManager cmb = (ClipboardManager) context .getSystemService(Context.CLIPBOARD_SERVICE); 
	cmb.setText(content.trim()); 
} 
/** 
复制代码 代码如下:
* 实现粘贴功能 
*/ 
public static String paste(Context context) { 
// 得到剪贴板管理器 
ClipboardManager cmb = (ClipboardManager) context .getSystemService(Context.CLIPBOARD_SERVICE); 
return cmb.getText().toString().trim(); 
} 
