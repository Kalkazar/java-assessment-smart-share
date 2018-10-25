package com.ftd.smartshare.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.ftd.smartshare.dto.UploadRequestDto;
import com.ftd.smartshare.dto.ViewDto;
import com.ftd.smartshare.dto.ViewRequestDto;
import com.ftd.smartshare.utils.NoCloseInputStream;
import com.ftd.smartshare.dto.DownloadDto;
import com.ftd.smartshare.dto.DownloadRequestDto;
import com.ftd.smartshare.dao.SmartShareDao;

public class SmartShareClientHandler implements Runnable {
	
	private Socket clientSocket;
	
	public SmartShareClientHandler(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		SmartShareDao dao = new SmartShareDao();
		try {
			JAXBContext context = JAXBContext.newInstance(
					UploadRequestDto.class,
					DownloadRequestDto.class, DownloadDto.class,
					ViewRequestDto.class, ViewDto.class);
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			StringReader stringReader = new StringReader(input.readLine());
			Object request = unmarshaller.unmarshal(stringReader);
			
			if (request instanceof UploadRequestDto) {
				UploadRequestDto upload = (UploadRequestDto) request;
				DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
				output.writeBoolean(dao.uploadFile(upload));
				
			} else {// if (request instanceof DownloadRequestDto){
				DownloadRequestDto download = (DownloadRequestDto) request;
				DownloadDto downloadDto = new DownloadDto(dao.downloadFile(download));
				
				Marshaller marshaller = context.createMarshaller();
				StringWriter stringWriter = new StringWriter();
	    		marshaller.marshal(downloadDto, stringWriter);
	    		
	    		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	    		output.write(stringWriter.toString());
	    		output.newLine();
	    		output.flush();
			}
//			else {
//				ViewRequestDto view = (ViewRequestDto) request;
//				ViewDto viewDto = new ViewDto(dao.viewFile(view));
//				
//				Marshaller marshaller = context.createMarshaller();
//				StringWriter stringWriter = new StringWriter();
//	    		marshaller.marshal(viewDto, stringWriter);
//	    		
//	    		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//	    		output.write(stringWriter.toString());
//	    		output.newLine();
//	    		output.flush();
//			}
		} catch (IOException | JAXBException e) {
			e.printStackTrace();
		}
		
	}
	
}