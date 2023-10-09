import java.io.*;
import java.net.*;

public class App {
    public static void main(String[] args) {
        // Server IP address and port
        String serverIp = "192.168.1.61";
        int serverPort = 8001;

        DatagramSocket clientSocket = null;

        try {
            // Create a UDP socket
            clientSocket = new DatagramSocket();

            // Set a timeout (100 milliseconds in this example)
            int timeout = 10000;
            clientSocket.setSoTimeout(timeout);

            InetAddress serverAddress = InetAddress.getByName(serverIp);

            // Coordinate input from the client
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter latitude1: ");
            String latitude1 = inputReader.readLine();
            System.out.print("Enter longitude1: ");
            String longitude1 = inputReader.readLine();
            System.out.print("Enter latitude2: ");
            String latitude2 = inputReader.readLine();
            System.out.print("Enter longitude2: ");
            String longitude2 = inputReader.readLine();
            // String latitude1 = "45.4642700";
            // String longitude1 = "9.1895100";
            // String latitude2 = "45.8558900";
            // String longitude2 = "9.3970400";

            // Create a string with coordinates
            String coordinates = latitude1 + "," + longitude1 + ";" + latitude2 + "," + longitude2;
            System.out.println(coordinates);

            // Convert the coordinates to bytes
            byte[] sendData = coordinates.getBytes();

            // Create a DatagramPacket to send the data to the server
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            // Send data to the server
            clientSocket.send(sendPacket);

            // Create a buffer to receive the response
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            // Receive response from the server
            clientSocket.receive(receivePacket);

            // Convert the received data to a string
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
    }
}
