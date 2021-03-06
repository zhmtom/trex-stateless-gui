/**
 * *****************************************************************************
 * Copyright (c) 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************
 */
package com.exalttech.trex.simulator.profiles;

import com.exalttech.trex.packets.TrexEthernetPacket;
import com.exalttech.trex.packets.TrexIpV4Packet;
import static com.exalttech.trex.simulator.profiles.AbstractIPV4Test.LOG;
import com.exalttech.trex.simulator.models.PacketData;
import com.exalttech.trex.ui.views.streams.builder.Payload;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.IpV4Packet.Builder;
import org.pcap4j.packet.namednumber.IpNumber;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * IPV4 profile test
 *
 * @author Georgekh
 */
public class IPV4Test extends AbstractIPV4Test {

    @DataProvider(name = "packetData")
    public Object[][] packetData() throws IOException, URISyntaxException {
        return prepareTestData("Ipv4PcapData.json");
    }
    
    @Test(dataProvider = "packetData", dependsOnGroups = {"init"})
    public void testIPV4Profile(PacketData packetData) throws IOException, InterruptedException, PcapNativeException, NotOpenException, URISyntaxException {
        TrexEthernetPacket ethernetPacket = buildIPV4Packet(packetData);

        // prepare and save yaml data
        LOG.info("Prepare and save Yaml file");
        packetUtil.prepareAndSaveYamlFile(ethernetPacket.getPacket().getRawData(), packetData);

        //Generate pcap files
        LOG.info("Generate Pcap file for " + packetData.getTestFileName() + ".yaml");
        packetUtil.generatePcapFile(packetData.getTestFileName());

        // compare pcaps
        boolean result = packetUtil.comparePcaps(packetData.getTestFileName(), "generated_" + packetData.getTestFileName());
        Assert.assertEquals(result, true, "Invalid generated " + packetData.getTestFileName() + " pcap. ");
    }

    /**
     * Create IPV4 packet
     *
     * @param totalLength
     * @param packetLength
     * @param payload
     * @param packetData
     * @return IPV4 packet
     * @throws java.net.UnknownHostException
     */
    @Override
    protected Builder getIPV4PacketBuilder(int totalLength, int packetLength, Payload payload, PacketData packetData) throws UnknownHostException {

        LOG.info("Create IPV4 Packet");
        TrexIpV4Packet ipV4Packet = prepareIPV4Packet(totalLength, packetLength, payload, packetData);

        // build IPV4 packet
        ipV4Packet.buildPacket(null, IpNumber.getInstance((byte) 0));

        return ipV4Packet.getBuilder();
    }
}
