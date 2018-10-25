package com.ftd.smartshare.client.commands.subcommands;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import com.ftd.smartshare.client.api.Api;
import com.ftd.smartshare.dto.DownloadRequestDto;
import com.ftd.smartshare.dto.ViewRequestDto;

import picocli.CommandLine;

@CommandLine.Command(
        description = "Displays information about a file so long as the password is provided. Display information includes: file name, maximum number of downloads, time before auto-expiration",
        name = "view",
        aliases = "v",
        mixinStandardHelpOptions = true
)
public class View implements Runnable{
	
	@CommandLine.Parameters(arity="1", index = "0", description = "Name of file to be viewed")
	private String fileName;
	
	@CommandLine.Parameters(arity="1", index = "1", description = "The password for the file")
	private String password;
	
	@Override
	public void run() {
		System.out.println("Accessing file: " + fileName);
		long[] result = Api.view(new ViewRequestDto(fileName, password));
		if (result != null) {
        	System.out.println("SUCCESS");
        	int deletionTime = (int) (result[1]*1000);
        	System.out.println("File Name: " + fileName);
        	System.out.println("Time left before deletion: " + Integer.toString(deletionTime));
        	System.out.println("Number of downloads left before deletion: " + Long.toString(result[0]));
        } else {
        	System.out.println("ERROR: could not locate and/or access file");
        }
	}

}
