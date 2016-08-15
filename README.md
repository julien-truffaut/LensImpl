# Lens Impl

Propose various `Lens` implementation in scala and compare performance with vanilla scala

## Run

*   `sbt "bench/jmh:runMain lensimpl.bench.Main s"` replace `s` by `m` or `l` to get more precise results
*   `sbt "bench/jmh:run -i 3 -wi 3 -f1 -t1 .*Lens.*"`

If you want to run the benchmarks for scala 2.12 or other prepend the command with `++<scala_version>` e.g.

`sbt "++2.12.0-M5 bench/jmh:runMain lensimpl.bench.Main s"``