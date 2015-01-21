/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.leisore.coder._2015._01;

import static cn.leisore.coder.common.Reflections.getFieldVal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;

public class FileHandleTest {

    public static void testFileDescriptor() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        // FileInputStream
        File tmpInFile = File.createTempFile("int", ".tmp");
        tmpInFile.deleteOnExit();
        FileInputStream fis = new FileInputStream(tmpInFile);

        // FileOutputStream
        File tmpOutFile = File.createTempFile("out", ".tmp");
        tmpOutFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tmpOutFile);

        // RandomAccessFile
        File tmpRandFile = File.createTempFile("rand", ".tmp");
        tmpRandFile.deleteOnExit();
        RandomAccessFile acf = new RandomAccessFile(tmpRandFile, "rw");

        // ServerSocket & Socket
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = new Socket("localhost", 8888);

        System.out.println("open\n-------------------------------");
        System.out.println(String.format("FileInputStream : FileDescriptor[fd=%d handle=%d]", getFieldVal(fis, "fd", "fd"), getFieldVal(fis, "fd", "handle")));
        System.out.println(String.format("FileOutputStream: FileDescriptor[fd=%d handle=%d]", getFieldVal(fos, "fd", "fd"), getFieldVal(fos, "fd", "handle")));
        System.out.println(String.format("RandomAccessFile: FileDescriptor[fd=%d handle=%d]", getFieldVal(acf, "fd", "fd"), getFieldVal(acf, "fd", "handle")));
        System.out.println(String.format("ServerSocket    : FileDescriptor[fd=%d handle=%d]", getFieldVal(serverSocket, "impl", "fd", "fd"), getFieldVal(serverSocket, "impl", "fd", "handle")));
        System.out.println(String.format("Socket          : FileDescriptor[fd=%d handle=%d]", getFieldVal(socket, "impl", "fd", "fd"), getFieldVal(socket, "impl", "fd", "handle")));

        System.out.println("\nenter to continue:");
        System.in.read();

        fos.close();
        fis.close();
        acf.close();
        serverSocket.close();
        socket.close();
        System.out.println("\nclose\n-------------------------------");
        System.out.println(String.format("FileInputStream : FileDescriptor[fd=%d handle=%d]", getFieldVal(fis, "fd", "fd"), getFieldVal(fis, "fd", "handle")));
        System.out.println(String.format("FileOutputStream: FileDescriptor[fd=%d handle=%d]", getFieldVal(fos, "fd", "fd"), getFieldVal(fos, "fd", "handle")));
        System.out.println(String.format("RandomAccessFile: FileDescriptor[fd=%d handle=%d]", getFieldVal(acf, "fd", "fd"), getFieldVal(acf, "fd", "handle")));
        System.out.println(String.format("ServerSocket    : FileDescriptor[fd=%d handle=%d]", getFieldVal(serverSocket, "impl", "fd", "fd"), getFieldVal(serverSocket, "impl", "fd", "handle")));
        System.out.println(String.format("Socket          : FileDescriptor[fd=%d handle=%d]", getFieldVal(socket, "impl", "fd", "fd"), getFieldVal(socket, "impl", "fd", "handle")));
    }

    public static void testJarFile() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        JarFile jarFile = new JarFile(System.getProperty("java.home") + "/lib/rt.jar");
        System.out.println("open\n-------------------------------");
        System.out.println(String.format("JarFile : [jzfile=%d]", getFieldVal(jarFile, "jzfile")));

        jarFile.close();
        System.out.println("\nclose\n-------------------------------");
        System.out.println(String.format("JarFile : [jzfile=%d]", getFieldVal(jarFile, "jzfile")));
    }

    public static void testJarURLConnection() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        new URL("file://").openConnection().setDefaultUseCaches(false);
        File jar = new File(System.getProperty("java.home") + "/lib/rt.jar");
        URL url = new URL("jar:" + jar.toURI().toURL().toExternalForm() + "!/java/lang/String.class");

        URLConnection conn = url.openConnection();
        InputStream stream = conn.getInputStream();

        System.out.println("open\n-------------------------------");
        System.out.println(String.format("JarURLConnection : [jarFile.jzfile=%d]", getFieldVal(conn, "jarFile", "jzfile")));

        stream.close();
        System.out.println("\nclose\n-------------------------------");
        System.out.println(String.format("JarURLConnection : [jarFile.jzfile=%d]", getFieldVal(conn, "jarFile", "jzfile")));
    }

    public static void main(String[] args) throws SecurityException, IllegalArgumentException, IOException, NoSuchFieldException, IllegalAccessException {
        testJarURLConnection();
    }
}
