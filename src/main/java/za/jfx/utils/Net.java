package za.jfx.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Net {

    public static String sendPing(String ip, int timeout) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(ip);
//        String[] split = ip.split("\\.");
//        if(split.length!=4) return ip + " non ip - address\n";
//        InetAddress inetAddress = InetAddress.getByAddress(new byte[]{Byte.parseByte(split[0]),Byte.parseByte(split[1]),Byte.parseByte(split[2]),Byte.parseByte(split[3])});
        long nano = LocalTime.now().getNano();
        if (inetAddress.isReachable(timeout)) {
            return String.format("%s [%s : %s] - is active. Time - %sms\n", ip, inetAddress.getCanonicalHostName(), inetAddress.getHostAddress(), (LocalTime.now().getNano() - nano - 1500000) / 1000000);
        }
        return ip + " - isn't active\n";
    }

    public static InetAddress sendPingV1(String ip, int timeout) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(ip);
//        String[] split = ip.split("\\.");
//        if(split.length!=4) return ip + " non ip - address\n";
//        InetAddress inetAddress = InetAddress.getByAddress(new byte[]{Byte.parseByte(split[0]),Byte.parseByte(split[1]),Byte.parseByte(split[2]),Byte.parseByte(split[3])});
        long nano = LocalTime.now().getNano();
        if (inetAddress.isReachable(timeout)) {
            return inetAddress;
        }
        return null;
    }

    public static void rdpConnect(String ip) {
        if (ip == null || ip.trim().equals("")) return;
        try {
            new ProcessBuilder(System.getenv("SYSTEMROOT") + "\\System32\\mstsc.exe", "/v:" + ip).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compmgmtConnect(String ip) {
        if (ip == null || ip.trim().equals("")) return;
        try {
            new ProcessBuilder("mmc", System.getenv("SYSTEMROOT") + "\\System32\\compmgmt.msc", "/computer=" + ip).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void explorerWithShare(String ip, String resource) throws IOException {
        if(ip==null || ip.trim().equals("")) return;
        if(resource==null || resource.trim().equals("")) return;
        new ProcessBuilder("explorer.exe", "/root,\\\\" + ip + "\\" + resource).start();
    }

    public static void explorerWithShare(String ip) {
        if (ip == null || ip.trim().equals("")) return;
        try {
            explorerWithShare(ip, "c$");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<InetAddress> scanNetwork(String ip, int timeout, int rangeStart, int rangeEnd) throws IOException {
        List<InetAddress> hosts = new ArrayList<>();
        for (int i = rangeStart; i < rangeEnd; i++) {
            InetAddress inetAddressAvailable = getInetAddressAvailable(ip + i, timeout);
            if (inetAddressAvailable != null) {
                hosts.add(inetAddressAvailable);
                System.out.println(String.format("%s available", ip + i));
            } else {
                System.out.println(String.format("%s not available", ip + i));
            }

        }
        return hosts;
    }

    public static List<InetAddress> scanNetwork(String ip, int timeout) throws IOException {
        return scanNetwork(ip, timeout, 0, 256);
    }

    public static List<InetAddress> scanNetwork(String ip) throws IOException {
        return scanNetwork(ip, 1000);
    }

    private static InetAddress getInetAddressAvailable(String ip, int timeout) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(ip);
        if (inetAddress.isReachable(timeout)) {
            return inetAddress;
        }
        return null;
    }


}
