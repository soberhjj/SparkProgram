# 集合

scala中的集合分为不可变集合（scala.collection.immutable）和可变集合（scala.collection.mutable）。不可变集合是指集合本身不能动态变化，类似java的数组是不可以动态增长的。可变集合是指集合本身能动态变化，类似java的ArrayList是可以动态增长的。

scala的集合有三大类，分别是序列Seq、集Set、映射Map。scala对于几乎所有的集合类都同时提供了可变和不可变的版本。如果不指定所使用的包名，那么在默认情况下scala会使用不可变集合。



