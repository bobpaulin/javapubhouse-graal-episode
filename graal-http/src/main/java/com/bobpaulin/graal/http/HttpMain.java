package com.bobpaulin.graal.http;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.LogFactoryImpl;
import org.apache.commons.logging.impl.SimpleLog;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

public class HttpMain {

	public static void main(String[] args) throws IOException {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
		    HttpGet httpGet = new HttpGet("https://www.javapubhouse.com/");
		    try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
		        HttpEntity entity1 = response1.getEntity();
		        String content = EntityUtils.toString(entity1);
	            System.out.println(content);
	            System.out.println(response1.getStatusLine().getStatusCode() + " " + response1.getStatusLine().getReasonPhrase());
		    }
		}
	}
}

@SuppressWarnings("unused")
@TargetClass(LogFactory.class)
final class LogFactorySubstituted {
    @Substitute
    protected static LogFactory newFactory(final String factoryClass,
                                           final ClassLoader classLoader,
                                           final ClassLoader contextClassLoader) {
        return new LogFactoryImpl();
    }
}

@SuppressWarnings("unused")
@TargetClass(LogFactoryImpl.class)
final class LogFactoryImplSubstituted {
    @Substitute
    private Log discoverLogImplementation(String logCategory) {
        return new SimpleLog(logCategory);
    }
}