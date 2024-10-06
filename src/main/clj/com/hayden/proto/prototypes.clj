(ns com.hayden.proto.prototypes
    (:import (com.hayden.proto.proto_factory ProtoFactoryConfig))
    (:import (com.hayden.shared.config ConfigurationSupport)))

(defn first-test [first]
      (println first)
      (.new ProtoFactoryConfig)
      (.new ConfigurationSupport))

