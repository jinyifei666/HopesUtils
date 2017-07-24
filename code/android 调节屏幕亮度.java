public void setBrightness(int level) { 
ContentResolver cr = getContentResolver(); 
Settings.System.putInt(cr, "screen_brightness", level); 
Window window = getWindow(); 
LayoutParams attributes = window.getAttributes(); 
float flevel = level; 
attributes.screenBrightness = flevel / 255; 
getWindow().setAttributes(attributes); 
} 