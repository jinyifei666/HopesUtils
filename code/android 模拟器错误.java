1、找到android模拟器安装目录：C:\Documents and Settings\Administrator\.android\avd\AVD23.avd
2、编辑config.ini文件，就是这块配置错误导致错误产生。
3、如果硬盘空间比较紧张，可以把模拟器文件放到其它盘符上：你可以在命令行下用mkcard创建一个SDCARD文件，如： mksdcard 50M D:\sdcard.img
4、下面代码可以整个覆盖原来的config文件 hw.sdCard=yes hw.lcd.density=240 skin.path=800×480 skin.name=800×480 vm.heapSize=24 sdcard.path=D:\sdcard.img hw.ramSize=512 image.sysdir.1=platforms\android-8\images\
5、OK，模拟器正常运行