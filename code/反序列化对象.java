反序列化对象.对象要实现Serializeable接口 且原对象类的结构不可以变化,包名,类名都必须一样,
	File file=new File("C:/sms.dat");
	ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
	Object object = in.readObject();
	List<SmsName> sms=(List<SmsName>) object;
	for (int i = 0; i < sms.size(); i++) {
		System.out.println(sms);
	}