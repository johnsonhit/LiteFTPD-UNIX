package pers.adlered.liteftpd.wizard.init;

import pers.adlered.liteftpd.analyze.CommandAnalyze;
import pers.adlered.liteftpd.analyze.PrivateVariable;
import pers.adlered.liteftpd.user.status.bind.IPAddressBind;
import pers.adlered.liteftpd.logger.enums.Levels;
import pers.adlered.liteftpd.logger.Logger;
import pers.adlered.liteftpd.logger.enums.Types;
import pers.adlered.liteftpd.tool.AutoInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * <h3>LiteFTPD-UNIX</h3>
 * <p>To receive commands.</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-09-19 09:21
 **/
public class Receive extends Thread {
    private InputStream inputStream = null;
    private CommandAnalyze commandAnalyze = null;
    private PauseListen pauseListen = null;
    private PrivateVariable privateVariable = null;
    private IPAddressBind ipAddressBind = null;

    public Receive(InputStream inputStream, CommandAnalyze commandAnalyze, PauseListen pauseListen, PrivateVariable privateVariable, IPAddressBind ipAddressBind) {
        this.inputStream = inputStream;
        this.commandAnalyze = commandAnalyze;
        this.pauseListen = pauseListen;
        this.privateVariable = privateVariable;
        this.ipAddressBind = ipAddressBind;
    }

    @Override
    public void run() {
        try {
            // READ
            AutoInputStream autoInputStream = new AutoInputStream(inputStream, 1024, privateVariable);
            while (true) {
                String autoLine = autoInputStream.readLineAuto();
                if (!pauseListen.isRunning()) {
                    Logger.log(Types.RECV, Levels.WARN, "Receive stopped.");
                    break;
                }
                Logger.log(Types.RECV, Levels.INFO, ipAddressBind.getIPADD() + " ==> " + ipAddressBind.getSRVIPADD() + ": " + autoLine.replaceAll("\r|\n", ""));
                commandAnalyze.analyze(autoLine);
            }
        } catch (IOException IOE) {
            // TODO
            IOE.printStackTrace();
        }
    }


}
