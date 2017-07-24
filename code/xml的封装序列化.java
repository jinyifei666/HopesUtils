xml序列化的封装
1.不封装要遇到的问题:
	//问题①：学协议（上午） 五个人——2.5天
	//问题②：检验一下                                     2.5天
	//问题③：100个请求——易错
	//        抽取:不同程度抽取——样板很多
	//问题④：协议变更
		
2.封装后:		
	//抽取：抽取到以上的问题不存在了
	//一个人
	//一份代码
	//公布部分
3.例如:
<?xml version='1.0' encoding='utf-8' standalone='no' ?>
<message version="1.0">
	<header>
		<agenterid>889931</agenterid>
		<source>ivr</source>
		<compress>DES</compress>
		<messengerid>20130106075159469857</messengerid>
		<timestamp>20130106075159</timestamp>
		<digest>c87434208b1737fe0a23bd0b20fd9567</digest>//这是body的
		<transactiontype>14001</transactiontype>
		<username>1320000000</username>//这个是每个都不一样的,用户名
	</header>
	<body>
		cgYNf1rUkTlcXIFjWR1NnvH8iusYVv9RnPZwf1jwKdNUi4J/Re9gua2WYHkkzkn+5fnTxCH6QeQ7azLAVYgQTw4S8p5XqRVHdFxQBzWp5bA=//这是加密后的body内容
	</body>
</message>
1.定义最小的子标签,如:<agenterid>889931</agenterid>
/**
 * 叶子节点
 * 
 * @author Administrator
 * 
 */
public class Leaf {
	private String tagName;
	private String value;

	public Leaf(String tagName, String value) {
		super();
		this.tagName = tagName;
		this.value = value;
	}

	public Leaf(String tagName) {
		super();
		this.tagName = tagName;
	}

	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, tagName);
			// value=null
			if (StringUtils.isBlank(value)) {
				value = "";
			}
			serializer.text(value);
			serializer.endTag(null, tagName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTagName() {
		return tagName;
	}
}
2.定义header
/**
 * 头信息封装
 * 
 * @author Administrator
 * 
 */
public class Header {
	private Leaf agenterid = new Leaf("agenterid", ConstantValue.AGENTERID);
	private Leaf source = new Leaf("source", ConstantValue.SOURCE);
	private Leaf compress = new Leaf("compress", ConstantValue.COMPRESS);

	private Leaf messengerid = new Leaf("messengerid");
	private Leaf timestamp = new Leaf("timestamp");
	private Leaf digest = new Leaf("digest");

	private Leaf transactiontype = new Leaf("transactiontype");
	private Leaf username = new Leaf("username");

	public void serializer(XmlSerializer serializer, String body) {
		// 时间戳
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		// 随机数
		Random random = new Random();
		int num = random.nextInt(999999) + 1;
		// 六位随机数
		DecimalFormat format = new DecimalFormat("000000");
		String nums = format.format(num);

		messengerid.setValue(time + nums);
		timestamp.setValue(time);

		// MD5：时间戳+密码（子代理商）+body

		String md5Info = time + ConstantValue.AGENTER_PASSWORD + body;
		String md5=DigestUtils.md5Hex(md5Info);
		digest.setValue(md5);//把消息摘要传给服务器.
		
		try {
			serializer.startTag(null, "header");

			agenterid.serializer(serializer);
			source.serializer(serializer);
			compress.serializer(serializer);

			messengerid.serializer(serializer);
			timestamp.serializer(serializer);
			digest.serializer(serializer);

			transactiontype.serializer(serializer);
			username.serializer(serializer);
			serializer.endTag(null, "header");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//每个xml请求这个不一定相同
	public Leaf getTransactiontype() {
		return transactiontype;
	}

	public Leaf getUsername() {
		return username;
	}
}
3.定义body的二级子标签抽象类
/**
 * 请求信息封装
 */
public abstract class Element {
	// 所有请求用到——35
	/**
	 * 请求自身内容的序列化
	 */
	public abstract void serializer(XmlSerializer serializer);
	
	/**
	 * 每个请求标示的获取
	 * @return
	 */
	public abstract String getTransactiontype();
}
4.定义具体的子标签
/**
 * 用户登录
 * @author Administrator
 *
 */
public class UserLoginElement extends Element {
	private Leaf actpassword=new Leaf("actpassword");
	@Override
	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			actpassword.serializer(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String getTransactiontype() {
		return "14001";
	}
	public Leaf getActpassword() {
		return actpassword;
	}

}
5.定义body
/**
 * 消息体封装
 * 
 * @author Administrator
 * 
 */
public class Body {
	private List<Element> elements = new ArrayList<Element>();

	public void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "body");
			serializer.startTag(null, "elements");
			for (Element item : elements) {
				item.serializer(serializer);
			}
			serializer.endTag(null, "elements");
			serializer.endTag(null, "body");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Element> getElements() {
		return elements;
	}
	
	
	//问题一：header部分在MD5信息生成时需要用到body
	
	public String getBodyInfo()
	{
		try {
			XmlSerializer temp=Xml.newSerializer();
			StringWriter writer=new StringWriter();
			temp.setOutput(writer);
			this.serializer(temp);
			temp.flush();
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	//问题二：需要将传输用的（<elements></elements>）进行DES加密
	//DES加密：从服务器拿到DES.java
	
	public String getElementsDESInfo()
	{
		String bodyInfo = getBodyInfo();
		//<elements></elements>
		String elementsInfo=StringUtils.substringBetween(bodyInfo, "<body>", "</body>");
		//DES使用
		DES des=new DES();
		return des.authcode(elementsInfo, "DECODE", ConstantValue.DES_PASSWORD);
	}
}
6.定义整个消息体
/**
 * 消息封装
 * 
 * @author Administrator
 * 
 */
public class Message {
	private Header header = new Header();
	private Body body = new Body();

	private void serializer(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "message");
			serializer.attribute(null, "version", "1.0");

			Element element = body.getElements().get(0);

			header.getTransactiontype().setValue(element.getTransactiontype());

			header.serializer(serializer, body.getBodyInfo());// 传递<body>......</body>
			Log.i("Message", body.getBodyInfo());
			// body.serializer(serializer);
			
			serializer.startTag(null, "body");
			serializer.text(body.getElementsDESInfo());
			serializer.endTag(null, "body");
			

			serializer.endTag(null, "message");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * 向指定输入流写入xml数据
 * @param element
 * @return
 */
	public String getXml(Element element) {
		// 将请求添加到body
		body.getElements().add(element);

		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("utf-8", false);

			this.serializer(serializer);

			serializer.endDocument();

			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public Header getHeader() {
		return header;
	}
}
7.测试用法.
public void testGetXml(){
	UserLoginElement element=new UserLoginElement();
	element.getActpassword().setValue("00000000");	//设密码	
	Message message=new Message();	
	message.getHeader().getUsername().setValue("1320000000");//设用户名.
	String xml=message.getXml(element);
}

