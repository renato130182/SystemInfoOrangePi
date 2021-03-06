/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import com.pi4j.system.SystemInfo;
import com.pi4j.system.SystemInfo.BoardType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Apenas para OrangePi
 * @author renato.soares
 */
public class HardwareInfo {
    private String serialNumber;
    private String cpuRevision;
    private String cpuArchitecture;
    private String cpuPart;
    private float cpuTemperature;
    private String processor;
    private String hardware;
    private String hardwareRevision;
    private boolean hardFloatAbi;
    private BoardType boardType;
    private int intervaloTemperatura=1000;
    
    private List<ActionListener> listeners = new ArrayList<ActionListener>(0);
    Timer timer = new Timer();
    public void addActionListener(ActionListener listener) {
		this.listeners.add(listener);
	}
    
    public HardwareInfo() throws PlatformAlreadyAssignedException{
            
        try {
            PlatformManager.setPlatform(Platform.ORANGEPI);
            
            serialNumber=SystemInfo.getSerial();
            cpuRevision=SystemInfo.getCpuRevision();
            cpuArchitecture=SystemInfo.getCpuArchitecture();
            cpuPart=SystemInfo.getCpuPart();
            cpuTemperature=SystemInfo.getCpuTemperature();
            processor=SystemInfo.getProcessor();
            hardware=SystemInfo.getHardware();
            hardwareRevision=SystemInfo.getRevision();
            hardFloatAbi=SystemInfo.isHardFloatAbi();
            boardType=SystemInfo.getBoardType();
        } catch (IOException ex) {
            Logger.getLogger(HardwareInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HardwareInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedOperationException ex) {
            Logger.getLogger(HardwareInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run() {
            MonitorTemperatura();
        }
    }, intervaloTemperatura, intervaloTemperatura);    
     
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getCpuRevision() {
        return cpuRevision;
    }

    public String getCpuArchitecture() {
        return cpuArchitecture;
    }

    public String getCpuPart() {
        return cpuPart;
    }

    public Float getCpuTemperature() {
        return cpuTemperature;
    }

    public String getProcessor() {
        return processor;
    }

    public String getHardware() {
        return hardware;
    }

    public String getHardwareRevision() {
        return hardwareRevision;
    }

    public boolean getHardFloatAbi() {
        return hardFloatAbi;
    }

    public BoardType getBoardType() {
        return boardType;
    }
    
    public void MonitorTemperatura(){
        try {
            cpuTemperature=SystemInfo.getCpuTemperature();
        } catch (IOException | InterruptedException | NumberFormatException | UnsupportedOperationException ex) {
            Logger.getLogger(HardwareInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        listeners.forEach((listener) -> {
            listener.actionPerformed(new ActionEvent(this,2,Float.toString(cpuTemperature)));
        });
    }
    
    public int getIntervaloTemperatura() {
        return intervaloTemperatura;
    }

    public void setIntervaloTemperatura(int intervaloTemperatura) {
        this.intervaloTemperatura = intervaloTemperatura;
    }
    
}
