package com.ftd.smartshare.client.commands.subcommands;

import java.io.*;
import java.nio.file.Files;

import com.ftd.smartshare.client.api.Api;
import com.ftd.smartshare.dto.DownloadRequestDto;
import com.ftd.smartshare.dto.UploadRequestDto;

import picocli.CommandLine;

@CommandLine.Command(
        description = "Downloads a file",
        name = "download",
        aliases = "d",
        mixinStandardHelpOptions = true
)
public class Download implements Runnable {

    @CommandLine.Parameters(arity="1", index = "0", description = "Name of file to be downloaded")
    private String fileName;

    @CommandLine.Parameters(arity="1", index = "1", description = "The password for the file")
    private String password;

    public void run() {
        System.out.println("Downloading " + fileName);
        if (Api.download(new DownloadRequestDto(fileName, password))) {
        	System.out.println("DOWNLOAD SUCCESS");
        	System.out.println("Document has been successfully downloaded to the root of the classpath");
        } else {
        	System.out.println("DOWNLOAD FAILURE");
        	System.out.println("Document failed to download");
        }
    }

}
