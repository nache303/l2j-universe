/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2012.
 */

package l2p.gameserver.geodata.geoeditorcon;

import javolution.util.FastList;
import l2p.gameserver.model.Player;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Darvin
 * Date: 04.11.12
 * Time: 14:49
 */
public class GeoEditorThread extends Thread {
    private static Logger _log = Logger.getLogger(GeoEditorThread.class.getName());

    private boolean _working = false;

    private int _mode = 0; // 0 - don't send coords, 1 - send each

    // validateposition from client, 2 - send in intervals of _sendDelay ms.
    private int _sendDelay = 1000; // default - once in second

    private final Socket _geSocket;

    private OutputStream _out;

    private final FastList<Player> _gms;

    public GeoEditorThread(Socket ge) {
        _geSocket = ge;
        _working = true;
        _gms = new FastList<Player>();
    }

    @Override
    public void interrupt() {
        try {
            _geSocket.close();
        } catch (Exception e) {
        }
        super.interrupt();
    }

    @Override
    public void run() {
        try {
            _out = _geSocket.getOutputStream();
            int timer = 0;

            while (_working) {
                if (!isConnected()) {
                    _working = false;
                }

                if ((_mode == 2) && (timer > _sendDelay)) {
                    for (Player gm : _gms) {
                        if (!gm.getNetConnection().getConnection().isClosed()) {
                            sendGmPosition(gm);
                        } else {
                            _gms.remove(gm);
                        }
                    }
                    timer = 0;
                }

                try {
                    sleep(100);
                    if (_mode == 2) {
                        timer += 100;
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            _log.log(Level.WARNING, "GeoEditor disconnected. " + e.getMessage(), e);
        } finally {
            try {
                _geSocket.close();
            } catch (Exception e) {
            }
            _working = false;
        }
    }

    public void sendGmPosition(int gx, int gy, short z) {
        if (!isConnected()) {
            return;
        }
        try {
            synchronized (_out) {
                writeC(0x0b); // length 11 bytes!
                writeC(0x01); // Cmd = save cell;
                writeD(gx); // Global coord X;
                writeD(gy); // Global coord Y;
                writeH(z); // Coord Z;
                _out.flush();
            }
        } catch (Exception e) {
            _log.log(Level.WARNING, "GeoEditor disconnected. " + e.getMessage(), e);
            _working = false;
        } finally {
            try {
                _geSocket.close();
            } catch (Exception ex) {
            }
            _working = false;
        }
    }

    public void sendGmPosition(Player _gm) {
        sendGmPosition(_gm.getX(), _gm.getY(), (short) _gm.getZ());
    }

    public void sendPing() {
        if (!isConnected()) {
            return;
        }
        try {
            synchronized (_out) {
                writeC(0x01); // length 1 byte!
                writeC(0x02); // Cmd = ping (dummy packet for connection
                // test);
                _out.flush();
            }
        } catch (Exception e) {
            _log.log(Level.WARNING, "GeoEditor disconnected. " + e.getMessage(), e);
            _working = false;
        } finally {
            try {
                _geSocket.close();
            } catch (Exception ex) {
            }
            _working = false;
        }
    }

    private void writeD(int value) throws IOException {
        _out.write(value & 0xff);
        _out.write((value >> 8) & 0xff);
        _out.write((value >> 16) & 0xff);
        _out.write((value >> 24) & 0xff);
    }

    private void writeH(int value) throws IOException {
        _out.write(value & 0xff);
        _out.write((value >> 8) & 0xff);
    }

    private void writeC(int value) throws IOException {
        _out.write(value & 0xff);
    }

    public void setMode(int value) {
        _mode = value;
    }

    public void setTimer(int value) {
        if (value < 500) {
            _sendDelay = 500; // maximum - 2 times per second!
        } else if (value > 60000) {
            _sendDelay = 60000; // Minimum - 1 time per minute.
        } else {
            _sendDelay = value;
        }
    }

    public void addGM(Player gm) {
        if (!_gms.contains(gm)) {
            _gms.add(gm);
        }
    }

    public void removeGM(Player gm) {
        if (_gms.contains(gm)) {
            _gms.remove(gm);
        }
    }

    public boolean isSend(Player gm) {
        if ((_mode == 1) && _gms.contains(gm)) {
            return true;
        }
        return false;
    }

    private boolean isConnected() {
        return _geSocket.isConnected() && !_geSocket.isClosed();
    }

    public boolean isWorking() {
        sendPing();
        return _working;
    }

    public int getMode() {
        return _mode;
    }
}
