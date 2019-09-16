package cn.powertime.iatp.logging;

import cn.powertime.iatp.utils.SpringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;


/**
 * 该类定义了信息配置组件。<br/>
 * 信息配置文件指定放置在/config/目录下，名称以messages结尾，采用标准的Properties XML文件格式。
 */
public class MessageSource extends ReloadableResourceBundleMessageSource {

	private static final String MESSAGE_DIR = "/config/";

	public static final String UTF_8 = "UTF-8";

	/**
	 * 构造方法。
	 */
	public MessageSource() {
		setDefaultEncoding(UTF_8);
		setUseCodeAsDefaultMessage(true);
	}
	


	/**
	 * 获取指定编码的信息。
	 * 
	 * @param code
	 *            信息编码
	 * @param vars
	 *            信息变量
	 * @return 返回指定编码的信息。
	 */
	public String get(String code, Object... vars) {
		return getMessage(code, vars, null);
	}

	@Override
	public void setBasenames(String... basenames) {
		List<String> resourceBasenames = SpringUtils
				.getResourceBasenamesByWildcard(MESSAGE_DIR, basenames);
		super.setBasenames(resourceBasenames.toArray(new String[] {}));
		logger.info("加载配置信息文件" + resourceBasenames + "成功。");
	}
}
