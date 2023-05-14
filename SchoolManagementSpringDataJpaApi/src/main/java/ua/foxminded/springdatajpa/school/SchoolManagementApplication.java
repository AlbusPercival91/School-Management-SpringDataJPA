package ua.foxminded.springdatajpa.school;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SchoolManagementApplication {

	public static void main(String[] args) {
		initializeDatabase();
		ConfigurableApplicationContext context = SpringApplication.run(SchoolManagementApplication.class, args);
		context.close();
	}

	private static void initializeDatabase() {
		String initialScriptFileName = "initialScript.sql";

		try (InputStream is = SchoolManagementApplication.class.getResourceAsStream("/" + initialScriptFileName);
				ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}

			byte[] initialScriptBytes = baos.toByteArray();
			File initialScriptTempFile = File.createTempFile(initialScriptFileName, ".tmp");
			initialScriptTempFile.deleteOnExit();
			try (FileOutputStream out = new FileOutputStream(initialScriptTempFile)) {
				out.write(initialScriptBytes);
			}

			String cmdQuery = String.format("psql -U postgres -h localhost -p 5432 -f %s",
					initialScriptTempFile.getAbsolutePath());
			String[] envVars = { "PGPASSWORD=1234" };
			Process runInitScript = Runtime.getRuntime().exec(cmdQuery, envVars);
			runInitScript.waitFor();
		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
