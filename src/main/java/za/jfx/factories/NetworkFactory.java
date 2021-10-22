package za.jfx.factories;

import za.jfx.model.jfx.Network;

import java.net.InetAddress;
import java.time.LocalDateTime;

public class NetworkFactory {

    public static Network fromInetAddress(InetAddress inetAddress){
        Network network = new Network();
        network.setIpAdress(inetAddress.getHostAddress());
        network.setHostName(inetAddress.getHostName());
        network.setHostFullName(inetAddress.getCanonicalHostName());
        network.setDatePoll(LocalDateTime.now());
        return network;
    }

}
