1.XML和JSON都使用结构化方法来标记数据，下面来做一个简单的比较。

1.用XML表示中国部分省市数据如下：

<?xml version="1.0" encoding="utf-8"?>

<country>

    <name>中国</name>

    <province>

        <name>黑龙江</name>

     <cities>

            <city>哈尔滨</city>

            <city>大庆</city>

        </cities>

    </province>

    <province>

        <name>广东</name>

        <cities>

            <city>广州</city>

            <city>深圳</city>

            <city>珠海</city>

        </cities>

    </province>

</country>

2.用JSON表示如下：

{

{name:"中国", province:[ { name:"黑龙江", cities:{ city:["哈尔滨","大庆"] },

{name:"广东", cities:{ city:["广州","深圳","珠海"] } 

}
//json读js资源文件
private List<Blog> parseJSON(InputStream inputStream) throws Exception {
		List<Blog> blogs = new ArrayList<Blog>();
		
		byte[] data = Util.read(inputStream);		// 读取服务端写回的数据
		String json = new String(data);				// 生成一个字符串
		
		JSONArray arr = new JSONArray(json);		// 将字符串封装成一个JSON数组对象
		for (int i = 0; i < arr.length(); i++) {	// 循环遍历数组
			JSONObject obj = arr.getJSONObject(i);	// 得到每一个JSON对象
			Blog blog = new Blog();
			blog.setPortrait(obj.getString("portrait"));	// 获取JSON对象中的属性, 设置给Blog对象
			blog.setName(obj.getString("name"));
			blog.setContent(obj.getString("content"));
			String pic = obj.getString("pic");
			blog.setPic(pic.equals("null") ? null : pic);
			blog.setFrom(obj.getString("from"));
			blogs.add(blog);
		}
		
		return blogs;
	}