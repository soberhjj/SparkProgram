# 集合

scala中的集合分为不可变集合（scala.collection.immutable）和可变集合（scala.collection.mutable）。不可变集合是指集合本身不能动态变化，类似java的数组是不可以动态增长的。可变集合是指集合本身能动态变化，类似java的ArrayList是可以动态增长的。

scala的集合有三大类，分别是序列Seq、集Set、映射Map。scala对于几乎所有的集合类都同时提供了可变和不可变的版本。如果不指定所使用的包名，那么在默认情况下scala会使用不可变集合。

# 隐式转换
## 隐式转换函数

```
隐式转换函数是以implicit关键字声明的带单个参数的函数。隐式函数将会自动应用，将值从一种类型转换为另一种类型。
注意隐式转换函数在作用域内生效。 

隐式转换函数的注意事项和细节：
1.隐式转换函数的函数名可以是任意的，隐式转换函数与函数名称无关，只与函数签名（即函数参数类型和函数返回值类型）有关。
2.隐式转换函数可以有多个（即：隐式转换函数列表），但是需要保证在当前环境下只有一个隐式转换函数能被识别

```

## 用隐式转换丰富类库功能（即给类动态增加功能）

## 隐式值

## 隐式类

## 隐式的转换时机

- 当方法中的参数的类型与目标类型不一致时
- 当某个对象调用了不属于该对象所属的类的方法时，编译器会尝试自动将对象进行隐式转换（根据类型 ）

## 隐式转换的前提

- 不能存在二义性
- 隐式操作不能嵌套

