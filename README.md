# Lens Impl

Propose various `Lens` implementation in scala and compare performance with vanilla scala

## Run

*   `sbt "bench/jmh:runMain lensimpl.bench.Main s"` replace `s` by `m` or `l` to get more precise results
*   `sbt "bench/jmh:run -i 3 -wi 3 -f1 -t1 .*Lens.*"`