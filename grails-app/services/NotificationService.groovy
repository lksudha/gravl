import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration

class NotificationService {

    boolean transactional = false

    def sendChat(String to, String msg) {

        log.debug "Sending notification to: [${to}]"
        ConnectionConfiguration cc = new ConnectionConfiguration(
                ConfigurationHolder.config.chat.host, ConfigurationHolder.config.chat.port)
        XMPPConnection connection = new XMPPConnection(cc)
        connection.login(ConfigurationHolder.config.chat.username,
                ConfigurationHolder.config.chat.password)
        
        // might want to do Roster goodness to see if they're online now...
        connection.createChat(to).sendMessage(msg);

    }
}