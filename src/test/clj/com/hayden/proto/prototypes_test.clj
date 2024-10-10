(ns com.hayden.proto.prototypes-test
  (:require [clojure.test :refer :all])
  (:require [com.hayden.proto.prototypes :refer :all])
  (:import (clojure.reflect AsmReflector)
           (com.hayden.proto.proto_factory ProtoFactoryConfig))
  (:require [clojure.pprint :as pp]
            [clojure.reflect :as reflect]))

(deftest test-first-test
  (testing "first-test"
    (is (= #{:final :public} (get (first-test String) :flags)))))

(deftest test-first-test
  (testing "first-test"
    (is (= (first-test ProtoFactoryConfig) :flags))))

(defn p [k] (println k))

(deftest parse-method-annotations
  (testing "first-test"
    (dorun (map (fn [k] (p k))
                (map
                  (fn [v] (:name v))
                  (filter (fn [v] (not (empty? (re-matches #".*Method.*" (str v)))))
                          (seq (get (reflect/type-reflect ProtoFactoryConfig) :members))))))))

; parse the user classes
; generate the prototypes and validators using gen-class (with serialization)
;  - introspect rules from the class metadata available
;  - generate prototype implementations (recursively adapted from top-level objects to ending points in the model)
;  -
; load them into memory for returning prototypes from GraphQL for descriptors of the services and data model
;  - GraphQL prototype metadata retrieved from these objects and provided as a descriptor of the API and the underlying
;    data - for example the allowable values, etc.
