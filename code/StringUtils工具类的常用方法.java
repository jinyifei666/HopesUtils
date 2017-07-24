StringUtils 工具类的常用方法

StringUtils 源码，使用的是commons-lang3-3.1包。
下载地址 http://commons.apache.org/lang/download_lang.cgi

以下是StringUtils的各项用法
1.空字符串检查
使用函数: StringUtils.isBlank(testString)
函数介绍: 当testString为空,长度为零或者仅由空白字符(whitespace)组成时,返回True;否则返回False
例程:
    String test = "";
    String test2 = "\n\n\t";
    String test3 = null;
    String test4 = "Test";
    System.out.println( "test blank? " + StringUtils.isBlank( test ) );
    System.out.println( "test2 blank? " + StringUtils.isBlank( test2 ) );
    System.out.println( "test3 blank? " + StringUtils.isBlank( test3 ) );
    System.out.println( "test4 blank? " + StringUtils.isBlank( test4 ) );
输出如下:
test blank? true
test2 blank? true
test3 blank? true
test4 blank? False
函数StringUtils.isNotBlank(testString)的功能与StringUtils.isBlank(testString)相反.

2.清除空白字符
使用函数: StringUtils.trimToNull(testString)
函数介绍:清除掉testString首尾的空白字符,如果仅testString全由空白字符
(whitespace)组成则返回null
例程:
    String test1 = "\t";
    String test2 = "  A  Test  ";
    String test3 = null;
    System.out.println( "test1 trimToNull: " + StringUtils.trimToNull( test1 ) );
    System.out.println( "test2 trimToNull: " + StringUtils.trimToNull( test2 ) );
    System.out.println( "test3 trimToNull: " + StringUtils.trimToNull( test3 ) );
输出如下:
test1 trimToNull: null
test2 trimToNull: A  Test
test3 trimToNull: null
注意：函数StringUtils.trim(testString)与
StringUtils.trimToNull(testString)功能类似，但testString由空白字符
(whitespace)组成时返回零长度字符串。

3.取得字符串的缩写
使用函数: StringUtils.abbreviate(testString,width)和StringUtils.abbreviate(testString,offset，width)
函数介绍:在给定的width内取得testString的缩写,当testString的长度小于width则返回原字符串.
例程:
    String test = "This is a test of the abbreviation.";
    String test2 = "Test";
    System.out.println( StringUtils.abbreviate( test, 15 ) );
    System.out.println( StringUtils.abbreviate( test, 5,15 ) );
    System.out.println( StringUtils.abbreviate( test2, 10 ) );
输出如下:
This is a te...
...is a test...
Test
4.劈分字符串
使用函数: StringUtils.split(testString,splitChars,arrayLength)
函数介绍:splitChars中可以包含一系列的字符串来劈分testString,并可以设定得
到数组的长度.注意设定长度arrayLength和劈分字符串间有抵触关系,建议一般情况下
不要设定长度.
例程:
    String input = "A b,c.d|e";
    String input2 = "Pharmacy, basketball funky";
    String[] array1 = StringUtils.split( input, " ,.|");
    String[] array2 = StringUtils.split( input2, " ,", 2 );

    System.out.println( ArrayUtils.toString( array1 ) );
    System.out.println( ArrayUtils.toString( array2 ) );
输出如下:
{A,b,c,d,e}
{Pharmacy,basketball funky}
5.查找嵌套字符串
使用函数:StringUtils.substringBetween(testString,header,tail)
函数介绍：在testString中取得header和tail之间的字符串。不存在则返回空
例程：
    String htmlContent = "ABC1234ABC4567";
    System.out.println(StringUtils.substringBetween(htmlContent, "1234", "4567"));
    System.out.println(StringUtils.substringBetween(htmlContent, "12345", "4567"));
输出如下：
    ABC
    null

6.去除尾部换行符
使用函数:StringUtils.chomp(testString)
函数介绍:去除testString尾部的换行符
例程:
    String input = "Hello\n";
    System.out.println( StringUtils.chomp( input ));
    String input2 = "Another test\r\n";
    System.out.println( StringUtils.chomp( input2 ));
输出如下:
    Hello
    Another test

7.重复字符串
使用函数:StringUtils.repeat(repeatString,count)
函数介绍:得到将repeatString重复count次后的字符串
例程:
    System.out.println( StringUtils.repeat( "*", 10));
    System.out.println( StringUtils.repeat( "China ", 5));
输出如下:
    **********
    China China China China China
其他函数:StringUtils.center( testString, count,repeatString );
函数介绍:把testString插入将repeatString重复多次后的字符串中间,得到字符串
的总长为count
例程:
    System.out.println( StringUtils.center( "China", 11,"*"));
输出如下:
    ***China***

8.颠倒字符串
使用函数:StringUtils.reverse(testString)
函数介绍:得到testString中字符颠倒后的字符串
例程:
    System.out.println( StringUtils.reverse("ABCDE"));
输出如下:
    EDCBA
9.判断字符串内容的类型
函数介绍:
StringUtils.isNumeric( testString ) :如果testString全由数字组成返回True
StringUtils.isAlpha( testString ) :如果testString全由字母组成返回True
StringUtils.isAlphanumeric( testString ) :如果testString全由数字或数字组
成返回True
StringUtils.isAlphaspace( testString )  :如果testString全由字母或空格组
成返回True
例程:
    String state = "Virginia";
    System.out.println( "Is state number? " + StringUtils.isNumeric(
state ) );
    System.out.println( "Is state alpha? " + StringUtils.isAlpha( state )
);
    System.out.println( "Is state alphanumeric? " +StringUtils.isAlphanumeric( state ) );
    System.out.println( "Is state alphaspace? " + StringUtils.isAlphaSpace( state ) );
输出如下:
    Is state number? false
    Is state alpha? true
    Is state alphanumeric? true
    Is state alphaspace? true
10.取得某字符串在另一字符串中出现的次数
使用函数:StringUtils.countMatches(testString,seqString)
函数介绍:取得seqString在testString中出现的次数,未发现则返回零
例程:
    System.out.println(StringUtils.countMatches( "Chinese People", "e"
));
输出:
    4
11.部分截取字符串
使用函数:
StringUtils.substringBetween(testString,fromString,toString ):取得两字符
之间的字符串
StringUtils.substringAfter( ):取得指定字符串后的字符串
StringUtils.substringBefore( )：取得指定字符串之前的字符串
StringUtils.substringBeforeLast( )：取得最后一个指定字符串之前的字符串
StringUtils.substringAfterLast( )：取得最后一个指定字符串之后的字符串
函数介绍：上面应该都讲明白了吧。
例程：
    String formatted = " 25 * (30,40) [50,60] | 30";
    System.out.print("N0: " + StringUtils.substringBeforeLast( formatted, "*" ) );
    System.out.print(", N1: " + StringUtils.substringBetween( formatted, "(", "," ) );
    System.out.print(", N2: " + StringUtils.substringBetween( formatted, ",", ")" ) );
    System.out.print(", N3: " + StringUtils.substringBetween( formatted, "[", "," ) );
    System.out.print(", N4: " + StringUtils.substringBetween( formatted, ",", "]" ) );
    System.out.print(", N5: " + StringUtils.substringAfterLast( formatted, "|" ) );
输出如下：
    N0:  25 , N1: 30, N2: 40, N3: 50, N4: 40) [50,60, N5:  30

1. 检查字符串是否为空：
 static boolean isBlank(CharSequence str)  判断字符串是否为空或null;
 static boolean isNotBlank(CharSequence str) 判断字符串是否非空或非null;
 StringUtils.isBlank("a");
 返回结果为: false;
2. 缩进字符串：
 static String abbreviate(String str, int maxWidth) 缩进字符串，第二个参数至少为4（包括...）
 StringUtils.abbreviate("abcdefg", 20);
 返回结果为：abcdefg （正常显示）
 StringUtils.abbreviate("abcdefg", 4);
 返回结果为：a...
3. 首字母大写：
 static String capitalize(String str) 首字母大写
 static String uncapitalize(String str)首字母小写  
 StringUtils.capitalize("abcdefg");
 返回结果：Abcdefg
4. 字符串显示在一个大字符串的位置：
 static String center(String str, int size);  默认以空格填充
 static String center(String str, int size, String padString); 其余位置字符串填充
 public static String leftPad(String str,int size); 左侧空格填充
 public static String leftPad(String str,int size,String padStr);左侧字符串填充
 public static String rightPad(String str,int size); 左侧空格填充
 public static String rightPad(String str,int size,String padStr);左侧字符串填充
 
 StringUtils.center("abcdefg", 20)；
 返回结果：      abcdefg      
 StringUtils.center("abcdefg", 20,"*_");
 返回结果：*_*_*_abcdefg*_*_*_*
 StringUtils.leftPad("abc", 10, "*");
 返回结果：*******abc
5. 重复字符串次数
 static String repeat(String str, int repeat);
 StringUtils.repeat("abc", 5); 
 返回结果：abcabcabcabcabc
6. 是否全是大写，是否全是小写（3.0版本）
 public static boolean isAllLowerCase(String str);
 public static boolean isAllUpperCase(String str);
 StringUtils.isAllLowerCase("abC");
 返回结果：false
7. 是否都是由字母组成：
 public static boolean isAlpha(String str);  只由字母组成
 public static boolean isAlphaSpace(String str); 只有字母和空格组成
 public static boolean isAlphanumeric(String str);只由字母和数字组成
 public static boolean isAlphanumericSpace(String str);只由字母数字和空格组成
 public static boolean isNumeric(String str);只由数字组成
 public static boolean isNumericSpace(String str);只由数字和空格组成
 StringUtils.isAlpha("a2bdefg");
 返回结果：false
8. 小字符串在大字符串中的匹配次数
public static int countMatches(String str,String sub);
StringUtils.countMatches("ababsssababa", "ab");
 返回结果：4
9. 字符串倒转
 public static String reverse(String str);
 StringUtils.reverse("abcdef");
 返回结果：fedcba
10. 大小写转换，空格不动
 
 public static String swapCase(String str);
 StringUtils.swapCase("I am a-A*a")
 返回结果：i AM A-a*A
StringUtils工具类的使用
一、数组转成字符串： 
1、 将数组中的字符转换为一个字符串 
将数组中的字符转换为一个字符串 

@param strToConv 要转换的字符串 ,默认以逗号分隔 
@return 返回一个字符串 
String[3] s={"a","b","c"} 
StringUtil.convString（s)="a,b,c" 
2、 static public String converString(String strToConv) 
@param strToConv 要转换的字符串 , 
@param conv 分隔符,默认以逗号分隔 
@return 同样返回一个字符串 

String[3] s={"a","b","c"} 
StringUtil.convString（s,"@")="a@b@c" 
static public String converString(String strToConv, String conv) 


二、空值检测： 
3、 

Checks if a String is empty ("") or null. 


判断一个字符串是否为空，空格作非空处理。 StringUtils.isEmpty(null) = true StringUtils.isEmpty("") = true StringUtils.isEmpty(" ") = false StringUtils.isEmpty("bob") = false StringUtils.isEmpty(" bob ") = false 

NOTE: This method changed in Lang version 2.0. 

It no longer trims the String. 
That functionality is available in isBlank(). 


@param str the String to check, may be null 
@return true if the String is empty or null 
public static boolean isEmpty(String str) 


三、非空处理： 
4、 
Checks if a String is not empty ("") and not null. 


判断一个字符串是否非空，空格作非空处理. StringUtils.isNotEmpty(null) = false StringUtils.isNotEmpty("") = false StringUtils.isNotEmpty(" ") = true StringUtils.isNotEmpty("bob") = true StringUtils.isNotEmpty(" bob ") = true 

@param str the String to check, may be null 
@return true if the String is not empty and not null 
public static boolean isNotEmpty(String str) 

5、 

Checks if a String is not empty (""), not null and not whitespace only. 


判断一个字符串是否非空，空格作空处理. StringUtils.isNotBlank(null) = false StringUtils.isNotBlank("") = false StringUtils.isNotBlank(" ") = false StringUtils.isNotBlank("bob") = true StringUtils.isNotBlank(" bob ") = true 

@param str the String to check, may be null 
@return true if the String is 
not empty and not null and not whitespace 
@since 2.0 
public static boolean isNotBlank(String str) 


四、 空格处理 
6、 
Removes control characters (char <= 32) from both 

ends of this String, handling null by returning 
null. 


The String is trimmed using {@link String#trim()}. 

Trim removes start and end characters <= 32. 
To strip whitespace use {@link //strip(String)}. 


To trim your choice of characters, use the 

{@link //strip(String, String)} methods. 


格式化一个字符串中的空格，有非空判断处理； StringUtils.trim(null) = null StringUtils.trim("") = "" StringUtils.trim(" ") = "" StringUtils.trim("abc") = "abc" StringUtils.trim(" abc ") = "abc" 

@param str the String to be trimmed, may be null 
@return the trimmed string, null if null String input 
public static String trim(String str) 

7、 


Removes control characters (char <= 32) from both 

ends of this String returning null if the String is 
empty ("") after the trim or if it is null. 

The String is trimmed using {@link String#trim()}. 

Trim removes start and end characters <= 32. 
To strip whitespace use {@link /stripToNull(String)}. 


格式化一个字符串中的空格，有非空判断处理，如果为空返回null； StringUtils.trimToNull(null) = null StringUtils.trimToNull("") = null StringUtils.trimToNull(" ") = null StringUtils.trimToNull("abc") = "abc" StringUtils.trimToNull(" abc ") = "abc" 

@param str the String to be trimmed, may be null 
@return the trimmed String, 
null if only chars <= 32, empty or null String input 
@since 2.0 
public static String trimToNull(String str) 

8、 


Removes control characters (char <= 32) from both 

ends of this String returning an empty String ("") if the String 
is empty ("") after the trim or if it is null. 

The String is trimmed using {@link String#trim()}. 

Trim removes start and end characters <= 32. 
To strip whitespace use {@link /stripToEmpty(String)}. 


格式化一个字符串中的空格，有非空判断处理，如果为空返回""； StringUtils.trimToEmpty(null) = "" StringUtils.trimToEmpty("") = "" StringUtils.trimToEmpty(" ") = "" StringUtils.trimToEmpty("abc") = "abc" StringUtils.trimToEmpty(" abc ") = "abc" 

@param str the String to be trimmed, may be null 
@return the trimmed String, or an empty String if null input 
@since 2.0 
public static String trimToEmpty(String str) 


五、 字符串比较: 
9、 
Compares two Strings, returning true if they are equal. 


nulls are handled without exceptions. Two null 

references are considered to be equal. The comparison is case sensitive. 


判断两个字符串是否相等，有非空处理。 StringUtils.equals(null, null) = true StringUtils.equals(null, "abc") = false StringUtils.equals("abc", null) = false StringUtils.equals("abc", "abc") = true StringUtils.equals("abc", "ABC") = false 

@param str1 the first String, may be null 
@param str2 the second String, may be null 
@return true if the Strings are equal, case sensitive, or 
both null 
@see java.lang.String#equals(Object) 
public static boolean equals(String str1, String str2) 


10、 

Compares two Strings, returning true if they are equal ignoring 

the case. 


nulls are handled without exceptions. Two null 

references are considered equal. Comparison is case insensitive. 


判断两个字符串是否相等，有非空处理。忽略大小写 StringUtils.equalsIgnoreCase(null, null) = true StringUtils.equalsIgnoreCase(null, "abc") = false StringUtils.equalsIgnoreCase("abc", null) = false StringUtils.equalsIgnoreCase("abc", "abc") = true StringUtils.equalsIgnoreCase("abc", "ABC") = true 

@param str1 the first String, may be null 
@param str2 the second String, may be null 
@return true if the Strings are equal, case insensitive, or 
both null 
@see java.lang.String#equalsIgnoreCase(String) 
public static boolean equalsIgnoreCase(String str1, String str2) 


六、 IndexOf 处理 
11、 


Finds the first index within a String, handling null. 

This method uses {@link String#indexOf(String)}. 


A null String will return -1. 


返回要查找的字符串所在位置，有非空处理 StringUtils.indexOf(null, *) = -1 StringUtils.indexOf(*, null) = -1 StringUtils.indexOf("", "") = 0 StringUtils.indexOf("aabaabaa", "a") = 0 StringUtils.indexOf("aabaabaa", "b") = 2 StringUtils.indexOf("aabaabaa", "ab") = 1 StringUtils.indexOf("aabaabaa", "") = 0 

@param str the String to check, may be null 
@param searchStr the String to find, may be null 
@return the first index of the search String, 
-1 if no match or null string input 
@since 2.0 
public static int indexOf(String str, String searchStr) 

12、 

Finds the first index within a String, handling null. 

This method uses {@link String#indexOf(String, int)}. 


A null String will return -1. 

A negative start position is treated as zero. 
An empty ("") search String always matches. 
A start position greater than the string length only matches 
an empty search String. 


返回要由指定位置开始查找的字符串所在位置，有非空处理 StringUtils.indexOf(null, *, *) = -1 StringUtils.indexOf(*, null, *) = -1 StringUtils.indexOf("", "", 0) = 0 StringUtils.indexOf("aabaabaa", "a", 0) = 0 StringUtils.indexOf("aabaabaa", "b", 0) = 2 StringUtils.indexOf("aabaabaa", "ab", 0) = 1 StringUtils.indexOf("aabaabaa", "b", 3) = 5 StringUtils.indexOf("aabaabaa", "b", 9) = -1 StringUtils.indexOf("aabaabaa", "b", -1) = 2 StringUtils.indexOf("aabaabaa", "", 2) = 2 StringUtils.indexOf("abc", "", 9) = 3 

@param str the String to check, may be null 
@param searchStr the String to find, may be null 
@param startPos the start position, negative treated as zero 
@return the first index of the search String, 
-1 if no match or null string input 
@since 2.0 
public static int indexOf(String str, String searchStr, int startPos) 


七、 子字符串处理： 
13、 
Gets a substring from the specified String avoiding exceptions. 


A negative start position can be used to start n 

characters from the end of the String. 


A null String will return null. 

An empty ("") String will return "". 


返回指定位置开始的字符串中的所有字符 StringUtils.substring(null, *) = null StringUtils.substring("", *) = "" StringUtils.substring("abc", 0) = "abc" StringUtils.substring("abc", 2) = "c" StringUtils.substring("abc", 4) = "" StringUtils.substring("abc", -2) = "bc" StringUtils.substring("abc", -4) = "abc" 

@param str the String to get the substring from, may be null 
@param start the position to start from, negative means 
count back from the end of the String by this many characters 
@return substring from start position, null if null String input 
public static String substring(String str, int start) 

14、 

Gets a substring from the specified String avoiding exceptions. 


A negative start position can be used to start/end n 

characters from the end of the String. 


The returned substring starts with the character in the start 

position and ends before the end position. All postion counting is 
zero-based -- i.e., to start at the beginning of the string use 
start = 0. Negative start and end positions can be used to 
specify offsets relative to the end of the String. 


If start is not strictly to the left of end, "" 

is returned. 


返回由开始位置到结束位置之间的子字符串 StringUtils.substring(null, *, *) = null StringUtils.substring("", * , *) = ""; StringUtils.substring("abc", 0, 2) = "ab" StringUtils.substring("abc", 2, 0) = "" StringUtils.substring("abc", 2, 4) = "c" StringUtils.substring("abc", 4, 6) = "" StringUtils.substring("abc", 2, 2) = "" StringUtils.substring("abc", -2, -1) = "b" StringUtils.substring("abc", -4, 2) = "ab" 

@param str the String to get the substring from, may be null 
@param start the position to start from, negative means 
count back from the end of the String by this many characters 
@param end the position to end at (exclusive), negative means 
count back from the end of the String by this many characters 
@return substring from start position to end positon, 
null if null String input 
public static String substring(String str, int start, int end) 


15、 SubStringAfter/SubStringBefore（前后子字符串处理： 


Gets the substring before the first occurance of a separator. 

The separator is not returned. 


A null string input will return null. 

An empty ("") string input will return the empty string. 
A null separator will return the input string. 


返回指定字符串之前的所有字符 StringUtils.substringBefore(null, *) = null StringUtils.substringBefore("", *) = "" StringUtils.substringBefore("abc", "a") = "" StringUtils.substringBefore("abcba", "b") = "a" StringUtils.substringBefore("abc", "c") = "ab" StringUtils.substringBefore("abc", "d") = "abc" StringUtils.substringBefore("abc", "") = "" StringUtils.substringBefore("abc", null) = "abc" 

@param str the String to get a substring from, may be null 
@param separator the String to search for, may be null 
@return the substring before the first occurance of the separator, 
null if null String input 
@since 2.0 
public static String substringBefore(String str, String separator) 

16、 

Gets the substring after the first occurance of a separator. 

The separator is not returned. 


A null string input will return null. 

An empty ("") string input will return the empty string. 
A null separator will return the empty string if the 
input string is not null. 


返回指定字符串之后的所有字符 StringUtils.substringAfter(null, *) = null StringUtils.substringAfter("", *) = "" StringUtils.substringAfter(*, null) = "" StringUtils.substringAfter("abc", "a") = "bc" StringUtils.substringAfter("abcba", "b") = "cba" StringUtils.substringAfter("abc", "c") = "" StringUtils.substringAfter("abc", "d") = "" StringUtils.substringAfter("abc", "") = "abc" 

@param str the String to get a substring from, may be null 
@param separator the String to search for, may be null 
@return the substring after the first occurance of the separator, 
null if null String input 
@since 2.0 
public static String substringAfter(String str, String separator) 

17、 

Gets the substring before the last occurance of a separator. 

The separator is not returned. 


A null string input will return null. 

An empty ("") string input will return the empty string. 
An empty or null separator will return the input string. 


返回最后一个指定字符串之前的所有字符 StringUtils.substringBeforeLast(null, *) = null StringUtils.substringBeforeLast("", *) = "" StringUtils.substringBeforeLast("abcba", "b") = "abc" StringUtils.substringBeforeLast("abc", "c") = "ab" StringUtils.substringBeforeLast("a", "a") = "" StringUtils.substringBeforeLast("a", "z") = "a" StringUtils.substringBeforeLast("a", null) = "a" StringUtils.substringBeforeLast("a", "") = "a" 

@param str the String to get a substring from, may be null 
@param separator the String to search for, may be null 
@return the substring before the last occurance of the separator, 
null if null String input 
@since 2.0 
public static String substringBeforeLast(String str, String separator) 

18、 

Gets the substring after the last occurance of a separator. 

The separator is not returned. 


A null string input will return null. 

An empty ("") string input will return the empty string. 
An empty or null separator will return the empty string if 
the input string is not null. 


返回最后一个指定字符串之后的所有字符 StringUtils.substringAfterLast(null, *) = null StringUtils.substringAfterLast("", *) = "" StringUtils.substringAfterLast(*, "") = "" StringUtils.substringAfterLast(*, null) = "" StringUtils.substringAfterLast("abc", "a") = "bc" StringUtils.substringAfterLast("abcba", "b") = "a" StringUtils.substringAfterLast("abc", "c") = "" StringUtils.substringAfterLast("a", "a") = "" StringUtils.substringAfterLast("a", "z") = "" 

@param str the String to get a substring from, may be null 
@param separator the String to search for, may be null 
@return the substring after the last occurance of the separator, 
null if null String input 
@since 2.0 
public static String substringAfterLast(String str, String separator) 


八、 Replacing（字符串替换） 
19、 
Replaces all occurances of a String within another String. 


A null reference passed to this method is a no-op. 


以指定字符串替换原来字符串的的指定字符串 StringUtils.replace(null, *, *) = null StringUtils.replace("", *, *) = "" StringUtils.replace("aba", null, null) = "aba" StringUtils.replace("aba", null, null) = "aba" StringUtils.replace("aba", "a", null) = "aba" StringUtils.replace("aba", "a", "") = "aba" StringUtils.replace("aba", "a", "z") = "zbz" 

@param text text to search and replace in, may be null 
@param repl the String to search for, may be null 
@param with the String to replace with, may be null 
@return the text with any replacements processed, 
null if null String input 
@see #replace(String text, String repl, String with, int max) 
public static String replace(String text, String repl, String with) 

20、 

Replaces a String with another String inside a larger String, 

for the first max values of the search String. 


A null reference passed to this method is a no-op. 


以指定字符串最大替换原来字符串的的指定字符串 StringUtils.replace(null, *, *, *) = null StringUtils.replace("", *, *, *) = "" StringUtils.replace("abaa", null, null, 1) = "abaa" StringUtils.replace("abaa", null, null, 1) = "abaa" StringUtils.replace("abaa", "a", null, 1) = "abaa" StringUtils.replace("abaa", "a", "", 1) = "abaa" StringUtils.replace("abaa", "a", "z", 0) = "abaa" StringUtils.replace("abaa", "a", "z", 1) = "zbaa" StringUtils.replace("abaa", "a", "z", 2) = "zbza" StringUtils.replace("abaa", "a", "z", -1) = "zbzz" 

@param text text to search and replace in, may be null 
@param repl the String to search for, may be null 
@param with the String to replace with, may be null 
@param max maximum number of values to replace, or -1 if no maximum 
@return the text with any replacements processed, 
null if null String input 
public static String replace(String text, String repl, String with, int max) 


九、 Case conversion（大小写转换） 
21、 

Converts a String to upper case as per {@link String#toUpperCase()}. 


A null input String returns null. 


将一个字符串变为大写 StringUtils.upperCase(null) = null StringUtils.upperCase("") = "" StringUtils.upperCase("aBc") = "ABC" 

@param str the String to upper case, may be null 
@return the upper cased String, null if null String input 
public static String upperCase(String str) 22、 

Converts a String to lower case as per {@link String#toLowerCase()}. 


A null input String returns null. 


将一个字符串转换为小写 StringUtils.lowerCase(null) = null StringUtils.lowerCase("") = "" StringUtils.lowerCase("aBc") = "abc" 

@param str the String to lower case, may be null 
@return the lower cased String, null if null String input 
public static String lowerCase(String str) 23、 

Capitalizes a String changing the first letter to title case as 

per {@link Character#toTitleCase(char)}. No other letters are changed. 


For a word based alorithm, see {@link /WordUtils#capitalize(String)}. 

A null input String returns null. 


StringUtils.capitalize(null) = null StringUtils.capitalize("") = "" StringUtils.capitalize("cat") = "Cat" StringUtils.capitalize("cAt") = "CAt" 

@param str the String to capitalize, may be null 
@return the capitalized String, null if null String input 
@see /WordUtils#capitalize(String) 
@see /uncapitalize(String) 
@since 2.0 
将字符串中的首字母大写 
public static String capitalize(String str) 