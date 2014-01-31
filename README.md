# lein-worker

A Leiningen plugin that uploads your project jar to [IronWorker](http://www.iron.io/worker).

## Installation

This plugin relies on the [`iron_worker_ng`](http://dev.iron.io/worker/languages/java/) gem.
Make sure it is installed and [configured](http://dev.iron.io/worker/reference/configuration) on your system.

```clojure
[lein-worker "0.1.1"]
```

Put the dependency in the `:plugins` vector of either your project.clj or your
`:user` profile.


## Usage

From your project directory, run:

    $ lein worker

This will:

0. Generate a `project-name.worker` file in target based on Iron.io's [Writing Workers in
   Java](http://dev.iron.io/worker/languages/java/)
0. Generate an uberjar in the target directory using `lein uberjar`
0. Upload the jar by running `iron_worker upload target/project-name.worker`

Individual subtasks also exist.

```
$ lein help worker
Default behavior for `lein worker` will prepare a workerfile and uberjar then upload it to IronWorker.
Use available subtasks for more fine-grained control.

Subtasks available:
run         Run remote worker on IronWorker
prepare     Preapre a workerfile and uberjar
upload      Upload uberjar to IronWorker
run-local   Run the worker locally

Run `lein help worker $SUBTASK` for subtask details.

Arguments: ([run] [prepare] [upload] [run-local])
```


## TODO

- Run the worker remotely


## License

Copyright © 2014 Trevor Hartman

Distributed under the Eclipse Public License version 1.0.
