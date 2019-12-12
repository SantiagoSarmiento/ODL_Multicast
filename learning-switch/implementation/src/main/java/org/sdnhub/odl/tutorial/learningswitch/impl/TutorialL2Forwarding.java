/*
 fecha 25-09-2019
 * Copyright (C) 2015 SDN Hub

 Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 You may not use this file except in compliance with this License.
 You may obtain a copy of the License at

    http://www.gnu.org/licenses/gpl-3.0.txt

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 implied.

 *
 */

/*
 Modificaciones al proyecto de SDN Hub
 Se agrega funcionalidades para encaminar el tráfico multicast bajo el análisis de 
 paquetes IGMP
 
 Autor: Santiago Sarmiento Sotomayor
 Fecha: Agosto 2019
 
 */
package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;
import org.opendaylight.yangtools.concepts.Registration;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.sdnhub.odl.tutorial.utils.GenericTransactionUtils;
import org.sdnhub.odl.tutorial.utils.PacketParsingUtils;
import org.sdnhub.odl.tutorial.utils.inventory.InventoryUtils;
import org.sdnhub.odl.tutorial.utils.openflow13.MatchUtils;
import org.sdnhub.odl.tutorial.utils.openflow13.InstructionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import java.math.BigInteger; 


public class TutorialL2Forwarding  implements AutoCloseable, PacketProcessingListener {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final static long FLOOD_PORT_NUMBER = 0xfffffffbL;

    //Val 
    private String[] Blocked_host_IP = {"10.0.0.5","10.0.0.6"};
    private List<String> ID_FLUJO = new ArrayList<String>();
    List<Uri> Block = new ArrayList<Uri>();
    private Map<String, List<Uri>> MulticastTable = new HashMap<String,List<Uri>>();
    
    //Members specific to this class
    private Map<String, NodeConnectorId> macTable = new HashMap <String, NodeConnectorId>();
  	private String function = "multicast";
        
      //Members related to MD-SAL operations
  	private List<Registration> registrations;
  	private DataBroker dataBroker;
  	private PacketProcessingService packetProcessingService;
	
    public TutorialL2Forwarding(DataBroker dataBroker, NotificationProviderService notificationService, RpcProviderRegistry rpcProviderRegistry) {
    	//Store the data broker for reading/writing from inventory store
        this.dataBroker = dataBroker;

        //Get access to the packet processing service for making RPC calls later
        this.packetProcessingService = rpcProviderRegistry.getRpcService(PacketProcessingService.class);        

    	//List used to track notification (both data change and YANG-defined) listener registrations
    	this.registrations = Lists.newArrayList(); 

        //Register this object for receiving notifications when there are PACKET_INs
        registrations.add(notificationService.registerNotificationListener(this));
  	}

    @Override
    public void close() throws Exception {
        for (Registration registration : registrations) {
        	registration.close();
        }
        registrations.clear();
    }

    @Override
	  public void onPacketReceived(PacketReceived notification) {
    	LOG.trace("Received packet notification {}", notification.getMatch());

        NodeConnectorRef ingressNodeConnectorRef = notification.getIngress();
        NodeRef ingressNodeRef = InventoryUtils.getNodeRef(ingressNodeConnectorRef);
        NodeConnectorId ingressNodeConnectorId = InventoryUtils.getNodeConnectorId(ingressNodeConnectorRef);
        NodeId ingressNodeId = InventoryUtils.getNodeId(ingressNodeConnectorRef);

        // Useful to create it beforehand 
      	NodeConnectorId floodNodeConnectorId = InventoryUtils.getNodeConnectorId(ingressNodeId, FLOOD_PORT_NUMBER);
      	NodeConnectorRef floodNodeConnectorRef = InventoryUtils.getNodeConnectorRef(floodNodeConnectorId);

        /*
         * Logic:
         * 0. Ignore LLDP packets
         * 1. If behaving as "hub", perform a PACKET_OUT with FLOOD action
         * 2. Else if behaving as "learning switch",
         *    2.1. Extract MAC addresses
         *    2.2. Update MAC table with source MAC address
         *    2.3. Lookup in MAC table for the target node connector of dst_mac
         *         2.3.1 If found, 
         *               2.3.1.1 perform FLOW_MOD for that dst_mac through the target node connector
         *               2.3.1.2 perform PACKET_OUT of this packet to target node connector
         *         2.3.2 If not found, perform a PACKET_OUT with FLOOD action
         */

    	  //Ignore LLDP packets, or you will be in big trouble
        byte[] etherTypeRaw = PacketParsingUtils.extractEtherType(notification.getPayload());
        int etherType = (0x0000ffff & ByteBuffer.wrap(etherTypeRaw).getShort());
        String etherTypeString = PacketParsingUtils.rawToString(etherTypeRaw,2);
        if (etherType == 0x88cc) {
        	return;
        }
	      
        // Hub implementation
        if (function.equals("hub")) {
        	
        	//flood packet (1)
            packetOut(ingressNodeRef, floodNodeConnectorRef, notification.getPayload());
        } else {
            byte[] payload = notification.getPayload();
            byte[] dstMacRaw = PacketParsingUtils.extractDstMac(payload);
            byte[] srcMacRaw = PacketParsingUtils.extractSrcMac(payload);

            //Extract MAC addresses (2.1)
            String srcMac = PacketParsingUtils.rawMacToString(srcMacRaw);
            String dstMac = PacketParsingUtils.rawMacToString(dstMacRaw);

            //Learn source MAC address (2.2)
            this.macTable.put(srcMac, ingressNodeConnectorId);

            //Lookup destination MAC address in table (2.3)
            NodeConnectorId egressNodeConnectorId = this.macTable.get(dstMac) ;

            //If found (2.3.1)
            if (egressNodeConnectorId != null) {
                programL2Flow(ingressNodeId, dstMac, srcMac, ingressNodeConnectorId, egressNodeConnectorId);
                NodeConnectorRef egressNodeConnectorRef = InventoryUtils.getNodeConnectorRef(egressNodeConnectorId);
                packetOut(ingressNodeRef, egressNodeConnectorRef, payload);
            } else {
            	//2.3.2 Flood packet
                packetOut(ingressNodeRef, floodNodeConnectorRef, payload);
            }
       
        }
		
        //------------------------------------------------------------
        //-----------------------IGMPv3-------------------------------
        //------------------------------------------------------------
    	  byte[] protoTypeRaw = PacketParsingUtils.extractProtoType(notification.getPayload());	
        String protoTypeString = PacketParsingUtils.rawToString(protoTypeRaw,1);
           
      	if(protoTypeString.equals("02")){
          List<Uri> PortSwitch = new ArrayList<Uri>();
    			byte[] payload = {};
    			payload = notification.getPayload();
    			LOG.debug("Paquete IGMP  -------------------->");
    			byte[] recordTypeRaw = PacketParsingUtils.extractRecordType(payload);
    			byte[] IGMP_TypeRaw = PacketParsingUtils.extractTypeIGMP(payload);
    			byte[] dstMacRaw = PacketParsingUtils.extractDstMac(payload);
    			byte[] srcMacRaw = PacketParsingUtils.extractSrcMac(payload);
    			byte[] dstIPRaw = PacketParsingUtils.extractDSTIP(payload);
    			byte[] srcIPRaw = PacketParsingUtils.extractSRCIP(payload);
    			byte[] srcIP_MULTICASTRaw = PacketParsingUtils.extractMulticastIP(payload);
          byte[] numSrcRaw = PacketParsingUtils.extractNumSrc(payload);
          
    
    			String srcMac = PacketParsingUtils.rawMacToString(srcMacRaw);
    			String dstMac = PacketParsingUtils.rawMacToString(dstMacRaw);
    			String dstIp = PacketParsingUtils.rawIPToString(dstIPRaw);
    			String srcIp = PacketParsingUtils.rawIPToString(srcIPRaw);
    			int srcIP_key = PacketParsingUtils.rawIPtoInt(srcIPRaw);
    			String IP_Group_Multicast = PacketParsingUtils.rawIPToString(srcIP_MULTICASTRaw);	
    			String recordTypeString = PacketParsingUtils.rawToString(recordTypeRaw,1);
    			String IGMP_Type = PacketParsingUtils.rawToString(IGMP_TypeRaw,1);
          String numSrc = PacketParsingUtils.rawToString(numSrcRaw,2);
          String INDEX = "";
          String codex = "";
  		
          if(!numSrc.equals("0000")){//Cuando las fuentes sean diferentes de 0
            byte [] ServerIPRaw = PacketParsingUtils.extractServerIP(payload);
            String ServerIP = PacketParsingUtils.rawIPToString(ServerIPRaw);
            INDEX = ServerIP+"-"+IP_Group_Multicast;
  		      codex = recordTypeString+ServerIP;
            LOG.debug("****************************");
            LOG.debug("Escuchar fuente especifica IP " + ServerIP);
            LOG.debug("****************************");
          }else{//Cuando las fuentes sean 0, entiendase como LEAVE GROUP
            INDEX = IP_Group_Multicast;
  		      codex = "---";
            LOG.debug("****************************");
            LOG.debug("Todas las fuentes");
            LOG.debug("****************************");
          }
          LOG.debug("****************************");
          LOG.debug("START MULTICAST FORWARDING");
    			LOG.debug("Paquete IGMP type: 0x" + IGMP_Type);
    			LOG.debug("Paquete Record type: 0x" + recordTypeString);
          LOG.debug("****************************");
          
  
    			//Solicitud JOIN
    			if( recordTypeString.equals("04") || recordTypeString.equals("05") || (recordTypeString.equals("03")&&!numSrc.equals("0000")) ){       
    			  if(ID_FLUJO.contains(INDEX)){//Flujos existentes
    				  LOG.debug("Peticion a grupo multicast IP: " + IP_Group_Multicast + " -> Existente");
    					PortSwitch = MulticastTable.get(INDEX);
    				  LOG.debug("Puertos existentes: "+PortSwitch);
      				if(!PortSwitch.contains(ingressNodeConnectorId)){
                if(ACL(Blocked_host_IP, srcIp)){
        					PortSwitch.add(ingressNodeConnectorId);   
        				  LOG.debug("IP Multicast: "+ IP_Group_Multicast+" Puerto: " + ingressNodeConnectorId + " -> Agregado");
                }
      				}else{LOG.debug("IP Multicast: "+ IP_Group_Multicast+" Puerto: " + ingressNodeConnectorId + " -> Existente");}
    				  LOG.debug("Total Puertos en "+IP_Group_Multicast+" -> " +Integer.toString(PortSwitch.size()));
    			  }else{//Nuevos flujos				
              if(ACL(Blocked_host_IP, srcIp)){
                LOG.debug("Peticion a grupo multicast IP: " + IP_Group_Multicast + " -> Nuevo");
                ID_FLUJO.add(INDEX);
      				  PortSwitch.add(ingressNodeConnectorId);
              }
            }
            LOG.debug("CREANDO FLUJO MULTICAST ------------------>");
            LOG.debug("VERIFICACION FLUJOS ACTUALES "+ID_FLUJO);
            LOG.debug("****************************");
            if (ACL(Blocked_host_IP, srcIp)){//Evita crear flujos con action 0 en caso de host bloqueados
              MulticastTable.put(INDEX, PortSwitch);//Agregando puertos a Hashmap
    		      multicast(ingressNodeId,IP_Group_Multicast,MulticastTable.get(INDEX),codex);
            }
            
            //packetOut(ingressNodeRef, floodNodeConnectorRef, payload);
            
    			}// FIN Solicitud JOIN
  
    			//Solicitud LEAVE
    			else if(recordTypeString.equals("06") || (recordTypeString.equals("03")&&numSrc.equals("0000")) ){
     				LOG.debug("Salir de grupo multicast IP: " + IP_Group_Multicast);
    			  if(ID_FLUJO.contains(INDEX)){
      				PortSwitch = MulticastTable.get(INDEX);
              PortSwitch.remove(ingressNodeConnectorId);
      				if(PortSwitch.isEmpty()){
      				  //Borrar el flujo de IP_Group_Multicast
                ID_FLUJO.remove(INDEX);
                //Borrar el flujo 
                multicastDelete(ingressNodeId,IP_Group_Multicast,ingressNodeConnectorId,codex,5);
      				}else{
        				LOG.debug("Flujo Multicast: "+ INDEX +" Puerto: " + ingressNodeConnectorId + " -> Eliminado");
        				LOG.debug("VERIFICACION FLUJOS ACTUALES "+ID_FLUJO);
                LOG.debug("****************************");
        				MulticastTable.put(INDEX, PortSwitch);//Actualizando puertos de Hashmap
        				//Actualizar regla con salida para el key con puerto a 0 
        				multicastDelete(ingressNodeId,IP_Group_Multicast,ingressNodeConnectorId,codex,0);
              }
    			  }
               
    			}// FIN Solicitud LEAVE
  
  		  } 
        //------------------------------------------------------------
        //-----------------------IGMPv3-------------------------------
        //------------------------------------------------------------
    }//FIN 
  
  /*
  Funcion para el control de acceso de los host.
  - Dato de ingreso: Un valor que identifiquen a cada host
    - Opciones: El valor del puerto 
                La IP de cada host
                
  - Devuelve un booleano   
  */
    private boolean ACL(String[] NoPermitidos, String Host_IP ){
    // TO DO
    // Actualizar la lista de permitidos en el inventario
    // Leer la lista desde el inventario 
    // De esta manera se pueden realizar peticiones REST modificando
    // la lista de permitidos en ODL, y se puede hacer un control dinamico
    // 
    LOG.debug("****************************");

    List<String> Blocked = Arrays.asList(NoPermitidos);
    if(Blocked.contains(Host_IP)){
      LOG.debug("HOST BLOQUEADO -> " + Host_IP);
      LOG.debug("****************************");
      return false;
    }else{
      LOG.debug("HOST PERMITIDO -> " + Host_IP);
      LOG.debug("****************************");
      return true;
    }

  }
    
	private void multicast(NodeId nodeId, String GroupMulticast,List<Uri> Port,String codex){
		String ID = "";
		Ipv4Prefix GroupMulticast_IP = new Ipv4Prefix(GroupMulticast+"/32");		
   
    //Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();

    if( ("05".equals(codex.substring(0,2))) || ("03".equals(codex.substring(0,2))) ){
			Ipv4Prefix ServerMulticast_IP = new Ipv4Prefix(codex.substring(2,codex.length())+"/32");
      MatchUtils.preconfigL3IPv4Match(matchBuilder);
      Ipv4MatchBuilder ipv4match = new Ipv4MatchBuilder();
      ipv4match.setIpv4Source(ServerMulticast_IP);
      ipv4match.setIpv4Destination(GroupMulticast_IP);
      matchBuilder.setLayer3Match(ipv4match.build());
      
			ID = codex.substring(2,codex.length())+"-"+GroupMulticast;
      LOG.debug("----------------------------------");
      LOG.debug("SET IP de servidor Multicast: " + codex.substring(2,codex.length()));
		}else{
      MatchUtils.createDstL3IPv4Match(matchBuilder, GroupMulticast_IP);
			ID = GroupMulticast;
      LOG.debug("CODEX ---");
		}
		
		// Instructions List Stores Individual Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = Lists.newArrayList();
		InstructionBuilder ib = new InstructionBuilder();
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		ActionBuilder ab = new ActionBuilder();
		List<Action> actionList = Lists.newArrayList();
    LOG.debug("Instructions List ---");
	  
      for(int i=0;i<Port.size();i++){
        String key = "" + Port.get(i);
        LOG.debug("NODE_ID --- " + nodeId);
        LOG.debug("KEY ORIGINAL --- " + key);
        String s = key.substring(key.indexOf(":")+1,key.indexOf("]"));
        String sw = s.substring(0,s.indexOf(":"));
        String host = s.substring(s.indexOf(":")+1);
        BigInteger keyflow = new BigInteger(sw+host);
        LOG.debug("KEY ID FLOW --- " + sw + "-"+ host);
  			OutputActionBuilder output = new OutputActionBuilder();
  			output.setOutputNodeConnector(Port.get(i));
  			output.setMaxLength(65535); //Send full packet and No buffer
  			ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
  			ab.setOrder(keyflow.intValue());
  			ab.setKey(new ActionKey(keyflow.intValue()));
  			actionList.add(ab.build());
  		}
  	  
  		// Create Apply Actions Instruction
  		aab.setAction(actionList);
  		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
  		ib.setOrder(0);
  		ib.setKey(new InstructionKey(0));
  		instructions.add(ib.build());
      LOG.debug("Create Apply Actions ---");

		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();
		flowBuilder.setMatch(matchBuilder.build());
    
    
		String flowId = "L3_Multicast_Join:"+ ID;
		flowBuilder.setId(new FlowId(flowId));
		FlowKey key = new FlowKey(new FlowId(flowId));
		flowBuilder.setBarrier(true);
		flowBuilder.setTableId((short)0);
		flowBuilder.setKey(key);
		flowBuilder.setPriority(61111);
		flowBuilder.setFlowName(flowId);
		flowBuilder.setHardTimeout(0);
		flowBuilder.setIdleTimeout(0);
		flowBuilder.setInstructions(isb.setInstruction(instructions).build());
		LOG.debug("Create Flow ---");
   
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
		
	}

  private void multicastDelete(NodeId nodeId, String GroupMulticast,Uri Port, String codex, int timeout){
		Ipv4Prefix GroupMulticast_IP = new Ipv4Prefix(GroupMulticast+"/32");
    String ID = "";
        
		//Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();
		MatchUtils.createDstL3IPv4Match(matchBuilder, GroupMulticast_IP);
   
    if("06".equals(codex.substring(0,2))){
			Ipv4Prefix ServerMulticast_IP = new Ipv4Prefix(codex.substring(2,codex.length())+"/32");
      MatchUtils.preconfigL3IPv4Match(matchBuilder);
      Ipv4MatchBuilder ipv4match = new Ipv4MatchBuilder();
      ipv4match.setIpv4Source(ServerMulticast_IP);
      ipv4match.setIpv4Destination(GroupMulticast_IP);
      matchBuilder.setLayer3Match(ipv4match.build());
      
			ID = codex.substring(2,codex.length())+"-"+GroupMulticast;
		}else{
      MatchUtils.createDstL3IPv4Match(matchBuilder, GroupMulticast_IP);
			ID = GroupMulticast;
      LOG.debug("CODEX ---");
		}
		
		// Instructions List Stores Individual Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = Lists.newArrayList();
		InstructionBuilder ib = new InstructionBuilder();
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		ActionBuilder ab = new ActionBuilder();
		List<Action> actionList = Lists.newArrayList();

    String keyID = "" + Port;
    String s = keyID.substring(keyID.indexOf(":")+1,keyID.indexOf("]"));
    String sw = s.substring(0,s.indexOf(":"));
    String host = s.substring(s.indexOf(":")+1);
    BigInteger keyflow = new BigInteger(sw+host);
		OutputActionBuilder output = new OutputActionBuilder();
		output.setOutputNodeConnector(new Uri ("openflow:"+sw+":0"));
		output.setMaxLength(65535); //Send full packet and No buffer
		ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
		ab.setOrder(keyflow.intValue());
		ab.setKey(new ActionKey(keyflow.intValue()));
		actionList.add(ab.build());
  	
  
  	// Create Apply Actions Instruction
  	aab.setAction(actionList);
  	ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
  	ib.setOrder(0);
  	ib.setKey(new InstructionKey(0));
  	instructions.add(ib.build());
     
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();
		flowBuilder.setMatch(matchBuilder.build());

		String flowId = "L3_Multicast_Join:"+ ID;
		flowBuilder.setId(new FlowId(flowId));
		FlowKey key = new FlowKey(new FlowId(flowId));
		flowBuilder.setBarrier(true);
		flowBuilder.setTableId((short)0);
		flowBuilder.setKey(key);
		flowBuilder.setPriority(61111);
		flowBuilder.setFlowName(flowId);
		flowBuilder.setHardTimeout(timeout);
		flowBuilder.setIdleTimeout(0);
		flowBuilder.setInstructions(isb.setInstruction(instructions).build());
		
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
		
	}
    
    
  	private void packetOut(NodeRef egressNodeRef, NodeConnectorRef egressNodeConnectorRef, byte[] payload) {
      Preconditions.checkNotNull(packetProcessingService);
      LOG.debug("Flooding packet of size {} out of port {}", payload.length, egressNodeConnectorRef);

      //Construct input for RPC call to packet processing service
      TransmitPacketInput input = new TransmitPacketInputBuilder()
              .setPayload(payload)
              .setNode(egressNodeRef)
              .setEgress(egressNodeConnectorRef)
              .build();
      packetProcessingService.transmitPacket(input);       
    }
	
    private void programL2Flow(NodeId nodeId, String dstMac, String srcMac, NodeConnectorId ingressNodeConnectorId, NodeConnectorId egressNodeConnectorId) {

    	/* Programming a flow involves:
    	 * 1. Creating a Flow object that has a match and a list of instructions,
    	 * 2. Adding Flow object as an augmentation to the Node object in the inventory. 
    	 * 3. FlowProgrammer module of OpenFlowPlugin will pick up this data change and eventually program the switch.
    	 */
        
        Ipv4Prefix ServerMulticast_IP = new Ipv4Prefix("10.2.2.2"+"/32");
        //Creating match object
        MatchBuilder matchBuilder = new MatchBuilder();
        
        MatchUtils.createEthDstMatch(matchBuilder, new MacAddress(dstMac), null);
        MatchUtils.createInPortMatch(matchBuilder, ingressNodeConnectorId);
        //MatchUtils.createSrcL3IPv4Match(matchBuilder, ServerMulticast_IP);

        // Instructions List Stores Individual Instructions
        InstructionsBuilder isb = new InstructionsBuilder();
        List<Instruction> instructions = Lists.newArrayList();
        InstructionBuilder ib = new InstructionBuilder();
        ApplyActionsBuilder aab = new ApplyActionsBuilder();
        ActionBuilder ab = new ActionBuilder();
        List<Action> actionList = Lists.newArrayList();

        // Set output action
        OutputActionBuilder output = new OutputActionBuilder();
        output.setOutputNodeConnector(egressNodeConnectorId);
        output.setMaxLength(65535); //Send full packet and No buffer
        ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
        ab.setOrder(0);
        ab.setKey(new ActionKey(0));
        actionList.add(ab.build());

        // Create Apply Actions Instruction
        aab.setAction(actionList);
        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
        ib.setOrder(0);
        ib.setKey(new InstructionKey(0));
        instructions.add(ib.build());

        // Create Flow
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.setMatch(matchBuilder.build());

        String flowId = "L2_Rule_" + dstMac + "_"+srcMac;
        flowBuilder.setId(new FlowId(flowId));
        FlowKey key = new FlowKey(new FlowId(flowId));
        flowBuilder.setBarrier(true);
        flowBuilder.setTableId((short)0);
        flowBuilder.setKey(key);
        flowBuilder.setPriority(32768);
        flowBuilder.setFlowName(flowId);
        flowBuilder.setHardTimeout(0);
        flowBuilder.setIdleTimeout(0);
        flowBuilder.setInstructions(isb.setInstruction(instructions).build());

        InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
                .child(Node.class, new NodeKey(nodeId))
                .augmentation(FlowCapableNode.class)
                .child(Table.class, new TableKey(flowBuilder.getTableId()))
                .child(Flow.class, flowBuilder.getKey())
                .build();
        GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
    }
}
