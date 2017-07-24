常用控件 
DatePicker和DatePickerDialog的基本使用方法 
DatePicker:android内置日期控件 
创建DatePickerDialog的步骤 
1、声明一个监听器，使用匿名内部类： 
引用
OnDateSetListener onDateSetListener = new OnDateSetListener() { 
@Override 
public void onDateSet(DatePicker view, int year, int monthOfYear, 
int dayOfMonth) { 
System.out.println(year + "-" + monthOfYear + dayOfMonth); 
} 
};

2、复写onCreateDialog(int id)方法： 
Java代码  
@Override  
    protected Dialog onCreateDialog(int id) {  
        switch (id) {  
        case DATE_PICKER_ID:  
            return new DatePickerDialog(this,onDateSetListener,  
                    2012,02,20); //月份从0开始的，这里指的是三月  
        }  
        return null;  
    }  

3、在需要的时候调用showDialog方法： 
showDialog(DATE_PICKER_ID); 