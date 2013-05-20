/**
 * 
 */
package net.codestory.jajascript;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import net.codestory.jajascript.util.FileUtil;
import net.codestory.jajascript.util.JsonHelper;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

/**
 * @author jmoutsinga
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChallengeTests {

    public static final String PROP_SERVER = "server";
    private static final String PROP_SERVER_PORT = "port";

    public static final String DEFAULT_SERVER = "localhost";
    public static final String DEFAULT_SERVER_PORT = "8080";

    private static String server;
    private static String port;

    private WebConversation webConversation;

    @BeforeClass
    public static void initServer() {
        server = System.getProperty(PROP_SERVER, DEFAULT_SERVER);
        port = System.getProperty(PROP_SERVER_PORT, DEFAULT_SERVER_PORT);
    }

    @Before
    public void setup() {
        webConversation = new WebConversation();
    }

    @Test
    public void _1_sample() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-sample.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-sample-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _2_play10() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-10.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-10-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _2_play50() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-50.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-50-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _3_play100() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-100.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-100-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _4_play500() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-500.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-500-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _5_play1000() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-1000.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-1000-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _6_play5000() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-5000.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-5000-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _7_play10000() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-10000.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-10000-response.json");
        checkResponse(responseContent, expectedResult);
    }

    @Test(timeout = 30000)
    public void _8_play50000() throws IOException, SAXException {
        String responseContent = playRequest("jajascript-50000.json");
        String expectedResult = FileUtil.fileContentToString("jajascript-50000-response.json");
        checkResponse(responseContent, expectedResult);
    }

    private void checkResponse(String responseContent, String expectedResult) {
        JsonHelper helper = new JsonHelper();
        assertThat(helper.toJsonString(helper.fromJson(responseContent, OptimalSpaceshiftPath.class))).isEqualTo(
            helper.toJsonString(helper.fromJson(expectedResult, OptimalSpaceshiftPath.class)));
    }

    private String playRequest(String requestContent) throws IOException, SAXException {
        InputStream newInputStream = ClassLoader.getSystemResourceAsStream(requestContent);
        String urlString = "http://" + server + ":" + port + "/jajascript/optimize";
        WebRequest req = new PostMethodWebRequest(urlString, newInputStream, "text/plain");
        WebResponse resp = webConversation.getResponse(req);
        return resp.getText();
    }
}
