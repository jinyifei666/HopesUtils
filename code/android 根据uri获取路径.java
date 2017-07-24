Uri uri = data.getData();

String[] proj = { MediaStore.Images.Media.DATA };

Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);

int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

actualimagecursor.moveToFirst();

String img_path = actualimagecursor.getString(actual_image_column_index);

File file = new File(img_path);
