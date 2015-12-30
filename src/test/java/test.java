import java.nio.charset.Charset;

import org.junit.Test;

public class test {
	@Test
	public void getSystemEncoding() {
		// 获取系统默认编码
		System.out.println(System.getProperty("file.encoding"));
		// 获取系统默认语言
		System.out.println(System.getProperty("user.language"));
		// 获取系统属性列表
		System.getProperties().list(System.out);
		// 获取系统默认的字符编码
		System.out.println(Charset.defaultCharset());
		//操作系统构架
		System.out.println(System.getProperty("os.arch"));
		// 设置编码
		// System.getProperties().put("file.encoding", "GBK");
	}
}
