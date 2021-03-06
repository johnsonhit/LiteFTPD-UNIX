package pers.adlered.liteftpd.wizard.init;

import pers.adlered.liteftpd.analyze.CommandAnalyze;
import pers.adlered.liteftpd.analyze.PrivateVariable;
import pers.adlered.liteftpd.user.status.bind.IPAddressBind;
import pers.adlered.liteftpd.logger.enums.Levels;
import pers.adlered.liteftpd.logger.Logger;
import pers.adlered.liteftpd.logger.enums.Types;
import pers.adlered.liteftpd.user.status.bind.IpLimitBind;

import java.io.*;
import java.net.Socket;

/**
 * <h3>LiteFTPD-UNIX</h3>
 * <p>Split Socket to a few models, and execute the functions of new thread.</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-09-19 09:21
 **/
public class SocketHandler extends Thread {
    Send send = null;
    CommandAnalyze commandAnalyze = null;
    Receive receive = null;
    PrivateVariable privateVariable = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private BufferedInputStream bufferedInputStream = null;
    private BufferedOutputStream bufferedOutputStream = null;
    private Socket socket = null;
    private IPAddressBind ipAddressBind = null;
    private String IPADD = null;
    private String SRVIPADD = null;
    private IpLimitBind ipLimitBind = null;

    public SocketHandler(Socket socket, IpLimitBind ipLimitBind) {
        try {
            IPADD = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            SRVIPADD = socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort();
            // Import data streams.
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            // Input stream use buffer, but output is not because of trans situations.
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            this.socket = socket;
            privateVariable = new PrivateVariable();
            this.ipLimitBind = ipLimitBind;
        } catch (IOException IOE) {
            // TODO
            IOE.printStackTrace();
        }
    }

    @Override
    public void run() {
        Logger.log(Types.SYS, Levels.INFO, IPADD + " has been mounted into " + Thread.currentThread());
        ipAddressBind = new IPAddressBind(IPADD, SRVIPADD);
        // Process while user quit forced or manually.
        PauseListen pauseListen = new PauseListen(
                privateVariable, socket,
                bufferedOutputStream, outputStream,
                bufferedInputStream, inputStream,
                ipAddressBind, commandAnalyze,
                receive, ipLimitBind
        );
        // Start model
        send = new Send(outputStream, pauseListen, privateVariable, ipAddressBind);
        pauseListen.setSend(send);
        commandAnalyze = new CommandAnalyze(send, SRVIPADD, privateVariable, pauseListen, ipAddressBind, ipLimitBind);
        receive = new Receive(inputStream, commandAnalyze, pauseListen, privateVariable, ipAddressBind);
        receive.start();
        pauseListen.start();
    }
}
