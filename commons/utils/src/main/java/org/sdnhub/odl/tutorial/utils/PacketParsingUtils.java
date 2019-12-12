/**
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
 /**
  * Editado por Santiago Sarmiento Sotomayor
  * Agosto 2019
  * Se crean nuevas funciones con el objetivo de identificar los parametros de
  * un paquete IGMPv3.
  */ package org.sdnhub.odl.tutorial.utils; import java.util.Arrays; import 
org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress; 
/**
 *
 */ public abstract class PacketParsingUtils {
	/********************************************/
	/********************************************/
	/**
	 * Identificar si un paquete es IGMP en la cabecera IP
	 */
	private static final int PROTOCOL_TYPE_START_POSITION = 23;
	
	private static final int PROTOCOL_TYPE_END_POSITION = 24;
	
	private static final int RECORD_TYPE_START_POSITION = 46;
	
	private static final int RECORD_TYPE_END_POSITION = 47;
	
	private static final int MULTICAST_START_POSITION = 50;
	
	private static final int MULTICAST_END_POSITION = 54;
	
	private static final int SRC_IP_START_POSITION = 26;
	
	private static final int SRC_IP_END_POSITION = 30;
	
	private static final int DST_IP_START_POSITION = 30;
	
	private static final int DST_IP_END_POSITION = 34;
 
  private static final int IGMP_TYPE_START_POSITION = 38;
  
  private static final int IGMP_TYPE_END_POSITION = 39;
  
  private static final int NUM_SRC_START_POSITION = 48;
  
  private static final int NUM_SRC_END_POSITION = 50;
  
  private static final int SERVER_MULTICAST_START_POSITION = 54;
  
  private static final int SERVER_MULTICAST_END_POSITION = 58;
  
	/********************************************/
	/********************************************/
    /**
     * size of MAC address in octets (6*8 = 48 bits)
     */
    private static final int MAC_ADDRESS_SIZE = 6;
    /**
     * start position of destination MAC address in array
     */
    private static final int DST_MAC_START_POSITION = 0;
    /**
     * end position of destination MAC address in array
     */
    private static final int DST_MAC_END_POSITION = 6;
    /**
     * start position of source MAC address in array
     */
    private static final int SRC_MAC_START_POSITION = 6;
    /**
     * end position of source MAC address in array
     */
    private static final int SRC_MAC_END_POSITION = 12;
    /**
     * start position of ethernet type in array
     */
    private static final int ETHER_TYPE_START_POSITION = 12;
    /**
     * end position of ethernet type in array
     */
    private static final int ETHER_TYPE_END_POSITION = 14;
    private PacketParsingUtils() {
        //prohibite to instantiate this class
    }
	/********************************************/
	/********************************************/
	/**
	 * Extrae el valor del siguiente protocolo dentro de la cabecera IP
     * @param payload
     * @return source ProtoType 
     */
    public static byte[] extractProtoType(final byte[] payload) {
        return Arrays.copyOfRange(payload, PROTOCOL_TYPE_START_POSITION, PROTOCOL_TYPE_END_POSITION);
    }
	
	  /**
	   * Extrae el valor RECORD TYPE de la cabecera IGMP
     * @param payload
     * @return source Record Type
     */
    public static byte[] extractRecordType(final byte[] payload) {
        return Arrays.copyOfRange(payload, RECORD_TYPE_START_POSITION, RECORD_TYPE_END_POSITION);
    }
    /**
	   * Extrae la IP multicast
     * @param payload
     * @return source IP Multicast
     */
    public static byte[] extractMulticastIP(final byte[] payload) {
        return Arrays.copyOfRange(payload, MULTICAST_START_POSITION, MULTICAST_END_POSITION);
    }
    
    /**
	   * Extrae la IP del servidor multicast
     * @param payload
     * @return source IP from server
     */
    public static byte[] extractServerIP(final byte[] payload) {
        return Arrays.copyOfRange(payload, SERVER_MULTICAST_START_POSITION, SERVER_MULTICAST_END_POSITION);
    }
	
	  /**
	   * Extrae la IP FUENTE
     * @param payload
     * @return source Source IP
     */
    public static byte[] extractSRCIP(final byte[] payload) {
        return Arrays.copyOfRange(payload, SRC_IP_START_POSITION, SRC_IP_END_POSITION);
    }
	
	  /**
	   * Extrae la IP DESTINO
     * @param payload
     * @return source Destination IP
     */
    public static byte[] extractDSTIP(final byte[] payload) {
        return Arrays.copyOfRange(payload, DST_IP_START_POSITION, DST_IP_END_POSITION);
    }
    
    /**
	   * Extrae el tipo de paquete IGMP
     * @param payload
     * @return source Type of IGMP 
     */
    public static byte[] extractTypeIGMP(final byte[] payload) {
        return Arrays.copyOfRange(payload, IGMP_TYPE_START_POSITION, IGMP_TYPE_END_POSITION);
    }
    
    /**
	   * Extrae el numero de fuentes del primer group record
     * @param payload
     * @return number of source to listen  
     */
    public static byte[] extractNumSrc(final byte[] payload) {
        return Arrays.copyOfRange(payload, NUM_SRC_START_POSITION, NUM_SRC_END_POSITION);
    }
    
    /**
	   * Extrae cualquier valor
     * @param payload
     * @return valor 
     */
    public static byte[] extract(final byte[] payload, int initial_index, int final_index) {
        return Arrays.copyOfRange(payload, initial_index, final_index);
    }
	/********************************************/
	/********************************************/
	
    /**
     * @param payload
     * @return destination MAC address
     */
    public static byte[] extractDstMac(final byte[] payload) {
        return Arrays.copyOfRange(payload, DST_MAC_START_POSITION, DST_MAC_END_POSITION);
    }
    /**
     * @param payload
     * @return source MAC address
     */
    public static byte[] extractSrcMac(final byte[] payload) {
        return Arrays.copyOfRange(payload, SRC_MAC_START_POSITION, SRC_MAC_END_POSITION);
    }
    /**
     * @param payload
     * @return source MAC address
     */
    public static byte[] extractEtherType(final byte[] payload) {
        return Arrays.copyOfRange(payload, ETHER_TYPE_START_POSITION, ETHER_TYPE_END_POSITION);
    }
	
	/********************************************/
	/********************************************/
	/**
	 * Convierte el rawIP a String
     * @param rawIP
     * @return {@link MacAddress} wrapping string value, baked upon binary MAC
     * address
     */
	
	public static String rawIPToString(byte[] rawIP) {
        if (rawIP != null && rawIP.length == 4) {
            //StringBuffer sb = new StringBuffer();
			StringBuffer ip = new StringBuffer();
            for (byte octet : rawIP) {
				int IP = Integer.parseInt(String.format("%02X", octet),16);
				ip.append(".");
				ip.append(Integer.toString(IP));
            }
            return ip.substring(1);
        }
        return null;
    }
	
   /**
	 * Convierte el raw a String
     * @param raw
     * @return {@link MacAddress} wrapping string value, baked upon binary MAC
     * address
     */
   public static String rawToString(byte[] raw, int raw_length) {
        if (raw != null && raw.length == raw_length) {
            StringBuffer sb = new StringBuffer();
            for (byte octet : raw) {
                sb.append(String.format("%02X", octet));
            }
            return sb.substring(0);
        }
        return null;
    }
    
    /**
	 * Convierte el rawIP a INT
     * @param raw
     * @return {@link ip} 
     * address
     */
    public static Integer rawIPtoInt(byte[] rawIP_Host) {
        if (rawIP_Host != null && rawIP_Host.length == 4) {
            //StringBuffer sb = new StringBuffer();
			StringBuffer ip = new StringBuffer();
            for (byte octet : rawIP_Host) {
				int IP = Integer.parseInt(String.format("%02X", octet),16);
				ip.append(Integer.toString(IP));
            }
            return Integer.parseInt(ip.substring(0));
        }
        return null;
    }
    
    
    public static Integer stringIPtoInt(String IP_Host) {
        if (IP_Host != null ) {
            String[] parts = IP_Host.split("\\.");
			      int IP = Integer.parseInt(parts[0]+parts[1]+parts[2]+parts[3]);
            return IP;
        }
        return null;
    }
    
	/********************************************/
	/********************************************/
    /**
     * @param rawMac
     * @return {@link MacAddress} wrapping string value, baked upon binary MAC
     * address
     */
	 
	/** NO SE UTILIZA EN EL FICHERO TutorialL2Forwarding.java*/
    public static MacAddress rawMacToMac(final byte[] rawMac) {
        MacAddress mac = null;
        if (rawMac != null && rawMac.length == MAC_ADDRESS_SIZE) {
            StringBuilder sb = new StringBuilder();
            for (byte octet : rawMac) {
                sb.append(String.format(":%02X", octet));
            }
            mac = new MacAddress(sb.substring(1));
        }
        return mac;
    }
    
    public static String rawMacToString(byte[] rawMac) {
        if (rawMac != null && rawMac.length == 6) {
            StringBuffer sb = new StringBuffer();
            for (byte octet : rawMac) {
                sb.append(String.format(":%02X", octet));
            }
            return sb.substring(1);
        }
        return null;
    }
	
	/** NO SE UTILIZA EN EL FICHERO TutorialL2Forwarding.java*/
    public static byte[] stringMacToRawMac(String address) {
        String[] elements = address.split(":");
        if (elements.length != MAC_ADDRESS_SIZE) {
            throw new IllegalArgumentException(
                    "Specified MAC Address must contain 12 hex digits" +
                    " separated pairwise by :'s.");
        }
        byte[] addressInBytes = new byte[MAC_ADDRESS_SIZE];
        for (int i = 0; i < MAC_ADDRESS_SIZE; i++) {
            String element = elements[i];
            addressInBytes[i] = (byte)Integer.parseInt(element, 16);
        }
        return addressInBytes;
    }
}
