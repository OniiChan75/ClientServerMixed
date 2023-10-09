import java.io.*;
import java.net.*;

public class App {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Crea una socket UDP sulla porta 9876 with a timeout of 60 seconds
            socket = new DatagramSocket(8001);
            socket.setSoTimeout(60000);

            while (true) {
                byte[] receiveData = new byte[1024];

                // Crea un pacchetto DatagramPacket per ricevere i dati
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Ricevi il pacchetto dal client
                socket.receive(receivePacket);


                // Estrai i dati dalla ricezione
                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(receivedData);

                // Dividi le coordinate dalla stringa ricevuta
                String[] coordinates = receivedData.split(";");
                // System.out.println("Coordinate ricevute: " + coordinates);

                if (coordinates.length != 2) {
                    System.out.println("Pacchetto non valido: " + receivedData);
                } else {
                    // Ottieni le coordinate x e y
                    String[] coordinates1 = coordinates[0].split(",");
                    float x1 = Float.parseFloat(coordinates1[0]);
                    float y1 = Float.parseFloat(coordinates1[1]);

                    String[] coordinates2 = coordinates[1].split(",");
                    float x2 = Float.parseFloat(coordinates2[0]);
                    float y2 = Float.parseFloat(coordinates2[1]);

                    // Crea una risposta con le coordinate ricevute
                    // String response = "Coordinate ricevute: X = " + x1 + ", Y = " + y1 + "\nCoordinate ricevute: X = " + x2 + ", Y = " + y2;
                    float distance = (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
                    String response = String.valueOf(distance);
                    byte[] responseData = response.getBytes();

                    // Crea un pacchetto DatagramPacket per inviare la risposta al client
                    DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length,
                            receivePacket.getAddress(), receivePacket.getPort());

                    // Invia la risposta al client
                    socket.send(sendPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
