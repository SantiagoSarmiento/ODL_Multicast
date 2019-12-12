package org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration;
import org.opendaylight.yangtools.yang.binding.Augmentation;
import org.opendaylight.yangtools.yang.binding.AugmentationHolder;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.learning._switch.impl.RpcRegistry;
import org.opendaylight.yangtools.yang.binding.DataObject;
import java.util.HashMap;
import org.opendaylight.yangtools.concepts.Builder;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.learning._switch.impl.NotificationService;
import org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.learning._switch.impl.DataBroker;
import java.util.Collections;
import java.util.Map;


/**
 * Class that builds {@link org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl} instances.
 *
 * @see org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl
 *
 */
public class LearningSwitchImplBuilder implements Builder <org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl> {

    private DataBroker _dataBroker;
    private NotificationService _notificationService;
    private RpcRegistry _rpcRegistry;

    Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> augmentation = Collections.emptyMap();

    public LearningSwitchImplBuilder() {
    }

    public LearningSwitchImplBuilder(LearningSwitchImpl base) {
        this._dataBroker = base.getDataBroker();
        this._notificationService = base.getNotificationService();
        this._rpcRegistry = base.getRpcRegistry();
        if (base instanceof LearningSwitchImplImpl) {
            LearningSwitchImplImpl impl = (LearningSwitchImplImpl) base;
            if (!impl.augmentation.isEmpty()) {
                this.augmentation = new HashMap<>(impl.augmentation);
            }
        } else if (base instanceof AugmentationHolder) {
            @SuppressWarnings("unchecked")
            AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl> casted =(AugmentationHolder<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>) base;
            if (!casted.augmentations().isEmpty()) {
                this.augmentation = new HashMap<>(casted.augmentations());
            }
        }
    }


    public DataBroker getDataBroker() {
        return _dataBroker;
    }
    
    public NotificationService getNotificationService() {
        return _notificationService;
    }
    
    public RpcRegistry getRpcRegistry() {
        return _rpcRegistry;
    }
    
    @SuppressWarnings("unchecked")
    public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
        if (augmentationType == null) {
            throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
        }
        return (E) augmentation.get(augmentationType);
    }

    public LearningSwitchImplBuilder setDataBroker(DataBroker value) {
        this._dataBroker = value;
        return this;
    }
    
    public LearningSwitchImplBuilder setNotificationService(NotificationService value) {
        this._notificationService = value;
        return this;
    }
    
    public LearningSwitchImplBuilder setRpcRegistry(RpcRegistry value) {
        this._rpcRegistry = value;
        return this;
    }
    
    public LearningSwitchImplBuilder addAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> augmentationType, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl> augmentation) {
        if (augmentation == null) {
            return removeAugmentation(augmentationType);
        }
    
        if (!(this.augmentation instanceof HashMap)) {
            this.augmentation = new HashMap<>();
        }
    
        this.augmentation.put(augmentationType, augmentation);
        return this;
    }
    
    public LearningSwitchImplBuilder removeAugmentation(java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> augmentationType) {
        if (this.augmentation instanceof HashMap) {
            this.augmentation.remove(augmentationType);
        }
        return this;
    }

    public LearningSwitchImpl build() {
        return new LearningSwitchImplImpl(this);
    }

    private static final class LearningSwitchImplImpl implements LearningSwitchImpl {

        public java.lang.Class<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl> getImplementedInterface() {
            return org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl.class;
        }

        private final DataBroker _dataBroker;
        private final NotificationService _notificationService;
        private final RpcRegistry _rpcRegistry;

        private Map<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> augmentation = Collections.emptyMap();

        private LearningSwitchImplImpl(LearningSwitchImplBuilder base) {
            this._dataBroker = base.getDataBroker();
            this._notificationService = base.getNotificationService();
            this._rpcRegistry = base.getRpcRegistry();
            switch (base.augmentation.size()) {
            case 0:
                this.augmentation = Collections.emptyMap();
                break;
            case 1:
                final Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> e = base.augmentation.entrySet().iterator().next();
                this.augmentation = Collections.<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>>singletonMap(e.getKey(), e.getValue());
                break;
            default :
                this.augmentation = new HashMap<>(base.augmentation);
            }
        }

        @Override
        public DataBroker getDataBroker() {
            return _dataBroker;
        }
        
        @Override
        public NotificationService getNotificationService() {
            return _notificationService;
        }
        
        @Override
        public RpcRegistry getRpcRegistry() {
            return _rpcRegistry;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <E extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> E getAugmentation(java.lang.Class<E> augmentationType) {
            if (augmentationType == null) {
                throw new IllegalArgumentException("Augmentation Type reference cannot be NULL!");
            }
            return (E) augmentation.get(augmentationType);
        }

        private int hash = 0;
        private volatile boolean hashValid = false;
        
        @Override
        public int hashCode() {
            if (hashValid) {
                return hash;
            }
        
            final int prime = 31;
            int result = 1;
            result = prime * result + ((_dataBroker == null) ? 0 : _dataBroker.hashCode());
            result = prime * result + ((_notificationService == null) ? 0 : _notificationService.hashCode());
            result = prime * result + ((_rpcRegistry == null) ? 0 : _rpcRegistry.hashCode());
            result = prime * result + ((augmentation == null) ? 0 : augmentation.hashCode());
        
            hash = result;
            hashValid = true;
            return result;
        }

        @Override
        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataObject)) {
                return false;
            }
            if (!org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl.class.equals(((DataObject)obj).getImplementedInterface())) {
                return false;
            }
            org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl other = (org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl)obj;
            if (_dataBroker == null) {
                if (other.getDataBroker() != null) {
                    return false;
                }
            } else if(!_dataBroker.equals(other.getDataBroker())) {
                return false;
            }
            if (_notificationService == null) {
                if (other.getNotificationService() != null) {
                    return false;
                }
            } else if(!_notificationService.equals(other.getNotificationService())) {
                return false;
            }
            if (_rpcRegistry == null) {
                if (other.getRpcRegistry() != null) {
                    return false;
                }
            } else if(!_rpcRegistry.equals(other.getRpcRegistry())) {
                return false;
            }
            if (getClass() == obj.getClass()) {
                // Simple case: we are comparing against self
                LearningSwitchImplImpl otherImpl = (LearningSwitchImplImpl) obj;
                if (augmentation == null) {
                    if (otherImpl.augmentation != null) {
                        return false;
                    }
                } else if(!augmentation.equals(otherImpl.augmentation)) {
                    return false;
                }
            } else {
                // Hard case: compare our augments with presence there...
                for (Map.Entry<java.lang.Class<? extends Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>>, Augmentation<org.opendaylight.yang.gen.v1.urn.sdnhub.odl.tutorial.learning._switch.learning._switch.impl.rev150605.modules.module.configuration.LearningSwitchImpl>> e : augmentation.entrySet()) {
                    if (!e.getValue().equals(other.getAugmentation(e.getKey()))) {
                        return false;
                    }
                }
                // .. and give the other one the chance to do the same
                if (!obj.equals(this)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public java.lang.String toString() {
            java.lang.StringBuilder builder = new java.lang.StringBuilder ("LearningSwitchImpl [");
            boolean first = true;
        
            if (_dataBroker != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_dataBroker=");
                builder.append(_dataBroker);
             }
            if (_notificationService != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_notificationService=");
                builder.append(_notificationService);
             }
            if (_rpcRegistry != null) {
                if (first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append("_rpcRegistry=");
                builder.append(_rpcRegistry);
             }
            if (first) {
                first = false;
            } else {
                builder.append(", ");
            }
            builder.append("augmentation=");
            builder.append(augmentation.values());
            return builder.append(']').toString();
        }
    }

}
