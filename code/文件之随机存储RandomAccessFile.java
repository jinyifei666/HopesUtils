RandomAccessFile accessFile = null;
try {
	accessFile = new RandomAccessFile(tempBook.getPath(), "rw");//"rw"表示没有就创建
	accessFile.seek(chapterTemp.getWordStart());//关健方法
	String content = chapter.getChapterContent();
	byte[] bytes = content.getBytes("GBK");//可以写入字符串字符数组等等..
	accessFile.write(bytes);
	writeChapterOk=true;
	tempBook.setCurrentNumber(chapterTemp.getWordStart());
	bookService.updateChapterExist(tempBook.get_id(),chapterTemp.getChapterId(), 1);
} catch (Exception e) {
	e.printStackTrace();
}finally{
	try {
		accessFile.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}