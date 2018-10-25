package com.ftd.smartshare.client.commands.subcommands;

import com.ftd.smartshare.client.api.Api;
import com.ftd.smartshare.dto.UploadRequestDto;
import com.ftd.smartshare.utils.PasswordGenerator;
import org.apache.commons.text.RandomStringGenerator;
import picocli.CommandLine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@CommandLine.Command(
		description = "Uploads file using a given 'password', expiration (60 minutes by default), a max downloads (1 by default)",
		name = "upload",
		aliases = "u",
		mixinStandardHelpOptions = true
)
public class Upload implements Runnable {

	@CommandLine.Parameters(arity = "1", index = "0", description = "The file to be uploaded")
	private File file;

	@CommandLine.Parameters(arity = "0", index = "1", description = "The password for the file")
	private String password = PasswordGenerator.generate();

	@CommandLine.Parameters(arity = "0", index = "2", description = "Length of time (in minutes) for which the file will exist")
	private String expiration = "60";

	@CommandLine.Parameters(arity = "0", index = "3", description = "Number of times the file can be downloaded")
	private String maxDownloads = "0";

	public void run() {
		System.out.println("Uploading: " + file.getAbsolutePath()); // must account for whether file is even found
		try {
			String fileName = file.getName();
			byte[] fileBytes = Files.readAllBytes(file.toPath());
			int expInt = Integer.valueOf(expiration);
			int maxInt = Integer.valueOf(maxDownloads);

			if (1 <= expInt && expInt <= 1440) {
				if (Api.upload(new UploadRequestDto(fileName, fileBytes, password, expInt, maxInt))) {
					System.out.println("UPLOAD SUCCESS");
					System.out.println("This file will exist for " + expiration + " minutes");
					System.out.println("It can be downloaded " + maxDownloads + " times.");
					System.out.println("Password will be printed below");
					System.out.println(password);
				}
			} else {
				System.out.println("UPLOAD FAILURE");
				System.out.println("Document failed to upload");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
