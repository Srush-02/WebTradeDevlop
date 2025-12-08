package com.trade.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.time.Instant;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.annotation.Configuration;


import com.trade.OutrightTradeServiceApplication;

import jakarta.annotation.PostConstruct;

@Configuration
public class CustomLogger {
	
	private Logger loggerConfig;

    private String version;
    private String appName = "Outright-Trade-Service";
    private String machineName;
    private String machineIP;
	
    @PostConstruct
    public void setLogger() throws Exception {

        loggerConfig = Logger.getLogger("OutrightTradeCustomLogger");

        // Disable default console formatting
        loggerConfig.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new JsonLikeLogFormatter());  // custom formatter
        loggerConfig.addHandler(handler);

        // Load version + app name
        Model model = getModel();
        version = model.getVersion();
        appName = model.getArtifactId();

        // Machine details
        machineName = InetAddress.getLocalHost().getHostName();
        machineIP = InetAddress.getLocalHost().getHostAddress();
    }

    private Model getModel() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        try {
            model = reader.read(new FileReader("pom.xml"), true);
        } catch (Exception e) {
            model = reader.read(new InputStreamReader(
                    OutrightTradeServiceApplication.class.getResourceAsStream(
                            "/META-INF/maven/bmx/outright-trade-service/pom.xml"
                    )
            ));
        }
        return model;
    }
    
    
    public void debug(String msg, String correlationId, String user) {
        log(Level.FINE, msg, null, correlationId, user);
    }

    public void info(String msg, String correlationId, String user) {
        log(Level.INFO, msg, null, correlationId, user);
    }

    public void warn(String msg, String correlationId, String user) {
        log(Level.WARNING, msg, null, correlationId, user);
    }

    public void error(String msg, Exception error, String correlationId, String user) {
        log(Level.SEVERE, msg, error, correlationId, user);
    }
    
    private void log(Level level, String message, Exception ex,
            String correlationId, String user) {

		try {
			LogRecord record = new LogRecord(level, message);
			record.setParameters(new Object[] { correlationId, appName, version, user, machineName, machineIP,
					Instant.now().toEpochMilli() });

			if (ex != null) {
				record.setThrown(ex);
			}

			loggerConfig.log(record);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    private static class JsonLikeLogFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {

            Object[] p = record.getParameters();
            if (p == null || p.length < 7) {
                return record.getMessage() + "\n";
            }

            return String.format(
                    "{ \"level\": \"%s\", \"correlationId\": \"%s\", \"app\": \"%s\", " +
                    "\"version\": \"%s\", \"user\": \"%s\", \"machineName\": \"%s\", " +
                    "\"machineIP\": \"%s\", \"timestamp\": \"%s\", \"message\": \"%s\" }\n",
                    record.getLevel(),
                    p[0],
                    p[1],
                    p[2],
                    p[3],
                    p[4],
                    p[5],
                    p[6],
                    record.getMessage()
            );
        }
    }

}
