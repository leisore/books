# 问题 #
在Solaris上编译C客户端时，总是出现：

    make: Fatal error in reader: Makefile, line 3: unexpected end of line seen.

OS信息如下：

    SunOS solarisCompile 5.10 Generic_137138-09 i86pc i386 i86pc

# 解决 #
第一反应是makefile是dos格式的，使用工具一看果然是这样，转为Unix格式继续编译，继续出错：

    make: Fatal error in reader: Make.rules, line 3: unexpected end of line seen.

Make.rules文件前几行如下：

    1、[空行]
    2、ArchVal  := $(shell if test -z "${ArchVal}"; then echo $(shell getconf LONG_BIT); fi)
    3、ArchName := $(shell if test ${ArchVal} = 64; then echo 64; else echo 32; fi)
    4、BreakVal := $(shell if test -z "${BreakVal}" || test "${BreakVal}" != n; then echo exit 1; else echo "Continue on error"; fi)
    5、
    6、......

于是从第一行开始删，但是总是出现同样的错误，直到把整个Make.rules文件都删除完了还是出错。直接把Make.rules的内容copp近makefile，还是同样的错误。google了一个多小时也没找到原因。

总感觉是某个符号引起的，于是继续尝试，果然将第二行的[ArchVal  :=]中的冒号取得后，错误不一样了：

    make: Fatal error in reader: Make.rules, line 4: unexpected end of line seen.

将第三行的冒号的删除掉，就说是第5行出错了。原因大致明白了：

    SunOS自带的make（或者是make版本太低）不支持 := 赋值方式

后面继续尝试，还不支持如下语句：

    ifeq

于是想到换个make工具：

    find / -name "*make*"

最后在如下位置找到了gmake：

    /usr/sfw/bin/gmake

版本为3.80

换成gmake后，顺利编译通过。sfw=software?

-----------------
    Date: 2015/01/05
    Tags: Solaris SunOS Make GMake