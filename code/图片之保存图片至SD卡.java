private void saveBmpToSd(Bitmap bm, Stringurl) {
        if (bm == null) {
            Log.w(TAG, " trying to savenull bitmap");
            return;
        }
         //判断sdcard上的空间
        if (FREE_SD_SPACE_NEEDED_TO_CACHE >freeSpaceOnSd()) {
            Log.w(TAG, "Low free space onsd, do not cache");
            return;
        }
        String filename =convertUrlToFileName(url);
        String dir = getDirectory(filename);
        File file = new File(dir +"/" + filename);
        try {
            file.createNewFile();
            OutputStream outStream = newFileOutputStream(file);
           bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            Log.i(TAG, "Image saved tosd");
        } catch (FileNotFoundException e) {
            Log.w(TAG,"FileNotFoundException");
        } catch (IOException e) {
            Log.w(TAG,"IOException");
        }
    }