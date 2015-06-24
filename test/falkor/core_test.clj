(ns falkor.core-test
  (:require [clojure.test :refer :all]
            [falkor.parser :refer :all]))

(deftest test-compiles
  (testing "A OK"
    (is (= 1 1))))
