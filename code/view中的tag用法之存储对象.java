view中的tag用法。可以用setTag()向里面存储数据，可以用getTag()取出之前存放的数据。例如： 
Java代码  
ImageView image = new ImageView(this);  
image.setTag("I am data!");  

如果要存放多条数据,可以这样用： 
Java代码  
PopupWindow p = new PopupWindow();  
anchor.setTag(p);  
image.setTag(R.id.screenFlag, anchor);  
image.setOnClickListener(previewListener);  

在其他地方就可以这样取： 
Java代码  
OnClickListener previewListener = new OnClickListener() {  
        @Override  
        public void onClick(View v) {  
            View fView = (View) v.getTag(R.id.screenFlag);  
            PopupWindow p = (PopupWindow) fView.getTag();  
            if (null != p) {  
                ((PopupWindow) fView.getTag()).dismiss();  
            }  
        }  
    }; 