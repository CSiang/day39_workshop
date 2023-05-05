package day39_workshop.server;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import day39_workshop.server.Services.MD5HashingService;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	@Autowired
	private MD5HashingService hashSvc;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Date date = new Date();
		Timestamp timestamp2 = new Timestamp(date.getTime());


		String input = "1683251630479e9d3ecafe6586a96ba4e7e528246abe3998cfca007d2b57d7f20a32c38ecf812c6c86";
		String hash = hashSvc.getMD5HAsh(input);
		
		// .getTime() is to convert timestamp into millisecond.
		System.out.printf("\nThe timestamp: %d\n", timestamp2.getTime());
		System.out.printf("\nThe timestamp String: %s\n", hashSvc.getTimestamp());

		System.out.printf("The hash: %s\n", hash);
	}

}
