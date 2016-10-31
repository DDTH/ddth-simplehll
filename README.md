ddth-simplehll
==============

Simplify the usage of HyperLogLog library.

Project home:
[https://github.com/DDTH/ddth-simplehll](https://github.com/DDTH/ddth-simplehll)


There are many excellent HLL libraries. This small library is _not_ another HLL implementation. Its aim is to simplify the usage of
various HLL libraries, by:

- Wrap a unified and simple API over other HLL libraries.
- Default parameters for each HLL library that balance between accuracy and storage.


## License ##

See LICENSE.txt for details. Copyright (c) 2016 Thanh Ba Nguyen.

Third party libraries are distributed under their own license(s).


## Installation ##

Latest release version: `0.1.2`. See [RELEASE-NOTES.md](RELEASE-NOTES.md).

Maven dependency: if only a sub-set of `ddth-simplehll` functionality is used, choose the corresponding
dependency artifact(s) to reduce the number of unused jar files.

*ddth-simplehll-core*: include [Prasanth Jayachandran's HLL implementation](https://github.com/prasanthj/hyperloglog).

```xml
<dependency>
	<groupId>com.github.ddth</groupId>
	<artifactId>ddth-simplehll-core</artifactId>
	<version>0.1.2</version>
</dependency>
```

*ddth-simplehll-ak*: include *ddth-simplehll-core* and [Aggregate Knowledge's HLL implementation](https://github.com/aggregateknowledge/java-hll).

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-simplehll-al</artifactId>
    <version>0.1.2</version>
    <type>pom</type>
</dependency>
```

*ddth-simplehll-ats*: include *ddth-simplehll-core* and [AddThis' Stream HLL implementation](https://github.com/addthis/stream-lib).

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-simplehll-ats</artifactId>
    <version>0.1.2</version>
    <type>pom</type>
</dependency>
```

*ddth-simplehll-all*: include all HLL implementations.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-simplehll-all</artifactId>
    <version>0.1.2</version>
    <type>pom</type>
</dependency>
```


## Usage ##

Initialize a `IHLL` instance:
```java
IHLL hll = new PjHll().init();  //Prasanth Jayachandran implementation

IHLL hll = new AkHll().init();  //Aggregate Knowledge implementation

IHLL hll = new AtsHll().init(); // or AddThis Stream implementation
```

Add items and count cardinality: 
```java
//add an item
hll.add(obj);

//count number of distinct items
long cardinality = hll.count();
```

Merge/Union:
```
hll.merge(anotherHLL);
```

Serialize && Deserialize:
```
byte[] data = HLLUtils.toBytes();

IHLL hll = HLLUtils.fromBytes(data);
```


## Comparison of HLL implementations ##

See http://koff.io/posts/comparison-of-hll/ and [COMPARE.md](COMPARE.md).


## Credits ##

- AddThis Stream: https://github.com/addthis/stream-lib
- Aggregate Knowledge HLL: https://github.com/aggregateknowledge/java-hll
- Prasanth Jayachandran HLL: https://github.com/prasanthj/hyperloglog
