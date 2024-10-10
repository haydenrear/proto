(ns com.hayden.proto.prototypes
  (:import (com.hayden.proto.proto_factory ProtoFactoryConfig))
  (:import (com.hayden.shared.config ConfigurationSupport))
  (:require [clojure.reflect :as reflect]))

(defn first-test [first]
  {:docstring "Some testing"
   :test      (defn do-test []
                (println "hello")
                (first-test "one"))}
  (println first)
  (reflect/type-reflect first))

