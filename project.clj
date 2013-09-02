(defproject storm-ex01 "0.1.0-SNAPSHOT"
  :repositories {"spy" "http://files.couchbase.com/maven2/"}
  :dependencies [[commons-collections/commons-collections "3.2.1"]
    [storm "0.8.2"]
    [org.clojure/clojure "1.4.0"]
    [org.clojure/clojure-contrib "1.2.0"]
    [spy/spymemcached "2.7.1"]]
  :main storm-ex02.core)
