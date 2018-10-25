package com.ftd.smartshare.client;

import com.ftd.smartshare.client.commands.SmartShare;
import picocli.CommandLine;

class Main {

    public static void main(String[] args) {
//    	CommandLine.run(new SmartShare()); // Pass cli arguments here
//    	CommandLine.run(new SmartShare(), "-h");
//    	CommandLine.run(new SmartShare(), "--version");
//    	CommandLine.run(new SmartShare(), "upload");
//    	CommandLine.run(new SmartShare(), "upload", "pom.xml");
//    	CommandLine.run(new SmartShare(), "upload", ".gitignore");
//    	CommandLine.run(new SmartShare(), "upload", "pom.xml", "password");
//    	CommandLine.run(new SmartShare(), "upload", "java-map-collection-cheat-sheet.gif", "password", "120", "4");
//    	CommandLine.run(new SmartShare(), "upload", "pom.xml", "password", "1");
//    	CommandLine.run(new SmartShare(), "download", "pom.xml", "password");
//    	CommandLine.run(new SmartShare(), "download", "java-map-collection-cheat-sheet.gif", "password");
//    	CommandLine.run(new SmartShare(), "upload", "C:\\Users\\ftd-05\\Desktop\\Downloaded Notes & Diagrams\\flowcharts\\collections-clear.png", "password", "120");
//    	CommandLine.run(new SmartShare(), "view", "pom.xml", "password");
//    	CommandLine.run(new SmartShare(), "download", "collections-clear.png", "password");
    }
}

// Make sure the download count thing is ok. Like they're cool with it going to zero
//  - just start by setting max_downloads = max_downloads - 1;

// NEED TO TEST EXPIRATION
// errors if time < 0. Ask the guys if they're cool with that (Unknown option: -1)
// does the uploader need to tell you if it can't find the file?