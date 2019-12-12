package org.opendaylight.yang.gen.v1.urn.sdnhub.tutorial.odl.tap.rev150601;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.sdnhub.tutorial.odl.tap.rev150601.tap.grouping.MacAddressMatch;
import java.util.List;
import org.opendaylight.yang.gen.v1.urn.sdnhub.tutorial.odl.tap.rev150601.tap.grouping.IpAddressMatch;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;tap&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/tap.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * grouping tap-grouping {
 *     leaf node {
 *         type leafref;
 *     }
 *     leaf-list src-node-connector {
 *         type leafref;
 *     }
 *     leaf-list sink-node-connector {
 *         type leafref;
 *     }
 *     container mac-address-match {
 *         leaf type {
 *             type field-type;
 *         }
 *         leaf value {
 *             type mac-address;
 *         }
 *     }
 *     container ip-address-match {
 *         leaf type {
 *             type field-type;
 *         }
 *         leaf value {
 *             type ipv4-prefix;
 *         }
 *     }
 *     leaf traffic-match {
 *         type traffic-type;
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;tap/tap-grouping&lt;/i&gt;
 *
 */
public interface TapGrouping
    extends
    DataObject
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:sdnhub:tutorial:odl:tap","2015-06-01","tap-grouping"));

    NodeId getNode();
    
    List<NodeConnectorId> getSrcNodeConnector();
    
    List<NodeConnectorId> getSinkNodeConnector();
    
    MacAddressMatch getMacAddressMatch();
    
    IpAddressMatch getIpAddressMatch();
    
    TrafficType getTrafficMatch();

}

