Spinner 
Spinner构造下拉菜单组件 
创建一个Spinner的步骤 
1、在布局文件中声明： 
引用
<Spinner android:id="@+id/spinnerId" 
             android:layout_width="fill_parent" 
             android:layout_height="wrap_content" 
             />

2、在string.xml当中声明一个数组： 
引用
<string-array name="planets_array"> 
        <item>Mercury</item> 
        <item>Venus</item> 
        <item>Earth</item> 
        <item>Mars</item> 
    </string-array>

3、创建一ArrayAdapter: 
引用
ArrayAdapter<CharSequence> adapter = 
        ArrayAdapter.createFromResource( 
        this, R.array.planets_array, android.R.layout.simple_spinner_item); 
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

4、得到Spinner对象，并设置数据： 
Java代码  
spinner = (Spinner) findViewById(R.id.spinnerId);  
        spinner.setAdapter(adapter);  
        spinner.setPrompt("测试");  
创建监听器  
    class SpinnerOnSelectedListener implements OnItemSelectedListener {  
        @Override  
        public void onItemSelected(AdapterView<?> parent,   
                View view, int pos, long id) {  
            Toast.makeText(parent.getContext(), "The planet is " +   
              parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();   
        }  
        @Override  
        public void onNothingSelected(AdapterView<?> parent) {  
           System.out.println("onNothingSelected");           
        }  
    }  

绑定监听器 
spinner.setOnItemSelectedListener(new SpinnerOnSelectedListener()); 
ArrayAdapter的另一种用法 
除了从使用strings.xml文件当中的数组创建ArrayAdapter外可以动态创建Arraydapter 
引用
List<String> list = new ArrayList<String>(); 
        list.add("list1"); 
        list.add("list2"); 
  ArrayAdapter<CharSequence> adapter =  new ArrayAdapter(this,R.layout.item,R.id.textStringiewld,list);
