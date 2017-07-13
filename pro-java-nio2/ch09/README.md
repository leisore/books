#### 异步通道接口

异步通道接口是NIO.2中引入的一个功能强大的特性。在本章中会看到，异步I/O（AIO）开始于`java.nio.channels.AsynchronousChannel`接口，它继承自通道接口，支持异步I/O操作。有三个类实现了此接口：`AsynchronousFileChannel`、`AsynchronousSocketChannel`和`AsynchronousServerSocketChannel`。其实还有第四个实现类`AsynchronousDatagramChannel`，在Java 7 beta版本被加入，但在Java 7最终发行版中被移除了，在写作本书时，它尚不可用，考虑到在未来的Java 7发行版本中它有可能会被再包含进来，所以本章会简单的涉及到它的内容，以使大家大概明白它的用法。这几个类和NIO.2通道接口的风格类似。另外还有一个异步通道`AsynchronousByteChannel`，它是`AsynchronousChannel`的子接口（`AsynchronousSocketChannel`实现了接口），通过它可以字节的方式读写数据。还有一个类`AsynchronousChannelGroup`，它代表了*异步通道组*的概念，在*异步通道组*中，每个通道都归属于一个的通道组（默认的或者指定的通道组），同一通道组中的所有通道共享一个线程池。线程池中的线程接收指令，然后执行I/O事件，并把结果分发给`CompletionHandler`。

本章我们会从Java的视角来看异步机制。首先会从大的方面来看Java是如何实现异步I/O的，接着会开发几个相关的文件和Socket应用。我们会首先从`AsynchronousFileChannel`开始学习与文件相关的异步I/O操作，然后继续TCP和UDP的异步I/O操作。

但在进入这些特性之前，先简介一下同步I/O和异步I/O的一些区别。