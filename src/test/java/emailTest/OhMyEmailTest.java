package emailTest;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional.TxType;


/**
 * 发送邮件测试
 *
 * @author biezhi
 * 2017/5/30
 */
public class OhMyEmailTest {

    // 该邮箱修改为你需要测试的邮箱地址
    private static final String TO_EMAIL = "1094554830@qq.com";

    @Before
    public void before() {
        /*// 配置，一次即可
        OhMyEmail.config(SMTP_QQ(false), "xxx@qq.com", "*******");
        // 如果是企业邮箱则使用下面配置
        OhMyEmail.config(SMTP_ENT_QQ(false), "xxx@qq.com", "*******");*/
    	OhMyEmail.config(OhMyEmail.SMTP_163(false),"13672875006@163.com","smtp163com");
    }

    @Test
    public void testSendText() throws SendMailException {
    	long startTime = System.currentTimeMillis();
    	for(int i = 0 ; i < 5 ; i++){
	        OhMyEmail.subject("test send txt email")
	                .from("Helmet")
	                .to(TO_EMAIL)
	                .text("Hello word"+i)
	                .send();
    	}
        Assert.assertTrue(true);
        long endTime = System.currentTimeMillis();
        System.out.println("txt email time:"+(endTime-startTime));
    }

    @Test
    public void testSendHtml() throws SendMailException {
    	long startTime = System.currentTimeMillis();
    	for(int i = 0 ; i < 5 ; i++){
	        OhMyEmail.subject("Test send HTML email")
	                .from("Helmet")
	                .to(TO_EMAIL)
	                .html("<h1 font=red>Hello word</h1>"+i)
	                .send();
    	}
        Assert.assertTrue(true);
        long endTime = System.currentTimeMillis();
        System.out.println("HTML email time:"+(endTime-startTime));
    }

    @Test
    public void testSendAttach() throws SendMailException {
    	long startTime = System.currentTimeMillis();
    	for(int i = 0 ; i < 5 ; i++){
    		OhMyEmail.subject("Test send email attach local file")
    		.from("Helmet")
    		.to(TO_EMAIL)
    		.html("<h1 font=red>Hello word</h1>"+i)
    		.attach(new File("I:/photo/BJ_capf/qi.jpeg"), "测试图片.jpeg")
    		.send();
    	}
        Assert.assertTrue(true);
        long endTime = System.currentTimeMillis();
        System.out.println("local email time:"+(endTime-startTime));
    }

   /* @Test
    public void testSendAttachURL() throws SendMailException, MalformedURLException {
        OhMyEmail.subject("Test send email attach web resource")
                .from("Helmet")
                .to(TO_EMAIL)
                .html("<h1 font=red>Hello word</h1>")
                .attachURL(new URL("https://avatars1.githubusercontent.com/u/2784452?s=40&v=4"), "test.jpeg")
                .send();
        Assert.assertTrue(true);
    }*/

   /* @Test
    public void testPebble() throws IOException, PebbleException, SendMailException {
        PebbleEngine   engine           = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("register.html");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "biezhi");
        context.put("email", "admin@biezhi.me");

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        String output = writer.toString();
        System.out.println(output);

        OhMyEmail.subject("Test send email with Pebble model")
                .from("Helmet")
                .to(TO_EMAIL)
                .html(output)
                .send();
        Assert.assertTrue(true);
    }*/

   /* @Test
    public void testJetx() throws SendMailException {
        JetEngine   engine   = JetEngine.create();
        JetTemplate template = engine.getTemplate("/register.jetx");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "biezhi");
        context.put("email", "admin@biezhi.me");
        context.put("url", "<a href='http://biezhi.me'>https://biezhi.me/active/asdkjajdasjdkaweoi</a>");

        StringWriter writer = new StringWriter();
        template.render(context, writer);
        String output = writer.toString();
        System.out.println(output);

        OhMyEmail.subject("Test send email with jetx model")
                .from("Helmet")
                .to(TO_EMAIL)
                .html(output)
                .send();
        Assert.assertTrue(true);
    }*/

}