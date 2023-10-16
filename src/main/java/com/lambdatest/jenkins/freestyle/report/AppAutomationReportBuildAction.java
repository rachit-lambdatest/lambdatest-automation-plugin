package com.lambdatest.jenkins.freestyle.report;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import hudson.model.Run;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lambdatest.jenkins.freestyle.api.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.Logger;

public class AppAutomationReportBuildAction extends AbstractAppAutomationReportBuildAction {
    private final static Logger logger = Logger.getLogger(AppAutomationReportBuildAction.class.getName());
    private String lambdaTestBuildDeviceUrl;
    private String authString;
    private String buildName;
    private transient List < JSONObject > result = new ArrayList < JSONObject > ();

    public AppAutomationReportBuildAction(final Run<?, ?> build, String name,String password, String buildName) {
        super();
        setBuild(build);
        this.authString =  name + ":" + password;
        this.buildName = buildName;
    }
    public void generateLambdaTestAppAutomationReport() {
        byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        try {
            // Add a 5-second sleep
            Thread.sleep(5000);
            URL buildUrl = new URL(Constant.AppAutomationReport.BUILD_INFO_URL);
            URLConnection buildUrlConnection = buildUrl.openConnection();
            buildUrlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
            InputStream is = buildUrlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int numCharsReadForBuild;
            char[] charArrayForBuild = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsReadForBuild = isr.read(charArrayForBuild)) > 0) {
                sb.append(charArrayForBuild, 0, numCharsReadForBuild);
            }
            String buildResult = sb.toString();
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(buildResult);
                ArrayNode dataNode = (ArrayNode) rootNode.get("data");
                Iterator<JsonNode> dataIterator = dataNode.elements();
                while (dataIterator.hasNext()) {
                    JsonNode buildDetailNode = dataIterator.next();
                    if (buildDetailNode.get("name").toString().replaceAll("\"", "").equals(buildName)) {
                        String build_id = buildDetailNode.get("build_id").toString();

                        lambdaTestBuildDeviceUrl = "https://appautomation.lambdatest.com/build/?searchtxt=" + build_id;

                        logger.info("lambdaTestBuildDeviceUrl : " + lambdaTestBuildDeviceUrl);
                        URL sessionUrl = new URL(Constant.AppAutomationReport.SESSION_INFO_URL + "?limit=100&build_id=" + build_id);
                        URLConnection sessionUrlConnection = sessionUrl.openConnection();
                        sessionUrlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
                        is = sessionUrlConnection.getInputStream();
                        isr = new InputStreamReader(is);
                        int numCharsReadForSession;
                        char[] charArrayForSession = new char[1024];
                        sb = new StringBuffer();
                        while ((numCharsReadForSession = isr.read(charArrayForSession)) > 0) {
                            sb.append(charArrayForSession, 0, numCharsReadForSession);
                        }
                        String sessionResult = sb.toString();

                        JsonNode sessionRootNode = mapper.readTree(sessionResult);
                        ArrayNode sessionDataNode = (ArrayNode) sessionRootNode.get("data");
                        Iterator<JsonNode> sessionDataIterator = sessionDataNode.elements();

                        JSONArray array = new JSONArray();
                        while (sessionDataIterator.hasNext()) {
                            JsonNode sessionDetailNode = sessionDataIterator.next();
                            logger.info("sessiondetailnode : " + sessionDataNode.toString());
                            String testUrl = Constant.APP_AUTOMATION_APP_URL + "/test?testID=" + sessionDetailNode.get("test_id").toString().replaceAll("\"", "") + "&buildid=" + build_id;
                            logger.info("testUrl : " + testUrl);
                            JSONObject resultJSON = new JSONObject();
                            resultJSON.put("url",testUrl);
                            resultJSON.put("deviceVersion",sessionDetailNode.get("version").toString().replaceAll("\"", ""));
                            resultJSON.put("OS",sessionDetailNode.get("platform").toString().replaceAll("\"", ""));
                            resultJSON.put("testId",sessionDetailNode.get("test_id").toString().replaceAll("\"", ""));
                            resultJSON.put("buildId",sessionDetailNode.get("build_id").toString().replaceAll("\"", ""));
                            resultJSON.put("name",sessionDetailNode.get("name").toString().replaceAll("\"", ""));
                            resultJSON.put("createdAtReadable",sessionDetailNode.get("create_timestamp").toString().replaceAll("\"", ""));
                            resultJSON.put("testDuration",sessionDetailNode.get("duration").toString().replaceAll("\"", ""));
                            resultJSON.put("status",sessionDetailNode.get("status_ind").toString().replaceAll("\"", ""));
                            resultJSON.put("browser",sessionDetailNode.get("device").toString().replaceAll("\"", ""));
                            array.put(resultJSON);
                        }
                        for (int i = 0; i < array.length(); i++) {
                            result.add(array.getJSONObject(i));
                        }
                        logger.info("result : " + result.toString());
                        break;
                    }
                }
            } catch (JsonParseException e) {
                logger.warning("JsonParseException : " + e.getMessage());
                e.printStackTrace();
            } catch (JsonMappingException e) {
                logger.warning("JsonMappingException : " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                logger.warning("IOException : " + e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                logger.warning("JSONException : " + e.getMessage());
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            logger.warning("MalformedURLException : " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.warning("IOException : " + e.getMessage());
            e.printStackTrace();
                } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public List<JSONObject> getResult() {
        return result;
    }

    public String getLambdaTestBuildDeviceUrl() {
        return lambdaTestBuildDeviceUrl;
    }

    public String getBuildName() {
        return buildName;
    }
}