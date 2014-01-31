(defproject lein-worker "0.1.2-SNAPSHOT"
  :description "A plugin to upload worker jars to Iron Worker"
  :url "https://github.com/devth/lein-worker"
  :lein-release {:deploy-via :clojars}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [me.raynes/conch "0.6.0"]]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true)
