(ns storm-ex02.core
  (:import [backtype.storm LocalCluster]
           [net.spy.memcached MemcachedClient]
           [java.net InetSocketAddress])
  (:use [backtype.storm clojure config])
  (:use [clojure.contrib.def :only [defn-memo]])
  (:gen-class))

(defn- create-client
    [& {:keys [host port]
              :or {host "localhost" port 22133}}]
       (MemcachedClient. (list (InetSocketAddress. host port))))

(defn-memo default-client
               [& {:keys [host port]
                   :or {host "localhost" port 22133}}]
                    (create-client host port))

(defn get-item
    [queue-name]
    (.get (default-client) queue-name))

(defspout initial-number ["number"]
  [conf context collector]
  (spout
    (nextTuple []
      (Thread/sleep 100)
        (let [item (get-item "teste")]         
          (if item
            (emit-spout! collector [(read-string item)]))))))

(defbolt count1 ["add-number"]
  [tuple collector]
        (let [number (.getLong tuple 0)]
          (emit-bolt! collector [(+ 1 number)])))

(defbolt count2 ["add-number2"]
  [tuple collector]
    (let [number (.getLong tuple 0)]
      (emit-bolt! collector [(+ 1 number)])))

(defn mk-topology []
  (topology
    {"number-spout" (spout-spec initial-number)}
    {"adder-bolt-1" (bolt-spec {"number-spout" :shuffle} count1 :p 1)}))
    ;"adder-bolt-2" (bolt-spec {"adder-bolt-1" :shuffle} count2 :p 1)}))

(defn run-local! []
  (let [cluster (LocalCluster.)]
    (.submitTopology cluster "reduce-counter" {TOPOLOGY-DEBUG true} (mk-topology))
    (Thread/sleep 50000)
    (.shutdown cluster)))

(defn -main []
   (run-local!))
