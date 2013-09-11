(defproject storm_ex02 "0.0.1-SNAPSHOT"
  :repositories {"spy" "http://files.couchbase.com/maven2/"}
  :profiles {:provided {:dependencies [[storm "0.9.0-wip21"]]}}
  :dependencies [[commons-collections/commons-collections "3.2.1"]
    [org.clojure/clojure "1.4.0"]
    [org.clojure/clojure-contrib "1.2.0"]
    [spy/spymemcached "2.7.1"]]
  :main storm_ex02.core
  :aot :all
  :manifest {"Class-Path" "lib/clojure-contrib-1.2.0.jar"})
