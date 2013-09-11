(ns storm_ex02.core
  (:import [backtype.storm StormSubmitter LocalCluster]
           [java.net InetSocketAddress])
  (:use [backtype.storm clojure config])
  (:use [clojure.contrib.def :only [defn-memo]])
  
  (:gen-class :name storm_ex02.core))


(defspout initial-number ["number"]
  [conf context collector]
  (spout
    (nextTuple []
      (Thread/sleep 100)
      ;(let [item (get-item "teste")]         
      ;(if item
      (emit-spout! collector [123]))))

(defbolt count1 ["add-number"]
  [tuple collector]
    (let [number (.getLong tuple 0)]
      (emit-bolt! collector [(+ 1 number)])))

(defn mk-topology []
  (topology
    {"number-spout" (spout-spec initial-number)}
    {"adder-bolt-1" (bolt-spec {"number-spout" :shuffle} count1 :p 1)}))

(defn run-local! []
	(let [cluster (LocalCluster.)]
		(.submitTopology cluster "reduce-counter" {TOPOLOGY-DEBUG true} (mk-topology))
		(Thread/sleep 50000)
		(.shutdown cluster)))

(defn submit-topology! [name]
  (StormSubmitter/submitTopology
   name
   {TOPOLOGY-DEBUG true
    TOPOLOGY-WORKERS 1}
   (mk-topology)))

(defn -main
  ([]
   (run-local!))
  ([name]
   (submit-topology! name)))
