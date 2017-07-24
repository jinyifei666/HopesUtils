    /**
     * 修改文件的最后修改时间
     * @param dir
     * @param fileName
     */
    private void updateFileTime(String dir,String fileName) {
        File file = new File(dir,fileName);       
        long newModifiedTime =System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
    }