# lein-worker

A Leiningen plugin that uploads your project jar to [IronWorker](http://www.iron.io/worker).

## Setup

For user-level plugins:

Put `[lein-worker "0.1.0"]` into the `:plugins` vector of your
`:user` profile.

For project-level plugins:

Put `[lein-worker "0.1.0"]` into the `:plugins` vector of your project.clj.

This plugin relies on the [`iron_worker_ng` gem](http://dev.iron.io/worker/languages/java/).
Make sure it is installed and [configured](http://dev.iron.io/worker/reference/configuration) on your system.

## Usage

From your project directory, run:

    $ lein worker

This will:

0. Generate a `project-name.worker` file in target based on Iron.io's [Writing Workers in
   Java](http://dev.iron.io/worker/languages/java/)
0. Generate a jar in the target directory using `lein jar`
0. Upload the jar by running `iron_worker upload target/project-name.worker`

## TODO

- Allow testing the worker locally
- Run the worker remotely


## License

Copyright © 2014 Trevor Hartman

Distributed under the Eclipse Public License version 1.0.
