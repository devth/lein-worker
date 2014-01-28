(ns leiningen.worker
  (:require
    [clojure.pprint :refer [pprint]]
    [clojure.java.shell :as sh]
    [clojure.string :as s]))

(defn- fatal [msg]
  (println msg)
  (leiningen.core.main/abort))

(defn sh! [command-str]
  (println command-str)
  (let [args (s/split command-str #" ")
        res (apply sh/sh args)]
    (.println System/out (:out res))
    (.println System/err (:err res))
    (when-not (zero? (:exit res))
      (fatal "failed to execute %s\n%s" args res))))

(defn write-worker-file [target-dir project-name jar-file-name]
  (spit (format "%s/%s.worker" target-dir project-name)
        (format
"
runtime 'java'
# exec is the file that will be executed when you queue a task
exec '%s'
"
          jar-file-name)))

(defn- generate-jar [project]
  (sh! "lein jar"))

(defn- upload-worker [target-dir project-name]
  (sh! (format "iron_worker upload %s/%s.worker" target-dir project-name)))

(defn- prepare-and-upload-worker [project]
  (let [version  (get project :version)
        ; target-path for lein2, target-dir or jar-dir for lein1
        target-dir (:target-path project)
        project-name (:name project)
        jar-file-name (format "%s/%s-%s.jar" target-dir project-name version)]
    (write-worker-file target-dir project-name jar-file-name)
    (generate-jar project)
    ; upload
    (upload-worker target-dir project-name)
    (println "DONE\nManage your worker at https://hud.iron.io/dashboard")))


(defn worker
  "Ensure we're in the root then kick off the worker upload"
  [project & args]
  (if (:root project)
    (prepare-and-upload-worker project)
    (fatal "Must be run in the root of a Leiningen project.")))
