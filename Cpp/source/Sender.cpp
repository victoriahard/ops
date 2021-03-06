/**
 *
 * Copyright (C) 2006-2009 Anton Gravestam.
 *
 * This file is part of OPS (Open Publish Subscribe).
 *
 * OPS (Open Publish Subscribe) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * OPS (Open Publish Subscribe) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OPS (Open Publish Subscribe).  If not, see <http://www.gnu.org/licenses/>.
 */
#include "OPSTypeDefs.h"
#include "Sender.h"
#include "UDPSender.h"
#include "TCPServer.h"
#include <map>

namespace ops
{

    Sender* Sender::create(std::string localInterface, int ttl, __int64 outSocketBufferSize)
    {
        return new UDPSender(localInterface, ttl, outSocketBufferSize, true);
    }

    Sender* Sender::createUDPSender(std::string localInterface, int ttl, __int64 outSocketBufferSize)
    {
        return new UDPSender(localInterface, ttl, outSocketBufferSize, false);
    }

    Sender* Sender::createTCPServer(std::string ip, int port, IOService* ioService, __int64 outSocketBufferSize)
    {
		///LA Can't have a static storage of TCPServers with only port as key,
		/// this will not work if the same port is used on two different IP addresses.
		/// Besides, there is already a store of TCPSendDataHandlers, 
		/// so when we come here we should always create a new TCPServer

        //static std::map<int, TCPServer*> tcpSenderInstances;

        //TCPServer* newInstance = NULL;
        //if (tcpSenderInstances.find(port) == tcpSenderInstances.end())
        //{
        //    newInstance = new TCPServer(ip, port, ioService);
        //    tcpSenderInstances[port] = newInstance;
        //}
        //return tcpSenderInstances[port];

		return new TCPServer(ip, port, ioService, outSocketBufferSize);
    }


}
