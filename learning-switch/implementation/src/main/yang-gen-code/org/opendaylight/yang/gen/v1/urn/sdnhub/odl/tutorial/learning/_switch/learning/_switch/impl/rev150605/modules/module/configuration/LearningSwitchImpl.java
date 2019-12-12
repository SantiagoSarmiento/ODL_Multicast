package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.learning._switch.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.controller.config.rev130405.modules.module.Configuration;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.learning._switch.impl.NotificationService;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.learning._switch.impl.DataBroker;
import org.opendaylight.yangtools.yang.binding.Augmentable;


/**
 * &lt;p&gt;This class represents the following YANG schema fragment defined in module &lt;b&gt;learning-switch-impl&lt;/b&gt;
 * &lt;br&gt;(Source path: &lt;i&gt;META-INF/yang/learning-switch-impl.yang&lt;/i&gt;):
 * &lt;pre&gt;
 * case learning-switch-impl {
 *     container data-broker {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:learning-switch:learning-switch-impl?revision=2015-06-05)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container notification-service {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:learning-switch:learning-switch-impl?revision=2015-06-05)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 *     container rpc-registry {
 *         leaf type {
 *             type leafref;
 *         }
 *         leaf name {
 *             type leafref;
 *         }
 *         uses service-ref {
 *             refine (urn:sdnhub:odl:tutorial:learning-switch:learning-switch-impl?revision=2015-06-05)type {
 *                 leaf type {
 *                     type leafref;
 *                 }
 *             }
 *         }
 *     }
 * }
 * &lt;/pre&gt;
 * The schema path to identify an instance is
 * &lt;i&gt;learning-switch-impl/modules/module/configuration/(urn:sdnhub:odl:tutorial:learning-switch:learning-switch-impl?revision=2015-06-05)learning-switch-impl&lt;/i&gt;
 *
 */
public interface LearningSwitchImpl
    extends
    DataObject,
    Augmentable<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>,
    Configuration
{



    public static final QName QNAME = org.opendaylight.yangtools.yang.common.QName.cachedReference(org.opendaylight.yangtools.yang.common.QName.create("urn:sdnhub:odl:tutorial:learning-switch:learning-switch-impl","2015-06-05","learning-switch-impl"));

    DataBroker getDataBroker();
    
    NotificationService getNotificationService();
    
    RpcRegistry getRpcRegistry();

}

