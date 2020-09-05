package com.wgojtek.tcp;

import java.io.*;
import java.net.Socket;

public class Server extends Thread {
    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
        System.out.println(this.getName() + " -> " + "CONNECTED");
    }

    @Override
    public void run() {
        try {
            String main = "students";
            DataInputStream dis;
            BufferedInputStream bis;

            while(true) {
                System.out.println("Data from - " + this.getName());
                bis = new BufferedInputStream(socket.getInputStream());
                dis = new DataInputStream(bis);

                String directory = dis.readUTF();
                String dirPath = main + "/" + directory;
                String tmp = dirPath;

                //create directory
                if (!new File(dirPath).exists()) {
                    new File(dirPath).mkdir();
                    new File(dirPath + "/o").mkdir();
                    new File(dirPath + "/n").mkdir();
                }


                int fileCount = dis.readInt();
                File[] files = new File[fileCount];


                for (File f : files) {
                    long fileLength = dis.readLong();
                    String fileName = dis.readUTF();

                    dirPath = tmp;
                    if (fileName.contains("o")) {
                        dirPath = dirPath + "/o";
                    }

                    if (fileName.contains("n")) {
                        dirPath = dirPath + "/n";
                    }

                    f = new File(dirPath + "/" + fileName);

                    FileOutputStream fos = new FileOutputStream(f);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    for (int j = 0; j < fileLength; j++) bos.write(bis.read());

                    bos.close();
                    fos.flush();
                }
                //dis.close();
                //socket.shutdownInput();
            }
        } catch (IOException e) {
            System.out.println("->" + e.getMessage());
        }

    }
}
