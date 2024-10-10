(ns com.hayden.proto.prototypes-test
  (:require [clojure.test :refer :all])
  (:require [com.hayden.proto.prototypes :refer :all]))

(deftest test-first-test
  (testing "first-test"
    (is (= "one" (first-test String)))))

