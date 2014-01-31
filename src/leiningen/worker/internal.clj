(ns leiningen.worker.internal
  (:require
    [me.raynes.conch :refer [programs]]
    [me.raynes.conch.low-level :as sh]
    [clojure.string :as s]
    [leiningen.core.main :refer [abort]]))

;; shell interop

(programs iron_worker lein)

(defn print-output-seq [s] (dorun (map println s)))

;; utils

(defn fatal [& format-and-args]
  (println (apply format format-and-args))
  (abort))

;; jar utils

(defn jar-info [project]
  (let [version  (:version project)
        target-dir (:target-path project)
        project-name (:name project) ]
    {:version version
     :worker-filename (format "%s/%s.worker" target-dir project-name)
     :target-dir target-dir
     :project-name project-name
     :jar-file-name (format "%s/%s-%s-standalone.jar" target-dir project-name version)}))

;; prepare

(defn- write-worker-file [{:keys [target-dir project-name jar-file-name worker-filename]}]
  (println "generate .worker file at" worker-filename)
  (spit worker-filename
        (format
"
runtime 'java'
# exec is the file that will be executed when you queue a task
exec '%s'
"
          jar-file-name)))

(defn- generate-uberjar [_] (print-output-seq (lein "uberjar" {:seq true})))

(def prepare-worker (comp generate-uberjar write-worker-file))

;; upload

(defn upload-worker [{:keys [worker-filename]}]
  (print-output-seq (iron_worker "upload" worker-filename {:seq true})))

;; run locally

(defn run-local-worker [args {:keys [worker-filename]}]
  (println "iron_worker run" worker-filename "--payload" (first args))
  (print-output-seq (iron_worker "run" worker-filename "--payload" (first args) {:seq true})))
