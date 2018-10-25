package com.ftd.smartshare.client.api;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.ftd.smartshare.dto.DownloadDto;
import com.ftd.smartshare.dto.DownloadRequestDto;
import com.ftd.smartshare.dto.UploadRequestDto;
import com.ftd.smartshare.dto.ViewDto;
import com.ftd.smartshare.dto.ViewRequestDto;
import com.ftd.smartshare.utils.NotImplementedException;

public final class Api {

    private static final String HOST    = "localhost";
    private static final int    PORT    = 3000;

    private Api () {
        throw new UnsupportedOperationException();
    }

    /**
     * Send download request
     *
     * @param downloadRequestDto    JAXB annotated class representing the download request
     * @return true if request was successful and false if unsuccessful
     */
    public static boolean download(DownloadRequestDto downloadRequestDto) {
    	try (Socket socket = new Socket(HOST, PORT)) {
    		JAXBContext context = JAXBContext.newInstance(DownloadRequestDto.class, DownloadDto.class);
    		Marshaller marshaller = context.createMarshaller();
    		
    		StringWriter stringWriter = new StringWriter();
    		marshaller.marshal(downloadRequestDto, stringWriter);
    		
    		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    		output.write(stringWriter.toString());
    		output.newLine();
    		output.flush();
    		
    		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		StringReader stringReader = new StringReader(input.readLine());
    		
    		Unmarshaller unmarshaller = context.createUnmarshaller();
			DownloadDto downloadDto = (DownloadDto) unmarshaller.unmarshal(stringReader);
						
			return downloadDto.getFileBytes() != null;
    	} catch (IOException | JAXBException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    /**
     * Send upload request
     *
     * @param uploadRequestDto      JAXB annotated class representing the upload request
     * @return true if request was successful and false if unsuccessful
     */
    public static boolean upload(UploadRequestDto uploadRequestDto) {
    	try (Socket socket = new Socket(HOST, PORT)) {
    		JAXBContext context = JAXBContext.newInstance(UploadRequestDto.class);
    		Marshaller marshaller = context.createMarshaller();
    		
    		StringWriter stringWriter = new StringWriter();
    		marshaller.marshal(uploadRequestDto, stringWriter);
    		
    		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    		output.write(stringWriter.toString());
    		output.newLine();
    		output.flush();
    		
    		DataInputStream input = new DataInputStream(socket.getInputStream());
    		return input.readBoolean();
    		
    	} catch (IOException | JAXBException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    
    /**
     * Send view request
     *
     * @param ViewRequestDto    JAXB annotated class representing the view request
     * @return true if request was successful and false if unsuccessful
     */
    public static long[] view(ViewRequestDto viewRequestDto) {
    	try (Socket socket = new Socket(HOST, PORT)) {
    		JAXBContext context = JAXBContext.newInstance(ViewRequestDto.class, ViewDto.class);
    		Marshaller marshaller = context.createMarshaller();
    		
    		StringWriter stringWriter = new StringWriter();
    		marshaller.marshal(viewRequestDto, stringWriter);
    		
    		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    		output.write(stringWriter.toString());
    		output.newLine();
    		output.flush();
    		
    		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		StringReader stringReader = new StringReader(input.readLine());
    		Unmarshaller unmarshaller = context.createUnmarshaller();
    		
    		ViewDto viewDto = (ViewDto) unmarshaller.unmarshal(stringReader);
    		long[] loadsAndTimeLeft = {viewDto.getDownloadsLeft(), viewDto.getTimeLeft()};
    		return loadsAndTimeLeft;
    	} catch (IOException | JAXBException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
}