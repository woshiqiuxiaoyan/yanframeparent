import constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonTools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.MalformedURLException;

/**
 * <p>Title:SpringIniterListener </p>
 * <p>Description:初始化监听器</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/31
 * Time: 10:22
 */
public class SpringIniterListener implements ServletContextListener {

    private static Logger log = LoggerFactory.getLogger(SpringIniterListener.class);

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("------------------------------------启动加载配置-------------------------------");
        Constant.yanFrameParent_content_url = servletContextEvent.getServletContext().getContextPath()+"/";//  servletContextEvent.getServletContext().getRealPath("/");
        Constant.yanFrameParent_real_url = servletContextEvent.getServletContext().getRealPath("/");
        Constant.yanFrameParent_img_url = Constant.yanFrameParent_content_url  +Constant.PIC_URL_GOODS_UPLOAD;
        Constant.yanFrameParent_real_img_url= Constant.yanFrameParent_real_url +Constant.PIC_URL_GOODS_UPLOAD;


        log.info("全局路径:"+Constant.yanFrameParent_content_url);
        log.info("全局保存路径:"+Constant.yanFrameParent_real_url);
        log.info("全局图片显示路径:"+Constant.yanFrameParent_img_url);
        log.info("全局图片保存路径:"+Constant.yanFrameParent_real_img_url);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("------------------------------------88-------------------------------");
    }
}
