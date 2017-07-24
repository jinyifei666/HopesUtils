加密之MD5
public class Md5Util {
	/**
	 * md5加密的方法 
	 * @param text
	 * @return
	 */
	public static String encode(String text){
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(text.getBytes());
			StringBuilder sb = new StringBuilder();
			for(byte b : result){
				int number = b&0xff; //加盐为了防止网站破解,则在编译规则自已再定义下(加盐),这样就只有你自已能解密了,但是密钥要记住.
				String hex = Integer.toHexString(number);
				if(hex.length()==1){
					sb.append("0");
				}
				sb.append(hex);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}