/*
 * Copyright 2015-2020 Futeh Kao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.futeh.posng.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.*;

public class Server implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private ServerSocket serverSocket;
    private Endpoint endpoint;
    private ConnectionInfo connectionInfo;

    public Server() {
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public void shutdown() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                logger.warn("Error closing serverSocket.", e);
            }
        }
    }

    public void run() {
        while (!endpoint.isShutdown()) {
            try {
                serverSocket = createServerSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while(true) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    break;
                }
                ServerConnector connector = new ServerConnector();
                connector.setSocket(socket);
                endpoint.runEventLoop(connector);
            }
        }
    }

    protected ServerSocket createServerSocket() throws IOException {
        int port = getConnectionInfo().getPort();
        int backlog = getConnectionInfo().getBacklog();
        ServerSocket ss;
        if (connectionInfo.getSslContext() != null) {
            SSLServerSocketFactory factory = connectionInfo.getSslContext().getServerSocketFactory();
            if (getConnectionInfo().getBindAddress() != null)
                ss = factory.createServerSocket(port, backlog, InetAddress.getByName(getConnectionInfo().getBindAddress()));
            else
                ss = factory.createServerSocket(port, backlog);
            ss.setReuseAddress(true);
        } else {
            if (getConnectionInfo().getBindAddress() != null)
                ss = new ServerSocket(port, backlog, InetAddress.getByName(getConnectionInfo().getBindAddress()));
            else
                ss = new ServerSocket(port, backlog);
            ss.setReuseAddress(true);
        }
        return ss;
    }

}
