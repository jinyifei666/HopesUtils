// java.lang.Object
  // java.text.Format
      // java.text.NumberFormat
          // java.text.DecimalFormat
// DecimalFormat 是 NumberFormat 的一个具体子类，用于格式化十进制数字。该类设计有各种功能，使其能够解析和格式化任意语言环境中的数，
// 包括对西方语言、阿拉伯语和印度语数字的支持。它还支持不同类型的数，包括整数 (123)、定点数 (123.4)、科学记数法表示的数 (1.23E4)、
// 百分数 (12%) 和金额 ($123)。所有这些内容都可以本地化。 

// 在输出数字时，有时需要给数字配上单位，有时需要数字具有一定的精度，也有时需要用科学计数法表示数字。
// 关键技术剖析：
// v java.text.DecimalFormat类专门用于格式化数字。
// v 需要为DecimalFormat提供格式化模式Pattern。通过构造方法或者DecimalFormat的applyPattern方法设置模式。Pattern的类型为字符串。
// v 调用DecimalFormat的format实例方法，参数为待格式化的数字，该方法使用DecimalFormat对象的pattern对参数进行格式化。
import java.text.DecimalFormat;
 
public class TestDecimalFormat {
public static void main(String[] args) {
DecimalFormat df = new DecimalFormat();
double data = 1203.405607809;
System.out.println("格式化之前：" + data);
 
String pattern = "0.0";//1203.4
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
// 可以在模式后加上自己想要的任何字符，比如单位
pattern = "00000000.000kg";//00001203.406kg
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//#表示如果存在就显示字符，如果不存在就不显示，只能用在模式的两头
pattern = "##000.000kg";//1203.406kg
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//-表示输出为负数，必须放在最前面
pattern = "-000.000";//-1203.406
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//,是分组分隔符 ：输出结果12,03.41
pattern = "-0,00.0#";//-12,03.41
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//E表示输出为指数，”E“之前的字符串是底数的格式，之后的是指数的格式。
pattern = "0.00E000";//1.20E003
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//%表示乘以100并显示为百分数，要放在最后
pattern = "0.00%";//120340.56%
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//"\u2030"表示乘以1000并显示为千分数，要放在最后
pattern = "0.00\u2030";//203405.61‰
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//"\u00A4"货币符号，要放在两端*****1203.41￥
pattern = "0.00\u00A4";//1203.41￥
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
//'用于在前缀或或后缀中为特殊字符加引号，要创建单引号本身，请连续使用两个单引号："# o''clock"。 
pattern = "'#'#" ;//#1203
// pattern = "'#'" ;//#1203
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
 
pattern = "# o''clock" ;//1203 o'clock
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
//''放在中间或后面单引号就显示在最后，放在最前面单引号就显示在最前
// pattern = "# o''clock.000" ;//1203.406 o'clock
// pattern = "# .000o''clock";//1203.406 o'clock
// pattern = "# .000''";//1203.406 '
// pattern = "# .''000";//1203.406 '
pattern = "''# .000";//'1203.406 
df.applyPattern(pattern);
System.out.println("采用" + pattern + "模式格式化后：" + df.format(data));
}
}
输出结果为：
格式化之前：1203.405607809
采用0.0模式格式化后：1203.4
采用00000000.000kg模式格式化后：00001203.406kg
采用##000.000kg模式格式化后：1203.406kg
采用-000.000模式格式化后：-1203.406
采用-0,00.0#模式格式化后：-12,03.41
采用0.00E000模式格式化后：1.20E003
采用0.00%模式格式化后：120340.56%
采用0.00‰模式格式化后：1203405.61‰
采用0.00¤模式格式化后：1203.41￥
采用'#'#模式格式化后：#1203
采用# o''clock模式格式化后：1203 o'clock
采用''# .000模式格式化后：'1203.406
分析：
2 可以在java.text.DecimalForm的构造方法中制定模式（pattern），也可以使用applyPattern方法设置格式模式。
2 格式模式有特定的规则，不太的字符代表不同的意思，在使用中需要自己设计格式的模式。
常用的模式有：“.”、“-”、“#”、“%”、“\u2030”、“,”、“E”、“0”。关于这些模式的具体含义在程序注释中有详细的介绍，也可以参阅JDK的帮助文档。

// 模式中0与# 不同

// 模式中的"#"表示如果该位存在字符，则显示字符，如果不存在，则不显示。
    //定义一个数字格式化对象，格式化模板为".##"，即保留2位小数.
     DecimalFormat a = new DecimalFormat(".##");
     String s= a.format(333.335);
     System.err.println(s);
     //说明：如果小数点后面不够2位小数，不会补零.参见Rounding小节
     //---------------------------------------------

     //-----------------------------------------------
     //可以在运行时刻用函数applyPattern(String)修改格式模板
     //保留2位小数，如果小数点后面不够2位小数会补零
     a.applyPattern(".00");
     s = a.format(333.3);
     System.err.println(s);
     //------------------------------------------------

     //------------------------------------------------
     //添加千分号
     a.applyPattern(".##\u2030");
     s = a.format(0.78934);
     System.err.println(s);//添加千位符后,小数会进三位并加上千位符
     //------------------------------------------------

     //------------------------------------------------
     //添加百分号
     a.applyPattern("#.##%");
     s = a.format(0.78645);
     System.err.println(s);
     //------------------------------------------------

    //------------------------------------------------
     //添加前、后修饰字符串，记得要用单引号括起来
     a.applyPattern("'这是我的钱$',###.###'美圆'");
     s = a.format(33333443.3333);
     System.err.println(s);
     //------------------------------------------------

      //------------------------------------------------
     //添加货币表示符号(不同的国家，添加的符号不一样
     a.applyPattern("\u00A4");
     s = a.format(34);
     System.err.println(s);
     //------------------------------------------------

     //-----------------------------------------------
     //定义正负数模板,记得要用分号隔开
      a.applyPattern("0.0;'@'-#.0");
      s = a.format(33);
      System.err.println(s);
      s = a.format(-33);
      System.err.println(s);
      //-----------------------------------------------
    
     //综合运用，正负数的不同前后缀
     String pattern="'my moneny'###,###.##'RMB';'ur money'###,###.##'US'";
     a.applyPattern(pattern);
     System.out.println(a.format(1223233.456));
   }
}