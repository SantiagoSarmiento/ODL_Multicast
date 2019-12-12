package org.opendaylight.yang.gen.v1.urn.sdnhub.tutorial.odl.tap.rev150601;
import org.opendaylight.yangtools.yang.binding.DataRoot;


/**
 * Tap configuration
 *
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;tap&lt;/b&gt;
 * &lt;br&gt;Source path: &lt;i&gt;META-INF/yang/tap.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * module tap {
 *     yang-version 1;
 *     namespace "urn:sdnhub:tutorial:odl:tap";
 *     prefix "tap";
 *
 *     import opendaylight-inventory { prefix "inv"; }
 *     
 *     import yang-ext { prefix "ext"; }
 *     
 *     import ietf-inet-types { prefix "inet"; }
 *     
 *     import ietf-yang-types { prefix "yang"; }
 *     revision 2015-06-01 {
 *         description "Tap configuration
 *         ";
 *     }
 *
 *     container tap-spec {
 *         list tap {
 *             key "id"
 *             leaf id {
 *                 type string;
 *             }
 *             leaf node {
 *                 type leafref;
 *             }
 *             leaf-list src-node-connector {
 *                 type leafref;
 *             }
 *             leaf-list sink-node-connector {
 *                 type leafref;
 *             }
 *             container mac-address-match {
 *                 leaf type {
 *                     type field-type;
 *                 }
 *                 leaf value {
 *                     type mac-address;
 *                 }
 *             }
 *             container ip-address-match {
 *                 leaf type {
 *                     type field-type;
 *                 }
 *                 leaf value {
 *                     type ipv4-prefix;
 *                 }
 *             }
 *             leaf traffic-match {
 *                 type traffic-type;
 *             }
 *             leaf dl-type {
 *                 type uint16;
 *             }
 *             leaf nw-proto {
 *                 type uint8;
 *             }
 *             leaf tp-src {
 *                 type uint16;
 *             }
 *             leaf tp-dst {
 *                 type uint16;
 *             }
 *             augment \(urn:sdnhub:tutorial:odl:tap)tap-spec\(urn:sdnhub:tutorial:odl:tap)tap {
 *                 status CURRENT;
 *                 leaf dl-type {
 *                     type uint16;
 *                 }
 *                 leaf nw-proto {
 *                     type uint8;
 *                 }
 *                 leaf tp-src {
 *                     type uint16;
 *                 }
 *                 leaf tp-dst {
 *                     type uint16;
 *                 }
 *             }
 *             uses tap-grouping;
 *         }
 *     }
 *
 *     grouping tap-grouping {
 *         leaf node {
 *             type leafref;
 *         }
 *         leaf-list src-node-connector {
 *             type leafref;
 *         }
 *         leaf-list sink-node-connector {
 *             type leafref;
 *         }
 *         container mac-address-match {
 *             leaf type {
 *                 type field-type;
 *             }
 *             leaf value {
 *                 type mac-address;
 *             }
 *         }
 *         container ip-address-match {
 *             leaf type {
 *                 type field-type;
 *             }
 *             leaf value {
 *                 type ipv4-prefix;
 *             }
 *         }
 *         leaf traffic-match {
 *             type traffic-type;
 *         }
 *     }
 *
 *     augment \(urn:sdnhub:tutorial:odl:tap)tap-spec\(urn:sdnhub:tutorial:odl:tap)tap {
 *         status CURRENT;
 *         leaf dl-type {
 *             type uint16;
 *         }
 *         leaf nw-proto {
 *             type uint8;
 *         }
 *         leaf tp-src {
 *             type uint16;
 *         }
 *         leaf tp-dst {
 *             type uint16;
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 *
 */
public interface TapData
    extends
    DataRoot
{




    TapSpec getTapSpec();

}

