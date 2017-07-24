    /**
     * 计算sdcard上的剩余空间
     * @return
     */
    private int freeSpaceOnSd() {
        StatFs stat = newStatFs(Environment.getExternalStorageDirectory() .getPath());
        double sdFreeMB = ((double)stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
        return (int) sdFreeMB;
    }