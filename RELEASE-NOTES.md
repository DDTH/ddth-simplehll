ddth-simplehll release notes
============================

0.1.2 - 2016-10-31
------------------

- Factory class to create `IHLL` objects.
- Move implementations to package `com.github.ddth.hll.impl`.
- Move utility class `HLLUtils` to package `com.github.ddth.hll.utils`.
- Add some unit tests & bug fixes.


0.1.1 - 2016-10-30
------------------

- New class `HLLUtils`: IHLL serialization & deserialization should be performed via `HLLUtils.toBytes(IHLL)` and `HLLUtils.fromBytes(byte[])`


0.1.0 - 2016-10-29
------------------

- First release.
