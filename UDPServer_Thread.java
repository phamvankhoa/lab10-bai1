/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bai1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.StringTokenizer;
import javax.swing.JTextArea;

/**
 *
 * @author phamv
 */
public class UDPServer_Thread extends Thread{
    DatagramSocket mServerSocket;
    JTextArea mTxaStatus; //JTextArea để lưu status của Server
    public UDPServer_Thread(JTextArea txaStatus)
    {
        mTxaStatus = txaStatus;
    }
    @Override
    public void run() 
    {
        int port = 21;
        try
        {
            mServerSocket = new DatagramSocket(port);
            mTxaStatus.append("Server khởi động ở: "+port+"Đang đợi dữ liệu\n");
            byte[] buf = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            while (true) 
            {
                try
                {
                    mServerSocket.receive(receivePacket);
                    String ClientMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    StringTokenizer st = new StringTokenizer(ClientMsg, " ");//đếm sốtừ dựa vào dấu space
                    int numOfWords = st.countTokens();
                    String returnMsg = "Dữ liệu gốc: " + ClientMsg + "\n"+ "Dữ liệu đã xử lý:\n"+ ClientMsg.toUpperCase() + "\n" + ClientMsg.toLowerCase() + "\n" + "Số từ: " + numOfWords + "\n\n";
                    mTxaStatus.append(returnMsg);
                    DatagramPacket outPacket = new DatagramPacket(returnMsg.getBytes(), returnMsg.getBytes().length,receivePacket.getAddress(), receivePacket.getPort());
                    mServerSocket.send(outPacket);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        } 
        catch (SocketException e) 
        {
            e.printStackTrace();
        }
    } 
    public void StopServer()
    {
        super.stop();
        mServerSocket.close(); 
    }
}
