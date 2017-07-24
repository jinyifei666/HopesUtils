文件使用时间排序:
/**
 * TODO 根据文件的最后修改时间进行排序 *
 */
classFileLastModifSort implements Comparator<File>{
    public int compare(File arg0, File arg1) {
        if (arg0.lastModified() >arg1.lastModified()) {
            return 1;
        } else if (arg0.lastModified() ==arg1.lastModified()) {
            return 0;
        } else {
            return -1;
        }
    }
}