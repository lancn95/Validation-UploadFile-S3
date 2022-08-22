package com.amigos.awsuploadimage;

import com.amigos.awsuploadimage.utils.MoneyUntil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwsUploadImageApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsUploadImageApplication.class, args);

		MoneyUntil moneyUntil = new MoneyUntil();
		System.out.println(moneyUntil.readMoney("1827"));
	}

}
