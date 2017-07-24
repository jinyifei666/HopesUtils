£¨1£© BitMap  to   inputStream£º
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
    InputStream isBm = new ByteArrayInputStream(baos .toByteArray());
 
 £¨2£©BitMap  to   byte[]£º
  Bitmap defaultIcon = BitmapFactory.decodeStream(in);
  ByteArrayOutputStream stream = new ByteArrayOutputStream();
  defaultIcon.compress(Bitmap.CompressFormat.JPEG, 100, stream);
  byte[] bitmapdata = stream.toByteArray();
 £¨3£©Drawable  to   byte[]£º
  Drawable d; // the drawable (Captain Obvious, to the rescue!!!)
  Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
  ByteArrayOutputStream stream = new ByteArrayOutputStream();
  defaultIcon.compress(Bitmap.CompressFormat.JPEG, 100, bitmap);
  byte[] bitmapdata = stream.toByteArray();
 
£¨4£©byte[]  to  Bitmap £º
  Bitmap bitmap =BitmapFactory.decodeByteArray(byte[], 0,byte[].length);