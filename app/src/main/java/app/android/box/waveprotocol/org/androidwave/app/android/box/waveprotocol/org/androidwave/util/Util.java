package app.android.box.waveprotocol.org.androidwave.app.android.box.waveprotocol.org.androidwave.util;

/**
 * Created by charith on 7/2/15.
 */
public class Util {

    /**
     * This method get Wave username as input and it split it to username and hostname
     *
     * @param username Wave user's username
     * @return hostname and username
     */
    public static String[] getHostAndUserNames(String username) {

        String[] usernameAndHost = username.split("@");

        if (usernameAndHost.length > 1) {
            return usernameAndHost;
        }

        return null;
    }

    /**
     * This method generate http url for given hostname
     *
     * @param hostname Apache Wave server name
     * @return http url of the host
     */
    public static String hostCreator(String hostname, String servlet) {

        StringBuilder hostUrl = new StringBuilder();

        if (hostname.equalsIgnoreCase("local.net")) {
            hostname = "localhost:9898";
        }

        return hostUrl.append("http://").append(hostname).append("/").append(servlet).toString();
    }

}
